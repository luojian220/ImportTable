package com.luno.autoTask;

import com.luno.DoImport;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016-9-13.
 */
public class DataImportTask extends TimerTask {

    private static Logger logger = Logger.getLogger(DataImportTask.class);
    private static boolean isRunning = false;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public void run() {
        if (!isRunning) {
            isRunning = true;
            logger.info("开始执行任务..."); //开始任务

            //working  add what you want to do
            DoImport.doImport();

            logger.info("执行任务完成..."); //任务完成
            isRunning = false;
        } else {
            logger.info("上一次任务执行还未结束..."); //上一次任务执行还未结束

        }
    }
}
