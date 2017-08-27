package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
@Service
public class WS_feedback_module implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @JSONField(name="current")
    private   List<WS_feedback_value>  current = new ArrayList<>();
    @JSONField(name="position")
    private   List<WS_feedback_value>  position= new ArrayList<>();
    @JSONField(name="torque")
    private   List<WS_feedback_value>  torque= new ArrayList<>();
    @JSONField(name="voltage")
    private   List<WS_feedback_value>  voltage= new ArrayList<>();
    @JSONField(name="velocity")
    private   List<WS_feedback_value>  velocity= new ArrayList<>();
    @JSONField(name="led_fields")
    private  List<Led_field>  led_field =new ArrayList<>();
    
    public List<WS_feedback_value> getCurrent() {
        return current;
    }
    public void setCurrent(List<WS_feedback_value> current) {
        this.current = current;
    }
    public List<WS_feedback_value> getPosition() {
        return position;
    }
    public void setPosition(List<WS_feedback_value> position) {
        this.position = position;
    }
    public List<WS_feedback_value> getTorque() {
        return torque;
    }
    public void setTorque(List<WS_feedback_value> torque) {
        this.torque = torque;
    }
    public List<WS_feedback_value> getVoltage() {
        return voltage;
    }
    public void setVoltage(List<WS_feedback_value> voltage) {
        this.voltage = voltage;
    }
    public List<WS_feedback_value> getVelocity() {
        return velocity;
    }
    public void setVelocity(List<WS_feedback_value> velocity) {
        this.velocity = velocity;
    }
    public List<Led_field> getLed_field() {
        return led_field;
    }
    public void setLed_field(List<Led_field> led_field) {
        this.led_field = led_field;
    }
    
    
    
    
    
}
