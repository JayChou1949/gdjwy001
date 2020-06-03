package com.upd.hwcloud.bean.dto;

import com.upd.hwcloud.bean.entity.iaasConfig.PhyDevice;
import com.upd.hwcloud.bean.entity.iaasConfig.PoliceCloud;
import com.upd.hwcloud.bean.entity.iaasConfig.VmDevice;

public class PhyAndVm {

    private PhyDevice phyDevice;
    private VmDevice vmDevice;
    private PoliceCloud policeCloud;

    public PhyDevice getPhyDevice() {
        return phyDevice;
    }

    public void setPhyDevice(PhyDevice phyDevice) {
        this.phyDevice = phyDevice;
    }

    public VmDevice getVmDevice() {
        return vmDevice;
    }

    public void setVmDevice(VmDevice vmDevice) {
        this.vmDevice = vmDevice;
    }

    public PoliceCloud getPoliceCloud() {
        return policeCloud;
    }

    public void setPoliceCloud(PoliceCloud policeCloud) {
        this.policeCloud = policeCloud;
    }
}
