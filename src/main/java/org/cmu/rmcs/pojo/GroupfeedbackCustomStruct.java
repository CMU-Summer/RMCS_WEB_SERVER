package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
@Service
public class GroupfeedbackCustomStruct implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @JSONField(name = "positionsVec")
    private List<Double> positionsVec;
    
    @JSONField(name = "velocitysVec")
    private List<Double> velocitysVec;
    
    @JSONField(name = "torqueVec")
    private List<Double> torqueVec;
    
    @JSONField(name = "groupName")
    private String groupName;
    
    @JSONField(name = "timeStamp")
    private long timeStamp;
    
    @JSONField(name = "moduleFeedBackVec")
    private List<FeedbackCustomStruct> moduleFeedBackVec;

    public List<Double> getPositionsVec() {
        return positionsVec;
    }

    public void setPositionsVec(List<Double> positionsVec) {
        this.positionsVec = positionsVec;
    }

    public List<Double> getVelocitysVec() {
        return velocitysVec;
    }

    public void setVelocitysVec(List<Double> velocitysVec) {
        this.velocitysVec = velocitysVec;
    }

    public List<Double> getTorqueVec() {
        return torqueVec;
    }

    public void setTorqueVec(List<Double> torqueVec) {
        this.torqueVec = torqueVec;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<FeedbackCustomStruct> getModuleFeedBackVec() {
        return moduleFeedBackVec;
    }

    public void setModuleFeedBackVec(
            List<FeedbackCustomStruct> moduleFeedBackVec) {
        this.moduleFeedBackVec = moduleFeedBackVec;
    }

}
