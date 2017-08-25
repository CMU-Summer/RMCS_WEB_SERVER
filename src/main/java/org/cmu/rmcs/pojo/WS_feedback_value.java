package org.cmu.rmcs.pojo;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
@Service
public class WS_feedback_value implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @JSONField(name="value")
    private  double value;
    @JSONField(name="timeStamp")
    private  long timeStamp;
    
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public long getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
