package org.cmu.rmcs.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
@Service
public class WS_feedback_group implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @JSONField(name="groupName")
    private String groupName;
    
    @JSONField(name="fdList")
    private List<WS_feedback_module> fdList=new ArrayList<>();

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<WS_feedback_module> getFdList() {
        return fdList;
    }

    public void setFdList(List<WS_feedback_module> fdList) {
        this.fdList = fdList;
    }
    @SuppressWarnings("unchecked")
    public WS_feedback_group packageGroupFeedback(List<GroupfeedbackCustomStruct> gfdCustomStructList){
        this.groupName=gfdCustomStructList.get(0).getGroupName();
        
        List<List<WS_feedback_value>> voltageListList = new ArrayList<>();
        List<List<WS_feedback_value>> positionListList= new ArrayList<>();
        List<List<WS_feedback_value>> torqueListList= new ArrayList<>();
        List<List<WS_feedback_value>> currentListList= new ArrayList<>();
        List<List<WS_feedback_value>> velocityListList= new ArrayList<>();
        List<List<Led_field>> led_fieldss=new ArrayList<>();
        for(GroupfeedbackCustomStruct gfd:gfdCustomStructList){
            long timeStamp= gfd.getTimeStamp();
            List<WS_feedback_value>  voltageList=new ArrayList<WS_feedback_value>();
            List<WS_feedback_value>  positionList=new ArrayList<WS_feedback_value>();
            List<WS_feedback_value>  torqueList=new ArrayList<WS_feedback_value>();
            List<WS_feedback_value>  currentList=new ArrayList<WS_feedback_value>();
            List<WS_feedback_value>  velocityList=new ArrayList<WS_feedback_value>();
            List<Led_field> led_fields=new ArrayList<Led_field>();
            for(FeedbackCustomStruct fd:gfd.getModuleFeedBackVec()){
                WS_feedback_value voltage=new WS_feedback_value();
                voltage.setTimeStamp(timeStamp);
                voltage.setValue(fd.getActuator_field().getVoltage());
                
                WS_feedback_value position=new WS_feedback_value();
                position.setTimeStamp(timeStamp);
                position.setValue(fd.getActuator_field().getPosition());
                
                WS_feedback_value torque=new WS_feedback_value();
                torque.setTimeStamp(timeStamp);
                torque.setValue(fd.getActuator_field().getTorque());
                
                WS_feedback_value current=new WS_feedback_value();
                current.setTimeStamp(timeStamp);
                current.setValue(fd.getActuator_field().getMotorCurrent());
                
                WS_feedback_value velocity=new WS_feedback_value();
                velocity.setTimeStamp(timeStamp);
                velocity.setValue(fd.getActuator_field().getVelocity());
                
                voltageList.add(voltage);
                positionList.add(position);
                torqueList.add(torque);
                currentList.add(current);
                velocityList.add(velocity);
                Led_field led_field=new Led_field();
                led_field.setLed_B(fd.getLed_field().getLed_B());
                led_field.setLed_R(fd.getLed_field().getLed_R());
                led_field.setLed_G(fd.getLed_field().getLed_G());
                led_fields.add(led_field);
            }
      
            
            voltageListList.add(voltageList);
            velocityListList.add(velocityList);
            torqueListList.add(torqueList);
            positionListList.add(positionList);
            currentListList.add(currentList);
            led_fieldss.add(led_fields);
            
        }
      for(int i=0;i<velocityListList.size();i++){
          WS_feedback_module ws_feedback_module=new WS_feedback_module();
       
          for(int j=0;j<velocityListList.get(i).size();j++){
              ws_feedback_module.getPosition().add(positionListList.get(i).get(j));
              ws_feedback_module.getVelocity().add(velocityListList.get(i).get(j));
              ws_feedback_module.getVoltage().add(voltageListList.get(i).get(j));
              ws_feedback_module.getCurrent().add(currentListList.get(i).get(j));
              ws_feedback_module.getTorque().add(torqueListList.get(i).get(j));
              ws_feedback_module.getLed_field().add(led_fieldss.get(i).get(j));
              
          }
          this.fdList.add(ws_feedback_module);
          
          
      }
        return this;
    }
}
