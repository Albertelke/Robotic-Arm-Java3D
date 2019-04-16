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
     private WakeupCondition wc = new WakeupOnElapsedTime(70);   //will update every 70 ms
    @Override
    public void initialize()
    {
        this.wakeupOn(wc);    
    }

    @Override
    public void processStimulus(Enumeration enmrtn) 
    {
        update();
    }
    SimpleBehavior(TransformGroup CylinderR)
         {
             this.CylinderR = CylinderR;
            
         }
    public void SetInitCylinder()
    {
        Transform3D setUp = new Transform3D();
        setUp.setTranslation(new Vector3f(0.0f,2.5f,0.f));
        CylinderR.setTransform(setUp);
    }
     private void update()
         {
             SetInitCylinder();
         }
}
