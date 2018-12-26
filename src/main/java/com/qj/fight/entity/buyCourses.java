package com.qj.fight.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class buyCourses {
    private int id;
    private  String members_id;//openid
    private  String coursename;

    public buyCourses(String members_id, String coursename) {
        this.members_id = members_id;
        this.coursename = coursename;
    }

    public buyCourses() {
    }
}
