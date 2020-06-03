package com.upd.hwcloud.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by hisen on 17-8-26.
 * E-mail: hisenyuan@gmail.com
 */
public class MD5Util {

  public static String toMd5(String str) {
    return DigestUtils.md5Hex(str);
  }

  public static String md5bit16(String str) {
    return MD5Util.toMd5(str).substring(8, 24);
  }


  public static void main(String[] args) {
    System.out.println(toMd5("hisen"));
  }
}
