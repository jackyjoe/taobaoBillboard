package crawler;

/**
* Title: ProductConsts.java
* Description: 爬取时候所需的taobao商品标志符 
* Copyright: Copyright (c) 2014
* Company: NJU
* @author cwh
* @date 2014年12月26日
* @version 1.0
*/
public interface CrawlerConsts {

	// JSON数据所在script区域包含该标识
	final static String G_PAGE_CONFIG = "g_page_config";
	// 字段内JSON数据开始标示
	final static String FIELD_START = "auctions";
	// 字段内JSON数据结束标示
	final static String FIELD_END = ",\"postFeeText\":";

	// 商品评论数
	final static String COMMENT_COUNT = "comment_count";
	// 商品标题
	final static String TITLE = "raw_title";
	// 商品销量
	final static String SALESNUM = "view_sales";
	// 商品ID
	final static String PID = "nid";
	// 商品显示价格
	final static String VIEW_PRICE = "view_price";
	// 商品下架时间
	final static String PRODUCT_ENDS = "ends";
	// 包含商品下架时间所在标签
	final static String PRODUCT_ENDS_TAG = "J_listBuyerOnView";

}
