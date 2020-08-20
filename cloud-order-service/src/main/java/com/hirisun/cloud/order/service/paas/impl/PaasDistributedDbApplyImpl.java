package com.hirisun.cloud.order.service.paas.impl;

import java.util.List;

import com.hirisun.cloud.order.bean.paas.PaasLibraAccountImpl;
import com.hirisun.cloud.order.bean.paas.PaasLibraInfoImpl;
import com.hirisun.cloud.order.bean.paas.PaasLibraWhitelistImpl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaasDistributedDbApplyImpl {
    private PaasLibraInfoImpl paasLibraInfoImpl;

    private List<PaasLibraAccountImpl> paasLibraAccountImplList;

    private List<PaasLibraWhitelistImpl> paasLibraWhitelistImplList;
}
