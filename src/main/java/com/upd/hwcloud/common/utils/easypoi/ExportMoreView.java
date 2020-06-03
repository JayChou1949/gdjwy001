package com.upd.hwcloud.common.utils.easypoi;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author wuc
 * @date 2019/7/25
 */
public class ExportMoreView {

    private List<ExportView> moreViewList = Lists.newArrayList();

    public List<ExportView> getMoreViewList() {
        return moreViewList;
    }

    public void setMoreViewList(List<ExportView> moreViewList) {
        this.moreViewList = moreViewList;
    }

}