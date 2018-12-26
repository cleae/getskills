package com.qj.fight.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @function  速记知识点实体
 */
//@Setter
//@Getter
    @JsonFilter("remebers")
public class remebers implements Serializable {
    private Integer id;
    private String style;   //知识点风格
    private String content;   //知识点内容
    private Byte is_del;
    private String createdAt;
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getIs_del() {
        return is_del;
    }

    public void setIs_del(Byte is_del) {
        this.is_del = is_del;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
