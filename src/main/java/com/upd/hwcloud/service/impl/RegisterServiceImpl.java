package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.RedisKey;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.Register;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.dao.RegisterMapper;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IRegisterService;
import com.upd.hwcloud.service.IUserService;
import com.upd.hwcloud.service.application.IAppReviewInfoService;
import com.upd.hwcloud.service.application.ISpeedUpService;
import com.upd.hwcloud.service.workbench.impl.CommonHandler;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务注册表 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-05-27
 */
public class RegisterServiceImpl<M extends RegisterMapper<T>, T extends Register> extends ServiceImpl<M, T> implements IRegisterService<T> {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IAppReviewInfoService appReviewInfoService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IFilesService filesService;
    @Autowired
    private ISpeedUpService speedUpService;

    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);
   
    @Override
    @Transactional
    public void save(User user, T info) {
        info.setCreator(user.getIdcard());
        info.setCreatorName(user.getName());
        info.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());

        String orderNum = this.genOrderNum();
        info.setOrderNumber(orderNum);
        this.save(info);
        // 关联文件信息
        filesService.refFiles(info.getFileList(), info.getId());
    }



    @Override
    public T getDetails(String id) {

        T info = baseMapper.getDetails(id);
        if (null ==info) return null;
        info.setUser(userService.findUserByIdcard(info.getCreator()));
        List<Files> filesList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, id));
        info.setFileList(filesList);
        List<AppReviewInfo> allReviewInfo = appReviewInfoService.getAllReviewInfoByAppInfoId(id);
        // 审核信息
        info.setReviewList(allReviewInfo);
        return info;
    }

    /**
     * Paas注册未使用
     * @param user
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<T> getPage(User user, IPage<T> page,  Map<String, Object> param ) {

        page = baseMapper.getPage(page, param);
        List<T> records = page.getRecords();
        logger.debug("records -> {}",records);
        if (records != null && !records.isEmpty()) {
            speedUpService.dealProcessingPersonRegister(records,user);
        }
        return page;
    }

    @Override
    public IPage<T> getResponsePage(IPage<T> page, User user, String appName) {
        return baseMapper.getResponsePage(page,user.getName(),user.getOrgName(),user.getIdcard(),appName,user.getMobileWork());
    }

    @Transactional
    @Override
    public void update(User user, T info) {
        this.updateById(info);
        // 关联文件信息
        filesService.refFiles(info.getFileList(), info.getId());
    }


    @Override
    public void saveService(T register) {

    }

    @Override
    public int getTodoCount(User user) {
        return baseMapper.getTodoCount(user);
    }

    @Override
    public int getRegisterTodo(String idCard) {
        return 0;
    }

    private String genOrderNum() {
        // 生成单号
        String yyyyMMdd = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String redisKey = RedisKey.KEY_SAAS_PREFIX + yyyyMMdd;
        Long increment = stringRedisTemplate.opsForValue().increment(redisKey, 1L);
        if (increment == null) {
            throw new BaseException("申请单号生成错误,请重试!");
        }
        // 过期时间1天
        stringRedisTemplate.expire(redisKey, 1L, TimeUnit.DAYS);
        return String.format("%s%04d", yyyyMMdd, increment);
    }
}
