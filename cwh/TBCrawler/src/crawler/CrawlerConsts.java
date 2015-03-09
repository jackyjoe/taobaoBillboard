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

	/*
	 * 以下是taobao搜索界面涉及的标签
	 */
	//
	// JSON数据所在script区域包含该标识
	final static String G_PAGE_CONFIG = "g_page_config";
	// 字段内JSON数据开始标示
	final static String FIELD_START = "auctions";
	// 字段内JSON数据结束标示
	final static String FIELD_END = ",\"postFeeText\":";
	// 以下为json数据段表示
	// 商品评论数
	final static String COMMENT_COUNT = "comment_count";
	// 商品标题
	final static String RAW_TITLE = "raw_title";
	// 商品销量
	final static String SALESNUM = "view_sales";
	// 商品ID
	final static String PID = "nid";
	// 商品显示价格
	final static String VIEW_PRICE = "view_price";

	// 包含商品下架时间所在标签
	final static String PRODUCT_ENDS_TAG = "J_listBuyerOnView";

	// 搜索排序关键字人气排序
	final static String RENQI_DESC = "renqi-desc";

	final static String DEFAULT_ORDER = "default";

	/*
	 * 以下是在某id商品对应页面下，使用的标签
	 */
	//
	// 爬取某商品标题标签
	final static String TITLE = "title";

	// 爬取某商品价格所在位置的标签
	final static String J_STRPRICE = "J_StrPrice";
	// 商品下架时间
	final static String PRODUCT_ENDS = "ends";
	
	//包含http://hdc1.alicdn.com*****字段所在的标签,天猫和淘宝
	final static String TSHOP= "TShop.poc(";
	final static String HUB = "Hub={}";
	
	/*
	 * 以下是在http://hdc1.alicdn.com*****对应页面下，使用的标签
	 */
	//这对应id商品 数据所在的位置标签
	final static String LEFT = "left";

	

}
