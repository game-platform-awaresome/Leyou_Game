package com.leyou.game.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Entity mapped to table "FRIEND".
 */
public class Friend implements Parcelable {

    public Long id;
    public static final int NO_SYSTEM = 1;//未注册用户 显示邀请好友
    public static final int SYSTEM_NO_FRIEND = 2;//已注册 平台内非好友
    public static final int FRIEND = 3;//wo d好友
    public static final int ADDING_WAITING_CONFIRM = 4;//已发送添加好友请求 显示 等待验证
    public static final int ADDING_FRIEND_REFUSED = 5;//是否通过验证 显示 已拒绝
    public static final int ADDING_FRIEND_PASS_VERIFY = 6;//是否通过验证 显示 通过验证
    public Integer status;
    public String idNo;
    public String userId;
    public String name;
    public String phoneNameLetter;
    public String nickname;
    public String pictureUrl;
    public Integer sex;
    public Long birthday;
    /** Not-null value. */
    public String phone;
    public String phoneAddress;
    public String remarkName;
    public Integer source;
    public String comment;
    public String headImgUrl;
    public String region;

    public Friend() {
    }

    public Friend(Long id) {
        this.id = id;
    }

    public Friend(Long id, Integer status, String idNo, String userId, String name, String phoneNameLetter, String nickname, String pictureUrl, Integer sex, Long birthday, String phone, String phoneAddress, String remarkName, Integer source, String comment, String headImgUrl, String region) {
        this.id = id;
        this.status = status;
        this.idNo = idNo;
        this.userId = userId;
        this.name = name;
        this.phoneNameLetter = phoneNameLetter;
        this.nickname = nickname;
        this.pictureUrl = pictureUrl;
        this.sex = sex;
        this.birthday = birthday;
        this.phone = phone;
        this.phoneAddress = phoneAddress;
        this.remarkName = remarkName;
        this.source = source;
        this.comment = comment;
        this.headImgUrl = headImgUrl;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNameLetter() {
        return phoneNameLetter;
    }

    public void setPhoneNameLetter(String phoneNameLetter) {
        this.phoneNameLetter = phoneNameLetter;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    /** Not-null value. */
    public String getPhone() {
        return phone;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneAddress() {
        return phoneAddress;
    }

    public void setPhoneAddress(String phoneAddress) {
        this.phoneAddress = phoneAddress;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.status);
        dest.writeString(this.idNo);
        dest.writeString(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.phoneNameLetter);
        dest.writeString(this.nickname);
        dest.writeString(this.pictureUrl);
        dest.writeValue(this.sex);
        dest.writeValue(this.birthday);
        dest.writeString(this.phone);
        dest.writeString(this.phoneAddress);
        dest.writeString(this.remarkName);
        dest.writeValue(this.source);
        dest.writeString(this.comment);
        dest.writeString(this.headImgUrl);
        dest.writeString(this.region);
    }

    protected Friend(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.idNo = in.readString();
        this.userId = in.readString();
        this.name = in.readString();
        this.phoneNameLetter = in.readString();
        this.nickname = in.readString();
        this.pictureUrl = in.readString();
        this.sex = (Integer) in.readValue(Integer.class.getClassLoader());
        this.birthday = (Long) in.readValue(Long.class.getClassLoader());
        this.phone = in.readString();
        this.phoneAddress = in.readString();
        this.remarkName = in.readString();
        this.source = (Integer) in.readValue(Integer.class.getClassLoader());
        this.comment = in.readString();
        this.headImgUrl = in.readString();
        this.region = in.readString();
    }

    public static final Parcelable.Creator<Friend> CREATOR = new Parcelable.Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel source) {
            return new Friend(source);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", status=" + status +
                ", idNo='" + idNo + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", phoneNameLetter='" + phoneNameLetter + '\'' +
                ", nickname='" + nickname + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", phoneAddress='" + phoneAddress + '\'' +
                ", remarkName='" + remarkName + '\'' +
                ", source=" + source +
                ", comment='" + comment + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
