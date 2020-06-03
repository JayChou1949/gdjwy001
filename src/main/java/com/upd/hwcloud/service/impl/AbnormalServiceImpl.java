package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Abnormal;
import com.upd.hwcloud.bean.entity.Monitor;
import com.upd.hwcloud.dao.AbnormalMapper;
import com.upd.hwcloud.request.AbnormalPageRequest;
import com.upd.hwcloud.service.IAbnormalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huru
 * @since 2019-04-03
 */
@Service
public class AbnormalServiceImpl extends ServiceImpl<AbnormalMapper, Abnormal> implements IAbnormalService {

    @Autowired
    private AbnormalMapper abnormalMapper;

    @Override
    public Page<Abnormal> getPage(AbnormalPageRequest abnormalPageRequest) {
        Page<Abnormal> page = new Page<>();
        String keyWords = abnormalPageRequest.getKeywords();
        if(abnormalPageRequest.getPageNum() == null || abnormalPageRequest.getPageNum() <= 0) {
            abnormalPageRequest.setPageNum(1);
        }
        if(abnormalPageRequest.getPageSize() == null || abnormalPageRequest.getPageSize() <= 0) {
            abnormalPageRequest.setPageSize(20);
        }
        page.setCurrent(abnormalPageRequest.getPageNum());
        page.setSize(abnormalPageRequest.getPageSize());
        return abnormalMapper.getPage(page,keyWords);
    }
}
