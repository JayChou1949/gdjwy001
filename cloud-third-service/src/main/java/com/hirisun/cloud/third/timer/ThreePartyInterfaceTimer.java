package com.hirisun.cloud.third.timer;

import com.hirisun.cloud.third.service.ThreePartyInterfaceService;
import com.hirisun.cloud.third.service.impl.ThreePartyInterfaceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wuxiaoxing
 * 启动定时服务，定时服务将定时查询美亚提供的数据接口，将查询数据保存到本地的t_three_party_interface表
 */
@Component
public class ThreePartyInterfaceTimer {
    @Autowired
    ThreePartyInterfaceService threePartyInterfaceService;
    private static Logger logger = LoggerFactory.getLogger(ThreePartyInterfaceTimer.class);

    //美亚ip、开放端口
    @Value("${third.my.url}")
    private String BASE_URL;

    /**
     * 定时任务  30分钟执行一次，
     * 调用第三方接口获取数据并更新数据库
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void startTimer() {
        /*
         * 未定位接口使用位置，已删除
         * dataCheckCount 数据对账
         * dbConfig 数据库监控
         * resourceRelationCount 知识图谱库
         * zlCount10 daas总览-近10日每日增量趋势图
         * resourceCategory 获取资源分类
         */
        //开始调用美亚接口
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/fiveBigResource"
                , "fiveBigResource", "数据接入（数据概况）");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/fiveBigResource?code=01"
                , "fiveBigResource01", "原始库数据");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/fiveBigResource?code=02"
                , "fiveBigResource02", "资源库数据");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/fiveBigResource?code=03"
                , "fiveBigResource03", "主题库数据");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/fiveBigResource?code=04"
                , "fiveBigResource04", "知识库数据");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/fiveBigResource?code=05"
                , "fiveBigResource05", "业务库数据");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/fiveBigResource?code=06",
                "fiveBigResource06", "业务要素索引库（接口）");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/resourceDataSourceCount?resourceFirstClass=01&resourceDataSource=02",
                "resourceDataSource02", "地市上报");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/resourceDataSourceCount?resourceFirstClass=01&resourceDataSource=01",
                "resourceDataSource01", "警种及行业公安共享");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/resourceDataSourceCount?resourceFirstClass=01&resourceDataSource=04",
                "resourceDataSource04", "公安部下发");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/resourceDataSourceCount?resourceFirstClass=01&resourceDataSource=03",
                "resourceDataSource03", "政府部门及企事业单位共享");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/resourceDataSourceCount?resourceFirstClass=01&resourceDataSource=05",
                "resourceDataSource05", "外省共享");

        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/dataGovern",
                "dataGovern", "数据治理");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataQuality/sjcjgk",
                "sjcjgk", "数据处理概况");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/resourceSensitivity",
                "resourceSensitivity", "资源分级分类");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataQuality/sjzlgl",
                "sjzlgl", "数据质量管理");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataQuality/sjxygk",
                "sjxygk", "数据血缘管理");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/dataOverview/view",
                "zonglan", "daas总览");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/resourceTotal",
                "resourceTotal", "资源目录总数");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/dataSensitivity",
                "dataSensitivity", "数据分级分类");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/dataOperManage",
                "dataOperManage", "数据运维管理");

        //二级页面
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/resourceSourceCount",
                "resourceSourceCount", "各网域资源/数据共享占比");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/dataCount?day=7&resourceSource=07"
                , "dataCount07", "近7天JZ数据每日增量总数");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/dataCount?day=7&resourceSource=08"
                , "dataCount08", "近7天WA数据每日增量总数");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/dataCount?day=7"
                , "dataCount", "近7天每日增量趋势图");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/cityDataCountList?type=resource"
                , "cityDataCountList", "每个地市的总数据量");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/cityDataCountList?type=table"
                , "cityDataCountTable", "每个地市的总表数");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/cityDataCountList?type=yesterdayCount"
                , "yesterdayCount", "每个地市的昨日增量数");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/cityDataCountSum?type=resource"
                , "cityDataCountSum", "已汇聚地市数据总量");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/cityDataCountSum?type=table"
                , "cityDataCountSumTable", "已汇聚资源数量");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/cityDataIncrement?day=7"
                , "cityDataIncrementDay7", "本周数据增量");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/cityDataIncrement?day=1"
                , "cityDataIncrementDay1", "昨日数据增量");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/dataSubGroupCount?resourceFirstClass=01&resourceDataSource=01"
                , "dataGroupCountJz", "公安网共享");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/dataSubGroupCount?resourceFirstClass=01&resourceDataSource=03"
                , "dataGroupCountBm", "政府部门间共享(含企事业单位共享)");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/findPartSend"
                , "findPartSend", "公安部下发");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/departmentCount",
                "departmentCount", "外省共享");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataQuality/sjclqst",
                "sjclqst", "近30天数据处理趋势");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/resourceScale",
                "resourceScale", "近7天数据标准化资源数/数据量");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService//foreign/dataCount/action/cityDataCountNew",
                "cityDataCountNew", "数据元");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/resourceClassCount",
                "resourceClassCount", "资源分级分类概况");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataCount/action/queryCountGroupBySectionClass",
                "queryCountGroupBySectionClass", "分类概况");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataQuality/zyzbqst",
                "zyzbqst", "近7天异常更新率/缺业务代码资源占比趋势");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataQuality/zygxycl",
                "zygxycl", "近7天资源更新异常率TOP10");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataQuality/gxyczyssjz",
                "gxyczyssjz", "近7天更新异常资源所属警种TOP10");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataQuality/yszdkzl",
                "yszdkzl", "要素字段空值率资源TOP10");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataQuality/zyssjzpjkzl",
                "zyssjzpjkzl", "资源所属警种平均空值率TOP10");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataQuality/yszdzhglzy",
                "yszdzhglzy", "要素字段值合格率资源LAST10");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataQuality/zyssjzzdzhgl",
                "zyssjzzdzhgl", "资源所属警种字段值合格率LAST10");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataQuality/qywdmzy",
                "qywdmzy", "缺业务代码资源TOP10");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataQuality/zyssjzqywdm",
                "zyssjzqywdm", "资源所属警种缺业务代码TOP10");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/statistics/dataCount?day=30&resourceFirstClass=01",
                "dataCount3001", "原始库近30天每日增量趋势图");
        //数据目录板块
        threePartyInterfaceService.jsonDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataResource/action/attrCategory",
                "attrCategory","筛选条件","{}");
        threePartyInterfaceService.getDataHandler(BASE_URL+"/services/serviceInvoke/queryDataService/foreign/dataResource/action/tag",
                "dataTag","常用标签");

        /**
         * 未发现使用位置
         * apimodel 模型超市按模型上线时间排行
         * apimodelHot 模型超市按模型热度排行
         * modeljuesai 模型超市按决赛取
         */
        logger.info("====三方接口同步结束===");
    }
}
