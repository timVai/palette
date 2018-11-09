package com.mvai.hbase;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by wei.ma on 2017/3/29.
 */
public class HBaseUtilTest {
    @Test
    public void testCreateTable() {
        //创建表
        HBaseUtils.createTable("myTest", "myfc1", "myfc2", "myfc3");
        HBaseUtils.close();
        HBaseUtils.createTable("myTest02", "myfc1", "myfc2", "myfc3");
        HBaseUtils.close();
    }

    @Test
    public void testDropTable() {
        //删除表
        HBaseUtils.dropTable("myTest");
        HBaseUtils.dropTable("myTest02");
    }

    @Test
    public void testInsert() {
        //插入数据
        HBaseUtils.insert("myTest", "1", "myfc1", "sex", "men");
        HBaseUtils.insert("myTest", "1", "myfc1", "name", "xiaoming");
        HBaseUtils.insert("myTest", "1", "myfc1", "age", "32");
        HBaseUtils.insert("myTest", "1", "myfc2", "name", "xiaohong");
        HBaseUtils.insert("myTest", "1", "myfc2", "sex", "woman");
        HBaseUtils.insert("myTest", "1", "myfc2", "age", "23");
    }

    @Test
    public void testByGet() {
        //得到一行下一个列族下的某列的数据
        String result = HBaseUtils.byGet("myTest", "1", "myfc1", "name");
        System.out.println("结果是的： " + result);
        assertEquals("xiaosan", result);
    }

    @Test
    public void testByGet02() {
        //得到一行下一个列族下的所有列的数据
        Map<String, String> result = HBaseUtils.byGet("myTest", "1", "myfc1");
        System.out.println("结果是的： " + result);
        assertNotNull(result);
    }


    @Test
    public void testByGet03() {
        //得到一行的所有列族的数据
        Map<String, Map<String, String>> result = HBaseUtils.byGet("myTest", "1");

        System.out.println("所有列族的数据是:  " + result);
        System.out.println("结果是的： " + result.get("myfc1"));
        assertNotNull(result);
    }

    @Test
    public void testgetOneRecord() throws IOException {
        String tableName = "toutiao_article";
        System.out.println("&&&&&&&&&得到 一行的 所有列族的  数据&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println(HBaseUtils.byGet(tableName, "6358747385183879426"));
        System.out.println("&&&&&&&&&得到 一行下 一个列族下 的 所有列  的数据&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println(HBaseUtils.byGet(tableName, "6358747385183879426", "cf"));
        System.out.println("&&&&&&&&&&&&&得到 一行下 一个列族 下的 某列 的数据&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println(HBaseUtils.byGet(tableName, "6358747385183879426", "cf", "content"));
    }

    @Test
    public void testScan() throws IOException {
        String tableName = "toutiao_article";
//        HBaseUtils.getResultScanner(tableName, "cf", "title", new String(HConstants.EMPTY_START_ROW), new String(HConstants.EMPTY_START_ROW), new String(HConstants.EMPTY_END_ROW));
        ResultScanner rs = HBaseUtils.getAllRecord(tableName);
        saveContent(rs);
    }

    private void saveContent(ResultScanner rs) {
        String fileName = "F:\\db12\\nlp_\\java_save.txt";
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName))));
            for (Result result : rs) {
                /*得到单元格集合*/
                List<Cell> cs = result.listCells();
                for (Cell cell : cs) {
                    //取到修饰名
                    String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                    //取到值
                    String value = Bytes.toString(CellUtil.cloneValue(cell));

                    if (qualifier.equals("content") && !value.equals("-1")) {
                        writer.write((value.trim().replace("\n", "") + "\n").toCharArray());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processResultAll(ResultScanner rs) {
        for (Result result : rs) {
                /*得到单元格集合*/
            List<Cell> cs = result.listCells();
            for (Cell cell : cs) {
                //取行健
                String rowKey = Bytes.toString(CellUtil.cloneRow(cell));
                //取到时间戳
                long timestamp = cell.getTimestamp();
                //取到族列
                String family = Bytes.toString(CellUtil.cloneFamily(cell));
                //取到修饰名
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                //取到值
                String value = Bytes.toString(CellUtil.cloneValue(cell));

                System.out.println(" ===> rowKey : " + rowKey + ",  timestamp : " +
                        timestamp + ", family : " + family + ", qualifier : " + qualifier + ", value : " + value);
            }
        }
    }

}
