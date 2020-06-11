package com.upd.hwcloud.bean.entity.application.paas.libra;

import com.upd.hwcloud.bean.entity.Paas;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yyc
 * @date 2020/6/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaasDistributedDbApplyImpl {
    private PaasLibraInfoImpl paasLibraInfoImpl;

    private List<PaasLibraAccountImpl> paasLibraAccountImplList;

    private List<PaasLibraWhitelistImpl> paasLibraWhitelistImplList;
}
