package com.upd.hwcloud.bean.contains;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class UserTitleType {

    private static final Map<String, String> TITLE = new HashMap<>();

    static {
        TITLE.put("0130", "正部职");
        TITLE.put("0131", "正部级");
        TITLE.put("0132", "副部职");
        TITLE.put("0141", "副部级");
        TITLE.put("0142", "正厅职");
        TITLE.put("0143", "正厅级");
        TITLE.put("0150", "副厅职");
        TITLE.put("0153", "副厅级");
        TITLE.put("0154", "正处职");
        TITLE.put("0155", "正处级");
        TITLE.put("0156", "副处职");
        TITLE.put("0160", "副处级");
        TITLE.put("0161", "正科职");
        TITLE.put("0162", "正科级");
        TITLE.put("0163", "副科职");
        TITLE.put("0164", "副科级");
        TITLE.put("0165", "正股职");
        TITLE.put("0166", "正股级");
        TITLE.put("0170", "副股职");
        TITLE.put("0171", "副股级");
        TITLE.put("0172", "科员");
        TITLE.put("0173", "办事员");
        TITLE.put("0175", "未定职公务员");
        TITLE.put("0176", "一级警员");
        TITLE.put("0177", "二级警员");
        TITLE.put("0178", "三级警员");
        TITLE.put("0179", "四级警员");
        TITLE.put("0180", "一级警长");
        TITLE.put("0181", "二级警长");
        TITLE.put("0182", "三级警长");
        TITLE.put("0183", "四级警长");
        TITLE.put("0184", "一级高级警长");
        TITLE.put("0185", "二级高级警长");
        TITLE.put("0186", "三级高级警长");
        TITLE.put("0187", "四级高级警长");
        TITLE.put("0188", "一级技术主任");
        TITLE.put("0189", "二级技术主任");
        TITLE.put("0190", "三级技术主任");
        TITLE.put("0191", "四级技术主任");
        TITLE.put("0192", "一级技术主管");
        TITLE.put("0193", "二级技术主管");
        TITLE.put("0194", "三级技术主管");
        TITLE.put("0195", "四级技术主管");
        TITLE.put("0196", "一级技术员");
        TITLE.put("0197", "二级技术员");
        TITLE.put("0198", "三级技术员");
        TITLE.put("0199", "四级技术员");
    }

    /**
     * 根据职级编码查询职级名称
     * @param code 职级编码
     * @return title or null
     */
    public static String getTitleByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return TITLE.get(code);
    }

}
