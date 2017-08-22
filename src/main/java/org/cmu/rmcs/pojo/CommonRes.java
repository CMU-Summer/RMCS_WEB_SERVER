package org.cmu.rmcs.pojo;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;

@Service
public class CommonRes implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @JSONField(name="result")
    private boolean isSucceed ; // 成功与否
    @JSONField(name="des")
    private String des ;//描述设置
    public boolean isSucceed() {
        return isSucceed;
    }
    public void setSucceed(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }
    public String getDes() {
        return des;
    }
    public void setDes(String des) {
        this.des = des;
    }
    

}
