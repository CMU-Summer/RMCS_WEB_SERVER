package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
@Service
public class CommandGroupStruct implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @JSONField(name="groupName")
    private String groupName;
    
    @JSONField(name="familys")
    private Vector<String> familys;
    
    @JSONField(name="names")
    private Vector<String> names;
    
    @JSONField(name="fd")
    private Vector<CommandStruct> fd;
    int cmd;
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Vector<String> getFamilys() {
        return familys;
    }

    public void setFamilys(Vector<String> familys) {
        this.familys = familys;
    }

    public Vector<String> getNames() {
        return names;
    }

    public void setNames(Vector<String> names) {
        this.names = names;
    }

    public Vector<CommandStruct> getFd() {
        return fd;
    }

    public void setFd(Vector<CommandStruct> fd) {
        this.fd = fd;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    
}
