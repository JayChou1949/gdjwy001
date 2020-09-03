package com.hirisun.cloud.order.service.register.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.register.SaasRegister;
import com.hirisun.cloud.order.mapper.register.SaasRegisterMapper;
import com.hirisun.cloud.order.service.register.SaasRegisterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.vo.OrderCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * SAAS服务注册表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-02
 */
@Service
public class SaasRegisterServiceImpl extends RegisterServiceImpl<SaasRegisterMapper, SaasRegister> implements SaasRegisterService {

    @Autowired
    private FilesApi filesApi;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public QueryResponseResult change(UserVO user, SaasRegister info, String oldName) {

        if(StringUtils.isBlank(oldName)){
            return QueryResponseResult.fail("原应用名为空");
        }

        //更新应用注册信息及文件信息
        this.update(user,info);
        SubpageParam fileParam = new SubpageParam();
        fileParam.setFiles(info.getFileList());
        fileParam.setRefId(info.getId());
        filesApi.refFiles(fileParam);
        //更新注册信息对应应用信息 TODO
        try{
//            Saas saas = saasService.getOne(new QueryWrapper<Saas>().lambda().eq(Saas::getName,oldName));
//            Saas updaeSaas = createChangeEntity(saas.getId(),info);
//            saasService.updateById(updaeSaas);
        }catch (Exception e){
            throw  new CustomException(OrderCode.SERVICE_EXCEPTION);
        }
        return QueryResponseResult.success(null);
    }
}
