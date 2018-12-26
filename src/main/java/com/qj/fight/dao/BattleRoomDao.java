package com.qj.fight.dao;

import com.qj.fight.entity.battle_room;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

@SqlResource("fight.battleroom")
public interface BattleRoomDao extends BaseMapper<battle_room> {
    List<battle_room> querybattleHistory(Map<String,Object> param);
    /**function:获取最大的房间号 param: return:*/
    Integer getMaxRoomNumber();
    int update(@Param("number")Integer number,@Param("winner")String winner);
}
