package com.open.pxing.bean.app;

import java.util.List;

import com.open.android.bean.CommonBean;
import com.open.android.bean.db.OpenDBBean;

public class OpenDBListBean extends CommonBean {
	private List<OpenDBBean> list;

	public List<OpenDBBean> getList() {
		return list;
	}

	public void setList(List<OpenDBBean> list) {
		this.list = list;
	}

}
