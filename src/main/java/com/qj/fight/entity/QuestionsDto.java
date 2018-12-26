package com.qj.fight.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 */
//@Getter
//@Setter
public class QuestionsDto implements Serializable {
    private String question;
    private  List<Map<String,String>> options;  //选项数组
    private Byte answer;         //答案;
    private String analysis;//题目解析
    private String rembers;//速记知识点

    public void setRembers(String rembers) {
        this.rembers = rembers;
    }

    public String getRembers() {
        return rembers;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getQuestion() {
        return question;
    }

    public List<Map<String, String>> getOptions() {
        return options;
    }

    public Byte getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOptions(List<Map<String, String>> options) {
        this.options = options;
    }

    public void setAnswer(Byte answer) {
        this.answer = answer;
    }
}
