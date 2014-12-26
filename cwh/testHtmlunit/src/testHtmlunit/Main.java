package testHtmlunit;

import net.sf.json.JSONArray;

/**
 * Title: Main.java Description: Copyright: Copyright (c) 2014 Company: NJU
 * 
 * @author cwh
 * @date 2014年12月26日
 * @version 1.0
 */
public class Main {
	public static void main(String args[]) {
		Crawler crawler = new Crawler();

		crawler.setConditions("衣", "6666", 8888 + "");
		crawler.crawling(0);
		JSONArray array = crawler.convertToJsons();
		// crawler.test();

		// crawler.close();
	}
}
