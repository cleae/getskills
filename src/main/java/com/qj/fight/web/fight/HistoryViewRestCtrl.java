package com.qj.fight.web.fight;

import com.qj.fight.dao.BattleRoomDao;
import com.qj.fight.dao.QuestionBankDao;

import com.qj.fight.entity.award;
import com.qj.fight.entity.buyCourses;
import com.qj.fight.entity.courses;
import com.qj.fight.entity.member;
import com.qj.fight.service.BattleHistoryService;
import com.qj.fight.service.QueryQuestion;
import com.qj.fight.utiles.DataFactory;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.service.WxPayService;
import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

 */
@RestController
@RequestMapping("/user")
public class HistoryViewRestCtrl {
    @Autowired
    WxPayService wxPayService;
    @Autowired
    BattleRoomDao battleRoomDao;
    private BattleHistoryService battleHistoryService;
    @Autowired
    private QueryQuestion queryQuestion;
    @Autowired
    private QuestionBankDao questionBankDao;
    @Autowired
    private DataFactory dataFactory;
    @Autowired
    com.qj.fight.dao.coursesDao coursesDao;
    @Autowired
    public void setBattleHistoryService(BattleHistoryService battleHistoryService) {
        this.battleHistoryService = battleHistoryService;
    }
    @Autowired
    SQLManager sqlManager;
    /**function:获取对战记录 param: return:*/
    @GetMapping("/getRecord")
    public String getBattleRecords(){
        return null;
    }

    @PostMapping("/createHistory")
        public String insertHistory(String from,short scoreNumber){
        return "ok";
    }

    @RequestMapping("/experience")
    public Object question(@RequestBody member m) {
        Map map = new HashMap(4);
        String openId = m.getOpenid();
        List<member> list = questionBankDao.select_isperience(openId);
        member members = list.get(0);
        if (members.getIs_experience()==1) {
            map.put("is_experience", 1);
            return map;
        } else {
            map.put("question", queryQuestion.getQuestionLimit10());
            map.put("is_experience", 0);
            map.put("remembers", queryQuestion.queryRemebers(0));
            return map;
//        }
        }
    }

    @RequestMapping("/is_experience")
    public Object is_experience(@RequestBody Map map){
        String openid=map.get("openid").toString();
        int scoreNumber=Integer.parseInt(map.get("scoreNumber").toString());
        int type=Integer.parseInt(map.get("type").toString());
//        Map<String ,Integer> selectMap=questionBankDao.selectaward(type,openid);
        questionBankDao.insertaward(new award(openid,type,scoreNumber*20));
        int result=questionBankDao.update_experience(openid);

        Map map1=new HashMap(4);
        map1.put("award",scoreNumber*20);
        return map;
    }
    @PostMapping("/updatagrade")
    public Object updateGrade(@RequestBody Map map){
        Integer level=Integer.parseInt(map.get("level").toString());
        String openid=map.get("openid").toString();
        String result=queryQuestion.getQuestionLimit10();
        return result;
    }
    @RequestMapping("/getStyle")
    public String getStyle(@RequestBody Map map){
        String style=map.get("style").toString();
        String keyword=map.get("value").toString();
        String question=queryQuestion.selectQuestionByKeyword(keyword,style);
        return question;
    }
    @RequestMapping("/level")
    public Object level(@RequestBody member members){
        Map map=new HashMap(4);
        String openid=members.getOpenid();
        int level =questionBankDao.selectLevel(openid).get("level");
        map.put("level",level);
        return map;
    }
    @PostMapping(value="/pay/init")
    public Map<String, Object> pay_init(@RequestBody Map<String,Object> map){
        Integer pay_amount=Integer.parseInt(map.get("pay_amount").toString());
        String openid=map.get("openid").toString();
//        Map<String,Object> resultMap=Pay(openid,pay_amount);
        String trade=System.currentTimeMillis()+"";
        Map<String, Object> resultMap = new HashMap<>();
        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        //支付信息
        orderRequest.setBody("pay");
        //用户openid
        orderRequest.setOpenid(openid);
        //支付金额
        orderRequest.setTotalFee(pay_amount);   //注意：传入的金额参数单位为分
        //outTradeNo  订单号
        orderRequest.setOutTradeNo(trade);
        //用户IP地址
        orderRequest.setSpbillCreateIp("61.187.92.238");
        Object obj = null;
        Map<String, Object> payparams = new HashMap<>();

        try{
            obj = wxPayService.createOrder(orderRequest);
            beanToMap(payparams, obj);
            payparams.put("package", payparams.get("packageValue"));
            resultMap.put("payparams", payparams);
            resultMap.put("ok", 0);
//            resultMap.put("discount_amount", 0);
            resultMap.put("order_id",trade);
            resultMap.put("pay_amount", 1);
            resultMap.put("payment_id", 0);
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return resultMap;
    }

    public  void beanToMap(Map<String, Object> res, Object object) throws Exception {

        Class cls = object.getClass();//getclass()返回一个对象类型   通过cls可以获得获得该对象的所有对象和方法
        Field[] fields = cls.getDeclaredFields();//得到的是该类自己声明的成员变量的信息
        for (Field field : fields) {
            field.setAccessible(true);//有关反射
            if(field.get(object)!=null)
                res.put(field.getName(), field.get(object));//field.getName()得到成员变量名
        }

    }
    @PostMapping("/balance")
    public Object updataBalance (@RequestBody Map map){
        String openid=map.get("openid").toString();
        Integer balance=Integer.parseInt(map.get("balance").toString());
        int newbalance=questionBankDao.selectBalance(openid).get("balance");
        questionBankDao.updateBalance(newbalance-balance,openid);
        return newbalance-balance;
    }
    @PostMapping("/level/update")
    public int ulevel(@RequestBody Map map){
       Integer level=Integer.parseInt(map.get("level").toString());
        String openid=map.get("openid").toString();
        questionBankDao.updatelevel(++level,openid);
        return level;
    }
    @PostMapping("/get/course")   //给课程
    public Object getCourse(@RequestBody Map map){
        Map map1=new HashMap(4);
        String openid=map.get("openid").toString();
        int resuleLevel=questionBankDao.selectMaxLevel(openid);
        if(resuleLevel<3){
            map1.put("buy",0);
            return map;
        }else{
            List<courses> list=coursesDao.select();
            String result =dataFactory.getJsonString(list,false,"course","createdAt","updatedAt");
            map.put("buy",result);
            return map;
        }
    }
//    PostMapping("get/dis")
    @PostMapping("/payCourse/submit")
    public Object submit(@RequestBody Map map){//使用微信购买课程  小程序发来需要购买的课程 自己的openid
        String coursename=map.get("coursename").toString();
        String openid=map.get("openid").toString();
        Integer shouldPay;
        try{
            Map <String,Integer> coursePrice=coursesDao.selectCoursesByName(coursename);
            System.out.println(coursePrice);
            Integer totalDiscount=SumDiscount(openid);
            System.out.println(totalDiscount+"总奖金是");
            shouldPay=coursePrice.get("price")-totalDiscount;
        }catch (NullPointerException e){
            System.out.println("NullPointerException");
            return "没有书名的信息 或书名信息已被篡改";
        }
        catch (Exception e){
            System.out.println("Exception");
            return "没有书名的信息 或书名信息已被篡改";
        }
        coursesDao.insertBuyCourses(new buyCourses(openid,coursename));
        return Pay(openid,shouldPay);

    }
    private Integer SumDiscount(String openid){//返回玩家所有type的奖学金总和 用于抵扣课程费用
        Integer totalDiscount=0;
//        Map<String,Integer> sumAwardMap=questionBankDao.selectSumAwardByOpenid(openid);
        List<award> sumAwardMap=questionBankDao.selectSumAwardByOpenid(openid);
        for(award award1:sumAwardMap){
            totalDiscount=award1.getAward()+totalDiscount;
        }
        return  totalDiscount;
    }

    private Object Pay(String openid,int pay_amount){
        String trade=System.currentTimeMillis()+"";
        Map<String, Object> resultMap = new HashMap<>();
        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        //支付信息
        orderRequest.setBody("pay");
        //用户openid
        orderRequest.setOpenid(openid);
        //支付金额
        orderRequest.setTotalFee(pay_amount*100);   //注意：传入的金额参数单位为分
        //outTradeNo  订单号
        orderRequest.setOutTradeNo(trade);
        //用户IP地址
        orderRequest.setSpbillCreateIp("61.187.92.238");
        Object obj = null;
        Map<String, Object> payparams = new HashMap<>();
        try{
            obj=wxPayService.createOrder(orderRequest);
            beanToMap(payparams,obj);
            payparams.put("package", payparams.get("packageValue"));
            resultMap.put("payparams", payparams);
            resultMap.put("order_id",trade);
            resultMap.put("pay_amount", pay_amount);
            resultMap.put("payment_id", 0);
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return resultMap;
    }

}
