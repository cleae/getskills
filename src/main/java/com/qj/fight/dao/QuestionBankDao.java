package com.qj.fight.dao;

import com.qj.fight.entity.Questionbank;
import com.qj.fight.entity.award;
import com.qj.fight.entity.member;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

@SqlResource("fight.question")
public interface QuestionBankDao extends BaseMapper<Questionbank> {
//    List <Questionbank> selectLimit();
    List<Questionbank> selectLimit10();
    int update_experience(@Param("openId") String openId);
    List<member> getMemberByOpenId(@Param("openId") String openId);
    List<member> select_isperience(@Param("openId") String openId);//查询是否已经体验过

    
    /**function:通过前端来的等级获取随机10道题 param: return:*/
    List<Questionbank> getRandomQuestionByLevel(@Param("level") Integer level);
    /**function:等级是3的时候返回1随机5道题 param: return:*/
    List<Questionbank> getFiftheenQuestionByLevel(@Param("level") Integer level);

    /**function:随机获取随机风格的10个题 param: return:*/
    List<Questionbank> getRandomQuestion();

    List<Questionbank>selectLike(@Param("nickname") String nickname, @Param("style") String style);

    Map<String ,Integer> selectLevel (@Param("openid")String openid);

    int updateBalance(@Param("banance")Integer balance ,@Param("openid")String openid);

    int updatelevel(@Param("level")int level,@Param("openid")String openid);

    Map<String ,Integer>selectBalance (@Param("openid")String openid);

    Map<String ,Integer>selectaward(@Param("type")int type,@Param("openid")String openid);

    int insertaward(award award);

    int updateaward(@Param("award")int award,@Param("openid")String openid);

    Integer selectMaxLevel(@Param("openid")String openid);

    /**查出玩家奖学金的总和**/
//    Map<String,Integer >selectSumAwardByOpenid(@Param("openid")String openid);
    List <award>selectSumAwardByOpenid(@Param("openid")String openid);

//    Map <String ,Integer> selectLevel(@Param ("openid")String openid);

}
