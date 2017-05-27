package com.gaoyuan.csdnandroid.bean;


/**
 * @author wgyscsf
 * @email wgyscsf@163.com
 * @dateTime 2017 2017-5-6 上午10:14:00
 * @details 
 */
public class Preference extends BaseModel{
	private String id;
	private String id_blog;
	private String id_csdn;
	private int type;//是否喜欢该文章，1：喜欢；2：不喜欢
	private String createTime;
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
	public String getId_csdn() {
		return id_csdn;
	}
	public void setId_csdn(String id_csdn) {
		this.id_csdn = id_csdn;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "Preference [id=" + id + ", id_blog=" + id_blog + ", id_csdn="
				+ id_csdn + ", type=" + type + ", createTime=" + createTime
				+ "]";
	}
	private int typeId;

	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
}
