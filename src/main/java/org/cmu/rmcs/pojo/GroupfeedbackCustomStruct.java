package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
@Service
public class GroupfeedbackCustomStruct implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @JSONField(name = "positionsVec")
    private Vector<Double> positionsVec;
    
    @JSONField(name = "velocitysVec")
    private Vector<Double> velocitysVec;
    
    @JSONField(name = "torqueVec")
    private Vector<Double> torqueVec;
    
    @JSONField(name = "groupName")
    private String groupName;
    
    @JSONField(name = "timeStamp")
    private long timeStamp;
    
    @JSONField(name = "moduleFeedBackVec")
    private Vector<FeedbackCustomStruct> moduleFeedBackVec;

    public Vector<Double> getPositionsVec() {
        return positionsVec;
    }

    public void setPositionsVec(Vector<Double> positionsVec) {
        this.positionsVec = positionsVec;
    }

    public Vector<Double> getVelocitysVec() {
        return velocitysVec;
    }

    public void setVelocitysVec(Vector<Double> velocitysVec) {
        this.velocitysVec = velocitysVec;
    }

    public Vector<Double> getTorqueVec() {
        return torqueVec;
    }

    public void setTorqueVec(Vector<Double> torqueVec) {
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

    public Vector<FeedbackCustomStruct> getModuleFeedBackVec() {
        return moduleFeedBackVec;
    }

    public void setModuleFeedBackVec(
            Vector<FeedbackCustomStruct> moduleFeedBackVec) {
        this.moduleFeedBackVec = moduleFeedBackVec;
    }

}
