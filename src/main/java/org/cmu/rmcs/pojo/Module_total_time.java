package org.cmu.rmcs.pojo;

import java.io.Serializable;

public class Module_total_time implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String family;
    private String name;
    private long total_time_second;

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

    public long getTotal_time_second() {
        return total_time_second;
    }

    public void setTotal_time_second(long total_time_second) {
        this.total_time_second = total_time_second;
    }

}
