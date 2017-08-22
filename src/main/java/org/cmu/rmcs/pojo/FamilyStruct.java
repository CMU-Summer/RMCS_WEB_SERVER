package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.Vector;

public class FamilyStruct implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private Vector<NameStruct> nameList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<NameStruct> getNameList() {
        return nameList;
    }

    public void setNameList(Vector<NameStruct> nameList) {
        this.nameList = nameList;
    }

}
