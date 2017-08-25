package org.cmu.rmcs.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class Led_field implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @JSONField(name="led_R")
    private int led_R;
    
    @JSONField(name="led_G")
    private int led_G;
    
    @JSONField(name="led_B")
    private int led_B;

    public int getLed_R() {
        return led_R;
    }

    public void setLed_R(int led_R) {
        this.led_R = led_R;
    }

    public int getLed_G() {
        return led_G;
    }

    public void setLed_G(int led_G) {
        this.led_G = led_G;
    }

    public int getLed_B() {
        return led_B;
    }

    public void setLed_B(int led_B) {
        this.led_B = led_B;
    }

}
