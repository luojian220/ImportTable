package com.luno;

import com.luno.db.DataImport;
import com.luno.db.DataImportUtils;
import com.luno.db.DbUtils;
import com.luno.generate.Generate;
import com.luno.pojo.ReaderInfo;
import com.luno.utils.FileUtil;
import com.luno.utils.FtpHelp;
import com.luno.utils.MarcCommon;
import com.luno.pojo.WaiYuLibrary;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;


public class DoImport {

	private static Logger logger = Logger.getLogger(DoImport.class);

	public static void main(String[] args){

//		doImport();

//		doImportReader();

		doImportLibBookNum();
	}

	public static void doImport(){
		//手动运行指定运行日期的文件
		String runDate = "20161020" ;
		//1.下载文件
		FtpHelp.downFile(runDate);

		logger.info("========================开始导入馆藏信息===========================");
		String bookFileName ;
		if (StringUtils.isBlank(runDate)){
			bookFileName = WaiYuLibrary.getBookFileName();
		}else{
			bookFileName = "D:\\ftp\\waiyu"+runDate+".iso";
		}
		logger.info("本次导入馆藏文件为："+bookFileName);
		if (StringUtils.isNotBlank(bookFileName)){
			String content = FileUtil.readString1(bookFileName);
			Set<String> isbnSet = MarcCommon.getIsbnSet(content);
			DataImport dataImport = new DataImport();
			dataImport.executeImport(isbnSet);
		}
		logger.info("========================导入馆藏信息完成===========================");

		logger.info("========================开始导入读者信息===========================");
		String readerFileName ;
		if (StringUtils.isBlank(runDate)){
			readerFileName = WaiYuLibrary.getUserFileName();
		}else{
			readerFileName = "D:\\ftp\\\"+runDate+\".users.waiyu.txt";
		}
		logger.info("本次导入读者文件为："+readerFileName);
		/*if (StringUtils.isNotBlank(readerFileName)){
			String content = FileUtil.readString1(readerFileName);
			List<Object> readerList = Generate.genReaderInfo(content,2060L);
			DataImportUtils dataImportUtils = new DataImportUtils();
			DbUtils dbUtils = new DbUtils(1626,"192.168.10.19","sxlib","apimanager","");
			dataImportUtils.executeImport("READER_INFO",readerList,dbUtils);
			dbUtils.close();
		}*/
		logger.info("========================导入读者信息完成===========================");

	}

	/**
	 * 导入读者信息
	 * txt文件导入， 姓名	借阅证号	部门	密码
     */
	public static void doImportReader(){

		logger.info("========================开始导入读者信息===========================");

		String readerFileName = "D:\\导数\\辽宁警察学院\\辽宁警察学院\\16新生学信上传20161020.txt";
		logger.info("本次导入馆藏文件为："+readerFileName);
		if (StringUtils.isNotBlank(readerFileName)){
			String content = FileUtil.readString1(readerFileName);
			List<Object> readerInfoList = Generate.genReaderInfoForTabTxt(content,1833L,Generate.TYPE_readerInfo);
			List<Object> readerPwdList = Generate.genReaderInfoForTabTxt(content,1833L,Generate.TYPE_readerPwd);
			DataImportUtils dataImportUtils = new DataImportUtils();
			DbUtils dbUtils = new DbUtils(1626,"192.168.10.19","sxlib","apimanager","dfdre$da0cber42Odc");
			dataImportUtils.executeImport("READER_INFO",readerInfoList,dbUtils);
			dataImportUtils.executeImport("READER_PWD",readerPwdList,dbUtils);
			dbUtils.close();
		}
		logger.info("========================导入读者信息完成===========================");

	}

	/**
	 * 导入馆藏信息
	 * txt文件导入， 姓名	借阅证号	部门	密码
	 */
	public static void doImportLibBookNum(){

		logger.info("========================开始导入馆藏信息===========================");

		String bookFileName = "D:\\导数\\齐鲁师范学院\\齐鲁师范学院馆藏.txt";
		logger.info("本次导入馆藏文件为："+bookFileName);
		if (StringUtils.isNotBlank(bookFileName)){
			String content = FileUtil.readString1(bookFileName);
			List<Object> libBookNumList = Generate.genLibBookNumForTabTxt(content,301448L);
			DataImportUtils dataImportUtils = new DataImportUtils();
			DbUtils dbUtils = new DbUtils(1626,"192.168.10.19","sxlib","apimanager","dfdre$da0cber42Odc");
			dataImportUtils.executeImport("LIB_BOOK_NUMS",libBookNumList,dbUtils);
			dbUtils.close();
		}
		logger.info("========================导入馆藏信息完成===========================");

	}

}
