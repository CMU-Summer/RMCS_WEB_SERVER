package org.cmu.rmcs.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class ModuleRecord_cache implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @JSONField(name="startTime")
    private long startTime;
    @JSONField(name="endTime")
    private long endTime;
    
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public long getEndTime() {
        return endTime;
    }
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
