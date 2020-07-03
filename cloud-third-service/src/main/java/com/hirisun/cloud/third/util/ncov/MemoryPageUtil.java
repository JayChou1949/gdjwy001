package com.hirisun.cloud.third.util.ncov;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.hirisun.cloud.model.ncov.vo.daas.NcovExcelSheetOneVo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author wuc
 * @date 2020/2/23
 */
public class MemoryPageUtil {

    public static Page<NcovExcelSheetOneVo> ncovPageByDeptTypeAndName(List<NcovExcelSheetOneVo> fullList, Integer pageSize, Integer pageNum, String dept, String type, String name){

        Page<NcovExcelSheetOneVo> page = new Page<>();
        List<NcovExcelSheetOneVo> filterList = Lists.newArrayList();
        if(StringUtils.isBlank(name)){
          filterList= fullList.stream().filter(item-> StringUtils.equals(type,item.getElementType())&& StringUtils.equals(dept,item.getDepartmentOfData())).collect(Collectors.toList());

        }else {
            Pattern pattern = Pattern.compile(name.trim());
            filterList= fullList.stream().filter(item-> StringUtils.equals(type,item.getElementType())&& StringUtils.equals(dept,item.getDepartmentOfData())&&pattern.matcher(item.getTableCnName()).find()).collect(Collectors.toList());
        }
        List<NcovExcelSheetOneVo> records = filterList.stream()
                .skip(pageSize*(pageNum-1))
                .limit(pageSize).collect(Collectors.toList());
        page.setTotal(filterList.size());
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(records);
        Double pages = Math.ceil(Integer.valueOf(filterList.size()).doubleValue()/Integer.valueOf(pageSize).doubleValue());
        page.setPages(pages.longValue());
        return  page;
    }

    public static Page<NcovExcelSheetOneVo> ncovPageByName(List<NcovExcelSheetOneVo> fullList, Integer pageSize, Integer pageNum, String name){
        Page<NcovExcelSheetOneVo> page = new Page<>();
        List<NcovExcelSheetOneVo> filterList = Lists.newArrayList();
        if(StringUtils.isBlank(name)){
            filterList= fullList;

        }else {
            Pattern pattern = Pattern.compile(name.trim());
            filterList= fullList.stream().filter(item->pattern.matcher(item.getTableCnName()).find()).collect(Collectors.toList());
        }
        List<NcovExcelSheetOneVo> records = filterList.stream()
                .skip(pageSize*(pageNum-1))
                .limit(pageSize).collect(Collectors.toList());
        page.setTotal(filterList.size());
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(records);
        Double pages = Math.ceil(Integer.valueOf(filterList.size()).doubleValue()/Integer.valueOf(pageSize).doubleValue());
        page.setPages(pages.longValue());
        return  page;
    }


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
