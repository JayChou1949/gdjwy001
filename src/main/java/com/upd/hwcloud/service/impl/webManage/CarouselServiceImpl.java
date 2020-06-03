package com.upd.hwcloud.service.impl.webManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.NewsType;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Carousel;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.utils.NewsCarouselUtil;
import com.upd.hwcloud.dao.webManage.CarouselMapper;
import com.upd.hwcloud.service.webManage.ICarouselService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 轮播图 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements ICarouselService {

    @Autowired
    CarouselMapper carouselMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(User user, String id, Integer result, String remark) {
        Carousel carousel = this.getById(id);
        if (result.equals(1)) { // 上线
            carousel.setStatus(ReviewStatus.ONLINE.getCode());
            carousel.updateById();
        } else { // 下线
            carousel.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            carousel.updateById();
        }
    }

    /**
     * 手动排序 上移/下移
     * @param type up:上移 down:下移
     * @param id   目标轮播图id
     * @param provincial  新闻类型
     * @param belong   归属
     * @return
     */
    @Override
    public R move(String type, String id, Integer provincial,String  belong) {
        if(!checkParam(provincial,belong)){
            return R.error("参数不合法");
        }
        List<Carousel> list = getAllOfType(provincial,belong);

        //sortNum值不唯一
        if(list.stream().map(Carousel::getSortNum).distinct().count()<list.size()){
            System.out.println("sortNum 重排序");
            //重排序使sort值唯一有序
            reSortOrderly(list);
        }


        Carousel aimCarousel = new Carousel();

        int count = 0;

        //遍历寻找目标元素，并记录下标
        for(Carousel c: list){
            if(StringUtils.equals(c.getId(),id)){
                aimCarousel = c;
                break;
            }
            count = count  + 1;
        }


        //上移操作
        if(StringUtils.equals(type,"up")){
            if(count == 0){
                return  R.error("无效上移操作");
            }
            //取前一个元素
            Carousel pre = list.get(count-1);
            swapSort(aimCarousel,pre);
        }else if( StringUtils.equals(type,"down")){ //下移操作
            if(count == list.size()-1){
                return R.error("无效下移操作");
            }
            //取后一个元素
            Carousel next = list.get(count + 1);
            swapSort(aimCarousel,next);
        }
        return R.ok();

    }

    //交换修改时间
    private void swapModifiedTime(Carousel aim,Carousel preOrNext){
        Date aimeDate = aim.getModifiedTime();

        //更新aim的修改时间为preOrNext的,,sort不变
        carouselMapper.updateModifiedTimeAndSort(preOrNext.getModifiedTime(),aim.getSortNum(),aim.getId());

        //更新preOrNext的修改时间为aim的,sort不变
        carouselMapper.updateModifiedTimeAndSort(aimeDate,preOrNext.getSortNum(),preOrNext.getId());

    }

    //交换排序，修改时间不变
    private void swapSort(Carousel aim,Carousel preOrNext){
        long temp = aim.getSortNum().longValue();


        //更新目标实体的sort值为preOrNext的,修改时间不变
        carouselMapper.updateModifiedTimeAndSort(aim.getModifiedTime(),preOrNext.getSortNum(),aim.getId());

        //更新相邻实体的sort值为aim的,修改时间不变
        carouselMapper.updateModifiedTimeAndSort(preOrNext.getModifiedTime(),temp,preOrNext.getId());

    }


    /**
     * 地市、省厅、警种重排序使sortNum唯一有序
     *
     */
    private void reSortOrderly(List<Carousel> carouselList){
        long count = 1L;
        for(Carousel carousel:carouselList){
            carousel.setSortNum(count);
            count++;
        }
        this.updateBatchById(carouselList);

    }


    /**
     * 获取省厅/地市/警种的所有在线轮播图
     * @param provincial
     * @param belong
     * @return
     */
    private List<Carousel> getAllOfType( Integer provincial,String  belong){

        Map<String,Object> param = Maps.newHashMap();
        param.put("provincial",provincial);
        if(NewsType.PROVINCE.getCode().intValue() == provincial.intValue()){
            param.put("area","");
            param.put("police","");
        }else if(NewsType.AREA.getCode().intValue() == provincial.intValue()){
           param.put("area",belong);
           param.put("police","");
        }else if(NewsType.POLICE.getCode().intValue() == provincial.intValue()){
            param.put("area","");
            param.put("police",belong);

        }
        return carouselMapper.getAllOnlineOfType(param);
    }

    /**
     * 校验非省厅是否传递归属
     * @param provincial
     * @param belong
     * @return
     */
    private boolean checkParam(Integer provincial,String  belong){
        if(!(NewsType.PROVINCE.getCode().intValue() == provincial.intValue())){
            if(StringUtils.isBlank(belong)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Page<Carousel> getPage(Page<Carousel> page, User user, Integer status, String title,Integer type,String belong) {
        Map<String,Object> param = NewsCarouselUtil.handlerOfParam(type,belong);
        return carouselMapper.getPage(page,user,status,title,param);
    }

    public List<Carousel> getIndexCarousel(Integer type,String belong){
        Map<String,Object> param = NewsCarouselUtil.handlerOfParam(type,belong);
        return carouselMapper.getIndexCarouse(param);
    }

    @Override
    public Page<Carousel> getCrByPage(Page<Carousel> page) {
        return carouselMapper.getCrByPage(page);
    }
}
