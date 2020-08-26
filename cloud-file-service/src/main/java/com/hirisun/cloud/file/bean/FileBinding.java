package com.hirisun.cloud.file.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 主表-文件系统绑定表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-25
 */
@TableName("T_FILE_BINDING")
@ApiModel(value="FileBinding对象", description="主表-文件系统绑定表")
public class FileBinding implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "ID",type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "主表id")
    @TableField("MASTER_ID")
    private String masterId;

    @ApiModelProperty(value = "文件系统id")
    @TableField("FILE_ID")
    private String fileId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "FileBinding{" +
        "id=" + id +
        ", masterId=" + masterId +
        ", fileId=" + fileId +
        "}";
    }
}
