/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-21下午1:37:14
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.json.pc;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.pxing.bean.pc.HomeArticleBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-21下午1:37:14
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class HomeArticleJson extends CommonJson {
	private List<HomeArticleBean> list;

	public List<HomeArticleBean> getList() {
		return list;
	}

	public void setList(List<HomeArticleBean> list) {
		this.list = list;
	}
	
	
}
