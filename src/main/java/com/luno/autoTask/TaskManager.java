package com.luno.autoTask;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

/**
 * Created by Administrator on 2016-9-13.
 */
public class TaskManager implements ServletContextListener {

    private final Logger logger = Logger.getLogger(TaskManager.class);

    /**
     * 每天的毫秒数
     */
    public static final long PERIOD_DAY = DateUtils.MILLIS_PER_DAY;
    /**
     * 一周内的毫秒数
     */
    public static final long PERIOD_WEEK = PERIOD_DAY * 7;
    /**
     * 无延迟
     */
    public static final long NO_DELAY = 0;
    /**
     * 定时器
     */
    private Timer timer;
    /**
     * 在Web应用启动时初始化任务
     */
    public void contextInitialized(ServletContextEvent event) {
        //定义定时器
        timer = new Timer("数据导入任务",true);

        logger.info("定时器初始化。。。");
        //启动备份任务,每  执行一次
//        timer.schedule(new DataImportTask(),NO_DELAY, DateUtils.MILLIS_PER_SECOND * 1);

        // 设置指定时间点启动任务
        String startHour =  event.getServletContext().getInitParameter("startHour");
        String startMinute =  event.getServletContext().getInitParameter("startMinute");
        int hour = 14;
        if (StringUtils.isNotBlank(startHour)){
            hour = Integer.parseInt(startHour);
        }
        int minute = 14;
        if (StringUtils.isNotBlank(startMinute)){
            minute = Integer.parseInt(startMinute);
        }
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,minute);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("设置的启动时间为："+sdf.format(c.getTime()));
        timer.schedule(new DataImportTask(), c.getTime() , DateUtils.MILLIS_PER_DAY * 1);

    }
    /**
     * 在Web应用结束时停止任务
     */
    public void contextDestroyed(ServletContextEvent event) {

        logger.info("定时器销毁。。。");
        timer.cancel(); // 定时器销毁
    }
}
