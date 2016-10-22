package com.luno.generate;

import com.luno.pojo.LibBookNum;
import com.luno.pojo.ReaderInfo;
import com.luno.pojo.ReaderInfoTemp;
import com.luno.pojo.ReaderPwd;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 * 根据文件内容，生成要插入表的对象集合
 */
public class Generate {

    private static Logger logger = Logger.getLogger(Generate.class);

    public static final String TYPE_readerInfo = "readerInfo";
    public static final String TYPE_readerPwd = "readerPwd";

    public static List<Object> genReaderInfo(String content,Long libid){
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
                    String cell = reader.substring(start,end).trim();
                    start = end + 1;
                    if (i == 0){
                        readerInfo.setCertId(cell);
                    }else if (i == 1) {
                        readerInfo.setName(cell);
                    }else if (i == 2) {
                        readerInfo.setDept(cell);
                    }
                    readerInfo.setLibid(libid);
                }
                list.add(readerInfo );
            }
        }catch (Exception e){
            logger.error("根据文件内容生成读者信息异常：" + e.getMessage());
        }
        logger.info("根据文件内容生成"+list.size()+"个读者信息");
        return list;
    }

    /**
     * 将字符串内容转换为读者对象
     * @param content 字符串内容  格式为：姓名	读者证号	初始密码
     * @param libid 图书馆id
     * @param type  是readerInfo 还是 readerPwd
     * @return
     */
    public static List<Object> genReaderInfoForTabTxt(String content,Long libid,String type){

        String cellSplit = "\t";
        String rowSplit = "\n";
        List<Object> list = new ArrayList<>();
        if (StringUtils.isBlank(content)){
            return null;
        }
        if (content.indexOf(cellSplit) < 0 ){
            return null;
        }
        try{
            String[] readerArray =  content.split(rowSplit);
            for (String reader : readerArray) {
                if (!reader.contains(cellSplit)) {
                    logger.info("用户信息分隔符不正确："+reader);
                    continue;
                }
                String[] cellArray = reader.split(cellSplit);
                //姓名	读者证号	初始密码
                if (cellArray.length < 3){
                    return null;
                }
                String name = "";
                String certId = "";
                String dept = "";
                String password = "";
                if (cellArray.length == 3){
                    name = cellArray[0].trim();
                    certId = cellArray[1].trim();
                    password = cellArray[2].trim();
                }else if (cellArray.length == 4){
                    name = cellArray[0].trim();
                    certId = cellArray[1].trim();
                    dept = cellArray[2].trim();
                    password = cellArray[3].trim();
                }else{
                    return null;
                }
                if (StringUtils.equalsIgnoreCase(type,TYPE_readerInfo)){
                    ReaderInfo readerInfo = new ReaderInfo();
                    readerInfo.setName(name);
                    readerInfo.setCertId(certId);
                    readerInfo.setDept(dept);
                    readerInfo.setLibid(libid);
                    list.add(readerInfo );
                }else if (StringUtils.equalsIgnoreCase(type,TYPE_readerPwd)){
                    ReaderPwd readerPwd = new ReaderPwd();
                    readerPwd.setCertId(certId);
                    readerPwd.setLibid(libid);
                    readerPwd.setPassword(password);
                    list.add(readerPwd );
                }
            }
        }catch (Exception e){
            logger.error("根据文件内容生成读者信息异常：" + e.getMessage());
        }
        logger.info("根据文件内容生成"+list.size()+"个读者信息");
        return list;
    }

    /**
     * 将字符串转换成表LibBookNum中对应的字段 格式为 isbn	纸质书数量
     * @param content
     * @param libid
     * @return
     */
    public static List<Object> genLibBookNumForTabTxt(String content,Long libid){
        String cellSplit = "\t";
        String rowSplit = "\n";
        List<Object> list = new ArrayList<>();
        if (StringUtils.isBlank(content)){
            return null;
        }
        try{
            String[] rowArray =  content.split(rowSplit);
            for (String row : rowArray) {
                if (!row.contains(cellSplit)) {
                    logger.info("用户信息分隔符不正确："+row);
                    continue;
                }
                String[] cellArray = row.split(cellSplit);
                //isbn	纸质书数量
                if (cellArray.length != 2){
                    logger.info("行分隔后列数不正确："+row);
                    continue;
                }
                LibBookNum libBookNum = new LibBookNum();
                String isbn = cellArray[0].trim();
                Integer Pbooknum = Integer.parseInt(cellArray[1].trim());
                libBookNum.setLibid(libid);
                libBookNum.setIsbn(isbn);
                libBookNum.setPbooknum(Pbooknum);
                libBookNum.setEbooknum(0);
                list.add(libBookNum);
            }
        }catch (Exception e){
            logger.error("根据文件内容生成馆藏信息异常：" + e.getMessage());
        }
        logger.info("根据文件内容生成"+list.size()+"个馆藏信息");
        return list;
    }

    public static void main(String[] args){
        /*String content  = "LIBFOREIGN|外语学院馆|LIBILINK||外语学院馆||||0|0|LIBFO|20020826||0|外语大学馆|\n" +
                "XIAOQ|肖强|ADMIN||_系统维护||STAFF||0|0|55722324|20030313||0|外语大学馆|\n" +
                "CHENBL|陈步苓|TJFSU||_流通主管||STAFF||0|0|511728|20030314||0|外语大学馆|\n" +
                "ZHANGLI|张力|TJFSU||_编目主管||STAFF||0|0|23245575|20030314||0|外语大学馆|\n" +
                "R120020301001|张滨江 (先生)|英语学院|先生|无效||||0|20131230|0000|20030711||0|外语大学馆|\n" +
                "SILH|司丽慧 (女士)|TJFSU|女士|_流通人员2||STAFF||0|0|123456|20030827||0|外语大学馆|\n" +
                "R1200600300001|史春英 (女士)|阿拉伯语系|女士|无效||||0|20131230|0000|20030830||0|外语大学馆|";
        genReaderInfo(content,2060l);*/

        String content = "王仁祥\t1020150462\t888\n" +
                "陈靓秋\t1020150203\t888\n" +
                "吴俊\t1020150145\t888\n" +
                "杨德刚\t1020150101\t888\n" +
                "钱超\t1020150284\t888";
        List<Object> readerInfoList = genReaderInfoForTabTxt(content,6001400L,TYPE_readerPwd);
        logger.info(readerInfoList);
    }
}
