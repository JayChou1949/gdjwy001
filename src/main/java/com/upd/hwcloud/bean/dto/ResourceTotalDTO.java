package com.upd.hwcloud.bean.dto;

public class ResourceTotalDTO {

    private int code;

    private ResourceTotalDataDTO data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResourceTotalDataDTO getData() {
        return data;
    }

    public void setData(ResourceTotalDataDTO data) {
        this.data = data;
    }

    public static class ResourceTotalDataDTO {

        private long storageTotal;

        private long tenant;

        private long serviceNum;

        private long node;

        public long getStorageTotal() {
            return storageTotal;
        }

        public void setStorageTotal(long storageTotal) {
            this.storageTotal = storageTotal;
        }

        public long getTenant() {
            return tenant;
        }

        public void setTenant(long tenant) {
            this.tenant = tenant;
        }

        public long getServiceNum() {
            return serviceNum;
        }

        public void setServiceNum(long serviceNum) {
            this.serviceNum = serviceNum;
        }

        public long getNode() {
            return node;
        }

        public void setNode(long node) {
            this.node = node;
        }

        @Override
        public String toString() {
            return "ResourceTotalDTO{" +
                    "storageTotal=" + storageTotal +
                    ", tenant=" + tenant +
                    ", serviceNum=" + serviceNum +
                    ", node=" + node +
                    '}';
        }

    }

}
