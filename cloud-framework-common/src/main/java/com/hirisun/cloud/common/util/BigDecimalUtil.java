package com.hirisun.cloud.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Description:
 * @author: yyc
 * @date: 2019-08-26 16:03
 **/
public class BigDecimalUtil {
    private BigDecimalUtil(){

    }

    public static BigDecimal add(double v1, double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    public static  BigDecimal sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }


    public static  BigDecimal mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    public static  BigDecimal div(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);
    }
    public static  BigDecimal div4(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,4,BigDecimal.ROUND_HALF_UP);
    }

    public static String formatBigNum2Str(BigDecimal num,int scale,String unit){
        String value;
        if (num.doubleValue() > 100000000){
            value = num.divide(new BigDecimal(100000000), scale, RoundingMode.HALF_UP)+"亿"+unit;
        }else if (num.doubleValue() > 10000){
            value = num.divide(new BigDecimal(10000), scale, RoundingMode.HALF_UP)+"万"+unit;
        }else {
            value = num + unit;
        }
        return value;
    }

}
