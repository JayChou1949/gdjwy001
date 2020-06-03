package com.upd.hwcloud.service;

/**
 * @author junglefisher
 * @date 2019/11/21 17:05
 */
public interface RepeatedJudgementService {
    boolean judgeServiceName(String serviceName);
    boolean judgeApiName(String apiName);
    boolean judgeApiOperationName(String apiOperationName);
    boolean judgeApiOperationPath(String apiOperationPath);
    boolean judgeApiProductName(String apiProductName);
}
