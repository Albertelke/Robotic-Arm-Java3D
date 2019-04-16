/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cylinderarm;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.vecmath.Vector3f;

/**
 *
 * @author Albert
 */
public class SimpleBehavior extends Behavior 
{
    public TransformGroup CylinderR;
    public TransformGroup Handle;
    public TransformGroup Arm;
    public float handlepos=2.0f;
    public float Arm_Pos;
    //will store information about input, [0]=Left Arrow[1]=RightArrow,[2] up,[3] down[4]=<,[5]=>,
    public boolean[] IsKeyPressed = new boolean[6];
    public float CylinderR_Angle=0.f;
     private WakeupCondition wc = new WakeupOnElapsedTime(70);   //will update every 70 ms
  
    public void initialize()
    {
        this.wakeupOn(wc);    
    }

   @Override
    public void processStimulus(Enumeration criteria) 
    {
        update();
        this.wakeupOn(wc);
    }
    
    SimpleBehavior(TransformGroup CylinderR,TransformGroup Handle,TransformGroup Arm)
     {
             this.CylinderR = CylinderR;
             this.Handle = Handle;
             this.Arm = Arm;
            
     }
    public void CheckForRotation()
    {
        if(IsKeyPressed[0]) { CylinderR_Angle+=0.3f;}
        if(IsKeyPressed[1]) CylinderR_Angle-=0.3f;
    }
    
    public void CheckForArm()
    {
        if(IsKeyPressed[5] && Arm_Pos<=0.f ) Arm_Pos +=0.2f;
        if(IsKeyPressed[4] && Arm_Pos>=-2.4f  )Arm_Pos-=0.2f;
     
    }
    public void CheckForHandleMovement()
    {
        
        if(IsKeyPressed[2]&&handlepos<5.f) handlepos+=0.3f;
        if(IsKeyPressed[3]&&handlepos>1.25f)handlepos-=0.3f;
    }
    public void SetCylinder()
    {
        Transform3D setUp = new Transform3D();
        Transform3D Rot = new Transform3D();
        Transform3D Fin = new Transform3D();
        setUp.setTranslation(new Vector3f(0.0f,2.5f,0.f));
        Rot.rotY(CylinderR_Angle);
        Fin.mul(Rot,setUp);
        CylinderR.setTransform(Fin);
    }
    public void SetHandle()
    {
        Transform3D setUp = new Transform3D();
        Transform3D Rot = new Transform3D();
        Transform3D Fin = new Transform3D();
        setUp.setTranslation(new Vector3f(0.0f,handlepos,0.0f));
        Rot.rotY(CylinderR_Angle);
        Fin.mul(Rot,setUp);
        Handle.setTransform(Fin);
    }
    public void SetArm()
    {
        Transform3D setUp = new Transform3D();
        Transform3D Rot = new Transform3D();
        Transform3D Fin = new Transform3D();
        Transform3D YRot = new Transform3D();
        //move to position and rotate 90 degrees so that will be set in good starting position
        setUp.setTranslation(new Vector3f(0.0f,handlepos,1.5f+Arm_Pos));
        Rot.rotX(toRadians(90));    
        Fin.mul(setUp,Rot);
        //set rotation to be in line with Handle
        Rot = new Transform3D();
        Rot.rotY(CylinderR_Angle);
        Fin.mul(Rot,Fin);
   
        Arm.setTransform(Fin);
    }
     private void update()
         {
             CheckForRotation();
             CheckForHandleMovement();
             CheckForArm();
             SetCylinder();
             SetHandle();
             SetArm();
             
         }
}
