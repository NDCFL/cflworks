package top.cflwork.controller;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import top.cflwork.util.PDFUtil;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import top.cflwork.domain.BookDO;
import top.cflwork.service.BookService;
import top.cflwork.util.PageUtils;
import top.cflwork.util.Query;
import top.cflwork.util.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 图书表
 * 
 * @author 陈飞龙
 * @email 275300091@qq.com
 * @date 2018-10-22 14:17:18
 */
 
@Controller
@RequestMapping("/system/book")
public class BookController {
	@Autowired
	private BookService bookService;
	
	@GetMapping()
	@RequiresPermissions("system:book:book")
	String Book(){
	    return "system/book/book";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:book:book")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<BookDO> bookList = bookService.list(query);
		int total = bookService.count(query);
		PageUtils pageUtils = new PageUtils(bookList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:book:add")
	String add(){
	    return "system/book/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:book:edit")
	String edit(@PathVariable("id") Long id,Model model){
		BookDO book = bookService.get(id);
		model.addAttribute("book", book);
	    return "system/book/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:book:add")
	public R save( BookDO book){
		if(bookService.save(book)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:book:edit")
	public R update( BookDO book){
		bookService.update(book);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:book:remove")
	public R remove( Long id){
		if(bookService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:book:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		bookService.batchRemove(ids);
		return R.ok();
	}

	@RequestMapping(value="downLoad")
	public void pdfDownLoad(HttpServletRequest request, HttpServletResponse response){
		PDFUtil pdf = new PDFUtil();
		String htmlCode = getHtmlCode();
		String fileName = "MyPDF.pdf";
		pdf.createPDF(htmlCode, fileName);
		pdf.downLoad(fileName, response);
		pdf.deletePDF(fileName);
	}

	private String getHtmlCode(){
		StringBuffer html = new StringBuffer();
		html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
				.append("<head>")
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")
				.append("<style type=\"text/css\" mce_bogus=\"1\">body {font-family: SimSun;}</style>")
				.append("<style type=\"text/css\">img {width: 700px;}</style>")
				.append("</head>")
				.append("<body>");
		html.append("<h1>这是一个PDF文档</h1>");
		html.append("<table>");
		html.append("<tr>");
		html.append("<td>第一列</td>");
		html.append("<td>第二列</td>");
		html.append("<td>第三列</td>");
		html.append("<td>第四列</td>");
		html.append("</tr>");
		html.append("</table>");
		html.append("</body></html>");
		return html.toString();
	}

	@RequestMapping("lookDoc")
	public void word(String src,HttpServletResponse response) throws Exception{
		BufferedInputStream bis = null;
		URL url = null;
		HttpURLConnection httpUrl = null; // 建立链接
		url = new URL(src);
		httpUrl = (HttpURLConnection) url.openConnection();// 连接指定的资源
		httpUrl.connect();// 获取网络输入流
		bis = new BufferedInputStream(httpUrl.getInputStream());

		String bodyText = null;
		WordExtractor ex = new WordExtractor(bis);
		bodyText = ex.getText();
		response.getWriter().write(bodyText);
	}

    @RequestMapping("lookPpt")
    public void lookPpt(String src,HttpServletResponse response) throws Exception{
        BufferedInputStream bis = null;
        URL url = null;
        HttpURLConnection httpUrl = null; // 建立链接
        url = new URL(src);
        httpUrl = (HttpURLConnection) url.openConnection();// 连接指定的资源
        httpUrl.connect();// 获取网络输入流
        bis = new BufferedInputStream(httpUrl.getInputStream());

        StringBuffer content = new StringBuffer("");
        SlideShow ss = new SlideShow(new HSLFSlideShow(bis));
        Slide[] slides = ss.getSlides();
        for (int i = 0; i < slides.length; i++) {
            TextRun[] t = slides[i].getTextRuns();
            for (int j = 0; j < t.length; j++) {
                content.append(t[j].getText());
            }
            content.append(slides[i].getTitle());
        }
        response.getWriter().write(content.toString());
    }
}
