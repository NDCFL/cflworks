package top.cflwork.util;

import com.itextpdf.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PDFUtil {
    private static String BASE_PATH;
    private static String PDF_TEMP_PATH;
    private static String FONT_PATH;

    /**
     * 初始化
     */
    public PDFUtil(){
        BASE_PATH = this.getClass().getResource("/").getPath();
        BASE_PATH = new File(BASE_PATH).getParentFile().getPath();
        PDF_TEMP_PATH = BASE_PATH + "\\" + "pdf\\";
        FONT_PATH = BASE_PATH + "\\fonts\\";
        File filePath = new File(PDF_TEMP_PATH);
        if (!filePath.exists()){
            filePath.mkdir();
        }
    }

    /**
     * 创建PDF文件到服务器
     * @param htmlCode HTML代码
     * @param fileName 文件名
     */
    public void createPDF(String htmlCode, String fileName){
        try{
            File file = new File(PDF_TEMP_PATH + fileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(FONT_PATH + "simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setDocumentFromString(htmlCode);
            renderer.layout();
            renderer.createPDF(outputStream);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * 从服务器上下载PDF
     * @param fileName 文件名
     * @param response
     */
    public void downLoad(String fileName, HttpServletResponse response){
        try {

            FileInputStream fileInputStream = new FileInputStream(PDF_TEMP_PATH + fileName);
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("content-disposition","attachment;filename=MyPDF.pdf");
            response.setHeader("content-type", "application/pdf");
            //输出
            int len = 1;
            byte[] bs = new byte[1024];
            while((len = fileInputStream.read(bs)) != -1){
                outputStream.write(bs, 0, len);
            }
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 删除PDF
     * @param fileName 文件名
     */
    public void deletePDF(String fileName){
        File file = new File(PDF_TEMP_PATH + fileName);
        if (file.exists()){
            file.delete();
        }
    }

}
