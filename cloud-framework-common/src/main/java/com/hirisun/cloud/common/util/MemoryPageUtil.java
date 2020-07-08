package com.hirisun.cloud.common.util;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author wuc
 * @date 2020/2/23
 */
public class MemoryPageUtil {

    /**
     * 泛型内存分页
     * @param fullList
     * @param pageSize
     * @param pageNum
     * @param <T>
     * @return
     */
    public static<T> Page<T> page(List<T> fullList, long pageSize, long pageNum){
        Page<T> page = new Page<>();
        List<T> records = fullList.stream()
                .skip(pageSize*(pageNum-1))
                .limit(pageSize).collect(Collectors.toList());
        page.setTotal(fullList.size());
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(records);
        Double pages = Math.ceil(Integer.valueOf(fullList.size()).doubleValue()/Long.valueOf(pageSize).doubleValue());
        page.setPages(pages.longValue());
        return  page;
    }

}
