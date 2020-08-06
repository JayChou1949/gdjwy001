package com.hirisun.cloud.model.system;
import com.hirisun.cloud.model.common.Tree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 字典
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-06
 */
@Data
@ApiModel(value="SysDict视图对象", description="字典")
public class SysDictVO extends Tree<SysDictVO> implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "父级字典")
    private String pid;

    @ApiModelProperty(value = "名")
    private String name;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "父级值")
    private String pValue;

    private List<SysDictVO> children;

    @Override
    public String toString() {
        return "SysDict{" +
        "id=" + id +
        ", sort=" + sort +
        ", pid=" + pid +
        ", name=" + name +
        ", value=" + value +
        ", remark=" + remark +
        ", pValue=" + pValue +
        "}";
    }
}
