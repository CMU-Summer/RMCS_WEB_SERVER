package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.List;

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
    private List<String> familys;
    
    @JSONField(name="names")
    private List<String> names;
    
    @JSONField(name="fd")
    private List<CommandStruct> fd;
    int cmd;
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getFamilys() {
        return familys;
    }

    public void setFamilys(List<String> familys) {
        this.familys = familys;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<CommandStruct> getFd() {
        return fd;
    }

    public void setFd(List<CommandStruct> fd) {
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
