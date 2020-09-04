package com.hirisun.cloud.order.service.recover.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.hirisun.cloud.common.enumer.ResourceRecoverStatus;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.DateUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.model.system.ResourceRecoverImportVo;
import com.hirisun.cloud.model.system.ResourceRecoverResponseVo;
import com.hirisun.cloud.order.bean.recover.ResourceRecover;
import com.hirisun.cloud.order.mapper.recover.ResourceRecoverMapper;
import com.hirisun.cloud.order.service.recover.IResourceRecoverService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资源回收列表 服务实现类
 */
@Service
public class ResourceRecoverServiceImpl extends ServiceImpl<ResourceRecoverMapper,
        ResourceRecover> implements IResourceRecoverService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceRecoverServiceImpl.class);

    @Autowired
    private ResourceRecoverMapper resourceRecoverMapper;


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void importData(MultipartFile file, String idCard) {
        ImportParams importParams = new ImportParams();

        try {
            List<ResourceRecoverImportVo> importList = ExcelImportUtil.importExcel(file.getInputStream(),ResourceRecoverImportVo.class,importParams);
            logger.debug("importList -> {}",importList.size());
            if(CollectionUtils.isNotEmpty(importList)){
                List<ResourceRecover> resourceRecoverList = Lists.newArrayList() ;
                for(ResourceRecoverImportVo resourceRecoverImport:importList){
                	
                	ResourceRecover resourceRecover = new ResourceRecover();
                    BeanUtils.copyProperties(resourceRecoverImport,resourceRecover);
                    Date date = new Date();
                    resourceRecover.setImportTime(date);
                    resourceRecover.setInstanceCreatedTime(DateUtil.formateDate(resourceRecoverImport.getInstanceCreatedDate(),"yyyy-MM-dd"));
                    resourceRecover.setImportTimeStr(DateUtil.formateDate(date,"yyyy-MM-dd HH:mm"));
                    resourceRecover.setStatus(ResourceRecoverStatus.UN_PROCESSED.getCode());
                    resourceRecover.setShrinkTime(DateUtil.formateDate(resourceRecoverImport.getShrinkDate(),"yyyy-MM-dd HH:mm"));
                    resourceRecover.setCreatorIdCard(idCard);
                    resourceRecoverList.add(resourceRecover);
                }
                logger.debug("resourceRecoverList -> {}",resourceRecoverList.size());
                this.saveBatch(resourceRecoverList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(CommonCode.IMPORT_ERROR);
        }
    }

    @Override
    public IPage<ResourceRecoverResponseVo> getGroupPage(IPage<ResourceRecoverResponseVo> page, 
    		Map<String, String> params) {
        return resourceRecoverMapper.groupPage(page,params);
    }

    @Transactional(rollbackFor = Throwable.class)
	public void delete(String applicant, String phone, String importTime) {
		
		this.remove(new QueryWrapper<ResourceRecover>().lambda()
                .eq(ResourceRecover::getApplicant,applicant)
                .eq(ResourceRecover::getApplicantPhone,phone)
                .eq(ResourceRecover::getImportTimeStr,importTime));
		
	}
}
