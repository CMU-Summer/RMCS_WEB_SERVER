package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
@Service
public class GroupStruct implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @JSONField(name="name")
    private String name;
    
    @JSONField(name="familyList")
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
