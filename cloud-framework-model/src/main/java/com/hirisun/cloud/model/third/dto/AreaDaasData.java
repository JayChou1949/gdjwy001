package com.hirisun.cloud.model.third.dto;

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
     * 资源总量-表数
     */
    private String countValue;
    /**
     * 资源总量-数据量
     */
    private String sumValue;
    /**
     * 上报省厅资源总量-表数
     */
    private String toProvinceCountValue;
    /**
     * 上报省厅资源总量-数据量
     */
    private String toProvinceValue;
    /**
     * 省厅资源总量-表数
     */
    private String provinceValue;
    /**
     * 省厅资源总量-表数据量
     */
    private String provinceSumValue;
    /**
     * 区县上报
     */
    private DistrictReport area;
    /**
     * 警种及行业公安共享
     */
    private PoliceShare police;
    /**
     * 政府部门及企事业单位共享
     */
    private GovShare government;

    @Data
    public static class TotalResources {
        /**
         * 表数
         */
        private String count;
        /**
         * 数据量
         */
        private String sum;
    }

    @Data
    public static class ReportToUp {
        /**
         * 表数
         */
        private String count;
        /**
         * 数据量
         */
        private String sum;
    }

    @Data
    public static class UpResources {
        /**
         * 表数
         */
        private String count;
        /**
         * 数据量
         */
        private String sum;
    }

    @Data
    public static class DistrictReport {
        /**
         * 表数
         */
        private String count;
        /**
         * 昨日新增表数
         */
        private String countIncrement;
        /**
         * 数据量
         */
        private String sum;
        /**
         * 昨日新增数据量
         */
        private String sumIncrement;
    }

    @Data
    public static class PoliceShare {
        /**
         * 表数
         */
        private String count;
        /**
         * 昨日新增表数
         */
        private String countIncrement;
        /**
         * 数据量
         */
        private String sum;
        /**
         * 昨日新增数据量
         */
        private String sumIncrement;
    }

    @Data
    public static class GovShare {
        /**
         * 表数
         */
        private String count;
        /**
         * 昨日新增表数
         */
        private String countIncrement;
        /**
         * 数据量
         */
        private String sum;
        /**
         * 昨日新增数据量
         */
        private String sumIncrement;
    }
}
