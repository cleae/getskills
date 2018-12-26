package com.qj.fight.dao;

import com.qj.fight.entity.remebers;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

/**
 * @function  速记知识点
 */
@SqlResource("fight.remebers")
public interface RembersDao extends BaseMapper<remebers> {
    /**function:通过玩家的openId与题目的风格获取与玩家对应的速记知识点 param: return:*/
    List<remebers> queryRembers(@Param("level") Integer level);
}
