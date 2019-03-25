//package com.mvai.hbase;
//
//import com.moji.utils.FileUtil;
//import org.apache.hadoop.hbase.Cell;
//import org.apache.hadoop.hbase.CellUtil;
//import org.apache.hadoop.hbase.client.Result;
//import org.apache.hadoop.hbase.client.ResultScanner;
//import org.apache.hadoop.hbase.util.Bytes;
//
//import java.io.*;
//import java.util.*;
//
///**
// * Created by wei.ma on 2017/3/30.
// */
//public class MainTest {
//    static long time;
////    static String tableName = "toutiao_article";
//    static String tableName = "tt_article_new";
////    static String filePath = "F:\\db12\\nlp_\\tag_t_c_0331.txt";
////    private static String tagPath = "F:\\db12\\nlp_\\tag_0411.txt";
////    private static String tagCountPath = "F:\\db12\\nlp_\\count_tag_0411.txt";
//
//    //    static String filePath = "/home/hadoop/mw/tag_0411.txt";
////    static String tag_title_content = "/home/hadoop/mw/tag_title_content_0420.txt";
//    static String tag_title_content = "F:\\moji\\new_new.txt";
////    static String tag_title_content = "/moji/nlp/vocab/old_news.txt";
////    static String tagPath = "/home/hadoop/mw/tag_0411.txt";
//    static String tagPath = "F:\\moji\\tag_1218.txt";
//    static String tagCountPath = "F:\\moji\\count_tag_1218.txt";
//
//    public static void main(String[] args) {
//
//        /*接收命令行参数*/
//        ResultScanner rs = HBaseUtils.getAllRecord(tableName);
////        saveContent(rs, filePath);
////        saveTagTitleContent(rs, tag_title_content);
////        saveTagTitleContentV2(rs, tag_title_content);
////        saveTagTitleContentV3(rs, tag_title_content);
//        saveTag(rs, tagPath);
//
//        /*得到一个key的一个列簇的所有列数据*/
////        String category = HBaseUtils.byGet(tableName, "3436512094", "cf", "category");
////        String category = HBaseUtils.byGet(tableName, "6465547710639374862", "cf", "category");
////        System.out.println("category -> " + category);
////        Map<String, String> rowCfCoulums = HBaseUtils.byGet(tableName, "6465547710639374862", "cf");
////        for (Map.Entry<String, String> item : rowCfCoulums.entrySet()) {
////            System.out.println(item.getKey() + " -> " + item.getValue());
////        }
//    }
//
//    private static void saveTag(ResultScanner rs, String filePath) {
//        Writer writer = null;
//        Map<String, Object> tagMap = new HashMap<String, Object>();
//        try {
//            time = System.currentTimeMillis();
//            for (Result result : rs) {
//                /*得到单元格集合*/
//                List<Cell> cs = result.listCells();
//                for (Cell cell : cs) {
//                    //取到修饰名
//                    String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
//                    //取到值
//                    String value = Bytes.toString(CellUtil.cloneValue(cell));
//                    if (qualifier.equals("news_tag") && !value.equals("-1")) {
//                        String[] labels = value.split(",");
//                        for (int i = 0; i < labels.length; i++) {
//                            if (tagMap.containsKey(labels[i])) {
//                                Integer count = (Integer) tagMap.get(labels[i]);
//                                tagMap.put(labels[i], ++count);
//                            } else {
//                                tagMap.put(labels[i], 1);
//                            }
//                        }
//                    }
//                }
//            }
//            /*保存map*/
//            Set<Map.Entry<String, Object>> set = tagMap.entrySet();
//            Iterator<Map.Entry<String, Object>> iterator = set.iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, Object> strTag = iterator.next();
//                FileUtil.saveFile(new File(tagPath), strTag.getKey() + "\n", "UTF-8", true);
//                FileUtil.saveFile(new File(tagCountPath), strTag.getKey() + "\t" + strTag.getValue() + "\n", "UTF-8", true);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                System.out.println("saveContent= " + (System.currentTimeMillis() - time) + " tag的长度= " + tagMap.size());
////                writer.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    private static void saveTagTitleContentV3(ResultScanner rs, String filePath) {
//        Writer writer = null;
//        int count = 0;
//        try {
//            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)), "UTF-8"));
//            StringBuilder builder = new StringBuilder();
//            time = System.currentTimeMillis();
//            for (Result result : rs) {
//                /*得到单元格集合*/
//                List<Cell> cs = result.listCells();
//                String news_tag = "";
//                String title = "";
//                String content = "";
//                String category_ = "";
//                for (Cell cell : cs) {
//                    //取到修饰名
//                    String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
//                    //取到值
//                    String value = Bytes.toString(CellUtil.cloneValue(cell));
//
//
//                    if (qualifier.equals("category") && value.contains("other")) {
//                        break;
//                    } else {
//                        if (qualifier.equals("title") && !value.equals("-1")) {
//                            title = value;
//                        }
//                        if (qualifier.equals("category") && !value.equals("-1")) {
//                            category_ = value;
//                        }
//
//                        if (qualifier.equals("content") && !value.equals("-1")) {
//                            content = value.replace("\t", " ").replace("\n", "");
//                        }
//                        if (qualifier.equals("news_tag") && !value.equals("-1")) {
//                            String[] labels = value.split(",");
//                            for (String label : labels) {
//                                news_tag += "__label__" + label + " ";
//                            }
//                        }
//                    }
//
//
//                }
//
////                if (!"".equals(news_tag) && !"".equals(title) && !"".equals(content)) {
//                if (!"".equals(title) && !"".equals(content)) {
//                    if ("".equals(news_tag)) news_tag = "-1";
//                    ++count;
//                    builder.append(category_).append("\t").append(news_tag.trim()).append("\t").append(title).append("\t").append(content);
////                    builder.append(4).append("\t").append(title).append("\t").append(content);
////                    System.out.println(builder.toString());
//                    writer.write((builder.toString() + "\n").toCharArray());
//                    /*写入后清空当前builer中的内容*/
//                    builder.delete(0, builder.length());
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                System.out.printf("有效的新闻篇数 %d \n", count);
//                System.out.println("saveTagTitleContent= " + (System.currentTimeMillis() - time));
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private static void saveTagTitleContentV2(ResultScanner rs, String filePath) {
//        Writer writer = null;
//        int count = 0;
//        try {
//            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)), "UTF-8"));
//            StringBuilder builder = new StringBuilder();
//            time = System.currentTimeMillis();
//            for (Result result : rs) {
//                /*得到单元格集合*/
//                List<Cell> cs = result.listCells();
//                String news_tag = "";
//                String title = "";
//                String content = "";
//                String category_ = "";
//                for (Cell cell : cs) {
//                    //取到修饰名
//                    String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
//                    //取到值
//                    String value = Bytes.toString(CellUtil.cloneValue(cell));
//
//
//                    if (qualifier.equals("category") && value.contains("other")) {
//                        break;
//                    } else {
//                        if (qualifier.equals("title") && !value.equals("-1")) {
//                            title = value;
//                        }
//                        if (qualifier.equals("category") && !value.equals("-1")) {
//                            category_ = value;
//                        }
//
//                        if (qualifier.equals("content") && !value.equals("-1")) {
//                            content = value.replace("\t", " ").replace("\n", "");
//                        }
//                        if (qualifier.equals("news_tag") && !value.equals("-1")) {
//                            String[] labels = value.split("_");
//                            for (String label : labels) {
//                                news_tag += "__label__" + label + " ";
//                            }
//                        }
//                    }
//
//
//                }
//
////                if (!"".equals(news_tag) && !"".equals(title) && !"".equals(content)) {
//                if (!"".equals(title) && !"".equals(content)) {
//                    if ("".equals(news_tag)) news_tag = "-1";
//                    ++count;
//                    builder.append(category_).append("\t").append(news_tag.trim()).append("\t").append(title).append("\t").append(content);
////                    builder.append(4).append("\t").append(title).append("\t").append(content);
////                    System.out.println(builder.toString());
//                    writer.write((builder.toString() + "\n").toCharArray());
//                    /*写入后清空当前builer中的内容*/
//                    builder.delete(0, builder.length());
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                System.out.printf("有效的新闻篇数 %d \n", count);
//                System.out.println("saveTagTitleContent= " + (System.currentTimeMillis() - time));
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private static void saveTagTitleContent(ResultScanner rs, String filePath) {
//        Writer writer = null;
//        int count = 0;
//        try {
//            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)), "UTF-8"));
//            StringBuilder builder = new StringBuilder();
//            time = System.currentTimeMillis();
//            for (Result result : rs) {
//                /*得到单元格集合*/
//                List<Cell> cs = result.listCells();
//                String news_tag = "";
//                String title = "";
//                String content = "";
//                for (Cell cell : cs) {
//                    //取到修饰名
//                    String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
//                    //取到值
//                    String value = Bytes.toString(CellUtil.cloneValue(cell));
//
////                    if (qualifier.equals("news_tag") && !value.equals("-1")) {
////                        String[] labels = value.split("_");
////                        for (String label : labels) {
////                            news_tag += "__label__" + label + " ";
////                        }
////                    }
//                    if (qualifier.equals("category") && !value.contains("news_entertainment")) {
//                        break;
//                    } else {
//                        if (qualifier.equals("news_tag") || qualifier.equals("tag_url") || qualifier.equals("tag")) {
//                            continue;
//                        }
//                        if (qualifier.equals("title") && !value.equals("-1")) {
//                            title = value;
//                        }
//
//                        if (qualifier.equals("content") && !value.equals("-1")) {
//                            content = value.replace("\t", " ").replace("\n", "");
//                        }
//                    }
//
//
//                }
//
////                if (!"".equals(news_tag) && !"".equals(title) && !"".equals(content)) {
//                if (!"".equals(title) && !"".equals(content)) {
//                    ++count;
////                    builder.append(news_tag.trim()).append("\t").append(title).append("\t").append(content);
//                    builder.append(4).append("\t").append(title).append("\t").append(content);
////                    System.out.println(builder.toString());
//                    writer.write((builder.toString() + "\n").toCharArray());
//                    /*写入后清空当前builer中的内容*/
//                    builder.delete(0, builder.length());
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                System.out.printf("有效的新闻篇数 %d \n", count);
//                System.out.println("saveTagTitleContent= " + (System.currentTimeMillis() - time));
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private static void saveContent(ResultScanner rs, String filePath) {
//        Writer writer = null;
//        try {
//            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)), "UTF-8"));
//            time = System.currentTimeMillis();
//            for (Result result : rs) {
//                /*得到单元格集合*/
//                List<Cell> cs = result.listCells();
//                for (Cell cell : cs) {
//                    //取到修饰名
//                    String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
//                    //取到值
//                    String value = Bytes.toString(CellUtil.cloneValue(cell));
//
//                    if (qualifier.equals("content") && !value.equals("-1")) {
//                        writer.write((value.replace("\n", "") + "\n").toCharArray());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                System.out.println("saveContent= " + (System.currentTimeMillis() - time));
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//}
