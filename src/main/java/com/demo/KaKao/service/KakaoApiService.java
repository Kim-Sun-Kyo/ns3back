package com.demo.KaKao.service;

import com.demo.KaKao.HttpCallService;
import com.demo.KaKao.vo.KakaoFriendsVO;
import com.demo.SMS.domain.entity.EventSender;
import com.demo.SMS.domain.entity.MessageRecord;
import com.demo.SMS.repository.MessageRecordRepository;
import com.demo.SMS.service.SendSMSService;
import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoApiService extends HttpCallService {
    private static final String MSG_SEND_FRIENDS_URL = "https://kapi.kakao.com/v1/api/talk/friends/message/send";
    private static final String REQUEST_FRIENDS_LIST = "https://kapi.kakao.com/v1/api/talk/friends";
    private static final String FILE_PATH = "http://192.168.0.97:8092/v1/img/";
//    private static final String FILE_PATH = "http://211.111.12.131:8092/v1/img/";

    private static final String SEND_SUCCESS_MSG = "메시지 전송에 성공했습니다.";
    private static final String SEND_FAIL_MSG = "메시지 전송에 실패했습니다.";
    private static final String SUCCESS_CODE = ""; // Kakao api 에서 return 하는 success_code 값.
    private final TokenService tokenService;
    private final SendSMSService smsService;

    private final MessageRecordRepository recordRepository;

    /* 카카오톡 친구정보 불러오기 */
    public Map<String, Object> getFriendsList(String accessToken, String refreshToken, String user) {
        Map<String, Object> returnValue = new HashMap<>();
        try {
            HttpHeaders header = new HttpHeaders();
            header.set("Authorization", "Bearer " + accessToken);
            HttpEntity<?> requestEntity = httpClientEntity(header, "");
            ResponseEntity<String> response = httpRequest(REQUEST_FRIENDS_LIST, HttpMethod.GET, requestEntity);
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray elements = jsonObject.getJSONArray("elements");
            ArrayList<KakaoFriendsVO> obj = new ArrayList<>();
            for (int i = 0; i < elements.length(); i++) {
                JSONObject friendsInfo = (JSONObject) elements.get(i);
                obj.add(i, new KakaoFriendsVO(friendsInfo.get("profile_nickname").toString(), friendsInfo.get("uuid").toString()));
            }
            returnValue.put("token", accessToken);
            returnValue.put("friend", obj);
            return returnValue;

        } catch (Exception e) {
            // 정확히는 401 Unauthorized  token 이 손상됐기 때문에 그에 따른 친구리스트가 불러와지지 않았으므로 null
            log.info("token are damaged");
            String renewalAccessToken = tokenService.renewalToken(refreshToken, user);
            HttpHeaders header = new HttpHeaders();
            header.set("Authorization", "Bearer " + renewalAccessToken);
            HttpEntity<?> requestEntity = httpClientEntity(header, "");
            ResponseEntity<String> response = httpRequest(REQUEST_FRIENDS_LIST, HttpMethod.GET, requestEntity);
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray elements = jsonObject.getJSONArray("elements");
            ArrayList<KakaoFriendsVO> obj = new ArrayList<>();
            for (int i = 0; i < elements.length(); i++) {
                JSONObject friendsInfo = (JSONObject) elements.get(i);
                obj.add(i, new KakaoFriendsVO(friendsInfo.get("profile_nickname").toString(), friendsInfo.get("uuid").toString()));
            }
            returnValue.put("token", renewalAccessToken);
            returnValue.put("friend", obj);
            return returnValue;
        }
    }

    public Map<String, ArrayList<EventSender>> sendKakao(String accessToken, List<EventSender> kakaoSenders, String trigger, String snapId, String eventType, String cameraName, String deviceId, String personName,String key) {
        Map<String, ArrayList<EventSender>> resultMap = new HashMap<>();
        ArrayList<EventSender> successList = new ArrayList<>();
        ArrayList<EventSender> failureList = new ArrayList<>();
        ArrayList<String> uuidObj = new ArrayList<>();
        for (EventSender kakaoSender : kakaoSenders) {
            uuidObj.add("\"" + kakaoSender.getKakaoUuid() + "\"");
        }
        String content = kakaoSenders.get(0).getContent();
        if(eventType.equals("101")||eventType.equals("102")){
            content=content.replace("{0}",personName);
        }
        String[] s = trigger.split(" ");
        String substring = s[0].substring(2).replace("-", "/");
        String substring1 = s[1].substring(0, 5);
        String occurTime = substring + " " + substring1;


        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content_type", APP_TYPE_URL_ENCODED);
            httpHeaders.set("Authorization", "Bearer " + accessToken);

            JSONObject argsObj = new JSONObject();
            argsObj.put("THU", FILE_PATH + snapId);
            argsObj.put("EVENT", content);
            argsObj.put("HEAD", cameraName + " " + deviceId + "_" + occurTime);
            argsObj.put("KEY", key);

            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            parameters.add("template_id", "83192");
            parameters.add("template_args", argsObj.toString());
            parameters.add("receiver_uuids", uuidObj.toString());

            HttpEntity<?> requestMessageEntity = httpClientEntity(httpHeaders, parameters);
            ResponseEntity<String> response = httpRequest(MSG_SEND_FRIENDS_URL, HttpMethod.POST, requestMessageEntity);
            JSONObject jsonData = new JSONObject(response.getBody());
            if (jsonData.has("failure_info")) {
                // 일부 성공, 일부 실패한 경우에 해당하는 것
                String[] successfulReceiverUuids = jsonData.get("successful_receiver_uuids").toString().replace("[", "").replace("]", "").replaceAll("\"", "").split(",");
                JSONObject failureInfo = (JSONObject) jsonData.get("failure_info");
                String[] failureReceiverUuids = failureInfo.get("receiver_uuid").toString().replace("[", "").replace("]", "").replaceAll("\"", "").split(",");
                for (String successfulReceiverUuid : successfulReceiverUuids) {
                    for (EventSender kakaoSender : kakaoSenders) {
                        if (kakaoSender.getKakaoUuid().equals(successfulReceiverUuid)) {
                            log.info("일치하는 이를 찾음. message record 추가 작업");
                            successList.add(kakaoSender);
                            MessageRecord kakaoSuccessRecord = MessageRecord.builder()
                                    .clientName(kakaoSender.getClientName())
                                    .deviceName(kakaoSender.getDeviceName())
                                    .cameraName(kakaoSender.getCameraName())
                                    .adminName(kakaoSender.getAdminName())
                                    .managerName(kakaoSender.getManagerName())
                                    .eventType(eventType)
                                    .msgType(kakaoSender.getMsgTypeNew())
                                    .trigger(trigger)
                                    .sendTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                    .successYn("Y")
                                    .build();

                            recordRepository.save(kakaoSuccessRecord);
                        }
                    }
                }

                for (String failureReceiverUuid : failureReceiverUuids) {
                    for (EventSender kakaoSender : kakaoSenders) {
                        if (kakaoSender.getKakaoUuid().equals(failureReceiverUuid)) {
                            log.info("일부 실패한 이를 찾음. message record 추가작업");
                            failureList.add(kakaoSender);
                            MessageRecord kakaoFailureRecord = MessageRecord.builder()
                                    .clientName(kakaoSender.getClientName())
                                    .deviceName(kakaoSender.getDeviceName())

                                    .cameraName(kakaoSender.getCameraName())
                                    .adminName(kakaoSender.getAdminName())
                                    .managerName(kakaoSender.getManagerName())
                                    .eventType(eventType)
                                    .msgType(kakaoSender.getMsgTypeNew())
                                    .trigger(trigger)
                                    .sendTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                    .successYn("N")
                                    .build();
                            recordRepository.save(kakaoFailureRecord);
                        }
                    }
                }
                resultMap.put("success", successList);
                resultMap.put("fail", failureList);
                return resultMap;
            } else {
                // 모두 성공
                String[] successfulReceiverUuids = jsonData.get("successful_receiver_uuids").toString().replace("[", "").replace("]", "").replaceAll("\"", "").split(",");
                for (String successfulReceiverUuid : successfulReceiverUuids) {
                    for (EventSender kakaoSender : kakaoSenders) {
                        if (kakaoSender.getKakaoUuid().equals(successfulReceiverUuid)) {
                            log.info("일치하는 이를 찾음, messageRecord 추가 작업");
                            successList.add(kakaoSender);
                            MessageRecord kakaoSuccessRecord = MessageRecord.builder()
                                    .clientName(kakaoSender.getClientName())
                                    .deviceName(kakaoSender.getDeviceName())
                                    .cameraName(kakaoSender.getCameraName())
                                    .adminName(kakaoSender.getAdminName())
                                    .managerName(kakaoSender.getManagerName())
                                    .eventType(eventType)
                                    .msgType(kakaoSender.getMsgTypeNew())
                                    .trigger(trigger)
                                    .sendTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                    .successYn("Y")
                                    .build();
                            recordRepository.save(kakaoSuccessRecord);
                        }
                    }
                }
                resultMap.put("success", successList);
                return resultMap;
            }
        } catch (HttpClientErrorException.BadRequest e) {
            log.info("모든 사람 전송 실패");
            failureList.addAll(kakaoSenders);
            resultMap.put("fail", failureList);

            for (EventSender kakaoSender : kakaoSenders) {
                MessageRecord kakaoFailureRecord = MessageRecord.builder()
                        .clientName(kakaoSender.getClientName())
                        .deviceName(kakaoSender.getDeviceName())
                        .cameraName(kakaoSender.getCameraName())
                        .adminName(kakaoSender.getAdminName())
                        .managerName(kakaoSender.getManagerName())
                        .eventType(eventType)
                        .msgType(kakaoSender.getMsgTypeNew())
                        .trigger(trigger)
                        .sendTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .successYn("N")
                        .build();
                recordRepository.save(kakaoFailureRecord);
            }
            return resultMap;
        }
    }
}