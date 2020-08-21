package com.hirisun.cloud.iaas.enumeration;

import com.hirisun.cloud.iaas.service.IApplicationHandler;
import com.hirisun.cloud.iaas.service.IImplHandler;

public class HandlerWrapper {

    private FormNum formNum;

    private IApplicationHandler handler;

    private IImplHandler implHandler;

    private Class applicationType;

    private Class implType;

    public FormNum getFormNum() {
        return formNum;
    }

    public void setFormNum(FormNum formNum) {
        this.formNum = formNum;
    }

    public IApplicationHandler getHandler() {
        return handler;
    }

    public void setHandler(IApplicationHandler handler) {
        this.handler = handler;
    }

    public IImplHandler getImplHandler() {
        return implHandler;
    }

    public void setImplHandler(IImplHandler implHandler) {
        this.implHandler = implHandler;
    }

    public Class getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(Class applicationType) {
        this.applicationType = applicationType;
    }

    public Class getImplType() {
        return implType;
    }

    public void setImplType(Class implType) {
        this.implType = implType;
    }

}
