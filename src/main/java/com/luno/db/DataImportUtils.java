package com.luno.db;

import com.luno.pojo.Field;
import com.luno.pojo.ReaderInfo;
import com.luno.pojo.Table;
import com.luno.utils.FiledUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Administrator on 2016-9-11.
 */
public class DataImportUtils {

    private static Logger logger = Logger.getLogger(DataImportUtils.class);

    /**
     * 将dataList中的数据插入到表tableName中，
     * @param tableName  表名
     * @param dataList  数据集合
     * @param dbUtils   数据库操作工具类
     */
    public void executeImport(String tableName,List<Object> dataList,DbUtils dbUtils){
        int sourceRowCounts = 0;
        int exeRowCounts = 0;
        int updateRowCounts = 0;
        int insertRowCounts = 0;
        int exeErrorCounts = 0;
        int cnt = 0;
        int commitNum = 1000;
        if (dataList == null || StringUtils.isBlank(tableName) || dataList.size() == 0){
            logger.error("传入的table对象不正确...");
        }
        //拼装sql
        try {
            String insertSQL = createSql(tableName, FiledUtils.getTableFiledName(dataList.get(0)));
            logger.info("生成的执行sql： " + insertSQL);
            PreparedStatement preparedStatement = dbUtils.prepareStatement(insertSQL.toString());
            for (Object data : dataList){
                // 设置prepsetatment字段值
                setPreparedStatementValue(preparedStatement,data);
                try {
                    //执行插入
                    preparedStatement.execute();
                    insertRowCounts ++;
                }catch (SQLException e) {
                    //主键冲突 ，不需要再次插入
                    logger.error("数据"+ data +"插入出错："+e.getMessage());
                    exeErrorCounts ++;
                }
                cnt += 1;
                exeRowCounts ++;
                if (cnt >= commitNum) {
                    dbUtils.commit();
                    logger.info("处理记录总数:" + sourceRowCounts + ", 已处理:" + exeRowCounts + ", 插入数量:" + insertRowCounts + ", 出错数量:" + exeErrorCounts);
                    cnt = 0;
                }
            }
            //关闭资源
            dbUtils.commit();
            logger.info("处理记录总数:" + sourceRowCounts + ", 已处理:" + exeRowCounts + ", 插入数量:" + insertRowCounts + ", 出错数量:" + exeErrorCounts);
        }catch(Exception e) {
            logger.error(tableName+"数据插入失败", e);
            logger.error(e.getMessage());
        }

    }

    /**
     * 组装sql
     * @param table
     * @return
     */
    private static String createSql(Table table){

        StringBuffer insertSQL = new StringBuffer();
        insertSQL.append("INSERT INTO " + table.getTableName() + "(");
        for (Field field : table.getFieldList()){
            insertSQL.append(field.getName()+",");
        }
        insertSQL.deleteCharAt(insertSQL.length()-1);
        insertSQL.append(") VALUES (") ;
        for (Field field : table.getFieldList()){
            insertSQL.append("?,");
        }
        insertSQL.deleteCharAt(insertSQL.length()-1);
        insertSQL.append(")");
        return insertSQL.toString();
    }

    /**
     *组装sql
     * @param tableName
     * @param fieldArray
     * @return
     */
    private static String createSql(String tableName,String[] fieldArray){

        StringBuffer insertSQL = new StringBuffer();
        insertSQL.append("INSERT INTO " + tableName + "(");
        for (String field : fieldArray){
            insertSQL.append(field + ",");
        }
        insertSQL.deleteCharAt(insertSQL.length()-1);
        insertSQL.append(") VALUES (") ;
        for (String field : fieldArray){
            insertSQL.append("?,");
        }
        insertSQL.deleteCharAt(insertSQL.length()-1);
        insertSQL.append(")");
        return insertSQL.toString();
    }

    private void setPreparedStatementValue(PreparedStatement preparedStatement, Object data) throws SQLException {

        List<Map> list = FiledUtils.getFiledsInfo(data);
        for (int i =0 ;  i < list.size() ; i++ ){
            Map<String,Object> fileds = list.get(i);
            String name = (String) fileds.get(FiledUtils.name);
            String type = (String) fileds.get(FiledUtils.type);
            Object value = fileds.get(FiledUtils.value);
            try {

                if (type.indexOf("java.lang.String") >= 0 ){
                    preparedStatement.setString( i+1 , value == null ? null : String.valueOf(value));
                }else if (type.indexOf("java.lang.Long") >= 0 ) {
                    preparedStatement.setLong(i+1 , value == null ? null : (Long)value);
                }else if (type.indexOf("java.lang.Integer") >= 0 ) {
                    preparedStatement.setInt(i+1 , value == null ? null : (Integer) value);
                }else if (type.indexOf("java.util.Date") >= 0 ) {
                    preparedStatement.setDate(i+1 , value == null ? null : (java.sql.Date) value);
                }else if (type.indexOf("java.lang.Double") >= 0 ) {
                    preparedStatement.setDouble(i+1 , value == null ? null : (Double)value);
                }else{
                    preparedStatement.setString(i+1 , String.valueOf(value));
                }
            } catch (SQLException e) {
                logger.error("组装preparedStatement出异常，字段名 "+name + "值 "+ value);
                throw e;
            }
        }

    }

    public static void main(String[] args){

        DataImportUtils dataImportUtils = new DataImportUtils();
        DbUtils dbUtils = new DbUtils(1626,"192.168.10.19","sxlib","apimanager","");
        List<Object> list = new ArrayList<>();
        list.add(new ReaderInfo("000000000",null,"建筑系","1","张三"));
        String tableName = "READER_INFO_TEMP";
        dataImportUtils.executeImport(tableName,list,dbUtils);
        dbUtils.close();

    }
}
