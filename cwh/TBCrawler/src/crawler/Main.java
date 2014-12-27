package crawler;

import net.sf.json.JSONArray;

/**
 * Title: Main.java 
 * Description: 
 * Copyright: Copyright (c) 2014 
 * Company: NJU
 * @author cwh
 * @date 2014年12月26日
 * @version 1.0
 */
public class Main {
	public static void main(String args[]) {
		Crawler crawler = new Crawler();

		crawler.setConditions("衣", "6666", 8888 + "");
		JSONArray array = crawler.crawlResult(23);

		// JSONArray array = crawler.test();

		JsonToDataTool jsonToData = new JsonToDataTool();

		ProductPO[] pros = jsonToData.jsonToProducts(array);
		for (int i = 0; i < pros.length; i++) {
			System.out.print(i + ": ");
			pros[i].display();
		}
		// crawler.getEnds("40778766102");
		// crawler.close();
	}
}
