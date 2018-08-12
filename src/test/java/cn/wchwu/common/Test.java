/**
 *Copyright (c) 2018, ShangHai HOWBUY INVESTMENT MANAGEMENT Co., Ltd.
 *All right reserved.
 *
 *THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF HOWBUY INVESTMENT
 *MANAGEMENT CO., LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED
 *TO THIRD PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *WITHOUT THE PRIOR WRITTEN PERMISSION OF HOWBUY INVESTMENT MANAGEMENT
 * CO., LTD.
*/

package cn.wchwu.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Test {
    public static void main(String[] args) {
        File file = new File("E:/crm/grade.txt");
        System.out.println(txt2String(file));
    }

    public static String txt2String(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
            int i = 0;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                i++;
                // String[] split = s.split(" ");
                // result.append(System.lineSeparator() + insertSql(split[0],
                // split[1], i));
                result.append(System.lineSeparator() + insertSql(s.substring(0, 2), s.substring(2), i));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String insertSql(String dictId, String dictName, int sortno) {
        String sql = "insert into sys_dict_entry(dicttypeid, dictid, dictname, STATUS, sortno, rank, parentid, seqno, filter1, filter2 ) ";
        sql += "values(";
        sql += "'DEGREE',";
        sql += "'" + dictId + "',";
        sql += "'" + dictName + "',";
        sql += "'0',";
        sql += "" + sortno + ",";
        sql += "0,";
        sql += "'',";
        sql += "'',";
        sql += "'',";
        sql += "''";
        sql += ");";
        return sql;
    }
}
