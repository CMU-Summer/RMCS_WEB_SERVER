package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class WS_fd_cmd implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @JSONField(name="glist")
    private List<WS_feedback_group> glist=new ArrayList<WS_feedback_group>();
    public List<WS_feedback_group> getGlist() {
        return glist;
    }
    public void setGlist(List<WS_feedback_group> glist) {
        this.glist = glist;
    }
    
    public WS_fd_cmd packageFd_cmd(List<WS_feedback_group> glist){
        this.glist=glist;
        
        return this;
    }
    
}
