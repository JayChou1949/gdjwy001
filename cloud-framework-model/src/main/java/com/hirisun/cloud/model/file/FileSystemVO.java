package com.hirisun.cloud.model.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wuxiaoxing
 * @description 文件系统视图对象
 */
@Data
public class FileSystemVO implements Serializable {

    /**
     * 文件ID，主键
     */
    @ApiModelProperty(value = "文件ID，主键")
    private String id;

    /**
     * 文件请求路径
     */
    @ApiModelProperty(value = "文件请求路径")
    private String filePath;

    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    private long fileSize;

    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    private String fileType;

    /**
     * 文件状态（0正常、1删除）
     */
    @ApiModelProperty(value = "文件状态（0正常、1删除）")
    private Integer fileState;

    /**
     * 业务key
     */
    @ApiModelProperty(value = "业务key")
    private String businessKey;

    /**
     * 业务标签
     */
    @ApiModelProperty(value = "业务标签")
    private String businessTag;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date updateDate;

}
