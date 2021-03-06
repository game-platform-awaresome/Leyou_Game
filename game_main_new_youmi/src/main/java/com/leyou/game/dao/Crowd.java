package com.leyou.game.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Entity mapped to table "CROWD".
 */
public class Crowd implements Parcelable {

    public Long id;//主键Id
    public String groupId;//群id
    public String groupNo;//群号id
    public String name;//群名称
    public String headImgUrl;//群头像
    public Integer memberNum;//群成员数量
    public Integer isTop;//是否置顶 1是 0否
    public String introduction;//群简介
    public Integer isMaster;//是否是群主，1是 0否
    public Integer isShielding;//是否消息免打扰，1是 0否
    public String myName;//我的群昵称
    public String userId;//用户的UserId
    public String applyId;//申请ID
    public Integer status;//状态1待处理2已同意3已忽略
    public Integer isJoin;//是否已加入群1是0否

    public Crowd() {
    }

    public Crowd(Long id) {
        this.id = id;
    }

    public Crowd(Long id, String groupId, String groupNo, String introduction, String name, String headImgUrl, Integer memberNum, Integer isTop, Integer isMaster, Integer isShielding, String myName) {
        this.id = id;
        this.groupId = groupId;
        this.groupNo = groupNo;
        this.introduction = introduction;
        this.name = name;
        this.headImgUrl = headImgUrl;
        this.memberNum = memberNum;
        this.isTop = isTop;
        this.isMaster = isMaster;
        this.isShielding = isShielding;
        this.myName = myName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Integer getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Integer getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(Integer isMaster) {
        this.isMaster = isMaster;
    }

    public Integer getIsShielding() {
        return isShielding;
    }

    public void setIsShielding(Integer isShielding) {
        this.isShielding = isShielding;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.groupId);
        dest.writeString(this.groupNo);
        dest.writeString(this.name);
        dest.writeString(this.headImgUrl);
        dest.writeValue(this.memberNum);
        dest.writeValue(this.isTop);
        dest.writeString(this.introduction);
        dest.writeValue(this.isMaster);
        dest.writeValue(this.isShielding);
        dest.writeString(this.myName);
        dest.writeString(this.applyId);
        dest.writeString(this.userId);
        dest.writeValue(this.status);
        dest.writeValue(this.isJoin);
    }

    protected Crowd(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.groupId = in.readString();
        this.groupNo = in.readString();
        this.name = in.readString();
        this.headImgUrl = in.readString();
        this.memberNum = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isTop = (Integer) in.readValue(Integer.class.getClassLoader());
        this.introduction = in.readString();
        this.isMaster = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isShielding = (Integer) in.readValue(Integer.class.getClassLoader());
        this.myName = in.readString();
        this.applyId = in.readString();
        this.userId = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isJoin = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Crowd> CREATOR = new Parcelable.Creator<Crowd>() {
        @Override
        public Crowd createFromParcel(Parcel source) {
            return new Crowd(source);
        }

        @Override
        public Crowd[] newArray(int size) {
            return new Crowd[size];
        }
    };
}
