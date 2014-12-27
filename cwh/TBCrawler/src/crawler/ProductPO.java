package crawler;

/**
* Title: Product.java
* Description: 
* Copyright: Copyright (c) 2014
* Company: NJU
* @author cwh
* @date 2014年12月27日
* @version 1.0
*/
public class ProductPO {

	// 商品ID
	private String pid;
	// 商品标题
	private String title;
	// 商品显示价格
	private String view_price;
	// 商品销量
	private String salesnum;
	// 商品评论数
	private String comment_count;
	// 商品下架时间 long型
	private String ends;

	public ProductPO(String pid, String title, String view_price,
			String salesnum, String comment_count) {
		super();
		this.pid = pid;
		this.title = title;
		this.view_price = view_price;
		this.salesnum = salesnum;
		this.comment_count = comment_count;

	}

	public ProductPO(String pid, String title, String view_price,
			String salesnum, String comment_count, String ends) {
		super();
		this.pid = pid;
		this.title = title;
		this.view_price = view_price;
		this.salesnum = salesnum;
		this.comment_count = comment_count;
		this.ends = ends;
	}

	public void display() {
		System.out.println("pid:" + pid + ", title:" + title + ", view_price:"
				+ view_price + ", salesnum:" + salesnum + ", comment_count:"
				+ comment_count + ", ends:" + ends);
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getView_price() {
		return view_price;
	}

	public void setView_price(String view_price) {
		this.view_price = view_price;
	}

	public String getSalesnum() {
		return salesnum;
	}

	public void setSalesnum(String salesnum) {
		this.salesnum = salesnum;
	}

	public String getComment_count() {
		return comment_count;
	}

	public void setComment_count(String comment_count) {
		this.comment_count = comment_count;
	}

}
