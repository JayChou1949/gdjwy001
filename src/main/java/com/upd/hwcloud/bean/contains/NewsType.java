package com.upd.hwcloud.bean.contains;

/**
 * @author wuc
 * @date 2020/3/4
 */
public enum  NewsType {

    PROVINCE("省厅",1),
    AREA("地市",2),
    POLICE("警种",3),
    NATIONAL_PROJECT("国家专项",4);



    private final String type;

    private final Integer code;

    NewsType(String type,Integer code){
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }



    public Integer getCode() {
        return code;
    }

}
