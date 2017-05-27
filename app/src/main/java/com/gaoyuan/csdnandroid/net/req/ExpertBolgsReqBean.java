package com.gaoyuan.csdnandroid.net.req;


import com.gaoyuan.csdnandroid.bean.BaseModel;

/**
 * @author 高远</n>
 *         编写日期   2016-12-24下午5:43:17</n>
 *         邮箱  wgyscsf@163.com</n>
 *         博客  http://blog.csdn.net/wgyscsf</n>
 */
public class ExpertBolgsReqBean extends BaseModel {
    private int orderId;
    private String id_author;

    public String getId_author() {
        return id_author;
    }

    public void setId_author(String id_author) {
        this.id_author = id_author;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
