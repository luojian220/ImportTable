package com.luno.utils;

import java.io.*;

public class FileUtil {

	public static String readString1(String FileName) {

        try {
            FileInputStream inStream= new FileInputStream(new File(FileName));

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] buffer=new byte[1024];

            int length=-1;

            while( (length = inStream.read(buffer)) != -1) {
                bos.write(buffer,0,length);
            }
            bos.close();
            inStream.close();
            return bos.toString();
//            return new String(bos.toByteArray(),"UTF-8");
        }catch(IOException e){
        	return "";
        }
    }
	
	public static void main(String[] args) {
		String s = readString1("D:\\导数\\湖北商贸学院\\湖北商贸2016最新教师读者证号2.txt");
		
		System.out.println(s);
	}
}
