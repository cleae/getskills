//package com.fox.qiji.web.fight;
//
//import QueryQuestion;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @function
// */
//@RestController
//@RequestMapping("/question")
//public class QuestionRestCtrl {
//    private QueryQuestion questionQueryService;
//    @Autowired
//    private RemebersService remebersService;
//
//    @Autowired
//    public void setQuestionQueryService(QueryQuestion questionQueryService) {
//        this.questionQueryService = questionQueryService;
//    }
//    /**function:通过open按照人的等级查询十个题目 param: return:*/
//    @GetMapping("/getQuestion")
//    public String getQuestionByOpenId(Integer level){
//        return questionQueryService.getRandomQuestionByLevel(level).toString();
//    }
//
//    @GetMapping("/a")
//    public String aA(Integer level){
//        return remebersService.queryRemebers(level);
//    }
//
//}
