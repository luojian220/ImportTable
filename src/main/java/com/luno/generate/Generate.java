package com.luno.generate;

import com.luno.pojo.ReaderInfo;
import com.luno.pojo.ReaderInfoTemp;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class Generate {

    private static Logger logger = Logger.getLogger(Generate.class);

    public static List<Object> genReaderInfo(String content){
        String cellSplit = "|";
        String rowSplit = "\n";
        List<Object> list = new ArrayList<>();
        if (StringUtils.isBlank(content)){
            return null;
        }
        if (content.indexOf("|") < 0 ){
            return null;
        }
        try{
            String[] readerArray =  content.split(rowSplit);
            for (String reader : readerArray) {
                ReaderInfo readerInfo = new ReaderInfo();
                int start =0 ,end = 0 ;
                for (int i =0 ;i < 15 ;i++){
                    end = reader.indexOf("|",end + 1);
                    String cell = reader.substring(start,end);
                    start = end + 1;
                    if (i == 0){
                        readerInfo.setCertId(cell);
                    }else if (i == 1) {
                        readerInfo.setName(cell);
                    }else if (i == 2) {
                        readerInfo.setDept(cell);
                    }
                }
                list.add(readerInfo );
            }
        }catch (Exception e){
            logger.error("根据文件内容生成读者信息异常：" + e.getMessage());
        }
        logger.info("根据文件内容生成"+list.size()+"个读者信息");
        return list;
    }

    public static List<Object> genReaderInfoTemp(String content){

        String cellSplit = "|";
        String rowSplit = "\n";
        List<Object> list = new ArrayList<>();
        if (StringUtils.isBlank(content)){
            return null;
        }
        if (content.indexOf("|") < 0 ){
            return null;
        }
        try{
            String[] readerArray =  content.split(rowSplit);
            for (String reader : readerArray) {
                ReaderInfoTemp readerInfoTemp = new ReaderInfoTemp();
                int start =0 ,end = 0 ;
                for (int i =0 ;i < 15 ;i++){
                    end = reader.indexOf("|",end + 1);
                    String cell = reader.substring(start,end);
                    start = end + 1;
                    if (i == 0){
                        readerInfoTemp.setCertId(cell);
                    }else if (i == 1) {
                        readerInfoTemp.setName(cell);
                    }else if (i == 2) {
                        readerInfoTemp.setDept(cell);
                    }
                }
                list.add(readerInfoTemp );
            }
        }catch (Exception e){
            logger.error("根据文件内容生成读者信息异常：" + e.getMessage());
        }
        logger.info("根据文件内容生成"+list.size()+"个读者信息"+list);
        return list;
    }

    public static void main(String[] args){
        String content  = "LIBFOREIGN|外语学院馆|LIBILINK||外语学院馆||||0|0|LIBFO|20020826||0|外语大学馆|\n" +
                "XIAOQ|肖强|ADMIN||_系统维护||STAFF||0|0|55722324|20030313||0|外语大学馆|\n" +
                "CHENBL|陈步苓|TJFSU||_流通主管||STAFF||0|0|511728|20030314||0|外语大学馆|\n" +
                "ZHANGLI|张力|TJFSU||_编目主管||STAFF||0|0|23245575|20030314||0|外语大学馆|\n" +
                "R120020301001|张滨江 (先生)|英语学院|先生|无效||||0|20131230|0000|20030711||0|外语大学馆|\n" +
                "SILH|司丽慧 (女士)|TJFSU|女士|_流通人员2||STAFF||0|0|123456|20030827||0|外语大学馆|\n" +
                "R1200600300001|史春英 (女士)|阿拉伯语系|女士|无效||||0|20131230|0000|20030830||0|外语大学馆|";
        genReaderInfo(content);
    }
}
