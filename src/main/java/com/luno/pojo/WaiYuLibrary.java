package com.luno.pojo;

import com.luno.utils.FtpHelp;

import java.io.File;
import java.io.InputStream;

public class WaiYuLibrary {
	
	public static final String USERFILENAMEFORMAT = "yyyyMMdd.users.waiyu.txt";
	
	public static final String BOOKFILENAMEFORMAT = "waiyuyyyyMMdd.iso";
	
	
	private InputStream userInputStream;
	
	private InputStream bookInputStream;

	public InputStream getUserInputStream() {
		return userInputStream;
	}

	public void setUserInputStream(InputStream userInputStream) {
		this.userInputStream = userInputStream;
	}

	public InputStream getBookInputStream() {
		return bookInputStream;
	}

	public void setBookInputStream(InputStream bookInputStream) {
		this.bookInputStream = bookInputStream;
	}

	public static String getUserFileName() {
		File userFile = new File(FtpHelp.localPath +
				WaiYuLibrary.USERFILENAMEFORMAT.replace("yyyyMMdd", FtpHelp.getDateString()));  
		if (userFile.exists()){
			return userFile.getPath();
		}
		return "";
	}

	public static String getBookFileName() {
		
		File bookFile = new File(FtpHelp.localPath + 
				WaiYuLibrary.BOOKFILENAMEFORMAT.replace("yyyyMMdd", FtpHelp.getDateString()));  //FtpHelp.getDateString()
		if (bookFile.exists()){
			return bookFile.getPath();
		}
		return "";
	}
	
	
	public static void main (String[] args){
		
		String s = getBookFileName();
		
		System.out.println(s);
	}

}
