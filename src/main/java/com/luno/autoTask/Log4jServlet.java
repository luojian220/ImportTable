package com.luno.autoTask;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by Administrator on 2016-9-14.
 */
public class Log4jServlet extends HttpServlet {

    private final Logger log = Logger.getLogger(Log4jServlet.class);

    @Override
    public void init() throws ServletException{

        try {

            String prefile = getInitParameter("log4j");
            String path = getServletContext().getRealPath(prefile);

            PropertyConfigurator.configure(path);

            log.info("日志初始化完成 ");
        }catch (Exception e){
            log.info("日志初始化异常 ");
        }

    }

}
