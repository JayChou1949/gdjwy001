package com.hirisun.cloud.paas.service.impl;

import java.util.List;

import com.hirisun.cloud.paas.bean.PaasLibraAccountImpl;
import com.hirisun.cloud.paas.bean.PaasLibraInfoImpl;
import com.hirisun.cloud.paas.bean.PaasLibraWhitelistImpl;

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
