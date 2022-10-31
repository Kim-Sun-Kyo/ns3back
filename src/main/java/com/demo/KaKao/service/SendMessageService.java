package com.demo.KaKao.service;

import com.demo.KaKao.HttpCallService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@Slf4j
public class SendMessageService extends HttpCallService {
    private static final String MSG_SEND_FRIENDS_URL = "https://kapi.kakao.com/v1/api/talk/friends/message/send";

    private static final String REQUEST_FRIENDS_LIST = "https://kapi.kakao.com/v1/api/talk/friends";
    private static final String SEND_SUCCESS_MSG = "메시지 전송에 성공했습니다.";
    private static final String SEND_FAIL_MSG = "메시지 전송에 실패했습니다.";
    private static final String SUCCESS_CODE = ""; // Kakao api 에서 return 하는 success_code 값.
    public boolean sendMessage(String accessToken,String device, String camera, String msg, String site,String occurTime,String snapid) throws Exception {

        // 카카오톡 친구 UUID Get
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization","Bearer "+accessToken);
        HttpEntity<?> requestEntity = httpClientEntity(header, "");
        ResponseEntity<String> response = httpRequest(REQUEST_FRIENDS_LIST, HttpMethod.GET, requestEntity);
        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONArray elements = jsonObject.getJSONArray("elements");
//        ArrayList<String> uuidObj = new ArrayList<>();
//        for (int i=0; i<elements.length(); i++) {
//            JSONObject friendsInfo = (JSONObject) elements.get(i);
//            uuidObj.add("\""+friendsInfo.get("uuid").toString()+"\"");
//        }

        // 메시지 보내기
        HttpHeaders newHeader = new HttpHeaders();
        newHeader.set("Content_type",APP_TYPE_URL_ENCODED);
        newHeader.set("Authorization","Bearer "+accessToken);

        JSONObject argsObj = new JSONObject();
//        argsObj.put("THU","https://ifh.cc/g/APBkvM.jpg");
        argsObj.put("THU","http://211.111.12.131:8092/v1/img/"+snapid);
        argsObj.put("EVENT",msg);
        argsObj.put("HEAD",device+"//"+camera+"\n"+occurTime);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("template_id","82493");
        parameters.add("template_args",argsObj.toString());
        parameters.add("receiver_uuids", "[\"ckBxRn5Kfkt5VWdTZFdvXG1fZ0t8RXVHd0Av\",\"ckFzQnRFfURwXGpdaFxsWGxccEd-TnxMexo\"]");
//        parameters.add("receiver_uuids", uuidObj.toString());

        HttpEntity<?> requestMessageEntity = httpClientEntity(newHeader, parameters);
        ResponseEntity<String> resultResponse = httpRequest(MSG_SEND_FRIENDS_URL, HttpMethod.POST, requestMessageEntity);
        //status, body(successful_receiver_uuids 메시지 전송 성공 아이디들)
        int statusCodeValue = resultResponse.getStatusCodeValue();
        HttpStatus statusCode = resultResponse.getStatusCode();
        String body = resultResponse.getBody();

        return false;
    }

    public boolean successCheck(String resultCode) {
        if(resultCode.equals(SUCCESS_CODE)) {
            log.info(SEND_SUCCESS_MSG);
            return true;
        }else {
            log.info(SEND_FAIL_MSG);
            return false;
        }
    }
}