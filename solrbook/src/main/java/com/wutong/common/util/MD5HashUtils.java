/***********************************************
 * File Name: MD5HashUtils
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 06 03 2019 11:37
 ***********************************************/

package com.wutong.common.util;

import com.google.common.base.Strings;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 本类主要实现对用户的密码进行加密和比对
 * 主要功能如下：
 * 1.对用户输入的密码以及盐值进行加密
 * 2.对用户输入的手机号和密码进行比对
 * 3.对用户输入的手机号和验证码进行比对(此方法2相同)
 */
public class MD5HashUtils {

    /**
     * 对单一字符串进行MD5加密
     * @param code
     * @return
     */
    public static String getMD5Hash(String code){
        return new Md5Hash(code).toString();
    }

    /**
     * 对密码和盐值进行加密
     * @param code 需要加密的数据
     * @param salt 数据的盐值
     * @return 加密后的密码
     */
    public static String getMD5HashWithSalt(String code,String salt){
         return new Md5Hash(code,salt,3).toString();
    }

    /**
     * 对用户输入的密码进行比对
     * @param inputCode 用户输入的密码
     * @param hashedCode  数据库查询得到的密码
     * @param salt  数据库查询得到的盐值
     * @return 是否匹配（true表示匹配，false表示不匹配）
     */
    public static boolean matchMD5Hash(String inputCode,String hashedCode,String salt){
        String s = new Md5Hash(inputCode, salt, 3).toString();
        if (s.equals(hashedCode)){
            return true;
        }
        return false;
    }

    /**
     * 获取盐值
     * @return
     */
    public static String getSalt(){
        return new SecureRandomNumberGenerator().nextBytes().toHex();
    }

    /**
     * 获取uuid
     * @return
     */
    public static String getRandomUUID(){
        return getMD5HashWithSalt(UUID.randomUUID().toString().replaceAll("-", ""), String.valueOf(new Date().getTime())).toUpperCase();
    }


    public static String getMD5HashSignString(Map<String,String> map){
        map.forEach((k,v)->{
            if (Strings.isNullOrEmpty(v)){
                return;
            }
        });

        String[] strings = map.keySet().toArray(new String[0]);
        sort(strings);
        StringBuffer stringBuffer= new StringBuffer();
        String salt = "HelloWorld";
        for (String string : strings) {
            if ("sign".equalsIgnoreCase(string)){
                continue;
            }
            stringBuffer.append(string);
            stringBuffer.append(map.get(string));
        }
        stringBuffer.append(salt);
        System.out.println(stringBuffer);
        String sign = new Md5Hash(stringBuffer.toString()).toString();
        return sign;
    }

    public static boolean getMD5HashSign(Map<String,String> map){
        map.forEach((k,v)->{
            if (Strings.isNullOrEmpty(v)){
                return;
            }
        });
        if (Math.abs(new Date().getTime()/1000-Long.parseLong(map.get("time")))>120){
            return false;
        }
        String[] strings = map.keySet().toArray(new String[0]);
        sort(strings);
        StringBuffer stringBuffer= new StringBuffer();
        String salt = "HelloWorld";
        for (String string : strings) {
            if ("sign".equalsIgnoreCase(string)){
                continue;
            }
            stringBuffer.append(string);
            stringBuffer.append(map.get(string));
        }
        stringBuffer.append(salt);
        System.out.println(stringBuffer);
        String sign = new Md5Hash(stringBuffer.toString()).toString();
        System.out.println("sign===="+sign);
        if (map.get("sign").equalsIgnoreCase(sign)){
            return true;
        }
        return false;
    }

    private static void sort(String[] x) {
        for (int i = 0; i < x.length - 1; i++) {
            for (int j = 0; j < x.length - i - 1; j++) {
                if (x[j].length() < x[j + 1].length()) {
                    String temp = x[j];
                    x[j] = x[j + 1];
                    x[j + 1] = temp;
                } else if (x[j].length() == x[j + 1].length()){
                    if (x[j].compareTo(x[j+1])>0){
                        String temp = x[j];
                        x[j] = x[j + 1];
                        x[j + 1] = temp;
                    }
                }
            }
        }
    }
}
