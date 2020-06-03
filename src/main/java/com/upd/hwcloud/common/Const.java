package com.upd.hwcloud.common;

/**
 * @author: yyc
 * @Date: 14/05/2019 5:04 PM
 */
public class Const {

    public enum PaasResourceEnum{

        FSRXSB("广东科信飞识人像识别服务","78cbcac9-8112-fb4a-22fb-77a887a044b4"),
        TXRLSB("广东科信腾讯人脸识别服务","f03e0c81-5350-1dab-9e53-86cb2bb82bcb"),
        TYYH("广东科信统一用户服务","572dfaf8-922a-e8c2-225f-87bc4673a135");

        PaasResourceEnum(String name,String guid){
            this.name = name;
            this.guid = guid;
        }

        private String name;
        private String guid;

        public String getName() {
            return name;
        }

        public String getGuid() {
            return guid;
        }


        public static String getGuidByName(String name){
            PaasResourceEnum[] paasResourceEnums = values();
            for (PaasResourceEnum paasResourceEnum : paasResourceEnums){
                if(paasResourceEnum.name.equals(name)){
                    return paasResourceEnum.guid;
                }
            }
            return null;
        }
    }
}
