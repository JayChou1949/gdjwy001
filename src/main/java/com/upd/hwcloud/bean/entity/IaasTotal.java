package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author yyc
 * @since 2018-10-28
 */
@TableName("TB_IAAS_TOTAL")
public class IaasTotal extends Model<IaasTotal> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    @TableField("NET")
    private Long net;

    @TableField("SERVER")
    private Long server;

    @TableField("ROUTE")
    private Long route;

    @TableField("STORE")
    private Long store;

    @TableField("SWITCHFC")
    private Long switchfc;

    @TableField("FIREWALL")
    private Long firewall;

    @TableField("CLOUDSERVER")
    private Long cloudserver;

    @TableField("BMS")
    private Long bms;

    @TableField("IMAGE")
    private Long image;

    @TableField("CDISK")
    private Long cdisk;

    @TableField("VPC")
    private Long vpc;

    @TableField("FIP")
    private Long fip;

    @TableField("ELP")
    private Long elp;

    @TableField("VFW")
    private Long vfw;

    @TableField("VCPUTOTAL")
    private Long vcputotal;

    @TableField("VCPUUSED")
    private Long vcpuused;

    @TableField("VCPURATIO")
    private Float vcpuratio;

    @TableField("DISKTOTAL")
    private Long disktotal;

    @TableField("DISKUSED")
    private Long diskused;

    @TableField("DISKRATIO")
    private Float diskratio;

    @TableField("RAMTOTAL")
    private Long ramtotal;

    @TableField("RAMUSED")
    private Long ramused;

    @TableField("RAMRATIO")
    private Float ramratio;

    @TableField("PUBLICPART")
    private Long publicpart;

    @TableField("ENTRYEXIT")
    private Long entryexit;

    @TableField("ANTITERRORIST")
    private Long antiterrorist;

    @TableField("TRAFFICMANAGER")
    private Long trafficmanager;

    @TableField("TRAFFICPOLICE")
    private Long trafficpolice;

    @TableField("DEPARTMENTONE")
    private Long departmentone;

    @TableField("DEPARTMENTTWO")
    private Long departmenttwo;

    @TableField("DEPARTMENTHREE")
    private Long departmenthree;

    @TableField("DEPARTMENTFOUR")
    private Long departmentfour;

    @TableField("BIGDATALIBRA")
    private Long bigdatalibra;

    @TableField("BIGDATAHD")
    private Long bigdatahd;

    @TableField("BIGDATADEEP")
    private Long bigdatadeep;

    @TableField("BIGDATACOLLECT")
    private Long bigdatacollect;

    @TableField("LIBRA1CPUTOTAL")
    private Long libra1cputotal;

    @TableField("LIBRA1CPUUSED")
    private Long libra1cpuused;

    @TableField("LIBRA1CPURATIO")
    private Long libra1cpuratio;

    @TableField("LIBRA2CPUTOTAL")
    private Long libra2cputotal;

    @TableField("LIBRA2CPUUSED")
    private Long libra2cpuused;

    @TableField("LIBRA2CPURATIO")
    private Long libra2cpuratio;

    @TableField("LIBRA3CPUTOTAL")
    private Long libra3cputotal;

    @TableField("LIBRA3CPUUSED")
    private Long libra3cpuused;

    @TableField("LIBRA3CPURATIO")
    private Long libra3cpuratio;

    @TableField("HD1CPUTOTAL")
    private Long hd1cputotal;

    @TableField("HD1CPUUSED")
    private Long hd1cpuused;

    @TableField("HD1CPURATIO")
    private Long hd1cpuratio;

    @TableField("HD2CPUTOTAL")
    private Long hd2cputotal;

    @TableField("HD2CPUUESD")
    private Long hd2cpuuesd;

    @TableField("HD2CPURATIO")
    private Long hd2cpuratio;

    @TableField("HD3CPUTOTAL")
    private Long hd3cputotal;

    @TableField("HD3CPUUSED")
    private Long hd3cpuused;

    @TableField("HD3CPURATIO")
    private Long hd3cpuratio;


    public String getId() {
        return id;
    }

    public IaasTotal setId(String id) {
        this.id = id;
        return this;
    }

    public Long getNet() {
        return net;
    }

    public IaasTotal setNet(Long net) {
        this.net = net;
        return this;
    }

    public Long getServer() {
        return server;
    }

    public IaasTotal setServer(Long server) {
        this.server = server;
        return this;
    }

    public Long getRoute() {
        return route;
    }

    public IaasTotal setRoute(Long route) {
        this.route = route;
        return this;
    }

    public Long getStore() {
        return store;
    }

    public IaasTotal setStore(Long store) {
        this.store = store;
        return this;
    }

    public Long getSwitchfc() {
        return switchfc;
    }

    public IaasTotal setSwitchfc(Long switchfc) {
        this.switchfc = switchfc;
        return this;
    }

    public Long getFirewall() {
        return firewall;
    }

    public IaasTotal setFirewall(Long firewall) {
        this.firewall = firewall;
        return this;
    }

    public Long getCloudserver() {
        return cloudserver;
    }

    public IaasTotal setCloudserver(Long cloudserver) {
        this.cloudserver = cloudserver;
        return this;
    }

    public Long getBms() {
        return bms;
    }

    public IaasTotal setBms(Long bms) {
        this.bms = bms;
        return this;
    }

    public Long getImage() {
        return image;
    }

    public IaasTotal setImage(Long image) {
        this.image = image;
        return this;
    }

    public Long getCdisk() {
        return cdisk;
    }

    public IaasTotal setCdisk(Long cdisk) {
        this.cdisk = cdisk;
        return this;
    }

    public Long getVpc() {
        return vpc;
    }

    public IaasTotal setVpc(Long vpc) {
        this.vpc = vpc;
        return this;
    }

    public Long getFip() {
        return fip;
    }

    public IaasTotal setFip(Long fip) {
        this.fip = fip;
        return this;
    }

    public Long getElp() {
        return elp;
    }

    public IaasTotal setElp(Long elp) {
        this.elp = elp;
        return this;
    }

    public Long getVfw() {
        return vfw;
    }

    public IaasTotal setVfw(Long vfw) {
        this.vfw = vfw;
        return this;
    }

    public Long getVcputotal() {
        return vcputotal;
    }

    public IaasTotal setVcputotal(Long vcputotal) {
        this.vcputotal = vcputotal;
        return this;
    }

    public Long getVcpuused() {
        return vcpuused;
    }

    public IaasTotal setVcpuused(Long vcpuused) {
        this.vcpuused = vcpuused;
        return this;
    }

    public Float getVcpuratio() {
        return vcpuratio;
    }

    public IaasTotal setVcpuratio(Float vcpuratio) {
        this.vcpuratio = vcpuratio;
        return this;
    }

    public Long getDisktotal() {
        return disktotal;
    }

    public IaasTotal setDisktotal(Long disktotal) {
        this.disktotal = disktotal;
        return this;
    }

    public Long getDiskused() {
        return diskused;
    }

    public IaasTotal setDiskused(Long diskused) {
        this.diskused = diskused;
        return this;
    }

    public Float getDiskratio() {
        return diskratio;
    }

    public IaasTotal setDiskratio(Float diskratio) {
        this.diskratio = diskratio;
        return this;
    }

    public Long getRamtotal() {
        return ramtotal;
    }

    public IaasTotal setRamtotal(Long ramtotal) {
        this.ramtotal = ramtotal;
        return this;
    }

    public Long getRamused() {
        return ramused;
    }

    public IaasTotal setRamused(Long ramused) {
        this.ramused = ramused;
        return this;
    }

    public Float getRamratio() {
        return ramratio;
    }

    public IaasTotal setRamratio(Float ramratio) {
        this.ramratio = ramratio;
        return this;
    }

    public Long getPublicpart() {
        return publicpart;
    }

    public IaasTotal setPublicpart(Long publicpart) {
        this.publicpart = publicpart;
        return this;
    }

    public Long getEntryexit() {
        return entryexit;
    }

    public IaasTotal setEntryexit(Long entryexit) {
        this.entryexit = entryexit;
        return this;
    }

    public Long getAntiterrorist() {
        return antiterrorist;
    }

    public IaasTotal setAntiterrorist(Long antiterrorist) {
        this.antiterrorist = antiterrorist;
        return this;
    }

    public Long getTrafficmanager() {
        return trafficmanager;
    }

    public IaasTotal setTrafficmanager(Long trafficmanager) {
        this.trafficmanager = trafficmanager;
        return this;
    }

    public Long getTrafficpolice() {
        return trafficpolice;
    }

    public IaasTotal setTrafficpolice(Long trafficpolice) {
        this.trafficpolice = trafficpolice;
        return this;
    }

    public Long getDepartmentone() {
        return departmentone;
    }

    public IaasTotal setDepartmentone(Long departmentone) {
        this.departmentone = departmentone;
        return this;
    }

    public Long getDepartmenttwo() {
        return departmenttwo;
    }

    public IaasTotal setDepartmenttwo(Long departmenttwo) {
        this.departmenttwo = departmenttwo;
        return this;
    }

    public Long getDepartmenthree() {
        return departmenthree;
    }

    public IaasTotal setDepartmenthree(Long departmenthree) {
        this.departmenthree = departmenthree;
        return this;
    }

    public Long getDepartmentfour() {
        return departmentfour;
    }

    public IaasTotal setDepartmentfour(Long departmentfour) {
        this.departmentfour = departmentfour;
        return this;
    }

    public Long getBigdatalibra() {
        return bigdatalibra;
    }

    public IaasTotal setBigdatalibra(Long bigdatalibra) {
        this.bigdatalibra = bigdatalibra;
        return this;
    }

    public Long getBigdatahd() {
        return bigdatahd;
    }

    public IaasTotal setBigdatahd(Long bigdatahd) {
        this.bigdatahd = bigdatahd;
        return this;
    }

    public Long getBigdatadeep() {
        return bigdatadeep;
    }

    public IaasTotal setBigdatadeep(Long bigdatadeep) {
        this.bigdatadeep = bigdatadeep;
        return this;
    }

    public Long getBigdatacollect() {
        return bigdatacollect;
    }

    public IaasTotal setBigdatacollect(Long bigdatacollect) {
        this.bigdatacollect = bigdatacollect;
        return this;
    }

    public Long getLibra1cputotal() {
        return libra1cputotal;
    }

    public IaasTotal setLibra1cputotal(Long libra1cputotal) {
        this.libra1cputotal = libra1cputotal;
        return this;
    }

    public Long getLibra1cpuused() {
        return libra1cpuused;
    }

    public IaasTotal setLibra1cpuused(Long libra1cpuused) {
        this.libra1cpuused = libra1cpuused;
        return this;
    }

    public Long getLibra1cpuratio() {
        return libra1cpuratio;
    }

    public IaasTotal setLibra1cpuratio(Long libra1cpuratio) {
        this.libra1cpuratio = libra1cpuratio;
        return this;
    }

    public Long getLibra2cputotal() {
        return libra2cputotal;
    }

    public IaasTotal setLibra2cputotal(Long libra2cputotal) {
        this.libra2cputotal = libra2cputotal;
        return this;
    }

    public Long getLibra2cpuused() {
        return libra2cpuused;
    }

    public IaasTotal setLibra2cpuused(Long libra2cpuused) {
        this.libra2cpuused = libra2cpuused;
        return this;
    }

    public Long getLibra2cpuratio() {
        return libra2cpuratio;
    }

    public IaasTotal setLibra2cpuratio(Long libra2cpuratio) {
        this.libra2cpuratio = libra2cpuratio;
        return this;
    }

    public Long getLibra3cputotal() {
        return libra3cputotal;
    }

    public IaasTotal setLibra3cputotal(Long libra3cputotal) {
        this.libra3cputotal = libra3cputotal;
        return this;
    }

    public Long getLibra3cpuused() {
        return libra3cpuused;
    }

    public IaasTotal setLibra3cpuused(Long libra3cpuused) {
        this.libra3cpuused = libra3cpuused;
        return this;
    }

    public Long getLibra3cpuratio() {
        return libra3cpuratio;
    }

    public IaasTotal setLibra3cpuratio(Long libra3cpuratio) {
        this.libra3cpuratio = libra3cpuratio;
        return this;
    }

    public Long getHd1cputotal() {
        return hd1cputotal;
    }

    public IaasTotal setHd1cputotal(Long hd1cputotal) {
        this.hd1cputotal = hd1cputotal;
        return this;
    }

    public Long getHd1cpuused() {
        return hd1cpuused;
    }

    public IaasTotal setHd1cpuused(Long hd1cpuused) {
        this.hd1cpuused = hd1cpuused;
        return this;
    }

    public Long getHd1cpuratio() {
        return hd1cpuratio;
    }

    public IaasTotal setHd1cpuratio(Long hd1cpuratio) {
        this.hd1cpuratio = hd1cpuratio;
        return this;
    }

    public Long getHd2cputotal() {
        return hd2cputotal;
    }

    public IaasTotal setHd2cputotal(Long hd2cputotal) {
        this.hd2cputotal = hd2cputotal;
        return this;
    }

    public Long getHd2cpuuesd() {
        return hd2cpuuesd;
    }

    public IaasTotal setHd2cpuuesd(Long hd2cpuuesd) {
        this.hd2cpuuesd = hd2cpuuesd;
        return this;
    }

    public Long getHd2cpuratio() {
        return hd2cpuratio;
    }

    public IaasTotal setHd2cpuratio(Long hd2cpuratio) {
        this.hd2cpuratio = hd2cpuratio;
        return this;
    }

    public Long getHd3cputotal() {
        return hd3cputotal;
    }

    public IaasTotal setHd3cputotal(Long hd3cputotal) {
        this.hd3cputotal = hd3cputotal;
        return this;
    }

    public Long getHd3cpuused() {
        return hd3cpuused;
    }

    public IaasTotal setHd3cpuused(Long hd3cpuused) {
        this.hd3cpuused = hd3cpuused;
        return this;
    }

    public Long getHd3cpuratio() {
        return hd3cpuratio;
    }

    public IaasTotal setHd3cpuratio(Long hd3cpuratio) {
        this.hd3cpuratio = hd3cpuratio;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasTotal{" +
                "id=" + id +
                ", net=" + net +
                ", server=" + server +
                ", route=" + route +
                ", store=" + store +
                ", switchfc=" + switchfc +
                ", firewall=" + firewall +
                ", cloudserver=" + cloudserver +
                ", bms=" + bms +
                ", image=" + image +
                ", cdisk=" + cdisk +
                ", vpc=" + vpc +
                ", fip=" + fip +
                ", elp=" + elp +
                ", vfw=" + vfw +
                ", vcputotal=" + vcputotal +
                ", vcpuused=" + vcpuused +
                ", vcpuratio=" + vcpuratio +
                ", disktotal=" + disktotal +
                ", diskused=" + diskused +
                ", diskratio=" + diskratio +
                ", ramtotal=" + ramtotal +
                ", ramused=" + ramused +
                ", ramratio=" + ramratio +
                ", publicpart=" + publicpart +
                ", entryexit=" + entryexit +
                ", antiterrorist=" + antiterrorist +
                ", trafficmanager=" + trafficmanager +
                ", trafficpolice=" + trafficpolice +
                ", departmentone=" + departmentone +
                ", departmenttwo=" + departmenttwo +
                ", departmenthree=" + departmenthree +
                ", departmentfour=" + departmentfour +
                ", bigdatalibra=" + bigdatalibra +
                ", bigdatahd=" + bigdatahd +
                ", bigdatadeep=" + bigdatadeep +
                ", bigdatacollect=" + bigdatacollect +
                ", libra1cputotal=" + libra1cputotal +
                ", libra1cpuused=" + libra1cpuused +
                ", libra1cpuratio=" + libra1cpuratio +
                ", libra2cputotal=" + libra2cputotal +
                ", libra2cpuused=" + libra2cpuused +
                ", libra2cpuratio=" + libra2cpuratio +
                ", libra3cputotal=" + libra3cputotal +
                ", libra3cpuused=" + libra3cpuused +
                ", libra3cpuratio=" + libra3cpuratio +
                ", hd1cputotal=" + hd1cputotal +
                ", hd1cpuused=" + hd1cpuused +
                ", hd1cpuratio=" + hd1cpuratio +
                ", hd2cputotal=" + hd2cputotal +
                ", hd2cpuuesd=" + hd2cpuuesd +
                ", hd2cpuratio=" + hd2cpuratio +
                ", hd3cputotal=" + hd3cputotal +
                ", hd3cpuused=" + hd3cpuused +
                ", hd3cpuratio=" + hd3cpuratio +
                "}";
    }
}
