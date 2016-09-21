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

		doImport();
	}

	public static void doImport(){

		//1.下载文件
		FtpHelp.downFile("");

		logger.info("开始导入馆藏信息");
//		String bookFileName = WaiYuLibrary.getBookFileName();
		String bookFileName = "D:\\ftp\\waiyu20160705.iso";
		logger.info("本次导入馆藏文件为："+bookFileName);
		if (StringUtils.isNotBlank(bookFileName)){
			String content = FileUtil.readString1(bookFileName);
			Set<String> isbnSet = MarcCommon.getIsbnSet(content);
			DataImport dataImport = new DataImport();
			dataImport.executeImport(isbnSet);
		}
		logger.info("导入馆藏信息完成");

		/*logger.info("开始导入读者信息");
		String readerFileName = WaiYuLibrary.getUserFileName();
//		String readerFileName = "D:\\ftp\\20160918.users.waiyu.txt";
		logger.info("本次导入馆藏文件为："+readerFileName);
		if (StringUtils.isNotBlank(readerFileName)){
			String content = FileUtil.readString1(readerFileName);
			List<Object> readerList = Generate.genReaderInfo(content);
			DataImportUtils dataImportUtils = new DataImportUtils();
			DbUtils dbUtils = new DbUtils(1626,"192.168.10.19","sxlib","apimanager","");
			dataImportUtils.executeImport("READER_INFO",readerList,dbUtils);
			dbUtils.close();
		}
		logger.info("导入读者信息完成");*/

	}

}
