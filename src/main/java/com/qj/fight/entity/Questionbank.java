package com.qj.fight.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;

import java.sql.Timestamp;

/**
 *  题目entity
 */
//@Data
@JsonFilter("questionbank")
public class Questionbank {
    private Integer id;
    private Short type;
    private String info;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private Byte answer;
    private String style;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String image;
    private String analysis;//题目的解析

    private String option1Url;
    private String option2Url;
    private String option3Url;
    private String option4Url;

    private String rembers;//速记知识点

    public String getRembers() {
        return rembers;
    }

    public void setRembers(String rembers) {
        this.rembers = rembers;
    }

    public void setOption1Url(String option1Url) {
        this.option1Url = option1Url;
    }

    public void setOption2Url(String option2Url) {
        this.option2Url = option2Url;
    }

    public void setOption3Url(String option3Url) {
        this.option3Url = option3Url;
    }

    public void setOption4Url(String option4Url) {
        this.option4Url = option4Url;
    }

    public String getOption1Url() {
        return option1Url;
    }

    public String getOption2Url() {
        return option2Url;
    }

    public String getOption3Url() {
        return option3Url;
    }

    public String getOption4Url() {
        return option4Url;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setAnswer(Byte answer) {
        this.answer = answer;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public Short getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public Byte getAnswer() {
        return answer;
    }

    public String getStyle() {
        return style;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public String getImage() {
        return image;
    }
}
