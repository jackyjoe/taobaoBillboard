package crawler;

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
 * Title: Crawling.java 
 * Description: 淘宝商品爬取
 * Copyright: Copyright (c) 2014 
 * Company: NJU
 * @author cwh
 * @date 2014年12月26日
 * @version 1.0
 */
public class Crawler {
	private static Crawler instance = new Crawler();

	private final WebClient webClient;
	private HtmlPage htmlPage = null;
	private String taobao_url;
	private String start_price;
	private String end_price;
	private String keyword;

	private JSONArray reslut_array = new JSONArray();

	// 模拟测试
	public JSONArray test() {
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
		return convertToJSONs(sourceData);
	}

	@SuppressWarnings("deprecation")
	private Crawler() {
		webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_9);
		webClient.getOptions().setJavaScriptEnabled(true);// 开启js解析
		webClient.getOptions().setCssEnabled(false);// 开启css解析
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		// getRealPrice("15682978288");
	}

	// 单例模式
	public static Crawler getCrawlerInstance() {
		return instance;
	}

	// 设定爬取页面条件，关键词，价格区间，爬取数量，默认排序为综合排序F
	public void setConditions(String keyword, String start_price,
			String end_price, String sort) {
		try {
			this.keyword = URLEncoder.encode(keyword, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.start_price = start_price;
		this.end_price = end_price;
		this.taobao_url = "http://s.taobao.com/search?filter=reserve_price%5B"
				+ this.start_price + "%2C" + this.end_price + "%5D&tab=all&q="
				+ this.keyword + "&stats_click=search_radio_all%253A1&sort="
				+ sort;
	}

	// 返回爬取结果json数据
	public JSONArray crawlResult(int num) {
		this.crawling(num);
		// this.appendJSON();
		return reslut_array;
	}

	// 按指定规则开始爬取一定数量商品
	private void crawling(int num) {
		reslut_array.clear();
		int count = num;
		int s = 0;
		JSONArray temp = null;

		if (null != taobao_url) {
			do {
				if (s == 0) {
					temp = convertToJSONs(getField(taobao_url));
				} else {
					temp = convertToJSONs(getField(taobao_url + "s=" + s));
				}
				s += 44;// 淘宝翻页标签以s=44*i 形式翻页

				if (count >= temp.size()) {
					reslut_array.addAll(temp);
					count -= temp.size();
				} else {
					reslut_array.addAll(temp.subList(0, count));
					count = 0;
				}
			} while (count != 0 && temp != null && temp.size() != 0);

		}
	}

	// 根据url，获取页面上script字段，找出含有json数据的正确的script字段
	private String getField(String url) {
		String field = null;
		try {
			HtmlPage htmlPage = webClient.getPage(url);

			// 获取取 script标签字段，寻找含有所需json数据
			DomNodeList<DomElement> list = htmlPage
					.getElementsByTagName("script");
			for (DomElement ele : list) {
				if (ele.asXml().contains(CrawlerConsts.G_PAGE_CONFIG)) {
					field = ele.asXml();
					break;
				}
			}

		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
		// System.out.println(field);
		// System.out.println(url);

		return field;
	}

	// 取出script字段内的json数据，返回json数组
	private JSONArray convertToJSONs(String field) {
		JSONArray array = null;
		if (null != field) {
			// 获取所需json的位置
			int index_start = field.indexOf(CrawlerConsts.FIELD_START);
			int index_end = field.indexOf(CrawlerConsts.FIELD_END);

			if (-1 != index_start && -1 != index_end) {
				String temp = field.substring(index_start
						+ CrawlerConsts.FIELD_START.length() + 2, index_end);

				array = JSONArray.fromObject(temp);
			}
		}
		return array;
	}

	// 为爬取JSON数据结果,添加信息
	@SuppressWarnings("unused")
	private void appendJSON() {
		for (int i = 0; i < reslut_array.size(); i++) {
			JSONObject o = (JSONObject) reslut_array.get(i);
			// System.out.println("iddddddddd:" +
			// o.getString(CrawlerConsts.PID));

			o.put(CrawlerConsts.PRODUCT_ENDS,
					getEndsTime(o.getString(CrawlerConsts.PID), true));
		}
	}

	/*
	 * 以上为配合使用，crawlResult为对外接口，以下为其他独立细小功能
	 */

	// 按排名返回商品处于前num中第几名
	public int crawlPidRank(String id, int num) {

		reslut_array.clear();
		int count = num;
		int rank = 0;
		int s = 0;
		JSONObject temp = null;
		JSONArray arr = null;

		if (null != taobao_url) {
			do {
				if (s == 0) {
					arr = convertToJSONs(getField(taobao_url));
				} else {
					arr = convertToJSONs(getField(taobao_url + "s=" + s));
				}
				s += 44;// 淘宝翻页标签以s=44*i 形式翻页

				for (int i = 0; i < arr.size(); i++) {
					temp = (JSONObject) arr.get(i);
					// System.out.println(temp.getString(CrawlerConsts.PID));
					if (temp.getString(CrawlerConsts.PID).equals(id)) {
						count = -1;
						break;
					}
					rank++;
				}
				if (count >= arr.size()) {
					count -= arr.size();
				} else {
					count = 0;
				}
			} while (count != 0 && arr != null && arr.size() != 0);

		}
		if (rank < num) {
			return rank;
		} else {
			return -1;

		}
	}

	// 获取某商品页面
	private HtmlPage getPage(String pid) {
		String url = "http://detail.tmall.com/item.htm?id=" + pid;
		this.htmlPage = null;
		try {
			htmlPage = webClient.getPage(url);
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return htmlPage;
	}

	// 爬取某商品标题
	public String getTitle(String pid, boolean flag) {
		if (flag) {
			this.getPage(pid);
		}
		String title = null;

		// 包含上下架时间的标签
		DomNodeList<DomElement> list = htmlPage
				.getElementsByTagName(CrawlerConsts.TITLE);
		if (list.size() > 0) {
			title = list.get(0).asText();
		}
		return title;
	}

	// 爬取某商品价格
	public double getPrice(String pid, boolean flag) {
		if (flag) {
			this.getPage(pid);
		}
		double result = -1;

		DomElement ele = htmlPage.getElementById(CrawlerConsts.J_STRPRICE);
		if (null != ele) {
			DomElement temp = ele.getLastElementChild();
			if (null != temp) {
				result = Double.parseDouble(temp.asText());
			}
		}
		return result;

	}

	// 爬取某商品下架时间返回long
	public long getEndsTime(String pid, boolean flag) {
		if (flag) {
			this.getPage(pid);
		}

		long result = -1;
		// 包含上下架时间的标签
		DomElement ele = htmlPage
				.getElementById(CrawlerConsts.PRODUCT_ENDS_TAG);
		if (null != ele) {
			String str = ele.asXml();
			str = str.substring(str.indexOf(CrawlerConsts.PRODUCT_ENDS));
			str = str.substring((CrawlerConsts.PRODUCT_ENDS + "=").length(),
					str.indexOf("&"));
			result = Long.parseLong(str);
		}

		return result;
	}

	// 爬取商品，返回数组{标题(String)，价格(double)，销量(int)}

	public Object[] getPoductInfo(String pid) {
		int salesnum = this.getPoductSalesNum(pid);
		String title = this.getTitle(pid, false);
		double price = this.getPrice(pid, false);
		Object[] arr = { title, price, salesnum };
		return arr;
	}

	public int getPoductSalesNum(String pid) {
		String field = null;
		int salesnum = -1;
		if (null != pid) {
			String temp = getDetailedField(pid);
			temp = HttpRequest.sendPost(temp.split("\\?")[0],
					temp.split("\\?")[1]);
			temp = temp.substring(temp.indexOf("{"));

			JSONObject jsonObject = JSONObject.fromObject(temp);
			// System.out.println(jsonObject);

			field = jsonObject.getString(CrawlerConsts.LEFT);
			field = field.substring(field.indexOf(pid));
			// System.out.println(result);

			/*
			 * field = field.substring(field.indexOf("title=\"") +
			 * "title=\"".length()); String title = field.substring(0,
			 * field.indexOf("\"")); System.out.println(title);
			 */

			// String a;
			// try {
			// a = URLEncoder.encode(title, "GBK");
			// System.out.println(a);
			// } catch (UnsupportedEncodingException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			temp = field.substring(
					field.indexOf("<strong>") + "<strong>".length(),
					field.indexOf("</strong>"));
			salesnum = Integer.parseInt(temp);
			// System.out.println(salesnum);

		}

		return salesnum;
	}

	private String getDetailedField(String pid) {
		this.getPage(pid);
		String field = null;

		// 获取取 script标签字段，寻找含有所需json数据
		DomNodeList<DomElement> list = htmlPage.getElementsByTagName("script");
		for (DomElement ele : list) {
			if (ele.asXml().contains(CrawlerConsts.TSHOP)
					|| ele.asXml().contains(CrawlerConsts.HUB)) {
				field = ele.asXml();
				break;
			}
		}

		if (null != field) {
			// 获取数据url的位置
			int index_start = field.indexOf("'http://hdc1.alicdn.com");
			if (-1 != index_start) {
				field = field.substring(index_start + 1);
				field = field.substring(0, field.indexOf("'"));
			}

		}

		return field;
	}

	public void close() {
		if (null != webClient) {
			webClient.closeAllWindows();
		}
	}

}
