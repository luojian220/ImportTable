package com.luno;

import com.luno.db.DataImport;
import com.luno.utils.FileUtil;
import com.luno.utils.FtpHelp;
import com.luno.utils.MarcCommon;
import com.luno.pojo.WaiYuLibrary;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Set;


public class DoImport {

	private static Logger logger = Logger.getLogger(DoImport.class);

	public static void main(String[] args){

		doImport();
	}

	public static void doImport(){

		//1.下载文件
//		FtpHelp.downFile("");
		//2.获取当前日期的书目文件名
//		String bookFileName = WaiYuLibrary.getBookFileName();
		String bookFileName = "D:\\ftp\\waiyu20160705.iso";

		logger.info("本次导入馆藏文件为："+bookFileName);
		if (StringUtils.isNotBlank(bookFileName)){
			String content = FileUtil.readString1(bookFileName);
			Set<String> isbnSet = MarcCommon.getIsbnSet(content);

			DataImport dataImport = new DataImport();
			dataImport.executeImport(isbnSet);
		}
	}

}
