package com.upd.hwcloud.bean.dto.cov;

import java.util.List;

public class EpidemicDeskIssue {

    private List<AreaBean> area;
    private List<PoliceBean> police;

    public List<AreaBean> getArea() {
        return area;
    }

    public void setArea(List<AreaBean> area) {
        this.area = area;
    }

    public List<PoliceBean> getPolice() {
        return police;
    }

    public void setPolice(List<PoliceBean> police) {
        this.police = police;
    }

    public static class AreaBean {
        /**
         * areaCount : 3
         * areaName : 地市
         */

        private int areaCount;
        private String areaName;

        public int getAreaCount() {
            return areaCount;
        }

        public void setAreaCount(int areaCount) {
            this.areaCount = areaCount;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }
    }

    public static class PoliceBean {
        /**
         * policeCount : 4
         * policeName : 警种
         */

        private int policeCount;
        private String policeName;

        public int getPoliceCount() {
            return policeCount;
        }

        public void setPoliceCount(int policeCount) {
            this.policeCount = policeCount;
        }

        public String getPoliceName() {
            return policeName;
        }

        public void setPoliceName(String policeName) {
            this.policeName = policeName;
        }
    }
}
