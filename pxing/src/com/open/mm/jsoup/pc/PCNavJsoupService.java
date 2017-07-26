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
package com.open.mm.jsoup.pc;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.android.jsoup.CommonService;
import com.open.mm.bean.m.MArticleBean;
import com.open.mm.bean.pc.HomeArticleBean;
import com.open.mm.utils.EscapeUnescapeUtils;
import com.open.mm.utils.UrlUtils;

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
public class PCNavJsoupService extends CommonService {
	public static List<MArticleBean> parseList(String href, int pageNo) {
		List<MArticleBean> list = new ArrayList<MArticleBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			//http://www.mm131.com/xinggan/list_6_2.html
			if(pageNo>1){
				if(href.contains("xinggan")){
					href = UrlUtils.MM_PC+"xinggan/list_6_"+pageNo+".html";
				}else if(href.contains("qingchun")){
					href = UrlUtils.MM_PC+"qingchun/list_1_"+pageNo+".html";
				}else if(href.contains("xiaohua")){
					href = UrlUtils.MM_PC+"xiaohua/list_2_"+pageNo+".html";
				}else if(href.contains("chemo")){
					href = UrlUtils.MM_PC+"chemo/list_3_"+pageNo+".html";
				}else if(href.contains("qipao")){
					href = UrlUtils.MM_PC+"qipao/list_4_"+pageNo+".html";
				}else if(href.contains("mingxing")){
					href = UrlUtils.MM_PC+"mingxing/list_5_"+pageNo+".html";
				}
			}
			
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);

//			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 * <a target="_blank" href="http://www.mm131.com/xinggan/2983.html">
				 * <img src="http://img1.mm131.com/pic/2983/0.jpg" 
				 * alt="巨乳少女梓熙大尺度浴室湿身诱惑" width="120" height="160"
				 *  />巨乳少女梓熙大尺度浴室</a>
				 */
				 Element globalnavElement =
				 doc.select("dl.list-left").first();
				Elements moduleElements = globalnavElement.select("dd");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						if(moduleElements.get(i).select("dd").first().attr("class").equals("page")){
							continue;
						}
							MArticleBean sbean = new MArticleBean();
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
									Element imgElement = moduleElements.get(i).select("img").first();
									if (imgElement != null) {
										String src = imgElement.attr("src");
										Log.i(TAG, "i==" + i + ";src==" + src);
										sbean.setSrc(src);

									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element imgElement = moduleElements.get(i).select("img").first();
									if (imgElement != null) {
										String alt = imgElement.attr("alt");
										if(alt.contains("%u")){
											alt = EscapeUnescapeUtils.unescape(alt);
										}
										Log.i(TAG, "i==" + i + ";alt==" + alt);
										sbean.setAlt(alt);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								 

								try {
									Element imgElement = moduleElements.get(i).select("a").first();
									if (imgElement != null) {
										String postmeta = imgElement.text();
										Log.i(TAG, "i==" + i + ";postmeta==" + postmeta);
										sbean.setPostmeta(postmeta);
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
	

	public static List<MArticleBean> parseFootList(String href, int pageNo) {
		List<MArticleBean> list = new ArrayList<MArticleBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);

//			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			
			try {
				 Element globalnavElement =  doc.select("dl.hot").first();
					Elements moduleElements = globalnavElement.select("dd");
					if (moduleElements != null && moduleElements.size() > 0) {
						for (int i = 0; i < moduleElements.size(); i++) {
							Element pElement = moduleElements.get(i);
							 
								MArticleBean sbean = new MArticleBean();
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
											String alt = imgElement.text();
											Log.i(TAG, "i==" + i + ";alt==" + alt);
											sbean.setAlt(alt);
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
				// TODO: handle exception
			}
			
			try {
				/**
				 */
				 Element globalnavElement =  doc.select("div.main-right").first();
				Elements moduleElements = globalnavElement.select("dd");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						Element pElement = moduleElements.get(i);
						 
							MArticleBean sbean = new MArticleBean();
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
										String alt = imgElement.text();
										Log.i(TAG, "i==" + i + ";alt==" + alt);
										sbean.setAlt(alt);
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
	
	public static List<MArticleBean> parseHomeHeadList(String href, int pageNo) {
		List<MArticleBean> list = new ArrayList<MArticleBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);

//			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				 Element globalnavElement =
				 doc.select("ul.public-box").first();
				Elements moduleElements = globalnavElement.select("li");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						Element pElement = moduleElements.get(i);
						 
							MArticleBean sbean = new MArticleBean();
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
										String alt = imgElement.text();
										Log.i(TAG, "i==" + i + ";alt==" + alt);
										sbean.setAlt(alt);
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
	
	public static List<MArticleBean> parsePagerList(String href, int pageNo) {
		List<MArticleBean> list = new ArrayList<MArticleBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			doc = Jsoup.parse(href);
			Log.i(TAG, "url = " + href);

//			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
//				 Element globalnavElement = doc.select("div.main_top").first();
				 Element ulElement = doc.select("ul").first();
				Elements moduleElements = ulElement.select("li");
				Elements moduleElements2 = doc.select("ul").get(1).select("li");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						Element pElement = moduleElements.get(i);
						 
							MArticleBean sbean = new MArticleBean();
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
									Element imgElement = moduleElements.get(i).select("img").first();
									if (imgElement != null) {
										String src = imgElement.attr("src");
										Log.i(TAG, "i==" + i + ";src==" + src);
										sbean.setSrc(src);

									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element imgElement = moduleElements2.get(i).select("img").first();
									if (imgElement != null) {
										String alt = imgElement.attr("alt");
										if(alt.contains("%u")){
											alt = EscapeUnescapeUtils.unescape(alt);
										}
										Log.i(TAG, "i==" + i + ";alt==" + alt);
										sbean.setAlt(alt);
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
	
	public static List<HomeArticleBean> parseHomeList(String href, int pageNo) {
		List<HomeArticleBean> list = new ArrayList<HomeArticleBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);
//			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				 Element globalnavElement = doc.select("div.main-left").first();
				Elements moduleElements = globalnavElement.select("ul");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						    HomeArticleBean hbean= new HomeArticleBean();
						    Element ulElement = moduleElements.get(i).select("ul").first();
							Elements liElements = ulElement.select("li");
							List<MArticleBean> mlist = new ArrayList<MArticleBean>();
							for(int j=0;j<liElements.size();j++){
								MArticleBean sbean = new MArticleBean();
								try {
									try {
										if(liElements.get(j).attr("class").contains("column-title")){
											hbean.setAlt(liElements.get(j).text());
											hbean.setHref(liElements.get(j).select("a").first().attr("href"));
											continue;
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									try {
										Element aElement = liElements.get(j).select("a").first();
										if (aElement != null) {
											String hrefa = aElement.attr("href");
											Log.i(TAG, "j==" + j + ";hrefa==" + hrefa);
											sbean.setHref(hrefa);
										}
									} catch (Exception e) {
										e.printStackTrace();
									}

									try {
										Element imgElement = liElements.get(j).select("a").first();
										if (imgElement != null) {
											String alt = imgElement.text();
											Log.i(TAG, "j==" + j + ";alt==" + alt);
											sbean.setAlt(alt);
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									try {
										Element imgElement = liElements.get(j).select("img").first();
										if (imgElement != null) {
											String src = imgElement.attr("src");
											Log.i(TAG, "j==" + j + ";src==" + src);
											sbean.setSrc(src);

										}
									} catch (Exception e) {
										e.printStackTrace();
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
								mlist.add(sbean);
							}
							hbean.setList(mlist);
							list.add(hbean);
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
	
	public static List<MArticleBean> parsePCPagerList(String href, int pageNo) {
		List<MArticleBean> list = new ArrayList<MArticleBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Log.i(TAG, "url = " + href);
			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				 Element globalnavElement = doc.select("div.otherlist").first();
				Elements moduleElements = globalnavElement.select("li");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						Element pElement = moduleElements.get(i);
						 
							MArticleBean sbean = new MArticleBean();
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
									Element imgElement = moduleElements.get(i).select("img").first();
									if (imgElement != null) {
										String src = imgElement.attr("src");
										Log.i(TAG, "i==" + i + ";src==" + src);
										sbean.setSrc(src);

									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element imgElement = moduleElements.get(i).select("img").first();
									if (imgElement != null) {
										String alt = imgElement.attr("alt");
										if(alt.contains("%u")){
											alt = EscapeUnescapeUtils.unescape(alt);
										}
										Log.i(TAG, "i==" + i + ";alt==" + alt);
										sbean.setAlt(alt);
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
