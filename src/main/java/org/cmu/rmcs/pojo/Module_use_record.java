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
    private Timestamp startTime;
    private Timestamp endTime;

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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

}
