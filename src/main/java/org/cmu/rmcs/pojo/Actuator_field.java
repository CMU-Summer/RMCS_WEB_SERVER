package org.cmu.rmcs.pojo;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
@Service
public class Actuator_field implements Serializable{
   /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @JSONField(name="position")
    private double position;
    
    @JSONField(name="velocity")
    private double velocity;
    
    @JSONField(name="torque")
    private double torque;
    
    @JSONField(name="voltage")
    private double voltage;
    
    @JSONField(name="motorCurrent")
    private double motorCurrent;
public double getPosition() {
    return position;
}
public void setPosition(double position) {
    this.position = position;
}
public double getVelocity() {
    return velocity;
}
public void setVelocity(double velocity) {
    this.velocity = velocity;
}
public double getTorque() {
    return torque;
}
public void setTorque(double torque) {
    this.torque = torque;
}
public double getVoltage() {
    return voltage;
}
public void setVoltage(double voltage) {
    this.voltage = voltage;
}
public double getMotorCurrent() {
    return motorCurrent;
}
public void setMotorCurrent(double motorCurrent) {
    this.motorCurrent = motorCurrent;
}
   

}
