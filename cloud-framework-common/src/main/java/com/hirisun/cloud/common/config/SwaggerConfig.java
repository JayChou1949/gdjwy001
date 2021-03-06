package com.hirisun.cloud.common.config;

import com.battcn.boot.swagger.properties.SwaggerProperties;
import com.hirisun.cloud.common.annotation.LoginUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import com.battcn.boot.swagger.configuration.SwaggerBeanValidatorPluginsConfiguration;
import com.battcn.boot.swagger.configuration.SwaggerSecurityFilterPluginsConfiguration;
import com.battcn.boot.swagger.properties.SwaggerSecurityProperties;
import com.battcn.boot.swagger.security.GlobalAccess;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Import;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.util.*;
import java.util.stream.Collectors;
import static com.google.common.base.Predicates.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @Author wuxiaoxing
 * 2020-07-25
 * swagger-ui??????
 * ????????????swagger-ui??????????????????
 */
@Configuration
@ConditionalOnProperty(name = "spring.swagger.enabled", havingValue = "true", matchIfMissing = true)
@Import({Swagger2DocumentationConfiguration.class, SwaggerBeanValidatorPluginsConfiguration.class})
@EnableConfigurationProperties({SwaggerProperties.class, SwaggerSecurityProperties.class})
public class SwaggerConfig implements BeanFactoryAware{

    private static final String DEFAULT_GROUP_NAME = "default";
    private static final String BASE_PATH = "/**";

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Bean
    @ConditionalOnProperty(name = {"spring.swagger.enabled"}, havingValue = "true")
    @ConditionalOnExpression("'${spring.swagger.security.filter-plugin}'=='true' || '${spring.swagger.security.filterPlugin}'=='true'")
    public FilterRegistrationBean<SwaggerSecurityFilterPluginsConfiguration> someFilterRegistration() {
        FilterRegistrationBean<SwaggerSecurityFilterPluginsConfiguration> registration = new FilterRegistrationBean<>();
        registration.setFilter(new SwaggerSecurityFilterPluginsConfiguration());
        registration.addUrlPatterns("/v2/api-docs", "/swagger-resources");
        return registration;
    }


    @Bean
    @ConditionalOnMissingBean
    public GlobalAccess globalAccess(SwaggerProperties swaggerProperties) {
        return new GlobalAccess(swaggerProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public List<Docket> createRestApi(SwaggerProperties swaggerProperties, GlobalAccess globalAccess) {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        List<Docket> docketList = new LinkedList<>();

        // ????????????
        if (swaggerProperties.getGroups().size() == 0) {
            ApiInfo apiInfo = new ApiInfoBuilder()
                    .title(swaggerProperties.getTitle())
                    .description(swaggerProperties.getDescription())
                    .version(swaggerProperties.getVersion())
                    .license(swaggerProperties.getLicense())
                    .licenseUrl(swaggerProperties.getLicenseUrl())
                    .contact(new Contact(swaggerProperties.getContact().getName(),
                            swaggerProperties.getContact().getUrl(),
                            swaggerProperties.getContact().getEmail()))
                    .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                    .build();

            // base-path??????
            // ?????????????????????path??????????????????/**
            if (swaggerProperties.getBasePath().isEmpty()) {
                swaggerProperties.getBasePath().add(BASE_PATH);
            }

            List<Predicate<String>> basePath = swaggerProperties.getBasePath().stream().map(PathSelectors::ant).collect(toList());

            // exclude-path ??????
            List<Predicate<String>> excludePath = Lists.newArrayList();
            for (String path : swaggerProperties.getExcludePath()) {
                excludePath.add(PathSelectors.ant(path));
            }

            Docket docket = new Docket(DocumentationType.SWAGGER_2)
                    .host(swaggerProperties.getHost())
                    .apiInfo(apiInfo)
                    .globalOperationParameters(buildGlobalOperationParameters(swaggerProperties.getGlobalOperationParameters()))
                    .select()
                    .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                    .paths(and(not(or(excludePath)), or(basePath)))
                    .build().ignoredParameterTypes(LoginUser.class);
            // ??????????????????
            if (!CollectionUtils.isEmpty(swaggerProperties.getGlobalResponseMessages())) {
                buildGlobalResponseMessage(swaggerProperties, docket);
            }
            // ??????????????????
            if (Objects.nonNull(swaggerProperties.getApiKey())) {
                docket.securitySchemes(newArrayList(globalAccess.apiKey())).securityContexts(newArrayList(globalAccess.securityContext()));
            }
            configurableBeanFactory.registerSingleton(DEFAULT_GROUP_NAME, docket);
            docketList.add(docket);
            return docketList;
        }

        // ????????????
        for (String groupName : swaggerProperties.getGroups().keySet()) {
            SwaggerProperties.GroupInfo groupInfo = swaggerProperties.getGroups().get(groupName);
            SwaggerProperties.Contact contact = groupInfo.getContact();
            ApiInfo apiInfo = new ApiInfoBuilder()
                    .title(defaultString(groupInfo.getTitle(), swaggerProperties.getTitle()))
                    .description(defaultString(groupInfo.getDescription(), swaggerProperties.getDescription()))
                    .version(defaultString(groupInfo.getVersion(), swaggerProperties.getVersion()))
                    .license(defaultString(groupInfo.getLicense(), swaggerProperties.getLicense()))
                    .licenseUrl(defaultString(groupInfo.getLicenseUrl(), swaggerProperties.getLicenseUrl()))
                    .contact(new Contact(
                            defaultString(contact.getName(), swaggerProperties.getContact().getName()),
                            defaultString(contact.getUrl(), swaggerProperties.getContact().getUrl()),
                            defaultString(contact.getEmail(), swaggerProperties.getContact().getEmail())))
                    .termsOfServiceUrl(defaultString(groupInfo.getTermsOfServiceUrl(), swaggerProperties.getTermsOfServiceUrl()))
                    .build();

            // base-path??????
            // ?????????????????????path??????????????????/**
            if (groupInfo.getBasePath().isEmpty()) {
                groupInfo.getBasePath().add(BASE_PATH);
            }

            List<Predicate<String>> basePath = groupInfo.getBasePath().stream().map(PathSelectors::ant).collect(toList());
            // exclude-path??????
            List<Predicate<String>> excludePath = Lists.newArrayList();
            for (String path : groupInfo.getExcludePath()) {
                excludePath.add(PathSelectors.ant(path));
            }

            Docket docket = new Docket(DocumentationType.SWAGGER_2)
                    .host(swaggerProperties.getHost())
                    .apiInfo(apiInfo)
                    .globalOperationParameters(assemblyGlobalOperationParameters(swaggerProperties.getGlobalOperationParameters(), groupInfo.getGlobalOperationParameters()))
                    .groupName(groupName)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage(groupInfo.getBasePackage()))
                    .paths(and(not(or(excludePath)), or(basePath)))
                    .build();
            // ??????????????????
            if (!CollectionUtils.isEmpty(swaggerProperties.getGlobalResponseMessages())) {
                buildGlobalResponseMessage(swaggerProperties, docket);
            }
            // ??????????????????
            if (Objects.nonNull(swaggerProperties.getApiKey())) {
                docket.securitySchemes(newArrayList(globalAccess.apiKey())).securityContexts(newArrayList(globalAccess.securityContext()));
            }
            configurableBeanFactory.registerSingleton(groupName, docket);
            docketList.add(docket);
        }
        return docketList;
    }

    /**
     * ?????? SwaggerProperties ??????,????????????????????????
     *
     * @param swaggerProperties swaggerProperties
     * @param docket            docket ????????????
     */
    private void buildGlobalResponseMessage(SwaggerProperties swaggerProperties, Docket docket) {
        final Map<RequestMethod, List<SwaggerProperties.ResponseMessageBody>> responseMessages = swaggerProperties.getGlobalResponseMessages();
        final Set<RequestMethod> methods = responseMessages.keySet();
        for (RequestMethod method : methods) {
            final List<SwaggerProperties.ResponseMessageBody> responseMessageBodies = responseMessages.get(method);
            final List<ResponseMessage> messages = responseMessageBodies.stream().map(my -> {
                ResponseMessageBuilder builder = new ResponseMessageBuilder().code(my.getCode()).message(my.getMessage());
                if (!StringUtils.isEmpty(my.getModelRef())) {
                    builder.responseModel(new ModelRef(my.getModelRef()));
                }
                return builder.build();
            }).collect(toList());
            docket.globalResponseMessage(method, messages);
        }
    }


    /**
     * ?????? SwaggerProperties ??????,????????????????????????
     *
     * @param globalOperationParameters ????????????
     * @return ?????????
     */
    private List<Parameter> buildGlobalOperationParameters(List<SwaggerProperties.GlobalOperationParameter> globalOperationParameters) {
        if (Objects.isNull(globalOperationParameters)) {
            return Lists.newArrayList();
        }
        return globalOperationParameters.stream().map(globalOperationParameter -> new ParameterBuilder()
                .name(globalOperationParameter.getName())
                .description(globalOperationParameter.getDescription())
                .modelRef(new ModelRef(globalOperationParameter.getModelRef()))
                .parameterType(globalOperationParameter.getParameterType())
                .required(globalOperationParameter.getRequired())
                .build()).collect(toList());
    }

    /**
     * ??????????????????name??????????????????
     *
     * @param globalOperationParameters ????????????
     * @param groupOperationParameters  ????????????
     * @return ?????????
     */
    private List<Parameter> assemblyGlobalOperationParameters(
            List<SwaggerProperties.GlobalOperationParameter> globalOperationParameters,
            List<SwaggerProperties.GlobalOperationParameter> groupOperationParameters) {
        if (CollectionUtils.isEmpty(groupOperationParameters)) {
            return buildGlobalOperationParameters(globalOperationParameters);
        }
        if (CollectionUtils.isEmpty(globalOperationParameters)) {
            return buildGlobalOperationParameters(groupOperationParameters);
        }
        Set<String> groupNames = groupOperationParameters.stream().map(SwaggerProperties.GlobalOperationParameter::getName).collect(toSet());
        List<SwaggerProperties.GlobalOperationParameter> resultOperationParameters =
                globalOperationParameters.stream().filter(parameter -> !groupNames.contains(parameter.getName())).collect(Collectors.toList());
        resultOperationParameters.addAll(groupOperationParameters);
        return buildGlobalOperationParameters(resultOperationParameters);
    }

    private static String defaultString(final String str, final String defaultStr) {
        return str == null || Objects.equals(str.trim(), "") || str.length() == 0 ? defaultStr : str;
    }

}
