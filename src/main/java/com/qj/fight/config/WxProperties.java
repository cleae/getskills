package com.qj.fight.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wxpay.config")
public class WxProperties {

    /**
     * 设置小程序的appId
     */
    private String appId;

    /**
     * 小程序秘钥
     */
    private String secret;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户key
     */
    private String mchKey;

    /**
     * 回调url
     */
    private String notifyUrl;

    /**
     * 交易类型
     */
    private String tradeType;


    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getAppId() {
        return appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

}

