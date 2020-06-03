package com.upd.hwcloud.bean.dto.apig;

/**
 * @author junglefisher
 * @date 2019/11/4 15:02
 */
public class ServiceReturnBean {
    // 服务ID
    private String serviceId;
    // 创建服务错误信息
    private String errorMessage;
    // 创建服务是否出错
    private boolean isError=false;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    @Override
    public String toString() {
        return "ServiceReturnBean{" +
                "serviceId='" + serviceId + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", isError=" + isError +
                '}';
    }
}
