package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModuleInfo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String family="";
    private String name="";
    private boolean isSucceed=false;
    private String des="";
    private String totalTime="";
    private List<ModuleRecord> moduleRecords=new ArrayList<ModuleRecord>();

    public String getName() {
        return name;
    }
    public String getDes() {
        return des;
    }
    public void setDes(String des) {
        this.des = des;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isSucceed() {
        return isSucceed;
    }
    public void setSucceed(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public String getFamily() {
        return family;
    }
    public void setFamily(String family) {
        this.family = family;
    }
    public String getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
    public List<ModuleRecord> getModuleRecords() {
        return moduleRecords;
    }
    public void setModuleRecords(List<ModuleRecord> moduleRecords) {
        this.moduleRecords = moduleRecords;
    }
    
}
