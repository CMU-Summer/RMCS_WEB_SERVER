package org.cmu.rmcs.pojo;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
@Service
public class FeedbackCustomStruct {
    @JSONField(name="led_field")
    private Led_field led_field;
    
    @JSONField(name="actuator_field")
    private Actuator_field actuator_field;

    public Led_field getLed_field() {
        return led_field;
    }

    public void setLed_field(Led_field led_field) {
        this.led_field = led_field;
    }

    public Actuator_field getActuator_field() {
        return actuator_field;
    }

    public void setActuator_field(Actuator_field actuator_field) {
        this.actuator_field = actuator_field;
    }

}
