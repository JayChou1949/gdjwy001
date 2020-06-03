package com.upd.hwcloud.controller.portal.ncov;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.ncov.NcovClusterApp;
import com.upd.hwcloud.bean.vo.ncov.NcovClusterAppVo;
import com.upd.hwcloud.common.utils.easypoi.NcovClusterImportUtil;
import com.upd.hwcloud.common.utils.ncov.MemoryPageUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;

import io.swagger.annotations.Api;

/**
 * @author wuc
 * @date 2020/3/2
 */
@Api("疫情-paas")
@RestController
@RequestMapping("/ncov/paas")
public class NcovPaasController {

    @RequestMapping(value = "/overview",method = RequestMethod.GET)
    public R overview(){
        return R.ok(NcovClusterImportUtil.getOverview());
    }

    @RequestMapping(value = "/resource",method = RequestMethod.GET)
    public  R resource(){
        return R.ok(NcovClusterImportUtil.getResourceList());
    }

    @RequestMapping(value = "/app",method = RequestMethod.GET)
    public R app(@RequestParam(required = false,defaultValue = "3") long pageSize, @RequestParam(required = false,defaultValue = "1") long current){
        List<NcovClusterApp> appList = NcovClusterImportUtil.getAppDetailList();
        return R.ok(MemoryPageUtil.page(convert2Vo(appList),pageSize,current));
    }


    /**
     * 转换为VO（处理最近7天时间轴坐标）
     * @param appList
     * @return
     */
    private List<NcovClusterAppVo> convert2Vo(List<NcovClusterApp> appList){
        List<NcovClusterAppVo> voList = Lists.newArrayList();
        LocalDate now = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd");

        appList.forEach(app->{
            NcovClusterAppVo vo = new NcovClusterAppVo();
            try {
                BeanUtils.copyProperties(vo,app);
                //转换CPU最近7天数据结构
                LinkedHashMap<String,Double> cpuRecent = Maps.newLinkedHashMap();
                cpuRecent.put(now.minus(7, ChronoUnit.DAYS).format(fmt),app.getCpuDay1());
                cpuRecent.put(now.minus(6, ChronoUnit.DAYS).format(fmt),app.getCpuDay2());
                cpuRecent.put(now.minus(5, ChronoUnit.DAYS).format(fmt),app.getCpuDay3());
                cpuRecent.put(now.minus(4, ChronoUnit.DAYS).format(fmt),app.getCpuDay4());
                cpuRecent.put(now.minus(3, ChronoUnit.DAYS).format(fmt),app.getCpuDay5());
                cpuRecent.put(now.minus(2, ChronoUnit.DAYS).format(fmt),app.getCpuDay6());
                cpuRecent.put(now.minus(1, ChronoUnit.DAYS).format(fmt),app.getCpuDay7());
                vo.setCpuRecent(cpuRecent);

                //处理内存最近7天数据结构
                LinkedHashMap<String,Double> memoryRecent = Maps.newLinkedHashMap();
                memoryRecent.put(now.minus(7, ChronoUnit.DAYS).format(fmt),app.getMemDay1());
                memoryRecent.put(now.minus(6, ChronoUnit.DAYS).format(fmt),app.getMemDay2());
                memoryRecent.put(now.minus(5, ChronoUnit.DAYS).format(fmt),app.getMemDay3());
                memoryRecent.put(now.minus(4, ChronoUnit.DAYS).format(fmt),app.getMemDay4());
                memoryRecent.put(now.minus(3, ChronoUnit.DAYS).format(fmt),app.getMemDay5());
                memoryRecent.put(now.minus(2, ChronoUnit.DAYS).format(fmt),app.getMemDay6());
                memoryRecent.put(now.minus(1, ChronoUnit.DAYS).format(fmt),app.getMemDay7());
                vo.setMemRecent(memoryRecent);
                voList.add(vo);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return voList;

    }

}
