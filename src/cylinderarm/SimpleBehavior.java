/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cylinderarm;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.vecmath.Vector3f;
import sun.audio.AudioPlayer;

/**
 *
 * @author Albert
 */
public class SimpleBehavior extends Behavior 
{
    public TransformGroup CylinderR;
    public TransformGroup Handle;
    public TransformGroup Arm;
    public TransformGroup GripperBase;
    public TransformGroup GripperLeft;
    public TransformGroup GripperRight;
    public TransformGroup TestObj;
    public float handlepos=2.0f;
    public float Arm_Pos;
    public float Gripper_PosL=-0.4f;
    public float Gripper_PosR=0.4f;
    public float TestObj_Angle=0.0f;
    public float TestObj_Pos=2.0f;
    public float TestObj_PosY=2.0f;
    public float TestObj_Vel=0.0f;
    public float CylinderR_Angle=0.f;
    private int iterator=0;
    private boolean IsDropped=false;
    public ArrayList <MovementContainer> Moves = new ArrayList<MovementContainer>();
    //will store information about input, [0]=Left Arrow[1]=RightArrow,[2] up,[3] down[4]=<,[5]=>,[6] k,[7] l,
    public boolean[] IsKeyPressed = new boolean[8];
    //will contain information whether should sava position for repeatable movement
    public boolean IsGettingInput=false;
    public boolean IsSetToGo=false;
     private WakeupCondition wc = new WakeupOnElapsedTime(20);   //will update every N ms
    
  
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
    
    SimpleBehavior(TransformGroup CylinderR,TransformGroup Handle,TransformGroup Arm,TransformGroup GripperBase,TransformGroup GripperLeft,TransformGroup GripperRight,TransformGroup TestObj)
     {
             this.CylinderR = CylinderR;
             this.Handle = Handle;
             this.Arm = Arm;
             this.GripperBase= GripperBase;
             this.GripperLeft=GripperLeft;
             this.GripperRight=GripperRight;
             this.TestObj=TestObj;
            
     }
    public void CheckForRotation()
    {
        if(IsKeyPressed[0]) { CylinderR_Angle+=0.01f;}
        if(IsKeyPressed[1]) CylinderR_Angle-=0.01f;
        if(abs(CylinderR_Angle)>6.28) CylinderR_Angle=0.0f;
       
    }
    
    public void CheckForArm()
    {
        if(IsKeyPressed[5] && Arm_Pos<=0.f ) Arm_Pos +=0.05f;
        if(IsKeyPressed[4] && Arm_Pos>=-2.4f  )Arm_Pos-=0.05f;
     
    }
    public void CheckForHandleMovement()
    {
        
        if(IsKeyPressed[2]&&handlepos<5.f) handlepos+=0.1f;
        if(IsKeyPressed[3]&&handlepos>1.05f)handlepos-=0.1f;
    }
    public void CheckForGripperMove()
    {
        
        if(IsKeyPressed[6]&&Gripper_PosL<-0.2f) {Gripper_PosL+=0.005f; Gripper_PosR-=0.005f;}
        if(IsKeyPressed[7]&&Gripper_PosL>-0.4f) {Gripper_PosL-=0.005f; Gripper_PosR+=0.005f;}
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
    public void SetPart()
    {
        Transform3D Scale=new Transform3D();
        Scale.setScale(1.f);
        
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
    public void SetGripperBase()
    {
        Transform3D setUp = new Transform3D();
        Transform3D Rot = new Transform3D();
        Transform3D Fin = new Transform3D();
        setUp.setTranslation(new Vector3f(0.0f,handlepos,1.5f+Arm_Pos+1.6f));
        Rot.rotY(CylinderR_Angle);
        Fin.mul(Rot,setUp);
        
        GripperBase.setTransform(Fin);
    }
    public void PlaySound()
    {
        try
        {
            AudioPlayer.player.start(new FileInputStream("gfx/bum.wav"));
        } catch (FileNotFoundException fnfe){System.out.println("cant read file");};
         
    }
    public void SetGripperLeft()
    {
        Transform3D setUp = new Transform3D();
        Transform3D Rot = new Transform3D();
        Transform3D Fin = new Transform3D();
        setUp.setTranslation(new Vector3f(Gripper_PosL,handlepos,1.5f+Arm_Pos+2.1f));
        Rot.rotY(CylinderR_Angle);
        Fin.mul(Rot,setUp);
        
        GripperLeft.setTransform(Fin);
        
    }
    public void SetGripperRight()
    {
        Transform3D setUp = new Transform3D();
        Transform3D Rot = new Transform3D();
        Transform3D Fin = new Transform3D();
        setUp.setTranslation(new Vector3f(Gripper_PosR,handlepos,1.5f+Arm_Pos+2.1f));
        Rot.rotY(CylinderR_Angle);
        Fin.mul(Rot,setUp);
        
        GripperRight.setTransform(Fin);
        
    }
    public void setImput()
    {
        
    }
    public void SetTestObj()
    {
        Transform3D setUp = new Transform3D();
        Transform3D Rot = new Transform3D();
        Transform3D Fin = new Transform3D();
        //If test object is catch
        if(IsInRange())
        {
        setUp.setTranslation(new Vector3f(0.0f,handlepos,1.5f+Arm_Pos+2.0f));
        Rot.rotY(CylinderR_Angle);
        Fin.mul(Rot,setUp);
        TestObj_Pos=1.5f+Arm_Pos+2.0f;
        TestObj_Angle = CylinderR_Angle;
        TestObj_PosY=handlepos;
        TestObj.setTransform(Fin);
        TestObj_Vel=0.0f;
            
        }
        //If test object isn't catch
        else
        {
          if(abs(TestObj_Angle)>6.25f) TestObj_Angle=0.0f;
        setUp.setTranslation(new Vector3f(0.f,TestObj_PosY,TestObj_Pos));
        Rot.rotY(TestObj_Angle);
        Fin.mul(Rot,setUp);
        //Setting gravity force
        if(TestObj_PosY<=0.95f)
        {
         
            TestObj_Vel=0.f;
            if(IsDropped)
            {
               PlaySound();
               IsDropped=false;

                
            }
        }
        else 
        {
            IsDropped=true;
            TestObj_Vel-=0.007f;
            TestObj_PosY+=TestObj_Vel;
        }
        TestObj.setTransform(Fin);
        }
       
        
    }
    //this method check if test objects is in range to be catch by gripper
    public boolean IsInRange()
    {
       
       if (CylinderR_Angle>=0.0f&&CylinderR_Angle >= TestObj_Angle-0.07f && CylinderR_Angle <= TestObj_Angle+0.07f
          &&abs(Gripper_PosR)<0.36f&&handlepos>=TestObj_PosY-0.3f&&handlepos<=TestObj_PosY+0.3f&&TestObj_Pos>=1.5f+Arm_Pos+1.7f&&TestObj_Pos<=1.5f+Arm_Pos+2.3f)
               return true; 
       else if(CylinderR_Angle<= TestObj_Angle+0.07f && CylinderR_Angle>= TestObj_Angle-0.07f&&abs(Gripper_PosR)<0.36f&&handlepos>=TestObj_PosY-0.3f
               &&handlepos<=TestObj_PosY+0.3f&&TestObj_Pos>=1.5f+Arm_Pos+1.7f&&TestObj_Pos<=1.5f+Arm_Pos+2.3f)
                {
                   return true;          
                }    
       return false;
    }
    
    private void setInput()
    {
        Moves.add(new MovementContainer(handlepos,Arm_Pos,Gripper_PosL,Gripper_PosR,CylinderR_Angle));
  
    }
     private void update()
         {
             //checking input data
             CheckForRotation();
             CheckForHandleMovement();
             CheckForArm();
             CheckForGripperMove();
             if(IsGettingInput)
             setInput();
             //setting posoition of robotic arm components
             if(IsSetToGo && (IsGettingInput==false))
             {
                 if(iterator<Moves.size())
                 {
                    handlepos=Moves.get(iterator).handlepos;
                    Arm_Pos=Moves.get(iterator).Arm_Pos;
                     Gripper_PosL=Moves.get(iterator).Gripper_PosL;
                     Gripper_PosR=Moves.get(iterator).Gripper_PosR;
                     CylinderR_Angle=Moves.get(iterator).CylinderR_Angle;
                     
                 }
                 else iterator=0;
                 iterator++;
                 
             }
             
             
             SetCylinder();
             SetHandle();
             SetArm();
             SetGripperBase();
             SetGripperLeft();
             SetGripperRight();
                     
             
            
             //setting position of test objects
             SetTestObj();
             
            
         }
}
