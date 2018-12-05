package top.cflwork;

import java.io.File;

/**
 * @author xsw
 * @ClassName: HtmlToPdf
 * @Description: TODO()
 * @date 2016-12-8 上午10:14:54
 */
public class test1 {
    //wkhtmltopdf在系统中的路径
    private static final String toPdfTool = "C:\\Users\\DELL\\Downloads\\wkhtmltox-0.12.5-1.msvc2015-win64\\bin\\wkhtmltopdf.exe";

    /**
     * html转pdf
     *
     * @param srcPath  html路径,可以是硬盘上的路径,也可以是网络路径
     * @param destPath pdf保存路径
     * @return 转换成功返回true
     */
    public static boolean convert(String srcPath, String destPath) {
        File file = new File(destPath);
        File parent = file.getParentFile();
//如果pdf保存路径不存在,则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }
        StringBuilder cmd = new StringBuilder();
        cmd.append(toPdfTool);
        cmd.append(" ");
//        cmd.append(" --header-line ");//页眉下面的线
//        cmd.append(" --footer-line ");//页脚上面的线
//        cmd.append(" --footer-center [page]/[topage] "); //在页脚中心放置页码
//        cmd.append(" --header-right 这里是我们系统的页眉 "); //页眉中间放置文字
//        cmd.append(" --header-spacing 10 ");//(设置页眉和内容的距离,默认0)
        cmd.append(srcPath);
        cmd.append(" ");
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

    public static void main(String[] args) {
        System.out.println(checkFileSize(new File("F:\\cflwork\\java开发环境工具\\ideaIU-2017.3.5.exe").length()));
//        new test1().convert("http://127.0.0.1:8020/Test1/test16.html", "F:/test16.pdf");
    }
    public static String checkFileSize(double len) {
        //单位为byte
        if(len>=0 && len<1024){
            return len+"b";
        }else if(len>=1024){
            len = len/1024;
            if(len>=1024){
                return len/1024+"M";
            }else{
                return len+"kb";
            }
        }
        return null;
    }
}
