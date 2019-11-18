/***********************************************
 * File Name: PatternTest
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 18 11 2019 21:07
 ***********************************************/

package com.wutong.serviceImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
    public static void main(String[] args) {
        Pattern pattern=Pattern.compile("\\w*");
        String a ="中国人民adadf465f456";
        Matcher matcher = pattern.matcher(a);
        while (matcher.find()){
            if (matcher.group().length()>0){
                System.out.println(matcher.group(0).trim());
            }
        }
    }
}
