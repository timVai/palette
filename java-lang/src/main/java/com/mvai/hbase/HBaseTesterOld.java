//package com.mvai.hbase;
//
//import org.apache.hadoop.hbase.client.*;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by wei.ma on 2017/3/29.
// */
//public class HBaseTesterOld {
//
////    String tableName = "MY_TABLE_NAME_TOO";
//    String tableName = "toutiao_article";
//
//    @Test
//    public void testCreateTable() throws IOException {
//        HBaseUtilOld.createTable(tableName, "username", "password");
//    }
//
//    @Test
//    public void testDeleteTable() throws IOException {
//        HBaseUtilOld.deleteTable(tableName);
//    }
//
//    @Test
//    public void testgetTableDesc() throws IOException {
//        HBaseUtilOld.getTableDesc(tableName);
//    }
//
//    //测试插入数据
//    @Test
//    public void testInsertData() throws IOException {
//        Table htable = HBaseUtilOld.getHTable(tableName);
//        List<Put> puts = new ArrayList<Put>();
//        Put put1 = HBaseUtilOld.getPut("1", "username", null, "chuliuxiang");
//        puts.add(put1);
//        Put put2 = HBaseUtilOld.getPut("1", "password", null, "654321");
//        puts.add(put2);
//        htable.put(puts);
//        htable.close();
//    }
//
//    //测试获取一行数据
//    @Test
//    public void testGetRow() throws IOException {
//        Result result = HBaseUtilOld.getResult(tableName, "1");
//        byte[] userName = result.getValue("cf".getBytes(), null);
////        byte[] password = result.getValue("password".getBytes(), null);
////        System.out.println("username is: " + Bytes.toString(userName) + " passwd is :" + Bytes.toString(password));
//        System.out.println("username is: " + Bytes.toString(userName));
//    }
//
//
//
//
//    //测试条件查询
//    @Test
//    public void testScan() throws IOException {
//        ResultScanner rs = HBaseUtilOld.getResultScanner(tableName, "username", "chuliuxiang", "1", "10");
//        for (Result r : rs) {
//            System.out.println(Bytes.toString(r.getValue(Bytes.toBytes("username"), null)));
//        }
//    }
//}
