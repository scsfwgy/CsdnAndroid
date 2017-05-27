package com.gaoyuan.csdnandroid.bean;

public class PStar  extends BaseModel{

	private String id;
	private String id_blog;
	private String createTime;
	private String id_csdn;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId_blog() {
		return id_blog;
	}
	public void setId_blog(String id_blog) {
		this.id_blog = id_blog;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "PStar [id=" + id + ", id_blog=" + id_blog + ", createTime="
				+ createTime + "]";
	}
	public String getId_csdn() {
		return id_csdn;
	}
	public void setId_csdn(String id_csdn) {
		this.id_csdn = id_csdn;
	}
	
	
	
}
