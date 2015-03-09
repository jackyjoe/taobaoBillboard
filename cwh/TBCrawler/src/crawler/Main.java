package crawler;

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
		TBDataAPIImpl testAPI = new TBDataAPIImpl();

		Object[] arr = testAPI.getPoductInfo("40673325270");
		String title = (String) arr[0];
		double price = (double) arr[1];
		int salesnum = (int) arr[2];
		System.out.println("title: " + title);
		System.out.println("price: " + price);
		System.out.println("salesnum: " + salesnum);

		// testAPI.getProductsSortByDefault("uniqlo", "300", "400", 24);
		// testAPI.getProductsSortByRenqi("uniqlo", "300", "400", 23);
		// System.out.println(testAPI.getRankByDefault("41118593898", "uniqlo",
		// "300", "400", 23));

		// String str =
		// "http://detail.tmall.com/item.htm?spm=a230r.1.14.15.Z5VqXY&id=42730493356&ad_id=&am_id=&cm_id=140105335569ed55e27b&pm_id=&abbucket=7";
		// System.out.println(testAPI.getProducEndsTimeByURL(str));
	}
}