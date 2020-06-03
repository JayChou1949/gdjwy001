package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.entity.application.IaasTxyfwImpl;
import com.upd.hwcloud.common.utils.BigDecimalUtil;
import com.upd.hwcloud.dao.application.IaasTxyfwImplMapper;
import com.upd.hwcloud.service.application.IIaasTxyfwImplService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>
 * 弹性云服务实施信息表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-12-04
 */
@Service
public class IaasTxyfwImplServiceImpl extends ServiceImpl<IaasTxyfwImplMapper, IaasTxyfwImpl> implements IIaasTxyfwImplService {

    @Autowired
    private IaasTxyfwImplMapper iaasTxyfwImplMapper;

    @Override
    public List<IaasTxyfwImpl> getImplServerListByAppInfoId(String id) {
        List<IaasTxyfwImpl> list = this.list(new QueryWrapper<IaasTxyfwImpl>().lambda()
                .eq(IaasTxyfwImpl::getAppInfoId, id)
                .orderByAsc(IaasTxyfwImpl::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<IaasTxyfwImpl> serverList) {
        this.remove(new QueryWrapper<IaasTxyfwImpl>().lambda()
                .eq(IaasTxyfwImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfwImpl txyfwImpl : serverList) {
                txyfwImpl.setId(null);
                txyfwImpl.setAppInfoId(appInfoId);
                this.save(txyfwImpl);
            }
        }
    }

    @Override
    public Map<String,Object> ncovOverview(String startTime) {

        List<IaasTxyfwImpl> iaasTxyfwList = iaasTxyfwImplMapper.ncovEcsList(startTime);

        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        //台数
        Long  ecsNum = iaasTxyfwList.stream().count();
        //cpu
        long cpuNum = 0L;
        //内存
        long memoryNum = 0L;
        //存储
        double storageNum = 0d;
        //统计CPU 和 内存
        for (IaasTxyfwImpl iaasTxyfw : iaasTxyfwList) {
            if(StringUtils.isNotBlank(iaasTxyfw.getSpecification())){
                String[] sp = iaasTxyfw.getSpecification()
                        .split("/");
                if(pattern.matcher(sp[0]).matches()){
                    cpuNum = Long.valueOf(sp[0]) +cpuNum;
                }
                if(pattern.matcher(sp[1]).matches()){
                    memoryNum = Long.valueOf(sp[1])+memoryNum;
                }
                storageNum = Double.valueOf(iaasTxyfw.getStorage())+storageNum;
            }
        }
        Map<String,Object> res = Maps.newHashMapWithExpectedSize(7);
        res.put("ecsNum",ecsNum);
        res.put("cpuNum",cpuNum);
        res.put("memoryNum",memoryNum);
        res.put("storageNum",BigDecimalUtil.div(storageNum,1024).doubleValue());
        return res;
    }
}
