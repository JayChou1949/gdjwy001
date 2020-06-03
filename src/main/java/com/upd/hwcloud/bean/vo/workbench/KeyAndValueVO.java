package com.upd.hwcloud.bean.vo.workbench;

import com.upd.hwcloud.bean.entity.Saas;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wuc
 * @date 2019/12/9
 */
@Data
@AllArgsConstructor
public class KeyAndValueVO {
    private String key;
    private Long value;
}
