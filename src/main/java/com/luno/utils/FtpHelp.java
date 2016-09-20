package com.luno.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FtpHelp {

    private static Logger logger = Logger.getLogger(FtpHelp.class);

    // 指定本地写入文件
    public static final String localPath="D:\\ftp\\";

	@SuppressWarnings("finally")
	public static void downFile(String dateString) {
        
        
        // ftp登录用户名  
        String userName = "sxlib";
        String userPassword = "sxP@ssw0rd";
 
        // ftp地址:直接IP地址  
        String server = "192.168.10.3";
 
        // ftp端口
        int port = 21;
 
        // 指定读取的目录  
        String path = "tianjin";
           
        FTPClient ftp = new FTPClient();  
        try {  
            int reply;  
            //1.连接服务器  
            ftp.connect(server,port);  
            //2.登录服务器 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
            ftp.login(userName, userPassword);  
            //3.判断登陆是否成功  
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {  
                ftp.disconnect();
            }
            logger.info(ftp.getStatus());
 
            //4.指定要下载的目录  
            boolean falg= ftp.changeWorkingDirectory(path);// 转移到FTP服务器目录
            logger.debug(falg);
            	
            //5.遍历下载的目录
//            String[] names = ftp.listNames();
//            System.out.println(ftp.getStatus(path));
            //本地文件夹如果不存在，则创建
            File ftpDict = new File(localPath);
            if (!ftpDict.exists()){
            	ftpDict.mkdir();
            }
            FTPFile[] fs = ftp.listFiles();
	        logger.info("文件数量：" + fs.length);
            String todayString ;
            if (StringUtils.isNotBlank(dateString)){
                todayString = dateString;
            }else{
                todayString = getDateString();
            }

	        for (FTPFile ff : fs) {  
	            //解决中文乱码问题，两次解码  
	            byte[] bytes=ff.getName().getBytes("iso-8859-1");  
	            String fn=new String(bytes,"utf8");  
	             
	            //6.写操作，将其写入到本地文件中  
	           
	            if (fn.contains(todayString)){
	            	 File localFile = new File(localPath + ff.getName());  
	                 OutputStream is = new FileOutputStream(localFile);  
	                 ftp.retrieveFile(ff.getName(), is);  
	                 is.close();
	                 logger.info("文件下载成功");
	            }
	           
	        }
	        
	        ftp.logout(); 
             
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (ftp.isConnected()) {  
                try {  
                    ftp.disconnect();  
                } catch (IOException ioe) {  
                }  
            } 
        }  
    }
	
	public static String getDateString(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String dateFormat = df.format(new Date());
        
        logger.debug(dateFormat);
        return dateFormat;
	}
	
	public static void main(String[] args) {
		downFile("20160912");
		/*String bookFileName = WaiYuLibrary.getBookFileName();
		String content = FileUtil.readString1(bookFileName);
		
		System.out.println(content);*/
		
	}
}
