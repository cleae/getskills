package com.qj.fight.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**

 */
//@Setter
//@Getter
public class member implements Serializable {
    private String nickname;
    private String avatar;
    private String openid;
    private Integer is_experience;

    public Integer getIs_experience() {
        return is_experience;
    }

    public void setIs_experience(Integer is_experience) {
        this.is_experience = is_experience;
    }
//    private int award;
//
//    public int getAward() {
//        return award;
//    }
//
//    public void setAward(int award) {
//        this.award = award;
//    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

//    public Boolean getIs_experience() {
//        return is_experience;
//    }
//
//    public void setIs_experience(Boolean is_experience) {
//        this.is_experience = is_experience;
//    }
}
