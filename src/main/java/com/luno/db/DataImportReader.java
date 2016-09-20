package com.luno.db;

import com.luno.pojo.Field;
import com.luno.pojo.Table;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.FieldAccessor_Double;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

/**
 * Created by Administrator on 2016-9-11.
 */
public class DataImportReader {

    private static Logger logger = Logger.getLogger(DataImportReader.class);

    public void executeImport(Table table,DbUtils dbUtils){

        if (table == null || StringUtils.isBlank(table.getTableName()) ||
                table.getFieldList() == null || table.getFieldList().size() == 0){
            logger.error("传入的table对象不正确...");
        }

        //拼装sql
        String insertSQL = createSql(table);


        // 设置prepsetatment字段值


        //执行插入


        //关闭资源

    }

    public static String createSql(Table table){

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

    public static void main(String[] args){
        String[][] fieldArray = {{"LIBID","int"},{"CERT_ID","string"},{"READER_TYPE","string"},
                {"DEPT","string"},{"CERT_FLAG","string"},{"CREATE_DATA","data"},{"NAME","string"}};
        String tableName = "READER_INFO";
        Table table = new Table(tableName,fieldArray);

        String result = DataImportReader.createSql(table);
//        DbUtils dbUtils = new DbUtils(1626,"192.168.10.19","sxlib","apimanager","dfdre$da0cber42Odc");

        System.out.println(result);
    }
}
