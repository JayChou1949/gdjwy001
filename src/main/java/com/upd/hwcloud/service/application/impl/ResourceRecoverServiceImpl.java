package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.entity.application.ResourceRecover;
import com.upd.hwcloud.bean.vo.resourceRecover.ResourceRecoverImport;
import com.upd.hwcloud.bean.vo.resourceRecover.ResourceRecoverResponse;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.dao.application.ResourceRecoverMapper;
import com.upd.hwcloud.service.application.IResourceRecoverService;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

/**
 * <p>
 * 资源回收列表 服务实现类
 * </p>
 *
 * @author yyc
 * @since 2020-05-14
 */
@Service
public class ResourceRecoverServiceImpl extends ServiceImpl<ResourceRecoverMapper, ResourceRecover> implements IResourceRecoverService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceRecoverServiceImpl.class);

    @Autowired
    private ResourceRecoverMapper resourceRecoverMapper;


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void importData(MultipartFile file, String idCard) {
        ImportParams importParams = new ImportParams();

        try {
            List<ResourceRecoverImport> importList = ExcelImportUtil.importExcel(file.getInputStream(),ResourceRecoverImport.class,importParams);
            logger.debug("importList -> {}",importList.size());
            if(CollectionUtils.isNotEmpty(importList)){
                List<ResourceRecover> resourceRecoverList = Lists.newArrayList() ;
                for(ResourceRecoverImport resourceRecoverImport:importList){
                    ResourceRecover resourceRecover = resourceRecoverImport.converterToResourceRecover();
                    resourceRecover.setCreatorIdCard(idCard);
                    resourceRecoverList.add(resourceRecover);
                }
                logger.debug("resourceRecoverList -> {}",resourceRecoverList.size());
                this.saveBatch(resourceRecoverList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("导入数据异常");
        }
    }

    @Override
    public IPage<ResourceRecoverResponse> getGroupPage(IPage<ResourceRecoverResponse> page, Map<String, String> params) {
        return resourceRecoverMapper.groupPage(page,params);
    }
}
