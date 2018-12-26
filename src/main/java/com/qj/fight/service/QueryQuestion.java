package com.qj.fight.service;

import com.qj.fight.bean.Message;
import com.qj.fight.entity.Questionbank;
import org.springframework.web.socket.TextMessage;

import java.util.List;
import java.util.Optional;

/**

 */
public interface QueryQuestion {
    /**function:发送对战者与题目信息 param: return:*/
    TextMessage getQuestionAndChallenger(List<Questionbank> Questionbanks, Message challenger);
    /**function:没有匹配到玩家直接发送题目给前端，开始机器人对战 param: return:*/
    TextMessage getQuestionMessages(Integer level);

    /**function:通过对手的openID获取对手的信息param: return:*/
    Optional<Message> getMemberByOpenId(String openId);

    String getQuestionLimit10();
/**function:通过前端来的等级获取随机的题目 param: return:*/
    List<Questionbank> getRandomQuestionByLevel(Integer level);
    /**function:通过玩家的 openId与风格获取速记知识点 param: return:*/
    String queryRemebers(Integer level);

    /**  通过style 或者关键字查询 **/
    String  selectQuestionByKeyword(String keyword,String style);
}
