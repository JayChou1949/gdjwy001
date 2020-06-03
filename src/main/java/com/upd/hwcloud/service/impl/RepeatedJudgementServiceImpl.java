package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.dao.*;
import com.upd.hwcloud.service.RepeatedJudgementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author junglefisher
 * @date 2019/11/21 17:05
 */
@Service
public class RepeatedJudgementServiceImpl implements RepeatedJudgementService {
    @Autowired
    private ServicePublishMapper servicePublishMapper;
    @Autowired
    private ServicePublishApiMapper servicePublishApiMapper;
    @Autowired
    private ApiOperationMapper apiOperationMapper;
    @Autowired
    private ServicePublishApiProductMapper servicePublishApiProductMapper;
    @Autowired
    private ServicePublishBackendMapper servicePublishBackendMapper;

    @Override
    public boolean judgeServiceName(String serviceName) {
        QueryWrapper<ServicePublish> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServicePublish::getServiceName, serviceName);
        List<ServicePublish> servicePublishes = servicePublishMapper.selectList(queryWrapper);
        if (servicePublishes!=null&&servicePublishes.size()!=0){
            return true;
        }
        return false;
    }

    @Override
    public boolean judgeApiName(String apiName) {
        QueryWrapper<ServicePublishApi> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServicePublishApi::getName, apiName);
        List<ServicePublishApi> servicePublishApi = servicePublishApiMapper.selectList(queryWrapper);
        if (servicePublishApi!=null&&servicePublishApi.size()!=0){
            return true;
        }
        return false;
    }


    @Override
    public boolean judgeApiOperationName(String apiOperationName) {
        QueryWrapper<ApiOperation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ApiOperation::getName, apiOperationName);
        List<ApiOperation> apiOperation = apiOperationMapper.selectList(queryWrapper);
        if (apiOperation!=null&&apiOperation.size()!=0){
            return true;
        }
        return false;
    }

    @Override
    public boolean judgeApiOperationPath(String apiOperationPath) {
        QueryWrapper<ApiOperation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ApiOperation::getPath, apiOperationPath);
        List<ApiOperation> apiOperation = apiOperationMapper.selectList(queryWrapper);
        if (apiOperation!=null&&apiOperation.size()!=0){
            return true;
        }
        return false;
    }

    @Override
    public boolean judgeApiProductName(String apiProductName) {
        QueryWrapper<ServicePublishApiProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServicePublishApiProduct::getName, apiProductName);
        List<ServicePublishApiProduct> servicePublishApiProduct = servicePublishApiProductMapper.selectList(queryWrapper);
        if (servicePublishApiProduct!=null&&servicePublishApiProduct.size()!=0){
            return true;
        }
        return false;
    }
}
