package com.gaoyuan.csdnandroid.net.req;


import com.gaoyuan.csdnandroid.bean.BaseModel;

/**
 * @author 高远</n>
 *         编写日期   2016-12-24下午5:43:17</n>
 *         邮箱  wgyscsf@163.com</n>
 *         博客  http://blog.csdn.net/wgyscsf</n>
 */
public class RecommendReqBean extends BaseModel {
    private int orderId=-1;
    //处理喜欢不喜欢的模块
    private String dislikeType;

    public String getDislikeType() {
        return dislikeType;
    }

    public void setDislikeType(String dislikeType) {
        this.dislikeType = dislikeType;
    }
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
