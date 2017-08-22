package org.cmu.rmcs.pojo;

import java.io.Serializable;

public class Actuator_field implements Serializable{
   /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private double position;
    private double velocity;
    private double torque;
    private double voltage;
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
