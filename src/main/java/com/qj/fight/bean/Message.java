package com.qj.fight.bean;

public class Message {
    private String name;
    private String touxiang;

    public Message() {
    }

    public Message(String name, String touxiang) {
        this.name = name;
        this.touxiang = touxiang;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public String getName() {
        return name;
    }

    public String getTouxiang() {
        return touxiang;
    }
}
