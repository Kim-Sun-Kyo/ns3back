package com.demo.NS3.service;

import com.demo.NS3.entity.StdHeartbeatEntity;
import com.demo.NS3.repository.StdHeartbeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeviceScheduler {
    private final StdHeartbeatRepository heartbeatRepository;

    public void checkDevice(){
        List<StdHeartbeatEntity> entity = heartbeatRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.printf("time::"+LocalDateTime.now());
        for (StdHeartbeatEntity list : entity) {
            System.out.println(now);
            LocalDateTime trigger = LocalDateTime.parse(list.getTrigger().replace(" ","T"));
            Long minutes = ChronoUnit.MINUTES.between(trigger,now);
            System.out.println(trigger);
            if(minutes>5){
                list.setOnoff("N");
               heartbeatRepository.save(list);
            }
        }
    }


    @Scheduled(cron= "0 0/5 * * * *")
    public void statusScheduler(){
        log.info("DEVICE CHECK@@@@@@");
        checkDevice();
    }
}
