/***********************************************
 * File Name: TestMysql
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 23 10 2019 下午 12:36
 ***********************************************/

package com.wutong;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.*;
import java.util.Arrays;
import java.util.Comparator;

public class TestMysql {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, UnsupportedEncodingException {
        File file = new File("E:\\变电运检五项通用制度\\变电运维通用管理规定及细则\\");
        String pathName = file.getName();
        String[] files = file.list();
        Arrays.sort(files, new StringComparator());
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://39.106.33.181:3306/crawl?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true", "root", "root");
        PreparedStatement ps = null;

        connection.setAutoCommit(false);
        String sql = "insert into book (course_id,book_name,book_addr) values (?,?,?)";
        ps = connection.prepareStatement(sql);
        String bookName = "";
        String bookAddr = "";
        for (String s : files) {
            bookName = s.substring(s.indexOf("第"),s.indexOf(".doc"));
            System.out.println(bookName);
            bookAddr = "https://doc.tjrckj.net/" + pathName + "/" + s;
            System.out.println(bookAddr);
            ps.setInt(1, 5);
            ps.setString(2, bookName);
            ps.setString(3, bookAddr);
            ps.addBatch();
        }
        ps.executeBatch();
        connection.commit();

        ps.close();
        connection.close();
    }

    private static class StringComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            int l1 = Integer.parseInt(o1.substring(o1.indexOf("第") + 1, o1.indexOf("分册")));
            int l2 = Integer.parseInt(o2.substring(o2.indexOf("第") + 1, o2.indexOf("分册")));
            return l1 - l2;
        }
    }
}
