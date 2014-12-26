package testHtmlunit;

public class Product {

	// 商品评论数
	private String comment_count;
	// 商品标题
	private String title;
	// 商品销量
	private String salesnum;
	// 商品ID
	private String pid;
	// 商品显示价格
	private String view_price;

	public Product(String comment_count, String title, String salesnum,
			String pid, String view_price) {
		super();
		this.comment_count = comment_count;
		this.title = title;
		this.salesnum = salesnum;
		this.pid = pid;
		this.view_price = view_price;
	}

	public String getComment_count() {
		return comment_count;
	}

	public void setComment_count(String comment_count) {
		this.comment_count = comment_count;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSalesnum() {
		return salesnum;
	}

	public void setSalesnum(String salesnum) {
		this.salesnum = salesnum;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getView_price() {
		return view_price;
	}

	public void setView_price(String view_price) {
		this.view_price = view_price;
	}
}
