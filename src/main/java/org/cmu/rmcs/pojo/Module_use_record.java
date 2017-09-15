package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Module_use_record implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String family;
    private String name;
    private long startTime;
    private long endTime;

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
