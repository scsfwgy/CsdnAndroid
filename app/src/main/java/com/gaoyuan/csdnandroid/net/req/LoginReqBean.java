package com.gaoyuan.csdnandroid.net.req;

import com.gaoyuan.csdnandroid.bean.BaseModel;

/**
 * 作者：wgyscsf on 2016/12/6 22:45
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class LoginReqBean extends BaseModel {
    public String account;
    public String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
