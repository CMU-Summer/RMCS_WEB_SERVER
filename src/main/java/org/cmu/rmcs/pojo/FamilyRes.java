package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class FamilyRes implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @JSONField(name="name")
    private String name =new String();
    
    @JSONField(name="nameList")
    private List<String> nameList=new ArrayList<String>();
    public List<String> getNameList() {
        return nameList;
    }
    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
