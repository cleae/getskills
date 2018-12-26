package com.qj.fight.utiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 数据过滤工厂
 */
@Component
public final class  DataFactory {
    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> String getJsonString(T data,boolean isInclude,String filterId,String...args){
        String json = "";
        if (isInclude){
            objectMapper = getReservedMapper(filterId,args);
        }else {
            objectMapper = getExcludeObjectMapper(filterId,args);
        }
        try {
            json = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return json;
    }

    private ObjectMapper getReservedMapper(String id, String...args){//传入不需要被过滤的属性
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(id,   // 设置要转换json的属性
                SimpleBeanPropertyFilter.filterOutAllExcept(args)));
        return objectMapper;
    }

    private ObjectMapper getExcludeObjectMapper(String id, String...args){//传入需要被过滤的属性
        ObjectMapper objectMapper1=new ObjectMapper();
        objectMapper1.setFilterProvider(new SimpleFilterProvider().addFilter(id,
                SimpleBeanPropertyFilter.serializeAllExcept(args)
        ));
        return objectMapper1;
    }
}
