package com.demo.NS3.vo;

import lombok.Data;

@Data
public class EventData {
    public Long originId;
    public String deviceName;
    public String cameraName;
    public String trigger;
    public String snapId;
    public String eventType;
    public String personName;

}
