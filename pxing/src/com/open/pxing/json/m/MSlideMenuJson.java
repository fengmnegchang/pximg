/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-8上午10:12:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.pxing.json.m;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.pxing.bean.m.MSlideMenuBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-6-8上午10:12:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MSlideMenuJson extends CommonJson {
	private List<MSlideMenuBean> list;

	public List<MSlideMenuBean> getList() {
		return list;
	}

	public void setList(List<MSlideMenuBean> list) {
		this.list = list;
	}
	

}
