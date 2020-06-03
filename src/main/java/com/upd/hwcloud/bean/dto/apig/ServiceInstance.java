package com.upd.hwcloud.bean.dto.apig;

/**
 * 服务实例
 */
public class ServiceInstance {


    /**
     * instance_guid : 7a02760f-c32d-51d2-c5e0-bdffde6e8021
     * status : approving
     * rejectreason :
     */

    private String instance_guid;
    private String status;
    private String rejectreason;

    public String getInstance_guid() {
        return instance_guid;
    }

    public void setInstance_guid(String instance_guid) {
        this.instance_guid = instance_guid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRejectreason() {
        return rejectreason;
    }

    public void setRejectreason(String rejectreason) {
        this.rejectreason = rejectreason;
    }

}
