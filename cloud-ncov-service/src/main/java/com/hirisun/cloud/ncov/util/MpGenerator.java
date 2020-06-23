package com.hirisun.cloud.ncov.util;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;

import com.baomidou.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 *
 * @author d
 * @ClassName: CodeGeneration
 * @Description: 代码生成，需要逆向工程生成实体类时，运行此程序
 */
public class MpGenerator {

    /**
     *
     * @Title: main
     * @Description: 生成
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //输出文件路径
        gc.setOutputDir("E://generator");//生成的所有类放在此处
        gc.setFileOverride(true);
        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columnList
        gc.setBaseColumnList(false);
        gc.setAuthor("dps");
        gc.setSwagger2(true);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.ORACLE);
        dsc.setDriverName("oracle.jdbc.driver.OracleDriver");
        dsc.setUsername("gd_jwymh");
        dsc.setPassword("gdjwymh");
        dsc.setUrl("jdbc:oracle:thin:@139.9.242.131:1521/jwymh");
//        dsc.setUsername("CLOUD_USER");
//        dsc.setPassword("CLOUD_USER");
//        dsc.setUrl("jdbc:oracle:thin:@139.9.245.85:1521/orcl");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略
        strategy.setTablePrefix(new String[]{"t_"});//去掉表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategy.setInclude("T_NCOV_REALTIME");
//        strategy.setInclude("TB_WFM_WORKFLOW");
//        strategy.setSuperServiceClass(null);
//        strategy.setSuperServiceImplClass(null);
//        strategy.setSuperMapperClass(null);

        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.hirisun.cloud.ncov");
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setEntity("bean");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        // 执行生成
        mpg.execute();

    }
}