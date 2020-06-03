package com.upd.hwcloud.bean.entity.application;

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
    private List<PaasDistributedDbInfo> paasDistributedDbInfos;
    /**
     * 白名单信息
     */
    private List<PaasLibraDbWhitelist> paasLibraDbWhitelists;
}
