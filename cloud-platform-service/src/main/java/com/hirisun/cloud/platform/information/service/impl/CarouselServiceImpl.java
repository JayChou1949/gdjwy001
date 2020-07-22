package com.hirisun.cloud.platform.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.platform.information.bean.Carousel;
import com.hirisun.cloud.platform.information.mapper.CarouselMapper;
import com.hirisun.cloud.platform.information.service.CarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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
        wrapper.eq(Carousel::getStatus,Carousel.STATUS_ONLINE);
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
