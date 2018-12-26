package com.qj.fight.service.impl;

import com.qj.fight.dao.BattleRoomDao;
import com.qj.fight.entity.battle_room;
import com.qj.fight.service.BattleHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**

 */
@Service
public class BattleHistoryServiceImpl implements BattleHistoryService {
    @Autowired
    private BattleRoomDao battleRoomDao;

    @Override
    public List<battle_room> queryBattle(Map<String, Object> param) {
        battleRoomDao.querybattleHistory(param);
        return null;
    }

    @Transactional
    @Override
    public Boolean createBattleHistory(battle_room battleRoom){
        battleRoomDao.insert(battleRoom,false);
        return true;
    }

    @Override
    public Integer getMaxRoomNumber() {
        return battleRoomDao.getMaxRoomNumber();
    }
}
