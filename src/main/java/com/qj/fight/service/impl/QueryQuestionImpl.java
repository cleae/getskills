package com.qj.fight.service.impl;

import com.alibaba.fastjson.JSON;

import com.qj.fight.bean.Message;
import com.qj.fight.dao.QuestionBankDao;
import com.qj.fight.dao.RembersDao;
import com.qj.fight.entity.QuestionsDto;
import com.qj.fight.entity.Questionbank;
import com.qj.fight.entity.member;
import com.qj.fight.entity.remebers;
import com.qj.fight.service.QueryQuestion;
import com.qj.fight.utiles.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.*;

/**

 */
@Service
public class QueryQuestionImpl implements QueryQuestion {
    private static  String []rembers=new String [15];//除去重复速记知识点
    private  static boolean flag=false;
    @Autowired
    QuestionBankDao questionBankDao;
    @Autowired
    private RembersDao rembersDao;
    @Autowired
    private DataFactory dataFactory;

    @Override
    public TextMessage getQuestionAndChallenger(List<Questionbank> Questionbanks, Message challenger) {
        List<QuestionsDto> questionsDtoList = formatTransfer(Questionbanks);
        String challengerMessage = JSON.toJSONString(challenger);
        Map<String,String> map = new HashMap<>(4);
        map.put("msg",challengerMessage);   //对战者信息
        map.put("questionsList",JSON.toJSONString(questionsDtoList));   //随机的题目信息
//        map.put("remembers",remebers);          //速记知识点
        return new TextMessage(JSON.toJSONString(map));
    }

    @Override
    public String  selectQuestionByKeyword(String keyword,String style){
        List<Questionbank>list=questionBankDao.selectLike(keyword,style);
        List<QuestionsDto> finallist=formatTransfer(list);
        return JSON.toJSONString(finallist);

    }

    /**function:机器人匹配对战 param: return:*/
    @Override
    public TextMessage getQuestionMessages(Integer level) {
        List<QuestionsDto> questionsDtos = formatTransfer(getRandomQuestionByLevel(level));
        Map<String,String> map = new HashMap<>();
        map.put("questionsList",JSON.toJSONString(questionsDtos));
        map.put("remembers",queryRemebers(level));
        return new TextMessage(JSON.toJSONString(map));
    }

    @Override
    public Optional<Message> getMemberByOpenId(String openId){
        List<member> queryResult = questionBankDao.getMemberByOpenId(openId);
        if (queryResult.size() > 0){
            member getResult = queryResult.get(0);
            Message message = new Message(getResult.getNickname(),getResult.getAvatar());
            return Optional.ofNullable(message);
        }
        return Optional.ofNullable(null);
    }

    /**
     * 随机获取10个随机风格的题目，形象小白
     * @return
     */
    @Override
    public String getQuestionLimit10(){
        List<Questionbank> fourQuestions=questionBankDao.selectLimit10();
        List<QuestionsDto> finQuestions=formatTransfer(fourQuestions);
        return JSON.toJSONString(finQuestions);
    }

    @Override
    public List<Questionbank> getRandomQuestionByLevel(Integer level) {
        if (level != null){
            if (level == 3){  //获取15道题
                return questionBankDao.getFiftheenQuestionByLevel(3);
            }else {
                return questionBankDao.getRandomQuestionByLevel(level);
            }
        }else {      //随机获取风格不定的10个题
            return questionBankDao.getRandomQuestion();
        }
    }


    /**function:获取所有的选项 param: return:*/
    private List<QuestionsDto> formatTransfer(List<Questionbank> list){
        List<QuestionsDto> allQuestions = new ArrayList<>(11);  //存储所有问题以及答案等信息
        for (Questionbank question:list
             ){
            QuestionsDto questionsDto = new QuestionsDto();            //单个问题
            List<Map<String,String>> optionList = new ArrayList<>(5);
            {
                Map<String,String> option = new HashMap<>(4);
                option.put("imageUrl",question.getOption1Url());
                option.put("content",question.getOption1());        //选项1
                optionList.add(option);
            }
            {
                Map<String,String> option = new HashMap<>(4);
                option.put("imageUrl",question.getOption2Url());
                option.put("content",question.getOption2());        //选项2
                optionList.add(option);
            }
            {
                Map<String,String> option = new HashMap<>(4);
                option.put("imageUrl",question.getOption3Url());
                option.put("content",question.getOption3());        //选项3
                optionList.add(option);
            }
            {
                Map<String,String> option = new HashMap<>(4);
                option.put("imageUrl",question.getOption4Url());
                option.put("content",question.getOption4());        //选项4
                optionList.add(option);
            }
            questionsDto.setOptions(optionList);  //选项
            questionsDto.setQuestion(question.getInfo());      //获取问题
            questionsDto.setAnswer(question.getAnswer());      //获取答案
            questionsDto.setAnalysis(question.getAnalysis());   //题目的解析
            if(rembers[0]==null){
                rembers[0]=question.getRembers();
                questionsDto.setRembers(rembers[0]);
            }else{
                //&i<rembers.length-1
                for(int i=0;i<rembers.length-1&&rembers[i]!=null;i++){
                    if(!rembers[i].equals(question.getRembers())){
                        flag=true;
                    }else{
                        flag=false;
                        break;
                    }
                }
                if(flag){
                    questionsDto.setRembers(question.getRembers());
                    for(int i=0;i<rembers.length;i++) {
                        if (rembers[i]==null) {
                            rembers[i] = question.getRembers();
                            break;
                        }
                    }
                }
            }
            allQuestions.add(questionsDto);              //所有的问题
        }
        rembers=new String[15];
        flag=false;
        return allQuestions;
    }

    @Override
    public String queryRemebers(Integer level){
        List<remebers> results = rembersDao.queryRembers(level);
        if (results.size() > 0){
            return dataFactory.getJsonString(results.get(0),true,"remebers","content");
        }else {
            return null;
        }
    }

}
