package com.demo.Front.service;

import com.demo.Front.domain.entity.GridData;
import com.demo.Front.repository.jpaRepository.GridDataRepository;
import com.demo.SMS.domain.entity.MessageTemplate;
import com.demo.SMS.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class MsgSettingService {
    private final GridDataRepository gridDataRepository;
    private final TemplateRepository templateRepository;

    public ResponseEntity<?> getMstSettingGrid(){
        List<GridData> entity = gridDataRepository.getGridData();
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    public ResponseEntity<?> save(List<String> data){
        for(String item : data){
            String[] items = item.split(",");
            Long manager_key = Long.valueOf(items[0]);
            Long device_key = Long.valueOf(items[1]);
            Long message_key = Long.valueOf(items[2]);
            Long policy_key = Long.valueOf(items[3]);
            String check = items[4];
            if(check.equals("true"))
                check="Y";
            else if (check.equals("false")) {
                check="N";
            }
            MessageTemplate entity = templateRepository.findByManagerIdAndDeviceIdAndMsgIdAndPolicyId(manager_key,device_key,message_key,policy_key).orElse(new MessageTemplate());
            if(entity.getId()!=null){
                entity.setCheckyn(check);

            }
            else{
                entity.setManagerId(manager_key);
                entity.setDeviceId(device_key);
                entity.setMsgId(message_key);
                entity.setPolicyId(policy_key);
                entity.setCheckyn(check);
            }
            templateRepository.save(entity);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
