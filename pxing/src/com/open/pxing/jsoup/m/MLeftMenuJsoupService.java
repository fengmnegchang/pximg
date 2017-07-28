/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7下午5:42:52
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.jsoup.m;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.android.jsoup.CommonService;
import com.open.pxing.bean.m.MSlideMenuBean;
import com.open.pxing.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-6-7下午5:42:52
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MLeftMenuJsoupService extends CommonService {
	public static List<MSlideMenuBean> parseList(String href, int pageNo) {
		List<MSlideMenuBean> list = new ArrayList<MSlideMenuBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			// if (pageNo > 1) {
			// doc = Jsoup.parse(href);
			// }else{
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// }
			Log.i(TAG, "url = " + href);

			// Document doc =
			// Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				MSlideMenuBean sbean = new MSlideMenuBean();
				sbean.setHref(href);
				sbean.setTitle("首页");
				list.add(sbean);
				Element globalnavElement = doc.select("ul.main-nav").first();
				Elements moduleElements = globalnavElement.select("li.menu-item");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						Element pElement = moduleElements.get(i);
						 
							  sbean = new MSlideMenuBean();
							try {
								try {
									Element aElement = moduleElements.get(i).select("a").first();
									if (aElement != null) {
										String hrefa = aElement.attr("href");
										 
										Log.i(TAG, "i==" + i + ";hrefa==" + hrefa);
										sbean.setHref(hrefa);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element imgElement = moduleElements.get(i).select("a").first();
									if (imgElement != null) {
										String title = imgElement.text();
										Log.i(TAG, "i==" + i + ";title==" + title);
										sbean.setTitle(title);

									}
								} catch (Exception e) {
									e.printStackTrace();
								}

							} catch (Exception e) {
								e.printStackTrace();
							}

							list.add(sbean);
						 
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<MSlideMenuBean> parsePCList(String href, int pageNo) {
		List<MSlideMenuBean> list = new ArrayList<MSlideMenuBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			// if (pageNo > 1) {
			// doc = Jsoup.parse(href);
			// }else{
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// }
			Log.i(TAG, "url = " + href);

			// Document doc =
			// Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				Element globalnavElement = doc.select("div.nav").first();
				Elements moduleElements = globalnavElement.select("li");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						Element pElement = moduleElements.get(i);
						 
							MSlideMenuBean sbean = new MSlideMenuBean();
							try {
								try {
									Element aElement = moduleElements.get(i).select("a").first();
									if (aElement != null) {
										String hrefa = aElement.attr("href");
										 
										Log.i(TAG, "i==" + i + ";hrefa==" + hrefa);
										sbean.setHref(hrefa);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element imgElement = moduleElements.get(i).select("a").first();
									if (imgElement != null) {
										String title = imgElement.text();
										Log.i(TAG, "i==" + i + ";title==" + title);
										sbean.setTitle(title);

									}
								} catch (Exception e) {
									e.printStackTrace();
								}

							} catch (Exception e) {
								e.printStackTrace();
							}

							list.add(sbean);
						 
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
