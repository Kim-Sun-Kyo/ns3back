package com.demo.Front.service;

import com.demo.Front.domain.dto.FriendVo;
import com.demo.Front.domain.dto.ManagerSettingInterface;
import com.demo.Front.domain.dto.ManagerVo;
import com.demo.Front.domain.entity.Client;
import com.demo.Front.domain.entity.Manager;
import com.demo.Front.repository.jpaRepository.ClientRepository;
import com.demo.Front.repository.jpaRepository.ManagerRepository;
import com.demo.Front.repository.jpaRepository.ManagerSettingRepository;
import com.demo.KaKao.HttpCallService;
import com.demo.KaKao.entity.Token;
import com.demo.KaKao.repository.TokenRepository;
import com.demo.KaKao.service.KakaoApiService;
import com.demo.KaKao.service.TokenService;
import com.demo.KaKao.vo.KakaoFriendsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManagerSettingService extends HttpCallService {
    private final ManagerSettingRepository repository;
    private final KakaoApiService kakaoApiService;
    private final TokenRepository tokenRepository;
    private final ClientRepository clientRepository;
    private final ManagerRepository managerRepository;
    private final TokenService tokenService;
    private static final String REQUEST_FRIENDS_LIST = "https://kapi.kakao.com/v1/api/talk/friends";

    public ResponseEntity<?> getManager(){
        List<ManagerSettingInterface> entity = repository.findManager();
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    public ResponseEntity<?> getFriend(){
        Token token = tokenRepository.findByUserId("2439553072");
//        Map<String, Object> list = kakaoApiService.getFriendsList(token.getAccessToken(),token.getRefreshToken(),token.getUserId());
//        List<FriendVo> friend = (ArrayList<FriendVo>) list.get("friend");
//        return new ResponseEntity<>(friend,HttpStatus.OK);

        Map<String, Object> returnValue = new HashMap<>();
        try {
            HttpHeaders header = new HttpHeaders();
            header.set("Authorization","Bearer "+token.getAccessToken());
            HttpEntity<?> requestEntity = httpClientEntity(header, "");
            ResponseEntity<String> response = httpRequest(REQUEST_FRIENDS_LIST, HttpMethod.GET, requestEntity);
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray elements = jsonObject.getJSONArray("elements");
            ArrayList<FriendVo> obj = new ArrayList<>();
            for(int i = 0; i<elements.length(); i++) {
                JSONObject friendsInfo = (JSONObject) elements.get(i);
                obj.add(i, new FriendVo(friendsInfo.get("id").toString() ,friendsInfo.get("profile_nickname").toString(),friendsInfo.get("uuid").toString()));
            }
//            returnValue.put("token", token.getAccessToken());
            returnValue.put("friend", obj);
            return new ResponseEntity<>(returnValue,HttpStatus.OK);

        } catch (Exception e) {
            // 정확히는 401 Unauthorized  token 이 손상됐기 때문에 그에 따른 친구리스트가 불러와지지 않았으므로 null
            log.info("token are damaged");
            String renewalAccessToken = tokenService.renewalToken(token.getRefreshToken(), token.getUserId());
            HttpHeaders header = new HttpHeaders();
            header.set("Authorization","Bearer "+renewalAccessToken);
            HttpEntity<?> requestEntity = httpClientEntity(header, "");
            ResponseEntity<String> response = httpRequest(REQUEST_FRIENDS_LIST, HttpMethod.GET, requestEntity);
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray elements = jsonObject.getJSONArray("elements");
            ArrayList<FriendVo> obj = new ArrayList<>();
            for(int i = 0; i<elements.length(); i++) {
                JSONObject friendsInfo = (JSONObject) elements.get(i);
                obj.add(i, new FriendVo(friendsInfo.get("id").toString(),friendsInfo.get("profile_nickname").toString(),friendsInfo.get("uuid").toString()));
            }
//            returnValue.put("token", renewalAccessToken);
            returnValue.put("friend", obj);
            return new ResponseEntity<>(returnValue,HttpStatus.OK);
        }
    }

    public ResponseEntity<?> saveManager(List<ManagerVo> vo){
        for(ManagerVo templist : vo){
            Client client = clientRepository.findByClientName(templist.getClient_name()).orElseThrow();
            if(templist.getId()==null){
                Manager manager = new Manager();
                manager.setClientId(client.getId());
                manager.setManagerName(templist.getManager_name());
                manager.setPhoneNumber(templist.getPhone_number());
                manager.setKakaoNickname(templist.getKakao_nickname());
                manager.setKakaoUuid(templist.getKakao_uuid());
                manager.setKakaoId(templist.getKakao_id());
                manager.setKakaoFlag(false);
                managerRepository.save(manager);
            }
            else{
                Manager entity = managerRepository.findById(templist.getId()).orElseThrow();

                entity.setClientId(client.getId());
                entity.setManagerName(templist.getManager_name());
                entity.setPhoneNumber(templist.getPhone_number());
                entity.setKakaoNickname(templist.getKakao_nickname());
                entity.setKakaoId(templist.getKakao_id());
                entity.setKakaoUuid(templist.getKakao_uuid());
                managerRepository.save(entity);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> getClient(){
        List<Client> client = clientRepository.findAll();
        List<String> clientList = new ArrayList<>();
        for(Client temp : client){
            clientList.add(temp.getClientName());
        }
        return new ResponseEntity<>(clientList,HttpStatus.OK);
    }
}
