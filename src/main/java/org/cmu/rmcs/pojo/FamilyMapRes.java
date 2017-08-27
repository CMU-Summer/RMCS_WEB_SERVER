package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class FamilyMapRes implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @JSONField(name="list")
    private List<FamilyRes> list =new ArrayList<FamilyRes>();

    public List<FamilyRes> getList() {
        return list;
    }

    public void setList(List<FamilyRes> list) {
        this.list = list;
    }
    
    
}
