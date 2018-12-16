package top.cflwork.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 图书表
 * 
 * @author 陈飞龙
 * @email 275300091@qq.com
 * @date 2018-10-22 14:17:18
 */
public class BookDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//图书编号
	private Long id;
	//图书名称
	private String bookName;
	//图书序列号
	private Long bookImei;
	//作者
	private String author;
	//发布时间
	private Date publicTime;
	//创建时间
	private Date createTime;

	/**
	 * 设置：图书编号
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：图书编号
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：图书名称
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	/**
	 * 获取：图书名称
	 */
	public String getBookName() {
		return bookName;
	}
	/**
	 * 设置：图书序列号
	 */
	public void setBookImei(Long bookImei) {
		this.bookImei = bookImei;
	}
	/**
	 * 获取：图书序列号
	 */
	public Long getBookImei() {
		return bookImei;
	}
	/**
	 * 设置：作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * 获取：作者
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * 设置：发布时间
	 */
	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}
	/**
	 * 获取：发布时间
	 */
	public Date getPublicTime() {
		return publicTime;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
