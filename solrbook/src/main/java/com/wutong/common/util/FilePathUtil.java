package com.wutong.common.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class FilePathUtil {
    public static String uploadFile(String rootPath,String fileType) throws IOException {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String date = String.valueOf(calendar.get(Calendar.DATE));
        if (month.length()<2){
            month="0"+month;
        }
        if (date.length()<2){
            date="0"+date;
        }
        String filePath=rootPath+"/"+year+"/"+month+"/"+date;
        File file=new File(filePath);
//        System.out.println(filePath);
        if (!file.exists()){
            file.mkdirs();
        }
        String fileName=MD5HashUtils.getRandomUUID()+"."+fileType;
        File file1=new File(filePath+"/"+fileName);
        file1.createNewFile();
        return "/"+year+"/"+month+"/"+date+"/"+fileName;
    }
}
