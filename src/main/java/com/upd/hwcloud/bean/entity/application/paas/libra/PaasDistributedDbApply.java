package com.upd.hwcloud.bean.entity.application.paas.libra;

import com.upd.hwcloud.bean.entity.application.PaasDistributedDbInfo;

import lombok.Data;

import java.util.List;

/**
 * @author junglefisher
 * @date 2020/5/9 9:53
 */
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
