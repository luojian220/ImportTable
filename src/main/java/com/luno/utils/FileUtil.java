package com.luno.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
            // return new String(bos.toByteArray(),"UTF-8");       
        }catch(IOException e){
        	return "";
        }
    }
	
	public static void main(String[] args) {
		String s = readString1("D:\\ftp\\waiyu20160708.iso");
		
		System.out.println(s);
	}
}
