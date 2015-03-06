package crawler;

import java.util.Date;

/**
 * Title: TBDataAPI.java
 * Description: 获取淘宝数据接口
 * Copyright: Copyright (c) 2014 
 * Company: NJU
 * @author cwh
 * @date 2015年1月6日
 * @version 1.0
 */
public interface TBDataAPI {

	// 按照关键字，起始价格，结束价格，搜索数量，按综合排名返回数据。
	public ProductPO[] getProductsSortByDefault(String keyword,
			String start_price, String end_price, int num);

	// 按照关键字，起始价格，结束价格，搜索数量，按人气排名返回数据。
	public ProductPO[] getProductsSortByRenqi(String keyword,
			String start_price, String end_price, int num);

	// 按照要搜索商品id，关键字，起始价格，结束价格，搜索数量，按综合排名返回商品处于前num中第几名
	public int getRankByDefault(String id, String keyword, String start_price,
			String end_price, int num);

	// 按照要搜索商品id，关键字，起始价格，结束价格，搜索数量，按人气排名返回商品处于前num中第几名
	public int getRankByRenqi(String id, String keyword, String start_price,
			String end_price, int num);

	// 搜索 商品 根据pid 获得商品 标题 销量

	// 根据商品pid爬取某商品下架时间返回long
	public long getProducEndsTime(String pid);

	// 根据商品pid爬取某商品下架时间返回util.Date
	public Date getProductEndsDate(String pid);

	// 根据链接返回下架时间（默认网址无错误可以进入，-2表示链接不包含商品id，-1不存在该商品）
	public long getProducEndsTimeByURL(String url);

	// 根据商品pid爬取某商品销量返回
	public Date getProductSalesnum(String pid);

	// 根据商品pid爬取某商品价格返回
	public double getProductPrice(String pid);
}
