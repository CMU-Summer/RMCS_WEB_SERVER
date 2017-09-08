package org.cmu.rmcs.pojo;

import java.io.Serializable;

public class ModuleRecord implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String startTime;
    private String endTime;
    
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
   
}
