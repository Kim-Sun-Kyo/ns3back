package com.demo.NS3.service;

import com.demo.NS3.entity.*;
import com.demo.NS3.repository.*;
import com.demo.NS3.vo.ApiBodyVo;
import com.demo.NS3.vo.ApiFaceVo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Async;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiService {
    private final BodyRepository bodyRepository;
    private final FaceRepository faceRepository;
    private final StdHeartbeatRepository heartbeatRepository;
    public ResponseEntity<?> saveData(MultipartHttpServletRequest request)throws Exception{

        log.info("======== Data push ===========");
        MultipartFile snap = request.getFile("snap");
        String json = request.getParameter("json");
        JsonNode parent = new ObjectMapper().readTree(json);
        String msgId = String.valueOf(parent.findValue("msg_id"));   //774(snap) or 775(heartbeat)
        ObjectMapper ob = new ObjectMapper();
        String path = "C:\\CloudSafety\\img\\";
        if(msgId.equals("774")){
            if(parent.findValue("face_attr")!=null){ //Face
                System.out.println();
                System.out.println("FACE Data Pushed");
                String data = String.valueOf(parent.findValue("data"));
                ApiFaceVo vo = ob.readValue(data,ApiFaceVo.class);
                String deviceID = vo.getDevice_id();
                String cameraID = vo.getCamera_name();
                String trigger = vo.getTrigger();
                String date = trigger.split(" ")[0];
                String path1 = path+File.separator+deviceID+File.separator+cameraID+File.separator+date+File.separator+"face_event"+File.separator;
                String filename = vo.getSnap_id()+".jpg";
                File imagePath = new File(path1);
                if(imagePath.exists()==false){ imagePath.mkdirs(); }
                try{
                    snap.transferTo(new File(imagePath,filename));
                }catch (NullPointerException e){
                    System.out.println("Snap not exist");
                }

                FaceEntity entity = new FaceEntity(vo);
                entity.setLocalPath(imagePath+File.separator+filename);

                faceRepository.save(entity);

            }
            else {                                            //Body
                String data = String.valueOf(parent.findValue("data"));
                ApiBodyVo vo = ob.readValue(data, ApiBodyVo.class);

                if( !vo.getEvents_type().isEmpty()){
                    log.info("Event Data Pushed");

                    String deviceID = vo.getDevice_id();
                    String cameraID = vo.getCamera_name();
                    String trigger = vo.getTrigger();
                    String date = trigger.split(" ")[0];
                    String path1 = path+File.separator+deviceID+File.separator+cameraID+File.separator+date+File.separator+"body_event"+File.separator;
                    String filename = vo.getSnap_id()+".jpg";
                    File imagePath = new File(path1);
                    if(imagePath.exists()==false){ imagePath.mkdirs(); }
                    try{
                        snap.transferTo(new File(imagePath,filename));
                    }catch (NullPointerException e){
                        System.out.println("Snap not exist");
                    }
                    if(vo.getEvents_type().contains(",")){
                        log.info("222222222 EVENTS TYPE 222222222222");
                        log.info(vo.getTrigger());
                        String[] temp = vo.getEvents_type().split(",");
                        for(int i=0;i< temp.length;i++){
                            BodyEntity entity = new BodyEntity(vo);
                            entity.setSnap_feat(imagePath+File.separator+filename);
                            entity.setEvents_type(temp[i]);
                            entity.setEvents_type_org(vo.getEvents_type());
                            entity.setSendflag("TEST");
                            bodyRepository.save(entity);
                        }
                    }
                    else{
                        BodyEntity entity = new BodyEntity(vo);
                        entity.setSnap_feat(imagePath+File.separator+filename);
                        entity.setSendflag("TEST");

                        bodyRepository.save(entity);
                    }
                }
            }
        }
        else{
            log.info("HeartBeat");
            String deviceId = String.valueOf(parent.findValue("device_id")).replaceAll("\"","");
            String trigger = String.valueOf(parent.findValue("trigger")).replaceAll("\"","");
            log.info(deviceId+"   "+trigger);
            StdHeartbeatEntity entity = heartbeatRepository.findByDeviceId(deviceId).orElse(new StdHeartbeatEntity());
            if(entity!=null){
                entity.setDeviceId(deviceId);
                entity.setTrigger(trigger);
                entity.setOnoff("Y");
                heartbeatRepository.save(entity);
            }

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public @ResponseBody byte[] getImage(String snapid) {
        byte[] imageByte=null;
        String pathdata="";
        Optional<BodyEntity> entity = bodyRepository.findBySnapid(snapid);
        if(entity.isEmpty()){
            FaceEntity entity1 = faceRepository.findBySnapId(snapid).orElseThrow();
            pathdata = entity1.getLocalPath();
        }else{
            pathdata = entity.get().getSnap_feat();
        }
        try{
            InputStream is = new FileInputStream(pathdata);
            imageByte = is.readAllBytes();
        }catch (IOException e){

        }
        return imageByte;
    }


}
