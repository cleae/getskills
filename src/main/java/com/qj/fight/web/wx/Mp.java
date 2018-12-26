package com.qj.fight.web.wx;




import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/mp")
public class Mp {
    public WxMpInMemoryConfigStorage config;
    public WxMpService wxMpService;
    public WxMpMessageRouter wxMpMessageRouter;

    public Mp(){
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId("wxdcac3d39d9adc3e1"); // 设置微信公众号的appid
        config.setSecret("6f779209a9a878a2c2e02148fdb52399"); // 设置微信公众号的app corpSecret
        config.setToken("jkdasfksdeee789GHd"); // 设置微信公众号的token
        config.setAesKey("jXdhvlCHq9jpYTID9Pws644H0L9xoQohZd1cOo9pqrR"); // 设置微信公众号的EncodingAESKey

        wxMpService = new WxMpServiceImpl();// 实际项目中请注意要保持单例，不要在每次请求时构造实例，具体可以参考demo项目
        wxMpService.setWxMpConfigStorage(config);

        //WxJsapiSignature wsig = wxMpService.createJsapiSignature()



    }



    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authGet(
            @RequestParam(name = "signature", required = false) String signature,
            @RequestParam(name = "timestamp", required = false) String timestamp,
            @RequestParam(name = "nonce", required = false) String nonce,
            @RequestParam(name = "echostr", required = false) String echostr) {


        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }


        if (wxMpService == null) {
            return "problem";

        }

        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }


    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String post(
            @RequestBody String requestBody,
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("openid") String openid,
            @RequestParam(name = "encrypt_type", required = false) String encType,
            @RequestParam(name = "msg_signature", required = false) String msgSignature) {



        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        System.out.println(openid);

        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutTextMessage outMessage
                    = WxMpXmlOutMessage.TEXT().content("测试feijiami消息").fromUser(inMessage.getToUser())
                    .toUser(inMessage.getFromUser()).build();
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toXml();
        } else if ("aes".equalsIgnoreCase(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxMpService.getWxMpConfigStorage(),
                    timestamp, nonce, msgSignature);

            WxMpXmlOutMessage outMessage = WxMpXmlOutMessage.TEXT().content("测试加密消息").fromUser(inMessage.getToUser())
                    .toUser(inMessage.getFromUser()).build();
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toEncryptedXml(wxMpService.getWxMpConfigStorage());
        }


        return out;
    }
    @GetMapping("/js/sig")
    String get_jssig(String url) throws WxErrorException{
        WxJsapiSignature jsSig = wxMpService.createJsapiSignature(url);
        //jsSig.get
        return jsSig.toString();

    }



}
