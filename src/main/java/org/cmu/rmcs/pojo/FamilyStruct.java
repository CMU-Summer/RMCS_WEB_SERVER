package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
@Service
public class FamilyStruct implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @JSONField(name="name")
    private String name;
    
    @JSONField(name="nameList")
    private List<NameStruct> nameList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NameStruct> getNameList() {
        return nameList;
    }

    public void setNameList(List<NameStruct> nameList) {
        this.nameList = nameList;
    }

}
