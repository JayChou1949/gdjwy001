package com.upd.hwcloud.service.application.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.dto.ImportSaasAppUseStaticstics;
import com.upd.hwcloud.bean.entity.application.ResourceRecover;
import com.upd.hwcloud.bean.entity.application.SassAppUseStatistics;
import com.upd.hwcloud.bean.vo.resourceRecover.ResourceRecoverImport;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.dao.application.SassAppUseStatisticsMapper;
import com.upd.hwcloud.service.application.SassAppUseStatisticsService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用访问统计表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-06-29
 */
@Service
public class SassAppUseStatisticsServiceImpl extends ServiceImpl<SassAppUseStatisticsMapper, SassAppUseStatistics> implements SassAppUseStatisticsService {

    @Autowired
    private SassAppUseStatisticsMapper sassAppUseStatisticsMapper;
    private static final Logger logger = LoggerFactory.getLogger(SassAppUseStatisticsServiceImpl.class);

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void importData(MultipartFile file, String idCard) {
        ImportParams importParams = new ImportParams();

        try {
            List<ImportSaasAppUseStaticstics> importList = ExcelImportUtil.importExcel(file.getInputStream(),ImportSaasAppUseStaticstics.class,importParams);
            logger.debug("importList -> {}",importList.size());
            if(CollectionUtils.isNotEmpty(importList)){
                List<SassAppUseStatistics> list = Lists.newArrayList() ;
                for(ImportSaasAppUseStaticstics importSaasAppUseStaticstics:importList){
                    SassAppUseStatistics sassAppUseStatistics = importSaasAppUseStaticstics.converterToSassAppUseStatisticsService();
                    list.add(sassAppUseStatistics);
                }
                logger.debug("resourceRecoverList -> {}",list.size());
                this.saveBatch(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("导入数据异常");
        }
    }

    @Override
    public List<Map> queryUseCount(Map map) {
        return sassAppUseStatisticsMapper.queryUseCount(map);
    }

    @Override
    public List<Map> queryAreaUseCount(Map map) {
        return sassAppUseStatisticsMapper.queryAreaUseCount(map);
    }

    @Override
    public List<Map> queryPoliceUseCount(Map map) {
        return sassAppUseStatisticsMapper.queryPoliceUseCount(map);
    }

    @Override
    public List<Map> queryAppUseCount(Map map) {
        return sassAppUseStatisticsMapper.queryAppUseCount(map);
    }

    @Override
    public List<Map> queryAreaUserCount(Map map) {
        return sassAppUseStatisticsMapper.queryAreaUserCount(map);
    }

    @Override
    public List<Map> queryPoliceUserCount(Map map) {
        return sassAppUseStatisticsMapper.queryPoliceUserCount(map);
    }

    @Override
    public Integer countByAppAuth(Map param) {
        return sassAppUseStatisticsMapper.countByAppAuth(param);
    }

    @Override
    public List<Map> queryAreaUserAvgCount(Map param) {
        return sassAppUseStatisticsMapper.queryAreaUserAvgCount(param);
    }

    @Override
    public List<Map> queryPoliceUserAvgCount(Map param) {
        return sassAppUseStatisticsMapper.queryPoliceUserAvgCount(param);
    }
    @Override
    public Long allCount(Map param) {
        return sassAppUseStatisticsMapper.allCount(param);
    }
}
