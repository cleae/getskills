package com.qj.fight.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxProperties.class)
public class WxPayConfiguration {

    private WxProperties properties;

    @Autowired
    public WxPayConfiguration(WxProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxService() {

        WxPayConfig payConfig = new WxPayConfig();

        payConfig.setAppId(StringUtils.trimToNull(this.properties.getAppId()));
        payConfig.setMchId(StringUtils.trimToNull(this.properties.getMchId()));
        payConfig.setMchKey(StringUtils.trimToNull(this.properties.getMchKey()));
        payConfig.setNotifyUrl(StringUtils.trimToNull(this.properties.getNotifyUrl()));
        payConfig.setTradeType(StringUtils.trimToNull(this.properties.getTradeType()));

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);

        return wxPayService;
    }

}
