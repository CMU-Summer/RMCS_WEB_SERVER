package org.cmu.rmcs.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class NameStruct implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @JSONField(name="name")
    private String name;
    
    @JSONField(name="connected")
    private int connected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConnected() {
        return connected;
    }

    public void setConnected(int connected) {
        this.connected = connected;
    }

}
