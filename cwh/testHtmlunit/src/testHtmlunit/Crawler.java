package testHtmlunit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Scanner;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Title: Crawling.java Description: Copyright: Copyright (c) 2014 Company: NJU
 * 
 * @author cwh
 * @date 2014年12月26日
 * @version 1.0
 */
public class Crawler {
	private final WebClient webClient;
	//包含json数据的字段
	private String field = null;

	private String url;
	private String start_price;
	private String end_price;
	private String keyword;

	// 模拟测试
	public void test() {
		String sourceData = "";
		try {
			FileInputStream fin = new FileInputStream(
					"temptestData/g_page_config.TXT");
			Scanner scanner = new Scanner(fin);
			while (scanner.hasNext()) {
				sourceData += scanner.next();
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		field = sourceData;
		convertToJsons();
	}

	@SuppressWarnings("deprecation")
	public Crawler() {
		webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_9);
		webClient.getOptions().setJavaScriptEnabled(true);// 开启js解析
		webClient.getOptions().setCssEnabled(false);// 开启css解析
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);

	}

	// 设定爬取页面条件
	public void setConditions(String keyword, String start_price,
			String end_price) {
		try {
			this.keyword = URLEncoder.encode(keyword, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.start_price = start_price;
		this.end_price = end_price;
		url = "http://s.taobao.com/search?tab=all&q=" + this.keyword
				+ "&stats_click=search_radio_all%253A1&filter=reserve_price%5B"
				+ this.start_price + "%2C" + this.end_price + "%5D";
	}

	// 开始爬取一定数量指定规则商品
	public void crawling(int num) {
		
		HtmlPage htmlPage;
		try {
			htmlPage = webClient.getPage(url);
			// 获取取第6个script标签字段，含有所需json数据
			DomNodeList<DomElement> list = htmlPage.getElementsByTagName("script");
			field = list.get(5).asXml();
			
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
	}

	// 取出字段内的json，返回json数组
	public JSONArray convertToJsons() {
		JSONArray array = null;
		String temp;
		// 获取所需json的位置
		int index_start = field.indexOf(ProductConsts.FIELD_START);
		int index_end = field.indexOf(ProductConsts.FIELD_END);

		if (-1 != index_start && -1 != index_end) {
			temp = field.substring(
					index_start + ProductConsts.FIELD_START.length() + 2,
					index_end);

			// System.out.println(temp);
			array = JSONArray.fromObject(temp);
			JSONObject o = null;
			for (int i = 0; i < array.size(); i++) {
				o = (JSONObject) array.get(i);
				System.out.println((i + 1) + ": " + o.toString());

			}
		}
		return array;
	}

	public void close() {
		if (null != webClient) {
			webClient.closeAllWindows();
		}
	}

	// 测试商品id
	public void parseId(String str) {
		String temp;
		int index = str.indexOf("auctionNids");
		if (index != -1) {
			temp = str.substring(index);// 找出位置
			temp = temp.substring(temp.indexOf('[') + 2, temp.indexOf(']') - 1);

			String[] strs = temp.split("\",\"");
			for (int i = 0; i < strs.length; i++) {
				System.out.println((i + 1) + ": " + strs[i]);

			}
		}

	}

	// 测试价格
	public void parsePrice(String str) {
		String temp;
		int index = str.indexOf("auctionPrices");
		if (index != -1) {
			temp = str.substring(index);// 找出价格位置
			temp = temp.substring(temp.indexOf('[') + 2, temp.indexOf(']') - 1);

			String[] strs = temp.split("\",\"");
			for (int i = 0; i < strs.length; i++) {
				System.out.println((i + 1) + ": " + strs[i]);

			}
		}

	}

}
