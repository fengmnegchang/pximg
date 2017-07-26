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
package com.open.mm.jsoup.m;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.android.jsoup.CommonService;
import com.open.mm.bean.m.MArticleBean;
import com.open.mm.json.m.MArticleJson;
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
public class MArticleJsoupService extends CommonService {
	public static List<MArticleBean> parseList(String href, int pageNo) {
		List<MArticleBean> list = new ArrayList<MArticleBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			if (pageNo > 1) {
				doc = Jsoup.parse(href);
			}else{
				doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			}
			Log.i(TAG, "url = " + href);

//			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 * <article id="post-1" class="post"><div class="post-header">
				 * <h2 class="post-title"><a class="post-title-link"
				 * href="http://m.mm131.com/xinggan/2962.html"
				 * rel="bookmark">诱人少妇优优身材火辣豪乳肉感十足</a></h2></div><div
				 * class="post-content post-text"><a
				 * href="http://m.mm131.com/xinggan/2962.html"><img
				 * src="http://img1.mm131.com/pic/2962/m.jpg"
				 * data-img="http://img1.mm131.com/pic/2962/m.jpg"
				 * alt="诱人少妇优优身材火辣豪乳肉感十足" /></a>
				 * <p>
				 * </p>
				 * </div><div class="post-footer"><span
				 * class="post-meta">2017-06-07
				 * 11:01:06</span></div></article><article class="post"
				 * id="myshow"></article>
				 */
				// Element globalnavElement =
				// doc.select("div.adFocusHtml").first();
				Elements moduleElements = doc.select("article.post");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						Element pElement = moduleElements.get(i);
						if (pElement.attr("id").contains("post-")) {
							MArticleBean sbean = new MArticleBean();
							try {
								try {
									Element aElement = moduleElements.get(i).select("a").first();
									if (aElement != null) {
										String hrefa = aElement.attr("href");
										if(!hrefa.contains(UrlUtils.MM_M)){
											hrefa = UrlUtils.MM_M+hrefa;
										}
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
									Element imgElement = moduleElements.get(i).select("img").first();
									if (imgElement != null) {
										String dataimg = imgElement.attr("data-img");
										Log.i(TAG, "i==" + i + ";dataimg==" + dataimg);
										sbean.setDataimg(dataimg);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element imgElement = moduleElements.get(i).select("span.post-meta").first();
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
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public static List<MArticleBean> parseSearchList(String href, int pageNo) {
		List<MArticleBean> list = new ArrayList<MArticleBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			href = href+pageNo;
			Document doc;
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);

//			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				// Element globalnavElement =
				// doc.select("div.adFocusHtml").first();
				Elements moduleElements = doc.select("article.post");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						Element pElement = moduleElements.get(i);
						if (pElement.attr("id").contains("post-")) {
							MArticleBean sbean = new MArticleBean();
							try {
								try {
									Element aElement = moduleElements.get(i).select("a").first();
									if (aElement != null) {
										String hrefa = aElement.attr("href");
										if(!hrefa.contains(UrlUtils.MM_M)){
											hrefa = UrlUtils.MM_M+hrefa;
										}
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
									Element imgElement = moduleElements.get(i).select("img").first();
									if (imgElement != null) {
										String dataimg = imgElement.attr("data-img");
										Log.i(TAG, "i==" + i + ";dataimg==" + dataimg);
										sbean.setDataimg(dataimg);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element imgElement = moduleElements.get(i).select("span.post-meta").first();
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
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	

	public static List<MArticleBean> parseImageList(String href, int pageNo) {
		List<MArticleBean> list = new ArrayList<MArticleBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			if(!href.contains("_")){
				if(pageNo>1){
					//http://m.mm131.com/xinggan/2847.html
					//http://m.mm131.com/xinggan/2847_2.html
					href = href.replace(".html", "_")+pageNo+".html";
				}
			}
			
			Document doc;
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);

//			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 * <article class="post">
         <div class="post-header">
            <h2 class="mm-title">大波女神悦悦艺术写真尺度大惑力强</h2>
            <div class="post-data"><span class="post-meta">2017-03-06 21:48:39</span></div>
         </div>
         <div class="post-content single-post-content"><
         a href="2847_2.html"><img alt="大波女神悦悦艺术写真尺度大惑力强(图1)"
          src="http://img2.mm131.com:55888/pic/2847/1.jpg" />
         </a></div>
         
		 <div class="fenye"><a href="2848.html" class="rw">上一篇</a><span class="rw">1/58页</span><a href="2847_2.html">下一张</a></div>
         <div class="mmss"><script type="text/javascript" src="http://img1.mm798.net/mixiu/js/ms.js"></script></div>
         <div class="post-footer single-post-footer">
            <span class="post-meta">分类：<a href="http://m.mm131.com/xinggan/">性感美女</a></span>
            <span class="post-tag">标签：<a href="http://m.mm131.com/tag/%B4%F3%B2%A8">大波</a><a href="http://m.mm131.com/tag/%D4%C3%D4%C3">悦悦</a><a href="http://m.mm131.com/tag/%D2%D5%CA%F5%D0%B4%D5%E6">艺术写真</a></span>
         </div>
		 <div class="jcdog"><script type="text/javascript">show_down();</script></div>
      </article>

				 */
				// Element globalnavElement =
				// doc.select("div.adFocusHtml").first();
				Elements moduleElements = doc.select("article.post");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						Element pElement = moduleElements.get(i);
						 
							MArticleBean sbean = new MArticleBean();
							try {
								try {
									Element aElement = moduleElements.get(i).select("a").first();
									if (aElement != null) {
										String hrefa = aElement.attr("href");
										if(!hrefa.contains(UrlUtils.MM_M)){
											hrefa = href;
										}
										Log.i(TAG, "i==" + i + ";hrefa==" + hrefa);
										sbean.setHref(hrefa);
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
									Element imgElement = moduleElements.get(i).select("img").first();
									if (imgElement != null) {
										String dataimg = imgElement.attr("src");
										Log.i(TAG, "i==" + i + ";dataimg==" + dataimg);
										sbean.setDataimg(dataimg);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element imgElement = moduleElements.get(i).select("span.post-meta").first();
									if (imgElement != null) {
										String postmeta = imgElement.text();
										Log.i(TAG, "i==" + i + ";postmeta==" + postmeta);
										sbean.setPostmeta(postmeta);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								
								/**
								 * <div class="post-footer single-post-footer">
            <span class="post-meta">分类：<a href="http://m.mm131.com/xinggan/">性感美女</a></span>
            <span class="post-tag">标签：<a href="http://m.mm131.com/tag/%B4%F3%B2%A8">大波</a><a href="http://m.mm131.com/tag/%D4%C3%D4%C3">悦悦</a><a href="http://m.mm131.com/tag/%D2%D5%CA%F5%D0%B4%D5%E6">艺术写真</a></span>
         </div>
								 */
								
								try {
									Element footerElement =  doc.select("div.single-post-footer").first();
									Element metaElement = footerElement.select("span.post-meta").first();
									sbean.setMeta(metaElement.toString());
									
									Element tagElement = footerElement.select("span.post-tag").first();
									sbean.setTag(tagElement.toString());
									
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
			if(pageNo>1){
				//http://m.mm131.com/xinggan/2847.html
				//http://m.mm131.com/xinggan/2847_2.html
				href = href.replace(".html", "_")+pageNo+".html";
			}
			
			Document doc;
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);

//			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				 Element globalnavElement =
				 doc.select("dl.other").first();
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
	
	public static MArticleJson parseImagePagerList(String href,int pageNo) {
		MArticleJson mMArticleJson = new MArticleJson();
		List<MArticleBean> list = new ArrayList<MArticleBean>();
		int currentposition = 0;
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
//			if(pageNo>1){
//				//http://m.mm131.com/xinggan/2847.html
//				//http://m.mm131.com/xinggan/2847_2.html
//				href = href.replace(".html", "_")+pageNo+".html";
//			}
//			
			Document doc;
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);

//			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 * <article class="post">
         <div class="post-header">
            <h2 class="mm-title">大波女神悦悦艺术写真尺度大惑力强</h2>
            <div class="post-data"><span class="post-meta">2017-03-06 21:48:39</span></div>
         </div>
         <div class="post-content single-post-content"><
         a href="2847_2.html"><img alt="大波女神悦悦艺术写真尺度大惑力强(图1)"
          src="http://img2.mm131.com:55888/pic/2847/1.jpg" />
         </a></div>
         
		 <div class="fenye">
		 <a href="2848.html" class="rw">上一篇</a>
		 <span class="rw">1/58页</span>
		 <a href="2847_2.html">下一张</a>
		 </div>
		 
         <div class="mmss"><script type="text/javascript" src="http://img1.mm798.net/mixiu/js/ms.js"></script></div>
         <div class="post-footer single-post-footer">
            <span class="post-meta">分类：<a href="http://m.mm131.com/xinggan/">性感美女</a></span>
            <span class="post-tag">标签：<a href="http://m.mm131.com/tag/%B4%F3%B2%A8">大波</a><a href="http://m.mm131.com/tag/%D4%C3%D4%C3">悦悦</a><a href="http://m.mm131.com/tag/%D2%D5%CA%F5%D0%B4%D5%E6">艺术写真</a></span>
         </div>
		 <div class="jcdog"><script type="text/javascript">show_down();</script></div>
      </article>

				 */
				/**
				 * 获取分页信息
				 */
				try {
					Element globalnavElement = doc.select("div.fenye").first();
					if(globalnavElement!=null){
						Element pElement = globalnavElement.select("span.rw").first();
						String pager = pElement.text().replace("页", "");
						currentposition = Integer.parseInt(pager.split("/")[0]);
						int tposition = Integer.parseInt(pager.split("/")[1]);
						mMArticleJson.setCurrentPosition(currentposition);
						mMArticleJson.setMaxPage(tposition);
						for(int i=0;i<tposition;i++){
							MArticleBean sbean = new MArticleBean();
							sbean.setHref(href.replace(".html", "_")+(i+1)+".html");
							list.add(sbean);
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// Element globalnavElement =
				// doc.select("div.adFocusHtml").first();
				Elements moduleElements = doc.select("article.post");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						Element pElement = moduleElements.get(i);
						 
							MArticleBean sbean = new MArticleBean();
							try {
								try {
									Element aElement = moduleElements.get(i).select("a").first();
									if (aElement != null) {
										String hrefa = aElement.attr("href");
										if(!hrefa.contains(UrlUtils.MM_M)){
											hrefa = href;
										}
										Log.i(TAG, "i==" + i + ";hrefa==" + hrefa);
										sbean.setHref(hrefa);
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
									Element imgElement = moduleElements.get(i).select("img").first();
									if (imgElement != null) {
										String dataimg = imgElement.attr("src");
										Log.i(TAG, "i==" + i + ";dataimg==" + dataimg);
										sbean.setDataimg(dataimg);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element imgElement = moduleElements.get(i).select("span.post-meta").first();
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

							if(currentposition>0){
								currentposition = currentposition-1;
							}
							list.set(currentposition, sbean);
						}
					 
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		mMArticleJson.setList(list);
		return mMArticleJson;
	}
	
	
	public static MArticleJson parsePCImagePagerList(String href,int pageNo) {
		MArticleJson mMArticleJson = new MArticleJson();
		List<MArticleBean> list = new ArrayList<MArticleBean>();
		int currentposition = 0;
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
//			if(pageNo>1){
//				//http://m.mm131.com/xinggan/2847.html
//				//http://m.mm131.com/xinggan/2847_2.html
//				href = href.replace(".html", "_")+pageNo+".html";
//			}
//			
			Document doc;
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);

//			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				/**
				 * 获取分页信息
				 */
				try {
					Element globalnavElement = doc.select("div.content-page").first();
					if(globalnavElement!=null){
						Element pElement = globalnavElement.select("span.page-ch").first();
						String pager = pElement.text().replace("共", "").replace("页", "");
						Element nElement = globalnavElement.select("span.page_now").first();
						currentposition = Integer.parseInt(nElement.text());
						
						int tposition = Integer.parseInt(pager);
						mMArticleJson.setCurrentPosition(currentposition);
						mMArticleJson.setMaxPage(tposition);
						for(int i=0;i<tposition;i++){
							MArticleBean sbean = new MArticleBean();
							sbean.setHref(href.replace(".html", "_")+(i+1)+".html");
							list.add(sbean);
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// Element globalnavElement =
				// doc.select("div.adFocusHtml").first();
				Elements moduleElements = doc.select("div.content-pic");
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
										sbean.setHref(href);
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
									Element imgElement = moduleElements.get(i).select("img").first();
									if (imgElement != null) {
										String dataimg = imgElement.attr("src");
										Log.i(TAG, "i==" + i + ";dataimg==" + dataimg);
										sbean.setDataimg(dataimg);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								 

							} catch (Exception e) {
								e.printStackTrace();
							}

							if(currentposition>0){
								currentposition = currentposition-1;
							}
							list.set(currentposition, sbean);
						}
					 
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		mMArticleJson.setList(list);
		return mMArticleJson;
	}
	
	
	public static List<MArticleBean> parsePCImageList(String href, int pageNo) {
		List<MArticleBean> list = new ArrayList<MArticleBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			if(!href.contains("_")){
				if(pageNo>1){
					//http://m.mm131.com/xinggan/2847.html
					//http://m.mm131.com/xinggan/2847_2.html
					href = href.replace(".html", "_")+pageNo+".html";
				}
			}
			
			Document doc;
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);

//			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				// Element globalnavElement =
				// doc.select("div.adFocusHtml").first();
				Elements moduleElements = doc.select("div.content-pic");
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
										sbean.setHref(href);
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
									Element imgElement = moduleElements.get(i).select("img").first();
									if (imgElement != null) {
										String dataimg = imgElement.attr("src");
										Log.i(TAG, "i==" + i + ";dataimg==" + dataimg);
										sbean.setDataimg(dataimg);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									Element imgElement = doc.select("div.place").first();
									if (imgElement != null) {
										String postmeta = imgElement.text();
										Log.i(TAG, "i==" + i + ";postmeta==" + postmeta);
										sbean.setMeta(postmeta);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								try {
									Element imgElement = doc.select("div.content-msg").first();
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
}
