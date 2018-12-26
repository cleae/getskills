package com.qj.fight.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@JsonFilter("course")
public class courses {
    private int id;
    private String briefintro;
    private String courseName;
    private int price;
    private Date createdAt;
    private Date updatedAt;

}
