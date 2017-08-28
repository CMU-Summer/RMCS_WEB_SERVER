package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
@Service
public class WS_group_info implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 与g_sock交互中，group的信息
    @JSONField(name="groupName")
    private String groupName =new String();
    @JSONField(name="modules")
    private List<WS_group_module_inf> modules = new ArrayList<WS_group_module_inf>();
    
    public WS_group_info() {
        
        // TODO Auto-generated constructor stub
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<WS_group_module_inf> getModules() {
        return modules;
    }

    public void setModules(List<WS_group_module_inf> modules) {
        this.modules = modules;
    }
    public void parseGroupStruct(GroupStruct gStruct){
        this.groupName=gStruct.getName();
        for(FamilyStruct fStruct:gStruct.getFamilyList()){
             String familyName=fStruct.getName();
             for(NameStruct nameStruct:fStruct.getNameList()){
                 WS_group_module_inf ws_m_inf =new WS_group_module_inf();
                 ws_m_inf.setFamily(familyName);
                 ws_m_inf.setName(nameStruct.getName());
                 ws_m_inf.setConnected(nameStruct.getConnected() == 1?true:false);
                 this.modules.add(ws_m_inf);//加进去
             }
           
        }
        
        
    }

}
