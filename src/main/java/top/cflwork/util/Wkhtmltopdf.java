package top.cflwork.util;

import java.io.File;
import java.util.Date;


/**
 *  * Created by Jerry on 2017/2/13.
 *  * God Bless Me
 *  
 */
public class Wkhtmltopdf {
    //wkhtmltopdf 在系统中的路径
    private static final String toPdfTool = "C:\\Users\\DELL\\Downloads\\wkhtmltox-0.12.5-1.msvc2015-win64\\bin\\wkhtmltopdf.exe";
    public static void main(String[] args) {
        Date date = new Date();
        String fileName = String.valueOf(date.getTime()) + ".pdf";
        convert("https://www.csdn.net/", "F:\\" + fileName);
    }
    /**
     *  * html转pdf
     *  *
     *  * @param srcPath  html路径，可以是硬盘上的路径，也可以是网络路径
     *  * @param destPath pdf保存路径
     *  * @return 转换成功返回true
     *  
     */
    public static boolean convert(String srcPath, String destPath) {
        File file = new File(destPath);
        File parent = file.getParentFile();
        //如果pdf保存路径不存在，则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }
        StringBuilder cmd = getFormal();
        //html路径 即目标网页路径
        cmd.append(" ");
        cmd.append(srcPath);
        cmd.append(" ");
        //pdf保存路径
        cmd.append(destPath);
        boolean result = true;
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }
    /**
     *  * 标准格式
     *  *
     *  * @return
     *  
     */
    public static StringBuilder getFormal() {
        StringBuilder cmd = new StringBuilder();
        //wkhtmltopdf 在系统中的路径
        cmd.append(toPdfTool);
        cmd.append(" ");
//        cmd.append(" --header-line");//页眉下面的线
//   cmd.append("  --footer-line");//页脚上面的线
//        cmd.append("  --footer-center [page]/[topage]"); //在页脚中心放置页码
//   cmd.append("  --header-right 这里是我们系统的页眉"); //页眉中间放置文字
//        cmd.append("  --header-html http://localhost:8090/myheader.html"); //页眉中间放置图片
//        cmd.append("  --header-spacing 5 ");// (设置页眉和内容的距离,默认0 )
//        cmd.append("  --margin-top 20mm  "); //设置页面上边距 (default 10mm)
//        cmd.append(" cover http://localhost:8090/firstPage.html ");
        return cmd;
    }
    public static StringBuilder test1() {
        StringBuilder cmd = new StringBuilder();
        //wkhtmltopdf 在系统中的路径
        cmd.append(toPdfTool);
//   cmd.append(" --cover http://localhost:8090/firstPage.html");
        cmd.append(" -T 15mm");
        cmd.append(" --header-spacing 5");
        cmd.append(" --outline");
        cmd.append(" cover http://image.baidu.com");
        cmd.append(" ");
        return cmd;
    }

}
