package com.qj.fight.entity;

import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
public class award  {
    private int id;
    private String member_openid;
    private int award;
    private int type;

    public void setId(int id) {
        this.id = id;
    }

    public void setMember_openid(String member_openid) {
        this.member_openid = member_openid;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getMember_openid() {
        return member_openid;
    }

    public int getAward() {
        return award;
    }

    public int getType() {
        return type;
    }

    public award() {
    }

    public award(String openid, int type, int award) {
        this.member_openid = openid;
        this.award = award;
        this.type = type;
    }

}
