package crawler;

import java.util.Date;

import net.sf.json.JSONArray;

/**
 * Title: TBDataAPIImpl.java
 * Description: 获取淘宝数据接口实现
 * Copyright: Copyright (c) 2014 
 * Company: NJU
 * @author cwh
 * @date 2015年1月6日
 * @version 1.0
 */
public class TBDataAPIImpl implements TBDataAPI {
	private Crawler crawler;
	JsonToDataTool jsonToData;

	TBDataAPIImpl() {
		crawler = Crawler.getCrawlerInstance();
		jsonToData = new JsonToDataTool();
	}

	public void test() {

	}

	// 按照关键字，起始价格，结束价格，搜索数量，按综合排名返回数据。
	@Override
	public ProductPO[] getProductsSortByDefault(String keyword,
			String start_price, String end_price, int num) {
		crawler.setConditions(keyword, start_price, end_price);
		JSONArray array = crawler.crawlResult(num);
		ProductPO[] pros = jsonToData.jsonToProducts(array);

		for (int i = 0; i < pros.length; i++) {
			System.out.print(i + ": ");
			pros[i].display();
		}
		return pros;
	}

	// 按照关键字，起始价格，结束价格，搜索数量，按人气排名返回数据。
	@Override
	public ProductPO[] getProductsSortByRenqi(String keyword,
			String start_price, String end_price, int num) {
		crawler.setConditions(keyword, start_price, end_price,
				CrawlerConsts.RENQI_DESC);
		JSONArray array = crawler.crawlResult(num);
		ProductPO[] pros = jsonToData.jsonToProducts(array);

		for (int i = 0; i < pros.length; i++) {
			System.out.print(i + ": ");
			pros[i].display();
		}
		return pros;
	}

	// 按照关键字，起始价格，结束价格，搜索数量，按人气排名返回数据。
	@Override
	public long getProducEndsTime(String pid) {
		crawler.getEndsTime(pid);
		return 0;
	}

	// 根据商品pid爬取某商品下架时间返回util.Date
	@Override
	public Date getProductEndsDate(String pid) {
		Date result = new Date(getProducEndsTime(pid));
		return result;
	}

	@Override
	public long getProducEndsTimeByURL(String url) {
		if (url.contains("&id=")) {
			int pos = url.indexOf("&id=") + "&id=".length();
			String pid = url.substring(pos);
			pid = (pid.split("&"))[0];
			return crawler.getEndsTime(pid);
		} else {
			return -2;
		}
	}

	@Override
	public Date getProductSalesnum(String pid) {
		return null;
	}

	// <strong id="J_StrPrice" ><em class="tb-rmb">&yen;</em><em
	// class="tb-rmb-num">29.80 - 32.80</em></strong>
	// defaultItemPrice
	
	//http://hdc1.alicdn.com/asyn.htm?从中找出改该代码
	@Override
	public double getProductPrice(String pid) {
		return 0;
	}

}
