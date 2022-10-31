package com.demo.SMS.service;

import com.demo.KaKao.HttpCallService;
import com.demo.SMS.domain.entity.EventSender;
import com.demo.SMS.domain.entity.MessageRecord;
import com.demo.SMS.repository.MessageRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendSMSService extends HttpCallService {
    private static final String SEND_SMS_URL = "https://apis.aligo.in/send/";

    private String encodingType = "utf-8";
    private final String boundary = "____boundary____"; //form-data boundary문구
    private String SEND_SMS_KEY = "0kzisw67o920f5d92idu3v6e16f5u7q5";
    private final MessageRecordRepository recordRepository;

    /************************ 문자전송하기 예제 **********************
     **          "result_code":결과코드,"message":결과문구,          **
     ** "msg_id":메세지ID,"error_cnt":에러갯수,"success_cnt":성공갯수 **
     ***************************************************************/
    public boolean sendSMS(String Content, EventSender messageSender, String phoneNumber, String eventType, String trigger) {       //TODO: SMS API호출식으로 전송할지 함수호출할건지 보고 api지우면됨
        String result = "";

        try {
            Thread.sleep(500);
            Map<String, String> sms = new HashMap<String, String>();

            /*************************** SMS 전송 내용 *************************************/
            sms.put("sender", "010-4451-8852"); // 발신번호 : 010-4451-8852
            sms.put("receiver", phoneNumber); // 수신번호
            sms.put("user_id", "jlabsys"); // SMS 아이디
            sms.put("key", "2l56b4h4qsm51b3hk828l8jv19r83hdy"); //인증키
            sms.put("testmode_yn", "N"); // Y 인경우 실제문자 전송X , 자동취소(환불) 처리
            sms.put("msg", Content);
//            String image = snapFeat;


            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            builder.setBoundary(boundary);
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName(encodingType));

            for (Iterator<String> i = sms.keySet().iterator(); i.hasNext(); ) {
                String key = i.next();
                builder.addTextBody(key, sms.get(key)
                        , ContentType.create("Multipart/related", encodingType));
            }
//            File imageFile = new File(image);
//            if (image != null && image.length() > 0 && imageFile.exists()) {
//                builder.addPart("image",
//                        new FileBody(imageFile, ContentType.create("application/octet-stream"),
//                                URLEncoder.encode(imageFile.getName(), encodingType)));
//            }
            HttpEntity entity = builder.build();
            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(SEND_SMS_URL);
            post.setEntity(entity);
            HttpResponse res = client.execute(post);

            if (res != null) {
                BufferedReader in = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), encodingType));
                String buffer = null;
                while ((buffer = in.readLine()) != null) {
                    result += buffer;
                }
                in.close();
            }

            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(result);
                JSONObject jsonObject = (JSONObject) obj;
                int resultCode = Integer.parseInt(String.valueOf(jsonObject.get("result_code")));
                if (resultCode > 0) {
                    log.info("메시지 발송 완료");
                    MessageRecord smsSuccessRecord = MessageRecord.builder()
                            .clientName(messageSender.getClientName())
                            .deviceName(messageSender.getDeviceName())
                            .cameraName(messageSender.getCameraName())
                            .adminName(messageSender.getAdminName())
                            .managerName(messageSender.getManagerName())
                            .eventType(eventType)
                            .msgType("SMS")
                            .trigger(trigger)
                            .sendTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .successYn("Y")
                            .build();
                    recordRepository.save(smsSuccessRecord);
                    log.info("메시지 전송기록 저장");
                    return true;
                } else {
                    log.info("알리고 메시지 발송 실패");
                    MessageRecord smsFailureRecord = MessageRecord.builder()
                            .clientName(messageSender.getClientName())
                            .deviceName(messageSender.getDeviceName())
                            .cameraName(messageSender.getCameraName())
                            .adminName(messageSender.getAdminName())
                            .managerName(messageSender.getManagerName())
                            .eventType(eventType)
                            .msgType("SMS")
                            .trigger(trigger)
                            .sendTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .successYn("N")
                            .build();
                    recordRepository.save(smsFailureRecord);
                    return false;
                }
            } catch (ParseException e1) {
                log.info("파싱에러");
                return false;
            }
        } catch (Exception e) {
            System.out.println("exception ::" + e);
            return false;
        }
    }
}
