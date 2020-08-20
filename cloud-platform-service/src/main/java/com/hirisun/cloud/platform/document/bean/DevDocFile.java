package com.hirisun.cloud.platform.document.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 开发文档-文件系统绑定表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-11
 */
@TableName("T_DEV_DOC_FILE")
@ApiModel(value="DevDocFile对象", description="开发文档-文件系统绑定表")
public class DevDocFile implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "开发文档id")
    @TableField("DOC_ID")
    private String docId;

    @ApiModelProperty(value = "文件系统id")
    @TableField("FILE_ID")
    private String fileId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "DevDocFile{" +
        "id=" + id +
        ", docId=" + docId +
        ", fileId=" + fileId +
        "}";
    }
}
