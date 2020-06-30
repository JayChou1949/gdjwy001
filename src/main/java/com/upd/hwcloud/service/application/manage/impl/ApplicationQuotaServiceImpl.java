package com.upd.hwcloud.service.application.manage.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.contains.RedisKey;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationQuota;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.dao.FilesMapper;
import com.upd.hwcloud.dao.application.manage.ApplicationQuotaMapper;
import com.upd.hwcloud.service.application.manage.IApplicationQuotaService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lqm
 * @since 2020-06-29
 */
@Service
public class ApplicationQuotaServiceImpl extends ServiceImpl<ApplicationQuotaMapper, ApplicationQuota> implements IApplicationQuotaService {


    @Autowired
    private ApplicationQuotaMapper applicationQuotaMapper;
    @Autowired
    private FilesMapper filesMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addApplicationQuota(ApplicationQuota applicationQuota) {

        applicationQuota.setId(UUIDUtil.getUUID());
        applicationQuota.setRefFilesId(UUIDUtil.getUUID());
        //生成申请单号  根据年月日来生成
        applicationQuota.setApplyNumber(genOrderNum());
        applicationQuota.insert();
        List<Files> filesList = applicationQuota.getFilesList();
        if(filesList!=null&&filesList.size()>0){
            for (Files files : filesList) {
                files.setId(UUIDUtil.getUUID());
                files.setRefId(applicationQuota.getRefFilesId());
                files.insert();
            }

        }

    }

    @Override
    public Page<ApplicationQuota> getApplicationQuotaList(String applyPerson) {
        return applicationQuotaMapper.getApplicationQuotaList(applyPerson);
    }

    @Override
    public ApplicationQuota getApplicationQuotaById(String id) {
        ApplicationQuota applicationQuota = applicationQuotaMapper.getApplicationQuotaById(id);
        if(null!=applicationQuota){
            List<Files> fileList = filesMapper.getFileListById(applicationQuota.getRefFilesId());
            applicationQuota.setFilesList(fileList);
        }
        return applicationQuota;
    }
    private String genOrderNum() {
        // 生成单号
        String yyyyMMdd = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String redisKey = RedisKey.KEY_IAAS_PREFIX + yyyyMMdd;
        Long increment = stringRedisTemplate.opsForValue().increment(redisKey, 1L);
        if (increment == null) {
            throw new BaseException("申请单号生成错误,请重试!");
        }
        // 过期时间1天
        stringRedisTemplate.expire(redisKey, 1L, TimeUnit.DAYS);
        return String.format("%s%04d", yyyyMMdd, increment);
    }
}
