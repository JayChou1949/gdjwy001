package com.hirisun.cloud.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ResponseResult
 * @Author zhoufeng
 * @Date 2019/11/14 10:32
 * @Description 响应结果
 */
@Data
@NoArgsConstructor
@ApiResponses(value = { @ApiResponse(code = 200, message = "成功") })
public class ResponseResult<T> implements Response {
	
	@ApiModelProperty(value="状态码200成功")
	Integer code = SUCCESS_CODE;
	
	@ApiModelProperty(value="状态信息")
    String msg = SUCCESS_MSG;

    @ApiModelProperty(value = "数据")
    private T data;

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(ResultCode resultCode) {
        this.code = resultCode.code();
        this.msg = resultCode.msg();
    }

    public static ResponseResult success(){
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public static ResponseResult fail(){
        return new ResponseResult(CommonCode.FAIL);
    }
}
