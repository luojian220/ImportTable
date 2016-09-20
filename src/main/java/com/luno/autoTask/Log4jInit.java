package com.luno.autoTask;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2016-9-13.
 */
public class Log4jInit implements ServletContextListener {

    Logger log = Logger.getLogger(Log4jInit.class);

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //得到servletContext对象的方法
        ServletContext sc = servletContextEvent.getServletContext();
        //指明文件的相对路径就能够得到文件的绝对路径
        String path = sc.getRealPath("/WEB-INF/classes/log4j.properties");
        //启动服务器的时候加载日志的配置文件
        init(path,sc);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("Log4jInit contextDestroyed!");
    }

    public void init(String path,ServletContext sc){
        FileInputStream istream = null;
        try{
            Properties props = new Properties();
            //加载配置文件
            istream = new FileInputStream(path);
//            props.remove("log4j.appender.file.File");
            //指明log文件的位置
//            props.put("log4j.appender.file.File", sc.getRealPath("/log/hb.log"));
            //加载文件流，加载Log4j文件的配置文件信息
            props.load(istream);
            PropertyConfigurator.configure(path);
        } catch (Exception ex){
            try {
                throw new Exception(ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally{
            try {
                istream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
