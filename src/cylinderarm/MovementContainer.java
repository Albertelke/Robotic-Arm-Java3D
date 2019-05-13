/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cylinderarm;

/**
 *
 * @author Albert
 */
public class MovementContainer 
{
    public float handlepos;
    public float Arm_Pos;
    public float Gripper_PosL;
    public float Gripper_PosR;
    float CylinderR_Angle;
    
    MovementContainer(float handlepos,float Arm_Pos,float Gripper_PosL,float Gripper_PosR,float CylinderR_Angle)
    {
    this.handlepos=handlepos;
    this.Arm_Pos=Arm_Pos;
    this.Gripper_PosL=Gripper_PosL;
    this.Gripper_PosR=Gripper_PosR;
    this.CylinderR_Angle=CylinderR_Angle;
   
    }
}
