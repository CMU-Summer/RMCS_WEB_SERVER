package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.Vector;

public class GroupStruct implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private Vector<FamilyStruct> familyList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<FamilyStruct> getFamilyList() {
        return familyList;
    }

    public void setFamilyList(Vector<FamilyStruct> familyList) {
        this.familyList = familyList;
    }

}
