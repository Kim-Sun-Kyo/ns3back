package com.demo.NS3.service;

import com.demo.Front.domain.entity.Manager;
import com.demo.Front.repository.jpaRepository.ManagerRepository;
import com.demo.KaKao.entity.Token;
import com.demo.KaKao.repository.TokenRepository;
import com.demo.KaKao.service.KakaoApiService;
import com.demo.KaKao.vo.KakaoFriendsVO;
import com.demo.NS3.entity.BodyEntity;
import com.demo.NS3.entity.FaceEntity;
import com.demo.NS3.repository.BodyRepository;
import com.demo.NS3.repository.FaceRepository;
import com.demo.NS3.vo.EventData;
import com.demo.SMS.domain.entity.EventSender;
import com.demo.SMS.repository.EventSenderRepository;
import com.demo.SMS.repository.MessageRecordRepository;
import com.demo.SMS.service.SMSTokenService;
import com.demo.SMS.service.SendSMSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class MsgScheduler {
    private final ManagerRepository managerRepository;

    private final BodyRepository bodyRepository;
    private final FaceRepository faceRepository;

    private final EventSenderRepository senderRepository;

    private final MessageRecordRepository recordRepository;

    private final KakaoApiService kakaoApiService;

    private final TokenRepository tokenRepository;

    private final SendSMSService smsService;

    private final SMSTokenService tokenService;


    @Scheduled(cron = "* * * * * *")
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
        List<EventData> data = new ArrayList<>();
        List<BodyEntity> findData = bodyRepository.findAllBySendflag("R");
        for (BodyEntity body : findData) {
            EventData temp = new EventData();
            temp.setOriginId(body.getId());
            temp.setDeviceName(body.getDeviceid());
            temp.setCameraName(body.getCameraname());
            temp.setSnapId(body.getSnapid());
            temp.setTrigger(body.getTrigger());
            temp.setEventType(body.getEvents_type());
            data.add(temp);
        }
        List<FaceEntity> findFaceData = faceRepository.findAllBySendFlag("R");
        for (FaceEntity face : findFaceData) {
            EventData temp=new EventData();
            temp.setOriginId(face.getId());
            temp.setDeviceName(face.getDeviceId());
            temp.setCameraName(face.getCameraName());
            temp.setSnapId(face.getSnapId());
            temp.setTrigger(face.getTrigger());
            if(face.getLibType()==2){
                temp.setEventType("101");
                temp.setPersonName(face.getPersonName());
            }
            else if (face.getLibType()==1) {
                temp.setEventType("102");
                temp.setPersonName(face.getPersonName());
            }
            else temp.setEventType("103");

            data.add(temp);
        }
        if (data.size() != 0) {
            log.info("**** event exist ****");
            for (EventData individualData : data) {
                boolean smsResult = false;
                List<EventSender> kakaoSenders = new ArrayList<>();
                List<EventSender> messageSenders = new ArrayList<>();
                List<EventSender> senders = senderRepository.findSenders(individualData.getCameraName(), individualData.getDeviceName(), individualData.getEventType());
                if (senders.size() > 0) {
                    for (EventSender sender : senders) {
                        if (sender.getMsgTypeNew().equals("TALK")) {
                            kakaoSenders.add(sender);
                        }
                        if (sender.getMsgTypeNew().equals("SMS")) {
                            messageSenders.add(sender);
                        }
                    }
                    String token = tokenService.createToken(individualData.getSnapId());
                    String key = tokenService.splitPayload(token);
                    Map<String, ArrayList<EventSender>> resultMap = kakaoApiService.sendKakao(accessToken, kakaoSenders, individualData.getTrigger(), individualData.getSnapId(), individualData.getEventType(), individualData.getCameraName(), individualData.getDeviceName(),individualData.getPersonName(), key);
                    if (resultMap.containsKey("success")) {
                        List<EventSender> eventSenders = resultMap.get("success");
                        for (EventSender eventSender : eventSenders) {
                            messageSenders.removeIf(o -> o.getKakaoUuid().equals(eventSender.getKakaoUuid()));
                        }
                        tokenService.createTokenEntity(individualData.getSnapId(), individualData.getCameraName(), individualData.getDeviceName(), eventSenders.get(0).getClientName(), token);
                    }

                    if (resultMap.containsKey("fail")) {
                        List<EventSender> eventSenders = resultMap.get("fail");
                        for (EventSender eventSender : eventSenders) {
                            if (messageSenders.stream().noneMatch(o -> o.getPhoneNumber().equals(eventSender.getPhoneNumber()))) {
                                messageSenders.add(eventSender);
                            }
                        }
                    }

                    for (EventSender messageSender : messageSenders) {
                        String contentSMS = messageSender.getContent();
                        if(individualData.getEventType().equals("101")||individualData.getEventType().equals("102")){
                            contentSMS = contentSMS.replace("{0}",individualData.getPersonName());
                        }
                        String content = "[클라우드안전관리]" + "\n" +contentSMS + "" + "\n" + individualData.getCameraName() + "" + "\n" + individualData.getTrigger() + "";
                        smsResult = smsService.sendSMS(content, messageSender, messageSender.getPhoneNumber(), individualData.getEventType(), individualData.getTrigger());
                    }

                    if (resultMap.containsKey("success") || smsResult) {
                        String type =individualData.getEventType();
                        if(type.equals("101")||type.equals("102")||type.equals("103")){
                            FaceEntity entity = faceRepository.findById(individualData.getOriginId()).orElseThrow();
                            entity.setSendFlag("Y");
                            faceRepository.save(entity);
                        }
                        else{
                            BodyEntity entity = bodyRepository.findById(individualData.getOriginId()).orElseThrow();
                            entity.setSendflag("Y");
                            bodyRepository.save(entity);
                        }
                    } else {
                        String type =individualData.getEventType();
                        if(type.equals("101")||type.equals("102")||type.equals("103")){
                            FaceEntity entity = faceRepository.findById(individualData.getOriginId()).orElseThrow();
                            entity.setSendFlag("N");
                            faceRepository.save(entity);
                        }
                        else{
                            BodyEntity entity = bodyRepository.findById(individualData.getOriginId()).orElseThrow();
                            entity.setSendflag("N");
                            bodyRepository.save(entity);
                        }
                    }
                } else {
                    log.info(" === Sender is not found === ");
                    String type =individualData.getEventType();
                    if(type.equals("101")||type.equals("102")||type.equals("103")){
                        FaceEntity entity = faceRepository.findById(individualData.getOriginId()).orElseThrow();
                        entity.setSendFlag("N");
                        faceRepository.save(entity);
                    }
                    else{
                        BodyEntity entity = bodyRepository.findById(individualData.getOriginId()).orElseThrow();
                        entity.setSendflag("N");
                        bodyRepository.save(entity);
                    }

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
