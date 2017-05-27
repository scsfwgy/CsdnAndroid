package com.gaoyuan.csdnandroid.net.req;

import com.gaoyuan.csdnandroid.bean.BaseModel;

/**
 * 作者：wgyscsf on 2016/12/6 22:45
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class SearchReqBean extends BaseModel {
    public String keyWords;
    public int searchType;

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
}
