package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.Vector;

public class CommandGroupStruct implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String groupName;
    private Vector<String> familys;

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

    private Vector<String> names;
    private Vector<CommandStruct> fd;
    int cmd;
}
