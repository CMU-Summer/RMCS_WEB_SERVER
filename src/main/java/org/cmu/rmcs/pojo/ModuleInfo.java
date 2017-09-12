package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class ModuleInfo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @JSONField(name="family")
    private String family="";
    @JSONField(name="name")
    private String name="";
    @JSONField(name="isSucceed")
    private boolean isSucceed=false;
    @JSONField(name="des")
    private String des="";
    @JSONField(name="totalTime")
    private String totalTime="";
    @JSONField(name="moduleRecords")
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
