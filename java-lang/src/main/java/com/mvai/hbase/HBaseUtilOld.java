package com.mvai.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by wei.ma on 2017/3/29.
 */
public class HBaseUtilOld {
    private static Connection connection = null;

    /**
     * Initialization
     */
    static {
        try {
            Configuration conf = HBaseConfiguration.create();
            //和hbase-site.xml中配置的一致
            conf.set("hbase.zookeeper.quorum", "192.168.1.203,192.168.1.165,192.168.1.204");
            conf.set("hbase.zookeeper.property.clientPort", "2181");
            conf.set("hbase.rootdir", "hdfs://192.168.1.165:9000/hbase");
            connection = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getHTable() throws IOException {
        Table table = connection.getTable(TableName.valueOf("table1"));
        try {
            // Use the table as needed, for a single operation and a single thread
        } finally {
            table.close();
            connection.close();
        }
    }

    /**
     * 初始化HBase的配置文件
     *
     * @return
     */
    @Deprecated
    public static Configuration getConfiguration() {
        Configuration conf = HBaseConfiguration.create();
        //和hbase-site.xml中配置的一致
        conf.set("hbase.zookeeper.quorum", "192.168.1.203,192.168.1.165,192.168.1.204");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.rootdir", "hdfs://192.168.1.165:9000/hbase");
        return conf;
    }

    /**
     * 实例化HBaseAdmin,HBaseAdmin用于对表的元素据进行操作
     *
     * @return
     * @throws MasterNotRunningException
     * @throws ZooKeeperConnectionException
     */
    @Deprecated
    public static HBaseAdmin getHBaseAdminDeprecated() throws IOException {
        return new HBaseAdmin(getConfiguration());
    }

    public static Admin getHBaseAdmin() throws IOException {
        return connection.getAdmin();
    }

    /**
     * 创建表
     *
     * @param tableName      表名
     * @param columnFamilies 列族
     * @throws IOException
     */
    public static void createTable(String tableName, String... columnFamilies) throws IOException {
        Admin admin = getHBaseAdmin();
        if (admin.tableExists(TableName.valueOf(tableName))) {
            System.out.println("table already exists!");
        } else {
            HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
            for (int i = 0; i < columnFamilies.length; i++) {
                tableDesc.addFamily(new HColumnDescriptor(columnFamilies[i]));
            }
            admin.createTable(tableDesc);
            System.out.println("create table " + tableName + " ok.");
        }
    }

    /**
     * Delete a table
     */
    public static void deleteTable(String tableName) {
        try {
            Admin admin = getHBaseAdmin();
            admin.disableTable(TableName.valueOf(tableName));
            admin.deleteTable(TableName.valueOf(tableName));
            System.out.println("delete table " + tableName + " ok.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get HTableDescriptor
     */
    public static void getTableDesc(String tableName) {
        try {
            Admin admin = getHBaseAdmin();
            HTableDescriptor tableDesc = admin.getTableDescriptor(TableName.valueOf(tableName));
            HColumnDescriptor[] colDescs = tableDesc.getColumnFamilies();
            for (HColumnDescriptor colDesc : colDescs) {
                System.out.println(colDesc.toString());
            }
            System.out.println("*************");

            List<String> coprcess = tableDesc.getCoprocessors();
            for (String coprces : coprcess) {
                System.out.println(coprces);
            }

            System.out.println("*************");

            Set<byte[]> familiesKeys = tableDesc.getFamiliesKeys();
            for (byte[] familiesKey : familiesKeys) {
                System.out.println(new String(familiesKey));
            }

            System.out.println("*************");

            System.out.println(tableDesc.getValue("title"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取HTableDescriptor
     *
     * @param tableName
     * @return
     * @throws IOException
     */
    public static HTableDescriptor getHTableDescriptor(byte[] tableName) throws IOException {
        return getHBaseAdmin().getTableDescriptor(TableName.valueOf(tableName));
    }

    /**
     * 获取表
     *
     * @param tableName 表名
     * @return
     * @throws IOException
     */
    public static Table getHTable(String tableName) throws IOException {
        return connection.getTable(TableName.valueOf(tableName));
    }

    /**
     * 获取Put,Put是插入一行数据的封装格式
     *
     * @param row
     * @param columnFamily
     * @param qualifier
     * @param value
     * @return
     * @throws IOException
     */
    public static Put getPut(String row, String columnFamily, String qualifier, String value) throws IOException {
        Put put = new Put(row.getBytes());
        if (qualifier == null || "".equals(qualifier)) {
            put.addColumn(columnFamily.getBytes(), null, value.getBytes());
        } else {
            put.addColumn(columnFamily.getBytes(), qualifier.getBytes(), value.getBytes());
        }
        return put;
    }

    /**
     * 查询某一行的数据
     *
     * @param tableName 表名
     * @param row       行键
     * @return
     * @throws IOException
     */
    public static Result getResult(String tableName, String row) throws IOException {
        Get get = new Get(row.getBytes());
        Table htable = getHTable(tableName);
        Result result = htable.get(get);
        htable.close();
        return result;

    }

    /**
     * Put (or insert) a row
     */
    public static void addRecord(String tableName, String rowKey,
                                 String family, String qualifier, String value) throws Exception {
        Table table = getHTable(tableName);
        try {
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes
                    .toBytes(value));
            table.put(put);
            System.out.println("insert recored " + rowKey + " to table "
                    + tableName + " ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a row
     */
    public static void delRecord(String tableName, String rowKey)
            throws IOException {
        Table table = getHTable(tableName);
        List<Delete> list = new ArrayList<Delete>();
        Delete del = new Delete(rowKey.getBytes());
        list.add(del);
        table.delete(list);
        System.out.println("del recored " + rowKey + " ok.");
    }

    /**
     * Get a row
     */
    @Deprecated
    public static void getOneRecord(String tableName, String rowKey) throws IOException {
        Table table = getHTable(tableName);
        Get get = new Get(rowKey.getBytes());
        Result rs = table.get(get);
        for (KeyValue kv : rs.raw()) {
            System.out.print(new String(kv.getRow()) + " ");
            System.out.print(new String(kv.getFamily()) + ":");
            System.out.print(new String(kv.getQualifier()) + " ");
            System.out.print(kv.getTimestamp() + " ");
            System.out.println(new String(kv.getValue()));
        }
        System.out.println("*************");
        for (Cell cell : rs.rawCells()) {
            System.out.println(cell.toString());
            System.out.print(new String(CellUtil.getRowByte(cell, 1) + " "));
            System.out.print(new String(CellUtil.cloneFamily(cell)) + ":");
            System.out.print(new String(CellUtil.cloneQualifier(cell)) + " ");
            System.out.print(cell.getTimestamp() + " ");
//            System.out.println(new String(CellUtil.()));
        }
    }

    /**
     * Scan (or list) a table
     */
    public static void getAllRecord(String tableName) {
        try {
            Table table = getHTable(tableName);
            Scan s = new Scan();
            ResultScanner ss = table.getScanner(s);
            for (Result r : ss) {
                for (KeyValue kv : r.raw()) {
                    System.out.print(new String(kv.getRow()) + " ");
                    System.out.print(new String(kv.getFamily()) + ":");
                    System.out.print(new String(kv.getQualifier()) + " ");
                    System.out.print(kv.getTimestamp() + " ");
                    System.out.println(new String(kv.getValue()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 条件查询
     *
     * @param tableName      表名
     * @param columnFamily   列族
     * @param queryCondition 查询条件值
     * @param begin          查询的起始行
     * @param end            查询的终止行
     * @return
     * @throws IOException
     */
    public static ResultScanner getResultScanner(String tableName, String columnFamily, String queryCondition, String begin, String end) throws IOException {

        Scan scan = new Scan();
        //设置起始行
        scan.setStartRow(Bytes.toBytes(begin));
        //设置终止行
        scan.setStopRow(Bytes.toBytes(end));

        //指定要查询的列族
        scan.addColumn(Bytes.toBytes(columnFamily), null);
        //查询列族中值等于queryCondition的记录
        Filter filter1 = new SingleColumnValueFilter(Bytes.toBytes(columnFamily), null, CompareFilter.CompareOp.EQUAL, Bytes.toBytes(queryCondition));
        //Filter filter2 = new SingleColumnValueFilter(Bytes.toBytes(columnFamily),null,CompareOp.EQUAL,Bytes.toBytes("chuliuxiang"));

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE, Arrays.asList(filter1));

        scan.setFilter(filterList);
        Table htable = getHTable(tableName);

        ResultScanner rs = htable.getScanner(scan);
        htable.close();
        return rs;
    }

}
