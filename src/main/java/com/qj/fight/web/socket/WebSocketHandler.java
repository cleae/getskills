package com.qj.fight.web.socket;


import com.qj.fight.bean.Message;
import com.qj.fight.bean.PlayerStatus;
import com.qj.fight.bean.SimpleMsg;
import com.qj.fight.bean.Winner;
import com.qj.fight.dao.BattleRoomDao;
import com.qj.fight.dao.QuestionBankDao;
import com.qj.fight.entity.Questionbank;
import com.qj.fight.entity.award;
import com.qj.fight.entity.battle_room;
import com.qj.fight.service.BattleHistoryService;
import com.qj.fight.service.QueryQuestion;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.*;

import com.alibaba.fastjson.JSON;

@Component
public class WebSocketHandler extends AbstractWebSocketHandler {
    @Autowired
    BattleRoomDao battleRoomDao;
    @Autowired
    QuestionBankDao questionBankDao;
    @Autowired
    SQLManager sqlManager;
    @Autowired
    private QueryQuestion queryQuestion;
    @Autowired
    private BattleHistoryService battleHistoryService;

    public static Map<String,PlayerStatus> USERS = new HashMap(1000);
    private static int maxRoomNumber = 0;
    @Override
    public void handleTextMessage(WebSocketSession session,TextMessage message) throws IOException{
        System.out.println("New Text Message Received");
        SimpleMsg m =JSON.parseObject(message.getPayload(),SimpleMsg.class);
        String from = m.getFrom();                //消息来源者

        if (m.getStatus() == 0){  //说明是玩家离线,插入对战记录
//            System.out.println(scoreNumber);
            //机器人返回答案给后端
            Map<String,Integer>macmap=questionBankDao.selectaward(m.getType(),from);
            if(USERS.get(from).getRivalSocketSession()==null){
                int machineAward;
                Winner winner=new Winner();

                if(m.getIs_winner()==0){
                    machineAward=m.getScoreNumber()*10;
                    winner.setWinner("machine");
                    winner.setAward(machineAward);
                }else{

                    machineAward=m.getScoreNumber()*20;
                    winner.setWinner(from);
                    winner.setAward(machineAward);
                }
                try{
                    System.out.println("未捕获异常");
                    if(macmap.size()==1){
                        if(machineAward<macmap.get("award")){
//                            machineAward=m.getScoreNumber()*20;
                            System.out.println(" machine no operation");
                        }else{
                            questionBankDao.updateaward(machineAward,from);
                        }
                    }
                }catch(NullPointerException e){
//                    machineAward=m.getScoreNumber()*20;
                    questionBankDao.insertaward(new award(from,m.getType(),machineAward));
                }
                session.sendMessage(new TextMessage(JSON.toJSONString(winner)));
            }
            System.out.println("离线时发送过来的from"+from);
            PlayerStatus test=USERS.get(from);
            System.out.println("是否为空");
            System.out.println(test.getRoomNumber()==null);
            System.out.println("是否包含"+USERS.containsKey(from));
            int roomNumber=USERS.get(from).getRoomNumber();
//        return createHistory(simpleMsg,WebSocketHandler.USERS.get(simpleMsg.getFrom()).getRoomNumber()).toString();
            battle_room battleRoom = new battle_room();
            battleRoom.setCreatedAt(new Date());
            battleRoom.setNumber(roomNumber);
            battleRoom.setIs_del((byte)0);
            if (battleRoom.getIs_winner() != null){  //说明是和机器人进行对战
                battleRoom.setIs_machine((byte)1);
            }else {
                battleRoom.setIs_machine((byte)0);
            }
            battleRoom.setMember_id(from);
            battleRoom.setScoreNumber(m.getScoreNumber());
            System.out.println("dsd"+m.getScoreNumber());
//        battleRoom.setScoreNumber((simpleMsg.getScoreNumber()));  //玩家所得分数
            battleHistoryService.createBattleHistory(battleRoom);
            Query<battle_room> query = sqlManager.query(battle_room.class);
            List<battle_room> list=query.andEq("number",roomNumber).select();
            if(USERS.get(from).getRivalSocketSession().isOpen()){
                if(list.size()==2){
                    WebSocketSession ownSession=USERS.get(from).getSocketSession();
                    WebSocketSession rivalSession=USERS.get(from).getRivalSocketSession();
//                StringBuilder stringBuilder=new StringBuilder("winner:");
                    Winner winner=new Winner();
                    Winner fail=new Winner();
                    for(battle_room battleRoom1:list){
                        int rightNumber=battleRoom1.getScoreNumber();
                        String battleRoomfrom=battleRoom1.getMember_id();
                        //添加award属性
                        int winnerAward=m.getScoreNumber()*20;
                        System.out.println("winner"+winnerAward);

                        int failAward=battleRoom1.getScoreNumber()*20;
                        System.out.println("fail"+failAward);
                        Map<String,Integer>map=questionBankDao.selectaward(m.getType(),from);
                        Map<String ,Integer>fmap=questionBankDao.selectaward(m.getType(),battleRoom1.getMember_id());
                        System.out.println(JSON.toJSONString(fmap));
                        System.out.println(JSON.toJSONString(map));
                        System.out.println(battleRoom1.getScoreNumber()*20);
                        try{
                            if(map.size()==1){
                                if(m.getScoreNumber()*20<map.get("award")){
//                                winnerAward=map.get("award");
                                    System.out.println("winner no operation");

                                }else{
                                    questionBankDao.updateaward(m.getScoreNumber()*20,from);
                                }
                            }
                        }catch(NullPointerException e){  //说明数据库没有记录 触发空指针异常  插入记录
//                        winnerAward=m.getScoreNumber()*20;
                            questionBankDao.insertaward(new award(from,m.getType(),m.getScoreNumber()*20));

                        }
                        System.out.println("fmap.size()==1"+fmap.size());
                        try{
                            if(fmap.size()==1){
                                if(battleRoom1.getScoreNumber()*20<fmap.get("award")){
//                                failAward=map.get("award");
                                    System.out.println(" fail  no operation");
                                }else{
                                    System.out.println("需要更新"+battleRoom1.getScoreNumber()*20);
                                    questionBankDao.updateaward(battleRoom1.getScoreNumber()*20,battleRoom1.getMember_id());
                                }
                            }
                        }catch(NullPointerException e){
//                        failAward=m.getScoreNumber()*20;
//                            System.out.println("ffffffffffffff"+battleRoom1.getScoreNumber()*20);
                            questionBankDao.insertaward(new award(battleRoom1.getMember_id(),m.getType(),battleRoom1.getScoreNumber()*20));
                        }
                        if(rightNumber <= m.getScoreNumber()){
                            battleRoom1.setWinner(from);
                            System.out.println(battleRoom1.getCreatedAt());
                            battleRoomDao.update(roomNumber,from);

                            winner.setWinner(from);
                            winner.setAward(winnerAward);
                            fail.setAward(failAward);
                            fail.setWinner(from);
                            String text=JSON.toJSONString(winner);
//                        stringBuilder.append(from);
                            String failText=JSON.toJSONString(fail);

                            TextMessage textMessage=new TextMessage(text);
                            TextMessage failMessage=new TextMessage(failText);
                            ownSession.sendMessage(textMessage);
                            rivalSession.sendMessage(failMessage);
//                    query.andEq("number",roomNumber).updateSelective(battleRoom1);
//                        return "获胜者是"+from;
                            USERS.remove(from);
                            USERS.remove(battleRoom1.getMember_id());
                            return;
                        }else{
//                        stringBuilder.append(battleRoom1.getMember_id());
                            winner.setWinner(battleRoom1.getMember_id());
                            winner.setAward(failAward);
                            fail.setAward(winnerAward);
                            fail.setWinner(battleRoom1.getMember_id());
                            String text=JSON.toJSONString(winner);
                            String failText=JSON.toJSONString(fail);
                            TextMessage failMessage=new TextMessage(failText);
                            TextMessage textMessage=new TextMessage(text);
                            ownSession.sendMessage(failMessage);
                            rivalSession.sendMessage(textMessage);
                            battleRoom1.setWinner(battleRoom1.getMember_id());
//                    query.andEq("number",roomNumber).updateSelective(battleRoom1);
                            battleRoomDao.update(roomNumber,from);
//                        return "获胜者是"+battleRoom1.getMember_id();
                            USERS.remove(from);
                            USERS.remove(battleRoom1.getMember_id());
                            return;
                        }
                    }
                }
            }else{
                session.sendMessage(new TextMessage("对手已离线"));
            }

//            System.out.println("离线人是:\t"+m.getFrom());
            //插入对战记录
//            createHistory(m,USERS.get(from).getRoomNumber());


        }else if(m.getHumanAnswer() != null){    //说明此条消息 是用户发送答案给对方的
            if(USERS.get(from).getRivalSocketSession() != null){
                WebSocketSession rivalSession=USERS.get(from).getRivalSocketSession();//得到对手的session
                rivalSession.sendMessage(message);
            }
        }else if (m.getStatus()== 3){  //表示此时玩家想要匹配机器人,直接发送给前端题目
            TextMessage marchineMessage = queryQuestion.getQuestionMessages(m.getLevel());
            session.sendMessage(marchineMessage);
            PlayerStatus playerStatus = new PlayerStatus(false,session); //匹配了机器人之后将玩家也置于忙碌状态
            if (maxRoomNumber == 0){             //当系统重新部署的时候重新获取最大的房间号给静态
                maxRoomNumber += battleHistoryService.getMaxRoomNumber();
            }else {
                maxRoomNumber ++;
            }
            playerStatus.setRoomNumber(maxRoomNumber);    //房间号
            System.out.println("当前对战分配到的房间号是:\t"+maxRoomNumber);
            USERS.put(from,playerStatus);
        } else {                       //玩家上线与给查询真人给玩家匹配
            System.out.println("上线者是:\t"+from);
            PlayerStatus playerStatus = new PlayerStatus();
            playerStatus.setIsAvailable(true);    //每次上线将玩家置于空闲状态
            playerStatus.setSocketSession(session);   //存储玩家的连接
            USERS.put(from,playerStatus);         //将上线玩家状态存储起来
            /**function:开始题目查询*/
            String queryResult = finalQueryMap(from);    //查询是否有空闲玩家

            if (queryResult == null){     //没有查询到玩家
                TextMessage textMessage=new TextMessage("noPlayer");
                session.sendMessage(textMessage);            //告诉前端没有玩家
            }else{                                           //查询到玩家就将对战双方信息题目进行发送
                if (maxRoomNumber == 0){
//                    maxRoomNumber += battleHistoryService.getMaxRoomNumber();
                    maxRoomNumber += battleHistoryService.getMaxRoomNumber()+1;
                }else {
                    maxRoomNumber ++;
                }
                System.out.println("当前对战分配到的房间号是:\t"+maxRoomNumber);
                List<Questionbank> randomQuesions = queryQuestion.getRandomQuestionByLevel(m.getLevel());  //获取随机10个题目

//                String remembers = queryQuestion.queryRemebers(m.getLevel());          //获取知识点

                TextMessage textMessage = getMemberMessage(queryResult,randomQuesions);
                session.sendMessage(textMessage);
                USERS.get(from).setIsAvailable(false);
                USERS.get(from).setRoomNumber(maxRoomNumber);
                USERS.get(queryResult).setIsAvailable(false);
                USERS.get(queryResult).setRoomNumber(maxRoomNumber);
                System.out.println("人人匹配到答案匹配者房间"+from+":"+USERS.get(from).getRoomNumber()+"被匹配到的人房间"+queryResult+":"+USERS.get(queryResult).getRoomNumber());
                /**function:再发送给接受挑战的人的信息return:void*/
                TextMessage challenger2 = getMemberMessage(from,randomQuesions);
                WebSocketSession socketSession2 = USERS.get(queryResult).getSocketSession();
                socketSession2.sendMessage(challenger2);
            }
        }
    }
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        System.out.println("New Binary Message Received");
        session.sendMessage(message);
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("当前连接的sessionId是\t"+session.getId());
        System.out.println("connectd!");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        Iterator<?> iterator=USERS.keySet().iterator();
        while(iterator.hasNext()){
            String palyer=(String)iterator.next();
            WebSocketSession needRemove=USERS.get(palyer).getSocketSession();
            WebSocketSession rivalSession=USERS.get(palyer).getRivalSocketSession();
            if(needRemove==session)
//                USERS.remove(palyer);//报java.util.ConcurrentModificationException: null异常
//                TextMessage run=new TextMessage("玩家中途退出");
//                rivalSession.sendMessage("玩家中途退出");
                //玩家中途退出  发送消息  后台在进行操作
                iterator.remove();
            //玩家中途退出  未退出的玩家判胜

        }
        System.out.println("关闭之后map的大小是:\t"+USERS.size());
        System.out.println("connection closed");

    }
//    /**function:插入对战记录 param: return:*/
//    private boolean createHistory(SimpleMsg simpleMsg,int roomNumber){
//        battle_room battleRoom = new battle_room();
//        battleRoom.setCreatedAt(new Date());
//        battleRoom.setNumber(roomNumber);
//        battleRoom.setIs_del((byte)0);
//        if (battleRoom.getIs_winner() != null){  //说明是和机器人进行对战
//            battleRoom.setIs_machine((byte)1);
//        }else {
//            battleRoom.setIs_machine((byte)0);
//        }
//        battleRoom.setMember_id(simpleMsg.getFrom());
//        battleRoom.setScoreNumber(simpleMsg.getScoreNumber());  //玩家所得分数
//        return battleHistoryService.createBattleHistory(battleRoom);
//    }

   /**function:遍历map，找出符合条件的对战者的key param: return:*/
    private String finalQueryMap(String currentPlayer){

        for (Map.Entry<String,PlayerStatus> map:USERS.entrySet()
             ){
            PlayerStatus playerStatus = map.getValue();
            if (playerStatus.getIsAvailable() && !map.getKey().equals(currentPlayer)){
                playerStatus.setRivalSocketSession(USERS.get(currentPlayer).getSocketSession());
                USERS.get(currentPlayer).setRivalSocketSession(playerStatus.getSocketSession());  //对手的session连接
                return map.getKey();                    //返回匹配到的玩家的信息
            }
        }
        return null;       //没有查询到
    }

    /**function:通过openId获取玩家基本信息,以及题目信息与速记知识点 param: return:*/
    private TextMessage getMemberMessage(String openId,List<Questionbank> randomQuesions){
        Optional<Message> result = queryQuestion.getMemberByOpenId(openId);  //根据对手的openId获取对手的基本信息
        TextMessage textMessage;
        if (result.isPresent()){
            textMessage = queryQuestion.getQuestionAndChallenger(randomQuesions,result.get());
        }
        else {
            System.out.println("未查询到对手信息");
            textMessage =   queryQuestion.getQuestionAndChallenger(randomQuesions,null);  //发送给发起挑战的玩家的信息
        }
        return textMessage;
    }

}
