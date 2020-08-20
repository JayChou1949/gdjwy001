package com.hirisun.cloud.order.bean.paas;

import java.util.List;

import lombok.Data;

@Data
public class PaasDistributedDbApply {
    /**
     * 数据库信息
     */
    private PaasLibraInfo paasLibraInfo;

    private List<PaasLibraAccount> paasLibraAccountList;
    /**
     * 白名单信息
     */
    private List<PaasLibraDbWhitelist> paasLibraDbWhitelists;
}
