package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.List;

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
    private List<FamilyStruct> familyList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FamilyStruct> getFamilyList() {
        return familyList;
    }

    public void setFamilyList(List<FamilyStruct> familyList) {
        this.familyList = familyList;
    }

}
