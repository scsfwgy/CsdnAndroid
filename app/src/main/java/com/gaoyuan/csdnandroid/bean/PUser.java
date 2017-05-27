package com.gaoyuan.csdnandroid.bean;

public class PUser extends BaseModel{

    private String id;
    private String id_csdn;
    private String nickName;
    private String name;
    private String email;
    private String avatarAddr;// 积分
    private String createDate;
    private String stamp;
    private String spare1;
    private String spare2;
    private String phone;
    private int praise = 1;//0:未赞赏，1:赞赏
    //处理喜欢不喜欢的模块
    private String dislikeType;

    public String getDislikeType() {
        return dislikeType;
    }

    public void setDislikeType(String dislikeType) {
        this.dislikeType = dislikeType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_csdn() {
        return id_csdn;
    }

    public void setId_csdn(String id_csdn) {
        this.id_csdn = id_csdn;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarAddr() {
        return avatarAddr;
    }

    public void setAvatarAddr(String avatarAddr) {
        this.avatarAddr = avatarAddr;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getSpare1() {
        return spare1;
    }

    public void setSpare1(String spare1) {
        this.spare1 = spare1;
    }

    public String getSpare2() {
        return spare2;
    }

    public void setSpare2(String spare2) {
        this.spare2 = spare2;
    }

    @Override
    public String toString() {
        return "PUser [id=" + id + ", id_csdn=" + id_csdn + ", nickName="
                + nickName + ", name=" + name + ", email=" + email
                + ", avatarAddr=" + avatarAddr + ", createDate=" + createDate
                + ", stamp=" + stamp + ", spare1=" + spare1 + ", spare2="
                + spare2 + "]";
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }
}
