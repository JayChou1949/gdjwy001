package com.upd.hwcloud.bean.dto;

import lombok.Data;

/**
 * @author junglefisher
 * @date 2020/6/14 11:25
 */
@Data
public class AreaDaasData {

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 地区名称拼音
     */
    private String areaNameEn;

    /**
     * 资源总量
     */
    private TotalResources totalResources;
    /**
     * 上报省厅资源总量
     */
    private ReportToUp reportToUp;
    /**
     * 省厅资源总量
     */
    private UpResources upResources;
    /**
     * 区县上报
     */
    private DistrictReport districtReport;
    /**
     * 警种及行业公安共享
     */
    private PoliceShare policeShare;
    /**
     * 政府部门及企事业单位共享
     */
    private GovShare govShare;

    @Data
    public static class TotalResources {
        /**
         * 表数
         */
        private Integer tabelNum;
        /**
         * 数据量
         */
        private Long dataNum;
    }

    @Data
    public static class ReportToUp {
        /**
         * 表数
         */
        private Integer tabelNum;
        /**
         * 数据量
         */
        private Long dataNum;
    }

    @Data
    public static class UpResources {
        /**
         * 表数
         */
        private Integer tabelNum;
        /**
         * 数据量
         */
        private Long dataNum;
    }

    @Data
    public static class DistrictReport {
        /**
         * 表数
         */
        private Integer tabelNum;
        /**
         * 昨日新增表数
         */
        private Integer addTable;
        /**
         * 数据量
         */
        private Long dataNum;
        /**
         * 昨日新增数据量
         */
        private Long addData;
    }

    @Data
    public static class PoliceShare {
        /**
         * 表数
         */
        private Integer tabelNum;
        /**
         * 昨日新增表数
         */
        private Integer addTable;
        /**
         * 数据量
         */
        private Long dataNum;
        /**
         * 昨日新增数据量
         */
        private Long addData;
    }

    @Data
    public static class GovShare {
        /**
         * 表数
         */
        private Integer tabelNum;
        /**
         * 昨日新增表数
         */
        private Integer addTable;
        /**
         * 数据量
         */
        private Long dataNum;
        /**
         * 昨日新增数据量
         */
        private Long addData;
    }
}
