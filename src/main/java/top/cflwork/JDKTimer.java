package top.cflwork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * jdk自带定时器
 *
 * @author LIUTIE
 *
 */
public class JDKTimer {


    public static void main(String[] args) throws ParseException {
        //日期格式工具
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Timer timer = new Timer();

        // 10s后执行定时器，仅执行一次
        System.out.print(sdf.format(new Date()));
        System.out.println("the timer one will be executed after 10 seconds...");
        long milliseconds = 10 * 1000;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.print(sdf.format(new Date()));
                System.out.println("the timer one has finished execution");
            }
        }, milliseconds);

        //学校的编号，加上设备编号，当天需要     开,  关  机得 时间  list   500

        //12秒后执行定时器，每1s执行一次
        System.out.print(sdf.format(new Date()));
        System.out.println("the timer two will be executed after 12 seconds...");
        //启动后延迟时间
        long afterSs = 12 * 1000;
        //执行周期
        long intervalSs1 = 1 * 1000;
        timer.schedule(new TimerTask() {
            // 执行计数器
            int i = 0;

            @Override
            public void run() {

                System.out.print(sdf.format(new Date()));
                System.out.println("the timer two has execution " + (++i) + " timers");
                // 执行10次后关闭定时器
                if (i == 10) {
                    this.cancel();
                }
            }
        }, afterSs, intervalSs1);


        // 指定时间执行定时器，仅执行一次
        System.out.print(sdf.format(new Date()));
        System.out.println("the timer three will be executed at 2017-06-27 21:47:00...");
        Date date = sdf.parse("2017-06-27 21:47:00");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.print(sdf.format(new Date()));
                System.out.println("the timer three has finished execution");
            }
        }, date);

        // 从指定时间开始周期性执行
        System.out.print(sdf.format(new Date()));
        System.out.println("the timer four will be executed at 2017-06-27 21:48:00...");
        // 执行间隔周期
        long intervalSs = 1 * 1000;
        // 开始执行时间
        Date beginTime = sdf.parse("2017-06-27 21:48:00");
        timer.schedule(new TimerTask() {
            // 执行计数器
            int i = 0;

            @Override
            public void run() {
                System.out.print(sdf.format(new Date()));
                System.out.println("the timer four has execution " + (++i) + " timers");
                // 执行10次后关闭定时器
                if (i == 10) {
                    this.cancel();
                }
            }
        }, beginTime, intervalSs);
    }

}