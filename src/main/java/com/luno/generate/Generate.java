package com.luno.generate;

import com.luno.pojo.ReaderInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class Generate {


    public static List<ReaderInfo> genReaderInfo(String content){

        String cellSplit = "|";
        String rowSplit = "\n";
        List<ReaderInfo> list = new ArrayList<>();
        if (StringUtils.isBlank(content)){
            return null;
        }
        if (content.indexOf("|") < 0 ){
            return null;
        }
        String[] readerArray =  content.split(rowSplit);
        for (String reader : readerArray) {
            String[] cellArray = reader.split(cellSplit);
            for (String cell : cellArray){
                System.out.print(cell + "");
            }
            System.out.println("================");

        }

        return null;
    }

    public static void main(String[] args){
        String content  = "LIBFOREIGN|外语学院馆|LIBILINK||外语学院馆||||0|0|LIBFO|20020826||0|外语大学馆|\n" +
                "XIAOQ|肖强|ADMIN||_系统维护||STAFF||0|0|55722324|20030313||0|外语大学馆|\n" +
                "CHENBL|陈步苓|TJFSU||_流通主管||STAFF||0|0|511728|20030314||0|外语大学馆|\n" +
                "ZHANGLI|张力|TJFSU||_编目主管||STAFF||0|0|23245575|20030314||0|外语大学馆|\n" +
                "R120020301001|张滨江 (先生)|英语学院|先生|无效||||0|20131230|0000|20030711||0|外语大学馆|\n" +
                "SILH|司丽慧 (女士)|TJFSU|女士|_流通人员2||STAFF||0|0|123456|20030827||0|外语大学馆|\n" +
                "R1200600300001|史春英 (女士)|阿拉伯语系|女士|无效||||0|20131230|0000|20030830||0|外语大学馆|";
//        genReaderInfo(content);
        String s = "LIBFOREIGN|外语学院馆|LIBILINK||外语学院馆||||0|0|LIBFO|20020826||0|外语大学馆|";
        int start =0 ,end = 0 ;
        for (int i =0 ;i < 10 ;i++){
            end = s.indexOf("|",start + 1);
            String s2 = s.substring(start,end);
            start = end;
            System.out.println(s2);

        }
        System.out.println(s.split("|").length);
    }
}
