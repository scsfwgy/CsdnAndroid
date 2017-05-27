package com.gaoyuan.csdnandroid.net.req;

import com.gaoyuan.csdnandroid.bean.BaseModel;

/**
 * 作者：wgyscsf on 2017/5/2 12:41
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class PStarReqBean extends BaseModel {
    private String id_blog;
    private String id_csdn;

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
}
