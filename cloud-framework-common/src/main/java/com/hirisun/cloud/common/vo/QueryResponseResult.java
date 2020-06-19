package com.hirisun.cloud.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName QueryResponseResult
 * @Author zhoufeng
 * @Date 2019/11/14 10:58
 * @Description 查询响应结果
 */

@Data
@NoArgsConstructor
public class QueryResponseResult extends ResponseResult {
    Object data;

    public QueryResponseResult(ResultCode resultCode, Object data) {
        super(resultCode);
        this.data = data;
    }

    public static QueryResponseResult success(Object data) {
        return new QueryResponseResult(CommonCode.SUCCESS, data);
    }

    public static QueryResponseResult fail(Object data) {
        return new QueryResponseResult(CommonCode.FAIL, data);
    }
}
