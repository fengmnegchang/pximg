/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-9下午2:23:36
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.json.m;

import java.util.List;

import com.open.android.bean.db.OpenDBBean;
import com.open.android.json.CommonJson;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-9下午2:23:36
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class OpenDBJson extends CommonJson {
	private List<OpenDBBean> list;

	public List<OpenDBBean> getList() {
		return list;
	}

	public void setList(List<OpenDBBean> list) {
		this.list = list;
	}
	
	

}
