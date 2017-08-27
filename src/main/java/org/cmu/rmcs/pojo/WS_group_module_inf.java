package org.cmu.rmcs.pojo;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
@Service
public class WS_group_module_inf implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 与g_sock交互中的group里面module的格式
    @JSONField(name="family")
    private String family = new String();
    @JSONField(name="name")
    private String name =new String();
    @JSONField(name="connected")
    private boolean connected =false;// 是否连接

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

}
