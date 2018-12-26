package com.qj.fight.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**

 */
//@Getter
//@Setter
public class battle_room {
    private Integer id;
    private String member_id;
    private int scoreNumber;
    private Date createdAt;
    private Date updatedAt;
    private Integer number;
    private Byte is_del;
    private Byte is_winner;
    private Byte is_machine;
    private String winner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public int getScoreNumber() {
        return scoreNumber;
    }

    public void setScoreNumber(int scoreNumber) {
        this.scoreNumber = scoreNumber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Byte getIs_del() {
        return is_del;
    }

    public void setIs_del(Byte is_del) {
        this.is_del = is_del;
    }

    public Byte getIs_winner() {
        return is_winner;
    }

    public void setIs_winner(Byte is_winner) {
        this.is_winner = is_winner;
    }

    public Byte getIs_machine() {
        return is_machine;
    }

    public void setIs_machine(Byte is_machine) {
        this.is_machine = is_machine;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
