package com.qj.fight.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Author: tan
 * @Date: 2018-11-21 10:21
 * @function  存储玩家状态等信息
 */
//@Setter
//@Getter
public class PlayerStatus {
    private Boolean isAvailable;    //玩家是否空闲
    private WebSocketSession socketSession;//玩家自己的session
    private WebSocketSession rivalSocketSession = null;//匹配到的对手的session
    private String question=null;   //是否对战双方已经已经先得到了答案
    private Integer roomNumber;   //对战的房间号

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setRivalSocketSession(WebSocketSession rivalSocketSession) {
        this.rivalSocketSession = rivalSocketSession;
    }

    public WebSocketSession getRivalSocketSession() {
        return rivalSocketSession;
    }

    public PlayerStatus(Boolean isAvailable, WebSocketSession socketSession) {
        this.isAvailable = isAvailable;
        this.socketSession = socketSession;
    }

    public PlayerStatus() {
    }

    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }

    public void setSocketSession(WebSocketSession socketSession) {
        this.socketSession = socketSession;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public WebSocketSession getSocketSession() {
        return socketSession;
    }
}
