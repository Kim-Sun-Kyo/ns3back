package com.demo.Front.service;

import com.demo.Front.domain.dto.CardInterface;
import com.demo.Front.domain.dto.CardVo;
import com.demo.Front.repository.jpaRepository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    public ResponseEntity<?> getCard(String clientName, String deviceName, String cameraName, String snapId){

        List<CardInterface> entity = cardRepository.findCard(snapId, clientName,deviceName,cameraName);
        List<CardVo> vo = new ArrayList<>();

        for(CardInterface list : entity){
            CardVo tempvo = new CardVo();

            tempvo.setClient(list.getClient());
            tempvo.setDevice_name(list.getDevice_name());
            tempvo.setCamera_name(list.getCamera_name());
            tempvo.setEvent_type(list.getEvent_type());
            tempvo.setAdmin_name(list.getAdmin_name());
            tempvo.setManager_name(list.getManager_name());
            tempvo.setMsg_type(list.getMsg_type());
            tempvo.setSend_time(list.getSend_time());
            tempvo.setTrigger(list.getTrigger());
            tempvo.setSnap(list.getSnap());
            tempvo.setPerson_name(list.getPerson_name());
            vo.add(tempvo);
        }
        return new ResponseEntity<>(vo,HttpStatus.OK);
    }


}

