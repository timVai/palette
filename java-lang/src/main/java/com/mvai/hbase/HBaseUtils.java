//package com.mvai.hbase;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.*;
//import org.apache.hadoop.hbase.client.*;
//import org.apache.hadoop.hbase.filter.CompareFilter;
//import org.apache.hadoop.hbase.filter.Filter;
//import org.apache.hadoop.hbase.filter.FilterList;
//import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
//import org.apache.hadoop.hbase.util.Bytes;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by wei.ma on 2017/3/29.
// */
//public class HBaseUtils {
//    private static Configuration conf;
//    private static Connection con;
//
//    /**
//     * 初始化连接
//     *
//     */
//    static {
//        conf = HBaseConfiguration.create(); // 获得配制文件对象
//        //和hbase-site.xml中配置的一致
//        conf.set("hbase.zookeeper.quorum", "192.168.1.203,192.168.1.165,192.168.1.204");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
//        conf.set("hbase.rootdir", "hdfs://192.168.1.165:9000/hbase");
//        try {
//            con = ConnectionFactory.createConnection(conf);// 获得连接对象
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 获得连接
//    public static Connection getCon() {
//        if (con == null || con.isClosed()) {
//            try {
//                con = ConnectionFactory.createConnection(conf);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return con;
//    }
//
//    /**
//     * 关闭连接
//     */
//    public static void close() {
//        if (con != null) {
//            try {
//                con.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 创建表
//     */
//    public static void createTable(String tableName, String... FamilyColumn) {
//        TableName tn = TableName.valueOf(tableName);
//        try {
//            Admin admin = con.getAdmin();
//            HTableDescriptor htd = new HTableDescriptor(tn);
//            for (String fc : FamilyColumn) {
//                HColumnDescriptor hcd = new HColumnDescriptor(fc);
//                htd.addFamily(hcd);
//            }
//            admin.createTable(htd);
//            admin.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 删除表
//     */
//    public static void dropTable(String tableName) {
//        TableName tn = TableName.valueOf(tableName);
//        try {
//            Admin admin = con.getAdmin();
//            admin.disableTable(tn);
//            admin.deleteTable(tn);
//            admin.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 插入或者更新数据
//     */
//    public static boolean insert(String tableName, String rowKey,
//                                 String family, String qualifier, String value) {
//        try {
//            Table t = con.getTable(TableName.valueOf(tableName));
//            Put put = new Put(Bytes.toBytes(rowKey));
//            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
//            t.put(put);
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            HBaseUtils.close();
//        }
//        return false;
//    }
//
//    /**
//     * 删除
//     */
//    public static boolean del(String tableName, String rowKey, String family,
//                              String qualifier) {
//        try {
//            Table t = con.getTable(TableName.valueOf(tableName));
//            Delete del = new Delete(Bytes.toBytes(rowKey));
//
//            if (qualifier != null) {
//                del.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
//            } else if (family != null) {
//                del.addFamily(Bytes.toBytes(family));
//            }
//            t.delete(del);
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            HBaseUtils.close();
//        }
//        return false;
//    }
//
//    /**
//     * 删除一行
//     */
//    public static boolean del(String tableName, String rowKey) {
//        return del(tableName, rowKey, null, null);
//    }
//
//    /**
//     * 删除一行下的一个列族
//     */
//    public static boolean del(String tableName, String rowKey, String family) {
//        return del(tableName, rowKey, family, null);
//    }
//
//    /**
//     * 数据读取
//     * 得到 一行下 一个列族 下的 某列 的数据
//     */
//    public static String byGet(String tableName, String rowKey, String family, String qualifier) {
//        try {
//            Table t = con.getTable(TableName.valueOf(tableName));
//            Get get = new Get(Bytes.toBytes(rowKey));
//            get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
//            Result r = t.get(get);
//            return Bytes.toString(CellUtil.cloneValue(r.listCells().get(0)));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            HBaseUtils.close();
//        }
//        return null;
//    }
//
//    /**
//     * 取到一个族列的值
//     * 得到 一行下 一个列族下 的 所有列  的数据
//     */
//    public static Map<String, String> byGet(String tableName, String rowKey, String family) {
//        Map<String, String> result = null;
//        try {
//            Table t = con.getTable(TableName.valueOf(tableName));
//            Get get = new Get(Bytes.toBytes(rowKey));
//            get.addFamily(Bytes.toBytes(family));
//            Result r = t.get(get);
//            List<Cell> cs = r.listCells();
//            result = cs.size() > 0 ? new HashMap<String, String>() : result;
//            for (Cell cell : cs) {
//                result.put(Bytes.toString(CellUtil.cloneQualifier(cell)), Bytes.toString(CellUtil.cloneValue(cell)));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            HBaseUtils.close();
//        }
//        return result;
//    }
//
//    /**
//     * 取到多个族列的值
//     * 得到 一行的 所有列族的  数据
//     */
//    public static Map<String, Map<String, String>> byGet(String tableName, String rowKey) {
//        Map<String, Map<String, String>> results = null;
//        try {
//            Table t = con.getTable(TableName.valueOf(tableName));
//            Get get = new Get(Bytes.toBytes(rowKey));
//            Result r = t.get(get);
//            List<Cell> cs = r.listCells();
//            results = cs.size() > 0 ? new HashMap<String, Map<String, String>>() : results;
//            for (Cell cell : cs) {
//                String familyName = Bytes.toString(CellUtil.cloneFamily(cell));
//                if (results.get(familyName) == null) {
//                    results.put(familyName, new HashMap<String, String>());
//                }
//                results.get(familyName).put(Bytes.toString(CellUtil.cloneQualifier(cell)), Bytes.toString(CellUtil.cloneValue(cell)));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            HBaseUtils.close();
//        }
//        return results;
//    }
//
//    /**
//     * 批量得到数据
//     */
//    public static ResultScanner getAllRecord(String tableName) {
//        ResultScanner rs = null;
//        try {
//            Table t = con.getTable(TableName.valueOf(tableName));
//            Scan s = new Scan();
//            s.setBatch(2000);
//            s.setCaching(5000);
//            /*得到扫描的结果集*/
//            rs = t.getScanner(s);
////            for (Result result : rs) {
////                /*得到单元格集合*/
////                List<Cell> cs = result.listCells();
////                for (Cell cell : cs) {
////                    //取行健
////                    String rowKey = Bytes.toString(CellUtil.cloneRow(cell));
////                    //取到时间戳
////                    long timestamp = cell.getTimestamp();
////                    //取到族列
////                    String family = Bytes.toString(CellUtil.cloneFamily(cell));
////                    //取到修饰名
////                    String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
////                    //取到值
////                    String value = Bytes.toString(CellUtil.cloneValue(cell));
////
////                    System.out.println(" ===> rowKey : " + rowKey + ",  timestamp : " +
////                            timestamp + ", family : " + family + ", qualifier : " + qualifier + ", value : " + value);
////                }
////            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return rs;
//    }
//
//    /**
//     * 条件查询
//     *
//     * @param tableName      表名
//     * @param columnFamily   列族
//     * @param queryCondition 查询条件值
//     * @param begin          查询的起始行
//     * @param end            查询的终止行
//     * @return 1646333
//     * @throws IOException
//     */
//    public static ResultScanner getResultScanner(String tableName, String columnFamily, String qualifier,
//                                                 String queryCondition, String begin, String end) throws IOException {
//        ResultScanner rs = null;
//        try {
//            Table t = con.getTable(TableName.valueOf(tableName));
//            Scan scan = new Scan();
//            //设置起始行
//            scan.setStartRow(Bytes.toBytes(begin));
//            //设置终止行
//            scan.setStopRow(Bytes.toBytes(end));
//            //指定要查询的列族
//            scan.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier));
//            //查询列族中值等于queryCondition的记录
//            Filter filter1 = new SingleColumnValueFilter(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier), CompareFilter.CompareOp.EQUAL, Bytes.toBytes(queryCondition));
//            FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE, Arrays.asList(filter1));
//            scan.setFilter(filterList);
//            rs = t.getScanner(scan);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return rs;
//    }
//}
