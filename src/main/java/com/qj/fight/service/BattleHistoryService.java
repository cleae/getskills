package com.qj.fight.service;


import com.qj.fight.entity.battle_room;

import java.util.List;
import java.util.Map;

/**

 */
public interface BattleHistoryService {
    List<battle_room> queryBattle(Map<String,Object> param);
    Boolean createBattleHistory(battle_room battleRoom);
    Integer getMaxRoomNumber();//获取最大的房间号，第一条信息插
}
