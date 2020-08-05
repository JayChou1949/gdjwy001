package com.hirisun.cloud.file.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoufeng
 * @version 1.0
 * @className FileSystem
 * @data 2020/7/22 17:09
 * @description 文件系统
 */
@TableName("T_FILE_SYSTEM")
@Data
@Accessors(chain = true)
public class FileSystem  implements Serializable {

    /**
     * 文件ID，主键
     */
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 文件请求路径
     */
    @TableField("FILE_PATH")
    private String filePath;

    /**
     * 文件大小
     */
    @TableField("FILE_SIZE")
    private long fileSize;

    /**
     * 文件名称
     */
    @TableField("FILE_NAME")
    private String fileName;

    /**
     * 文件类型
     */
    @TableField("FILE_TYPE")
    private String fileType;

    /**
     * 文件状态（0正常、1删除）
     */
    @TableField("FILE_STATE")
    private Integer fileState;

    /**
     * 业务key
     */
    @TableField("BUSINESS_KEY")
    private String businessKey;

    /**
     * 业务标签
     */
    @TableField("BUSINESS_TAG")
    private String businessTag;

    /**
     * 创建日期
     */
    @TableField("CREATE_DATE")
    private Date createDate;

    /**
     * 更新日期
     */
    @TableField("UPDATE_DATE")
    private Date updateDate;

}
