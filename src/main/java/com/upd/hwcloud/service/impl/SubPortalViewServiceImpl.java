package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.SubPortalView;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.dao.SubPortalViewMapper;
import com.upd.hwcloud.service.ISubPortalViewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 二级门户浏览量 服务实现类
 * </p>
 *
 * @author yyc
 * @since 2019-12-20
 */
@Service
public class SubPortalViewServiceImpl extends ServiceImpl<SubPortalViewMapper, SubPortalView> implements ISubPortalViewService {

    @Autowired
    private SubPortalViewMapper subPortalViewMapper;

    @Override
    public R incrementCount(String type, String name) {


        Integer typeCode = getTypeCode(type);

        int count = this.count(new QueryWrapper<SubPortalView>().lambda().eq(SubPortalView::getType,typeCode).eq(SubPortalView::getName,name));
        if(count == 0){
            SubPortalView subPortalView = new SubPortalView();
            subPortalView.setType(typeCode);
            subPortalView.setName(name);
            subPortalView.setViewCount(1L);
            subPortalView.insert();
            return R.ok();

        }else{
            int resCount = subPortalViewMapper.incrementCount(name,typeCode);
            if(resCount > 0){
                return R.ok();
            }
        }
        return R.error("update failed");

    }


    @Override
    public R getViewCount(String type, String name) {

        Integer typeCode = getTypeCode(type);
        SubPortalView subPortalView = this.getOne(new QueryWrapper<SubPortalView>().lambda().eq(SubPortalView::getType,typeCode));
        return R.ok(subPortalView);
    }

    private Integer getTypeCode(String type){
        if("police".equals(type)){
            return 0;
        }else if("area".equals(type)){
            return 1;
        }
        throw new BaseException("illegal param:"+type);

    }
}
