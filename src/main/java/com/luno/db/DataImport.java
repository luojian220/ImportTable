package com.luno.db;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

/**
 * Created by Administrator on 2016-9-11.
 */
public class DataImport {

    private static Logger logger = Logger.getLogger(DataImport.class);

    /*
        1.根据直接执行插入，主键存在的记录会算作错误记录
    */
    public void executeImport(Set<String> isbnList){
        int sourceRowCounts = 0;
        int exeRowCounts = 0;
        int updateRowCounts = 0;
        int exeErrorCounts = 0;
        int cnt = 0;
        int commitNum = 1000;

        String tableName = "LIB_BOOK_NUMS";
        
        if(isbnList == null || isbnList.isEmpty()){
            logger.info("文件中没有isbn编码：不执行导入");
        	return ;
        }
        
        try{
            sourceRowCounts = isbnList.size();
            DbUtils dbUtils = new DbUtils(1626,"192.168.10.19","sxlib","apimanager","dfdre$da0cber42Odc");
            StringBuffer insertSQL = new StringBuffer();
            insertSQL.append("INSERT INTO " + tableName + "(");
            insertSQL.append(" LIBID,ISBN,PBOOKNUM,EBOOKNUM,CREATE_DATE");
            insertSQL.append(") VALUES (") ;
            insertSQL.append("?,?,?,?,?");
            insertSQL.append(")");
            
            for (String isbn : isbnList){

                PreparedStatement preparedStatement = dbUtils.prepareStatement(insertSQL.toString());
                
                preparedStatement.setInt(1, 2060);
                preparedStatement.setString(2, isbn);
                preparedStatement.setInt(3, 1);
                preparedStatement.setInt(4, 0);
                Date now = new Date();
                preparedStatement.setDate(5, new java.sql.Date(now.getTime()));
                try {
                    preparedStatement.execute();
                }catch (SQLException e) {
                    //主键冲突 ，不需要再次插入
                    exeErrorCounts ++;
                }
                
                cnt += 1;
                exeRowCounts ++;
                if (cnt >= commitNum) {
                    dbUtils.commit();
                    logger.info("处理记录总数:" + sourceRowCounts + ", 已处理:" + exeRowCounts + ",更新数量:" + updateRowCounts + ", 出错数量:" + exeErrorCounts);
                    cnt = 0;
                }
            }
            dbUtils.commit();
            dbUtils.close();
            logger.info("处理记录总数:" + sourceRowCounts + ", 已处理:" + exeRowCounts + ",更新数量:" + updateRowCounts + ", 出错数量:" + exeErrorCounts);
        }catch(Exception e) {
            e.printStackTrace();
            logger.error(tableName+"数据插入失败", e);
        }
    }
}
