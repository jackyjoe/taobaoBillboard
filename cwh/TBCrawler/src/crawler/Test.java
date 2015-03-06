package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.net.MalformedURLException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Test {
	WebClient webClient;

	public static void main(String[] args) {
		// // 发送 GET 请求
		// String s = Test.sendGet("http://hdc1.alicdn.com/asyn.htm",
		// "pageId=438688386&userId=1821214016");
		// System.out.println(s);
		//
		// 发送 POST 请求
		// String sr = Test.sendPost("http://hdc1.alicdn.com/asyn.htm",
		// "userId=356090571&pageId=976607046&v=2014");
		// System.out.println(sr);

		Test test = new Test();
		test.getPoductInfo("40673325270");

	}

	// TShop.poc('buyshow'); //Hub={};
	// http://hdc1.alicdn.com
	String flag = "http://hdc1.alicdn.com";

	public Test() {
		webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_9);
		webClient.getOptions().setJavaScriptEnabled(true);// 开启js解析
		webClient.getOptions().setCssEnabled(false);// 开启css解析
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
	}

	public String getPoductInfo(String pid) {
		String result = null;
		if (null != pid) {
			String url = "http://detail.tmall.com/item.htm?id=" + pid;
			System.out.println(url);
			String temp = getDetailedField(url);
			temp = sendPost(temp.split("\\?")[0], temp.split("\\?")[1]);
			temp = temp.substring(temp.indexOf("{"));

			JSONObject jsonObject = JSONObject.fromObject(temp);
			System.out.println(jsonObject);

			result = jsonObject.getString("left");
			int index = result.indexOf(pid);
			System.out.println(result.substring(index));

		}

		// Iterator<String> it = jsonObject.keySet().iterator();
		// while (it.hasNext()) {
		// String str = it.next();
		// System.out.println(str + ": " + jsonObject.getString(str));
		// System.out.println("----------");
		// }

		return result;
	}

	public String getDetailedField(String url) {
		String field = null;
		try {

			HtmlPage htmlPage = webClient.getPage(url);

			// 获取取 script标签字段，寻找含有所需json数据
			DomNodeList<DomElement> list = htmlPage
					.getElementsByTagName("script");
			for (DomElement ele : list) {
				if (ele.asXml().contains("TShop.poc(")
						|| ele.asXml().contains("Hub={}")) {
					field = ele.asXml();
					break;
				}
			}

		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
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

	public void test() throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_9);
		webClient.getOptions().setJavaScriptEnabled(true);// 开启js解析
		webClient.getOptions().setCssEnabled(false);// 开启css解析
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		String url = "http://s.taobao.com/search?filter=reserve_price%5B300%2C400%5D&tab=all&q=uniqlo&stats_click=search_radio_all%253A1&sort=renqi-desc";
		HtmlPage htmlPage = webClient.getPage(url);
		System.out.println(htmlPage.asXml());
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */

	public static String sendGet(String url, String param) {
		return sendGet(url + "?" + param);
	}

	public static String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
