package com.demo.NS3.service;

import com.demo.Front.domain.entity.Manager;
import com.demo.Front.repository.jpaRepository.ManagerRepository;
import com.demo.KaKao.entity.Token;
import com.demo.KaKao.repository.TokenRepository;
import com.demo.KaKao.service.KakaoApiService;
import com.demo.KaKao.service.TokenService;
import com.demo.KaKao.vo.KakaoFriendsVO;
import com.demo.NS3.entity.BodyEntity;
import com.demo.NS3.repository.BodyRepository;
import com.demo.SMS.domain.entity.EventSender;
import com.demo.SMS.domain.entity.MessageRecord;
import com.demo.SMS.repository.EventSenderRepository;
import com.demo.SMS.repository.MessageRecordRepository;
import com.demo.SMS.service.SMSTokenService;
import com.demo.SMS.service.SendSMSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventScheduler {
    private final ManagerRepository managerRepository;

    private final BodyRepository bodyRepository;

    private final EventSenderRepository senderRepository;

    private final MessageRecordRepository recordRepository;

    private final KakaoApiService kakaoApiService;

    private final TokenRepository tokenRepository;

    private final SendSMSService smsService;

    private final SMSTokenService tokenService;


//    @Scheduled(cron = "* * * * * *")
    public void sendMsg() {
        log.info(" **** start task **** ");
        managerRepository.updateFlag();

        // getToken
        Token loginToken = tokenRepository.findByUserId("2439553072");
        String accessToken = loginToken.getAccessToken();
        String refreshToken = loginToken.getRefreshToken();
        String user = loginToken.getUserId();

        Map<String, Object> friendsList1 = kakaoApiService.getFriendsList(accessToken, refreshToken, user);
        accessToken = friendsList1.get("token").toString();
        ArrayList<KakaoFriendsVO> friendsList = (ArrayList<KakaoFriendsVO>) friendsList1.get("friend");
        ArrayList<String> uuid = new ArrayList();
        for (KakaoFriendsVO item : friendsList) {
            uuid.add(item.getKakaoUuid());
        }
        List<Manager> findManager = managerRepository.findByKakaoUuidIn(uuid);
        for (Manager manager : findManager) {
            for (KakaoFriendsVO friend : friendsList) {
                if (manager.getKakaoUuid().equals(friend.getKakaoUuid())) {
                    manager.setKakaoNickname(friend.getProfileNickname());
                    manager.setKakaoFlag(true);
                    break;
                }
            }
        }
        if (findManager.size() > 0) {
            managerRepository.saveAll(findManager);
        }

        List<BodyEntity> findData = bodyRepository.findAllBySendflag("R");

        if (findData.size() != 0) {
            log.info("**** event exist ****");
            for (BodyEntity individualData : findData) {
                boolean smsResult = false;
                List<EventSender> kakaoSenders = new ArrayList<>();
                List<EventSender> messageSenders = new ArrayList<>();
                List<EventSender> senders = senderRepository.findSenders(individualData.getCameraname(), individualData.getDeviceid(), individualData.getEvents_type());
                if (senders.size() > 0) {
                    for (EventSender sender : senders) {
                        if (sender.getMsgTypeNew().equals("TALK")) {
                            kakaoSenders.add(sender);
                        }
                        if (sender.getMsgTypeNew().equals("SMS")) {
                            messageSenders.add(sender);
                        }
                    }
                    String token = tokenService.createToken(individualData.getSnapid());
                    String key = tokenService.splitPayload(token);
//                    Map<String, ArrayList<EventSender>> resultMap = kakaoApiService.sendKakao(accessToken, kakaoSenders, individualData.getTrigger(), individualData.getSnapid(), individualData.getEvents_type(), individualData.getCameraname(), individualData.getDeviceid(), key);
//                    if (resultMap.containsKey("success")) {
//                        List<EventSender> eventSenders = resultMap.get("success");
//                        for (EventSender eventSender : eventSenders) {
//                            messageSenders.removeIf(o -> o.getKakaoUuid().equals(eventSender.getKakaoUuid()));
//                        }
//                        tokenService.createTokenEntity(individualData.getSnapid(), individualData.getCameraname(), individualData.getDeviceid(), eventSenders.get(0).getClientName(), token);
//                    }
//
//                    if (resultMap.containsKey("fail")) {
//                        List<EventSender> eventSenders = resultMap.get("fail");
//                        for (EventSender eventSender : eventSenders) {
//                            if (messageSenders.stream().noneMatch(o -> o.getPhoneNumber().equals(eventSender.getPhoneNumber()))) {
//                                messageSenders.add(eventSender);
//                            }
//                        }
//                    }
//
//                    for (EventSender messageSender : messageSenders) {
//                        String content = "[클라우드안전관리]" + "\n" + messageSender.getContent() + "" + "\n" + individualData.getCameraname() + "" + "\n" + individualData.getTrigger() + "";
//                        smsResult = smsService.sendSMS(content, messageSender, messageSender.getPhoneNumber(), individualData.getEvents_type(), individualData.getTrigger());
//                    }
//
//                    if (resultMap.containsKey("success") || smsResult) {
//                        individualData.setSendflag("Y");
//                        bodyRepository.save(individualData);
//                    } else {
//                        individualData.setSendflag("N");
//                        bodyRepository.save(individualData);
//                    }
                } else {
                    log.info(" === Sender is not found === ");
                    individualData.setSendflag("N");
                    bodyRepository.save(individualData);
                    // 총 책임자에게 메세지를 전송하는 로직 작성..
                    // Template 내용은 별도로 규정해야함.
                }
            }
        } else {
            log.info("**** event does not exist ****");
        }
        log.info(" **** task end ****");
    }
}
