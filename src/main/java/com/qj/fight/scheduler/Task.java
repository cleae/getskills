package com.qj.fight.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class Task {

    private Logger logger = LoggerFactory.getLogger(Task.class);

    @Scheduled(fixedRate = 50000)
    public void sendMessage(){

        String message = "你好";
        System.out.println("good!");



    }

}