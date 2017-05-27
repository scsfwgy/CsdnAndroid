package com.gaoyuan.csdnandroid.bean;

import java.io.Serializable;

/**
 * @author 高远</n>
 * 编写日期   2016-12-24下午5:56:43</n>
 * 邮箱  wgyscsf@163.com</n>
 * 博客  http://blog.csdn.net/wgyscsf</n>
 * TODO</n>
 */
public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public  int nowPager;
	public int pagerSize;

	public int getNowPager() {
		return nowPager;
	}

	public void setNowPager(int nowPager) {
		this.nowPager = nowPager;
	}

	public int getPagerSize() {
		return pagerSize;
	}

	public void setPagerSize(int pagerSize) {
		this.pagerSize = pagerSize;
	}

	@Override
	public String toString() {
		return "BaseModel [nowPager=" + nowPager + ", pagerSize=" + pagerSize
				+ "]";
	}

}
