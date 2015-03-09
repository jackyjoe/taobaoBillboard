package crawler;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* Title: JsonToDataTool.java
* Description: 工具类，将JSON数据格式转换成所需数据
* Copyright: Copyright (c) 2014
* Company: NJU
* @author cwh
* @date 2014年12月27日
* @version 1.0
*/
public class JsonToDataTool {
	public JsonToDataTool() {

	}

	public ProductPO jsonToProduct(JSONObject o) {
		ProductPO pro = new ProductPO(o.getString(CrawlerConsts.PID),
				o.getString(CrawlerConsts.RAW_TITLE),
				o.getString(CrawlerConsts.VIEW_PRICE),
				o.getString(CrawlerConsts.SALESNUM),
				o.getString(CrawlerConsts.COMMENT_COUNT));
		return pro;
	}

	public ProductPO[] jsonToProducts(JSONArray jsonArray) {
		ProductPO[] pros = new ProductPO[jsonArray.size()];
		JSONObject temp = null;
		for (int i = 0; i < jsonArray.size(); i++) {
			temp = (JSONObject) jsonArray.get(i);
			pros[i] = jsonToProduct(temp);
		}
		return pros;
	}

	public ProductPO jsonToDetailedProduct(JSONObject o) {
		ProductPO pro = new ProductPO(o.getString(CrawlerConsts.PID),
				o.getString(CrawlerConsts.RAW_TITLE),
				o.getString(CrawlerConsts.VIEW_PRICE),
				o.getString(CrawlerConsts.SALESNUM),
				o.getString(CrawlerConsts.COMMENT_COUNT),
				o.getString(CrawlerConsts.PRODUCT_ENDS));
		return pro;
	}

	public ProductPO[] jsonToDetailedProducts(JSONArray jsonArray) {
		ProductPO[] pros = new ProductPO[jsonArray.size()];
		JSONObject temp = null;
		for (int i = 0; i < jsonArray.size(); i++) {
			temp = (JSONObject) jsonArray.get(i);
			pros[i] = jsonToDetailedProduct(temp);
		}
		return pros;
	}
}
