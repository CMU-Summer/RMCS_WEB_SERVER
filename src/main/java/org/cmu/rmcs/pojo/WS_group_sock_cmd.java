package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.cmu.rmcs.util.ContantUtil;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
@Service
public class WS_group_sock_cmd  implements Serializable {
    /**
     * 
     */
    //不需要命令字段，因为每个list有东西，说明就是要执行的
    private static final long serialVersionUID = 1L;
    @JSONField(name="deleteList")
    private List<String> deleteList =new ArrayList<>(); // 如果命令使1 的话，这个字段表示了要删除的string
    @JSONField(name="addList")                             // 类型的group名字的列表
    private List<WS_group_info> addList=new ArrayList<>();// 如果命令使 0 的话，这个字段表示了要添加的group列表
    @JSONField(name="stateList")
    private List<WS_group_info> stateList=new ArrayList<>();// 如果命令使 2 的话，这个字段表示状态变更的group的列表
    
   

    public List<String> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(List<String> deleteList) {
        this.deleteList = deleteList;
    }

    public List<WS_group_info> getAddList() {
        return addList;
    }

    public void setAddList(List<WS_group_info> addList) {
        this.addList = addList;
    }

    public List<WS_group_info> getStateList() {
        return stateList;
    }

    public void setStateList(List<WS_group_info> stateList) {
        this.stateList = stateList;
    }

    @SuppressWarnings("unchecked")
    public WS_group_sock_cmd packageCmd(int sign,List a){
        //sign
        if(sign == ContantUtil.SIGN_PACKAGE_GROUP_ADD){
            //增加了group的命令，list应该转换为
            this.addList=(List<WS_group_info>)a;
         
            return this;
        }else if(sign == ContantUtil.SIGN_PACKAGE_GROUP_DEC){
            //group减少
            this.deleteList=(List<String>)a;
     
            return this;
        }else if(sign == ContantUtil.SIGN_PACKAGE_GROUP_STATE){
            //状态变化的打包命令
            this.stateList=(List<WS_group_info>)a;
    
            return this;
        }else {
            //什么都不做
            return this;
            
        }
        
    }
}
