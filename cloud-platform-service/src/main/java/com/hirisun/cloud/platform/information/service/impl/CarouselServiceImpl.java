package com.hirisun.cloud.platform.information.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.api.file.FileApi;
import com.hirisun.cloud.common.contains.ReviewStatus;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.file.FileSystemVO;
import com.hirisun.cloud.platform.document.bean.DevDoc;
import com.hirisun.cloud.platform.information.bean.Carousel;
import com.hirisun.cloud.platform.information.mapper.CarouselMapper;
import com.hirisun.cloud.platform.information.service.CarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 轮播图 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-15
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

    @Autowired
    private FileApi fileApi;

    /**
     * 上/下移动轮播图位置
     */
    @Override
    public QueryResponseResult<Carousel> movePosition(String type, String id, Integer provincial, String belong) {
        List<Carousel> carouselList=listCarouselByProvincial(provincial,belong);

        //sortNum值不唯一
        if(carouselList.stream().map(Carousel::getSortNum).distinct().count()<carouselList.size()){
            //重排序使sort值唯一有序
            reSortOrderly(carouselList);
        }

        Carousel targetCarousel = new Carousel();
        int count = 0;

        //遍历寻找目标元素，并记录下标
        for(int i=0;i<carouselList.size();i++){
            if(StringUtils.equals(carouselList.get(i).getId(),id)){
                targetCarousel = carouselList.get(i);
                count=i;
                break;
            }
        }

        //上移操作
        if(StringUtils.equals(type,"up")){
            if(count == 0){
                return  QueryResponseResult.fail("无效上移操作");
            }
            //取前一个元素,交换sortnum
            Carousel pre = carouselList.get(count-1);
            swapCarouselSortNum(targetCarousel,pre);
        }else if( StringUtils.equals(type,"down")){ //下移操作
            if(count == carouselList.size()-1){
                return QueryResponseResult.fail("无效下移操作");
            }
            //取后一个元素
            Carousel next = carouselList.get(count + 1);
            swapCarouselSortNum(targetCarousel,next);
        }
        return QueryResponseResult.success("操作成功");
    }

    @Override
    public List<Carousel> allList(Integer type, String belong) {
        //列表不查询新闻详情
        LambdaQueryWrapper<Carousel> wrapper=new QueryWrapper<Carousel>().lambda()
                .select(Carousel.class, info -> !info.getColumn().equals("CONTENT"));
        switch(type){
            case 1:
                break;
            case 2:
                wrapper.like(Carousel::getArea,belong);
                break;
            case 3:
                wrapper.like(Carousel::getPoliceCategory,belong);
                break;
            case 4:
                wrapper.like(Carousel::getProject, belong);
                break;
            default:
                break;
        }
        wrapper.eq(Carousel::getStatus, ReviewStatus.ONLINE.getCode());
        wrapper.eq(Carousel::getProvincial, type);
        wrapper.orderByAsc(Carousel::getSortNum);
        wrapper.orderByDesc(Carousel::getUpdateTime);
        List<Carousel> carouselList=this.list(wrapper);
        // 循环查出实际图片路径
        List<String> imageIds = carouselList.stream().map(Carousel::getImageId).distinct().collect(Collectors.toList());
        String fileStr = fileApi.getFileByIds(imageIds);
        List<FileSystemVO> fileList = new ArrayList<>();
        if (!StringUtils.isEmpty(fileStr)) {
            fileList = JSON.parseArray(fileStr, FileSystemVO.class);
        }
        for (Carousel carousel : carouselList) {
            for (FileSystemVO fileSystemVO : fileList) {
                if (carousel.getImageId().equals(fileSystemVO.getId())) {
                    carousel.setRealUrl(fileSystemVO.getFilePath());
                    break;
                }
            }
        }
        return carouselList;
    }

    @Override
    public Page<Carousel> getPage(Integer pageNum, Integer pageSize,Integer status,Integer type,String belong,String title) {
        //列表不查询轮播图详情
        LambdaQueryWrapper<Carousel> wrapper=new QueryWrapper<Carousel>().lambda()
                .select(Carousel.class, info -> !info.getColumn().equals("CONTENT"));
        switch(type){
            case 1:
                break;
            case 2:
                wrapper.like(Carousel::getArea,belong);
                break;
            case 3:
                wrapper.like(Carousel::getPoliceCategory,belong);
                break;
            case 4:
                wrapper.like(Carousel::getProject, belong);
                break;
            default:
                break;
        }
        //查询未上线
        if(status==null||status.equals(0)){
            wrapper.in(Carousel::getStatus,ReviewStatus.REVIEWING,ReviewStatus.REJECT.getCode());
        }else{//已上线
            wrapper.eq(Carousel::getStatus,status);
        }
        if (!org.apache.commons.lang3.StringUtils.isEmpty(title)) {
            wrapper.like(Carousel::getTitle,title);
        }
        wrapper.eq(Carousel::getProvincial, type);
        wrapper.orderByAsc(Carousel::getSortNum);
        Page<Carousel> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page=this.page(page,wrapper);
        List<Carousel> records = page.getRecords();
        // 循环查出实际图片路径
        List<String> imageIds = records.stream().map(Carousel::getImageId).distinct().collect(Collectors.toList());
        String fileStr = fileApi.getFileByIds(imageIds);
        List<FileSystemVO> fileList = new ArrayList<>();
        if (!StringUtils.isEmpty(fileStr)) {
            fileList = JSON.parseArray(fileStr, FileSystemVO.class);
        }
        for (Carousel carousel : records) {
            for (FileSystemVO fileSystemVO : fileList) {
                if (carousel.getImageId().equals(fileSystemVO.getId())) {
                    carousel.setRealUrl(fileSystemVO.getFilePath());
                    break;
                }
            }
        }
        return page;
    }

    @Override
    public Carousel getCarouselDetail(String id) {
        Carousel carousel = this.getById(id);
        if (carousel != null) {
            String fileStr = fileApi.getFileSystemInfo(carousel.getImageId());
            if (!StringUtils.isEmpty(fileStr)) {
                FileSystemVO fileSystemVO = JSON.parseObject(fileStr, FileSystemVO.class);
                carousel.setRealUrl(fileSystemVO.getFilePath());
            }
        }
        return carousel;
    }

    /**
     * 根据类型和所属信息查询已上线的所有轮播图
     */
    List<Carousel> listCarouselByProvincial(Integer provincial, String belong) {
        LambdaQueryWrapper<Carousel> wrapper = new QueryWrapper<Carousel>().lambda().eq(Carousel::getProvincial, provincial);

        switch(provincial){
            case 1:
                break;
            case 2:
                wrapper.eq(Carousel::getArea,belong);
                break;
            case 3:
                wrapper.eq(Carousel::getPoliceCategory,belong);
                break;
            case 4:
                wrapper.eq(Carousel::getProject, belong);
                break;
            default:
                break;
        }
        wrapper.eq(Carousel::getStatus,ReviewStatus.ONLINE.getCode());
        wrapper.orderByAsc(Carousel::getSortNum);
        wrapper.orderByDesc(Carousel::getUpdateTime);
        return this.list(wrapper);
    }

    /**
     * 地市、省厅、警种重排序使sortNum唯一有序
     */
    private void reSortOrderly(List<Carousel> carouselList){
        long count = 1L;
        for(Carousel carousel:carouselList){
            carousel.setSortNum(count);
            count++;
        }
        this.updateBatchById(carouselList);

    }

    void swapCarouselSortNum(Carousel target,Carousel slef) {
        long targetSortNum=target.getSortNum();
        target.setSortNum(slef.getSortNum());
        slef.setSortNum(targetSortNum);
    }
}
