package com.qj.fight.bean;

public class SimpleMsg{

//    private String type;
    private String from;
    private String to;

    private String style;  //知识点风格

    private Byte status;//0代表离线，1代表上线，3代表需求匹配机器人,4表示获取知识点

    private String body;

    private String humanAnswer=null;

    private Integer scoreNumber;

    private Integer level;//来自前端的等级

    private int type;//结算胜负时前端发过来的题目类型

    private int is_winner;

    public void setIs_winner(int is_winner) {
        this.is_winner = is_winner;
    }

    public int getIs_winner() {
        return is_winner;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getScoreNumber() {
        return scoreNumber;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setScoreNumber(Integer scoreNumber) {
        this.scoreNumber = scoreNumber;
    }

    public void setScoreNumber(Short scoreNumber) {
        scoreNumber = scoreNumber;
    }

    public void setHumanAnswer(String humanAnswer) {
        this.humanAnswer = humanAnswer;
    }

    public String getHumanAnswer() {
        return humanAnswer;
    }

//    public void setType(String type) {
//        this.type = type;
//    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setBody(String body) {
        this.body = body;
    }

//    public String getType() {
//        return type;
//    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getBody() {
        return body;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

}
