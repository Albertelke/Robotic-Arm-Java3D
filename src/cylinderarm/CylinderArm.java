/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cylinderarm;
import com.sun.j3d.loaders.Scene; 
import com.sun.j3d.loaders.objectfile.ObjectFile; 
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.LineArray;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author Albert, Michal
 */
public class CylinderArm  extends Applet implements KeyListener, ActionListener
{
    SimpleBehavior ArmBehavior;
    Appearance ap = new Appearance();
    Appearance ap_testObj = new Appearance();
    Appearance ap_Box = new Appearance();
    Color3f TestCol = new Color3f(0.941f, 0.941f, 0.937f);
    Color3f ultramaryna = new Color3f(0.227f, 0.909f, 0.949f); //
    Color3f black = new Color3f(1.0f,1.0f,1.0f);
    private Button test = new Button("Set");
    // creates button that will control getting inputs of repeatable movement
     private Button Start_Stop = new Button("Start");
     private Button GO = new Button("GO");
     Font font = new Font("SansSerif", Font.PLAIN,30);
    private TextField angleTextField = new TextField("Angle",6);
    Panel p = new Panel(new GridLayout(17,3,3,3));
    //KeyPressed, keyReleased ,keyTyped will allow handling inputs
    public void keyPressed(KeyEvent e) 
    {
        //sets true when pressed
       
           if (e.getKeyCode()==KeyEvent.VK_LEFT) {ArmBehavior.IsKeyPressed[0] = true;}   
           if (e.getKeyCode()==KeyEvent.VK_RIGHT) {ArmBehavior.IsKeyPressed[1] = true;}
           if (e.getKeyCode()==KeyEvent.VK_UP) {ArmBehavior.IsKeyPressed[2] = true;}   
           if (e.getKeyCode()==KeyEvent.VK_DOWN) {ArmBehavior.IsKeyPressed[3] = true;}
           if (e.getKeyCode()==KeyEvent.VK_COMMA) {ArmBehavior.IsKeyPressed[4] = true;}   
           if (e.getKeyCode()==KeyEvent.VK_PERIOD) {ArmBehavior.IsKeyPressed[5] = true;}
           if (e.getKeyCode()==KeyEvent.VK_K) {ArmBehavior.IsKeyPressed[6] = true;}   
           if (e.getKeyCode()==KeyEvent.VK_L) {ArmBehavior.IsKeyPressed[7] = true;}
        
    }
    
    public void keyReleased(KeyEvent e)
    {
        //sets false when Released
        if (e.getKeyCode()==KeyEvent.VK_LEFT) {ArmBehavior.IsKeyPressed[0] = false;}   
        if (e.getKeyCode()==KeyEvent.VK_RIGHT) {ArmBehavior.IsKeyPressed[1] = false;}
        if (e.getKeyCode()==KeyEvent.VK_UP) {ArmBehavior.IsKeyPressed[2] = false;}   
        if (e.getKeyCode()==KeyEvent.VK_DOWN) {ArmBehavior.IsKeyPressed[3] = false;}
        if (e.getKeyCode()==KeyEvent.VK_COMMA) {ArmBehavior.IsKeyPressed[4] = false;}   
        if (e.getKeyCode()==KeyEvent.VK_PERIOD) {ArmBehavior.IsKeyPressed[5] = false;}
         if (e.getKeyCode()==KeyEvent.VK_K) {ArmBehavior.IsKeyPressed[6] = false;}   
        if (e.getKeyCode()==KeyEvent.VK_L) {ArmBehavior.IsKeyPressed[7] = false;}
        
    }
     
    public void keyTyped(KeyEvent e)
    {
        
        
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==test)
        {
            //check Wheter String in TextField is numeric and set Angle of Robot arm to given value
            try {
        float d = Float.parseFloat(angleTextField.getText());
         ArmBehavior.CylinderR_Angle=(float)Math.toRadians(d);
        } catch (NumberFormatException | NullPointerException nfe) {}
            
        }
        if(e.getSource()==Start_Stop)
        {
            if(!ArmBehavior.IsGettingInput)
            {
                Start_Stop.setLabel("Stop");
                ArmBehavior.IsGettingInput=true;
            }
            
            else
            {
               Start_Stop.setLabel("Start");
               ArmBehavior.IsGettingInput=false;

            }
        }
        if(e.getSource()==GO)
        {
            ArmBehavior.IsSetToGo=true;
        }
        
    }
      
    public BranchGroup createSceneGraph()
      {
        // p.setLayout(null);
         // test.setLayout()
         p.setBackground(Color.blue);
         //angleTextField.setSize(80,80);
         p.add(test,BorderLayout.PAGE_END);
          angleTextField.setFont(font);
         p.add(angleTextField,BorderLayout.LINE_START);
         p.add(Start_Stop,BorderLayout.PAGE_START);
         p.add(GO);
         //test.setLocation(100,100);
        add("West" ,p);
        
         
         
         
          //Creating base node and adding others
            test.addActionListener(this);
            Start_Stop.addActionListener(this);
          
          BranchGroup objRoot = new BranchGroup();
          
          
          TransformGroup transBox = new TransformGroup();
          transBox.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
          TransformGroup CylinderR=new TransformGroup();
          CylinderR.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
          TransformGroup Handle=new TransformGroup();
          Handle.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
          TransformGroup Arm=new TransformGroup();
          Arm.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
          TransformGroup GripperBase = new TransformGroup();
          GripperBase.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
          TransformGroup GripperLeft = new TransformGroup();
          GripperLeft.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
           TransformGroup GripperRight = new TransformGroup();
          GripperRight.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
          TransformGroup TestObj = new TransformGroup();
          TestObj.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
          
          //Tutaj chciałem wrzucić do prgramu plik obj ale nie wyszło
          {
         // TransformGroup part = new TransformGroup();
         //part.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //   ObjectFile f = new ObjectFile();
          // Scene s = null;
           
   // try{
    //       s = f.load("gfx/chwytak.obj");
      //     part.addChild(s.getSceneGroup());
    //}catch(Exception e){System.exit(1);}
        //  objRoot.addChild(part);
      }
          ap.setMaterial(new Material( black,black,black,black,0.0f));
          ap_Box.setMaterial(new Material(black,black,black,black,0.0f));
          ap_testObj.setMaterial(new Material(black,black,black,black,0.0f));
          //Adds stable, base component
          transBox.addChild(new Box(0.5f,0.5f,0.5f,Box.GENERATE_TEXTURE_COORDS,ap_Box));
          //Adds Cylinder that can be rotate around own axis
          CylinderR.addChild(new Cylinder(0.25f,5.f,Cylinder.GENERATE_TEXTURE_COORDS,ap));
          //Adds simple texture to ap Appearance
          TextureLoader loader = new TextureLoader("gfx/silver.jpg",null);
          TextureLoader loaderBox = new TextureLoader("gfx/Metal_Black_Brushed.jpg",null);
          TextureLoader loadertest_Obj = new TextureLoader("gfx/test.png",null);
          //Adds handle that is attached to CylinderR
          Handle.addChild(new Box(0.5f,0.5f,0.5f,Box.GENERATE_TEXTURE_COORDS,ap_Box));
          //Adds Arm to robot
          Arm.addChild(new Cylinder(0.15f,3f,Cylinder.GENERATE_TEXTURE_COORDS,ap));
          //Adds GripperBase to roboot
          GripperBase.addChild(new Box(0.5f,0.5f,0.2f,Box.GENERATE_TEXTURE_COORDS,ap_Box));
          //Adds GripperLeft to robot
          GripperLeft.addChild(new Box(0.1f,0.5f,0.3f,Box.GENERATE_TEXTURE_COORDS,ap));
          //Adds GripperRight to robot
          GripperRight.addChild(new Box(0.1f,0.5f,0.3f,Box.GENERATE_TEXTURE_COORDS,ap));
           //Adds Test object that should be moved by robotic arm
          TestObj.addChild(new Sphere(0.23f,Sphere.GENERATE_TEXTURE_COORDS,ap_testObj));
          //loads texture image
          ImageComponent2D mImage = loader.getImage( );     
          Texture2D  tx2 = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, mImage.getWidth(), mImage.getHeight());
          tx2.setImage(0, mImage);
          ap.setTexture(tx2);
          ImageComponent2D BoxImage = loaderBox.getImage( );     
          Texture2D  tx3 = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, mImage.getWidth(), mImage.getHeight());
          tx3.setImage(0, BoxImage);
          ap_Box.setTexture(tx3);
          ImageComponent2D test_ObjImage = loadertest_Obj.getImage( );     
          Texture2D  tx4 = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, mImage.getWidth(), mImage.getHeight());
          tx4.setImage(0, test_ObjImage);
          ap_testObj.setTexture(tx4);
          //Lightning
          
          BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0f);
          AmbientLight alldirlight = new AmbientLight(ultramaryna);
          alldirlight.setInfluencingBounds(bounds);
          objRoot.addChild(alldirlight); // adds light in all direction 
          Color3f lightCol = new Color3f(1f, 1f, 1f);
          Vector3f lightDir = new Vector3f(0.0f, 0.0f, 0.0f);
          DirectionalLight light = new DirectionalLight (lightCol,lightDir);
          light.setInfluencingBounds(bounds);
          objRoot.addChild(light);// adds light in specific direction
          
          objRoot.addChild(transBox);
          objRoot.addChild(CylinderR);
          objRoot.addChild(Handle);
          objRoot.addChild(Arm);
          objRoot.addChild(GripperBase);
          objRoot.addChild(GripperLeft);
          objRoot.addChild(GripperRight);
          objRoot.addChild(TestObj);
          
          ArmBehavior = new SimpleBehavior(CylinderR,Handle,Arm,GripperBase,GripperLeft,GripperRight,TestObj);
          ArmBehavior.setSchedulingBounds(bounds);
          objRoot.addChild(ArmBehavior);
          
          objRoot.compile();
          return objRoot;
          
      }
    
     public CylinderArm()
     {
        
         GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration(); 
         setLayout(new BorderLayout()); 
         //tworzymy nasze plotno
         Canvas3D canvas3d = new Canvas3D(config);
         add("Center", canvas3d);
         canvas3d.addKeyListener(this);
         BranchGroup scena = createSceneGraph(); 
         //this transform that will be applied to viewingPlatform will allow us to create better initial view on our scene
         Transform3D betterView = new Transform3D();
         betterView.set(new Vector3f(0.0f,2.5f,10f));
         SimpleUniverse simpleU = new SimpleUniverse(canvas3d);
        // simpleU.getViewingPlatform().setNominalViewingTransform();
         OrbitBehavior orbit = new OrbitBehavior(canvas3d, OrbitBehavior.REVERSE_ROTATE); //allow rotating camera with mouse
         orbit.setSchedulingBounds(new BoundingSphere());    
         simpleU.getViewingPlatform().setViewPlatformBehavior(orbit);                        
         
       //  simpleU.getViewingPlatform().setNominalViewingTransform();                      //nice view from the beggining automatic
         simpleU.getViewingPlatform().getViewPlatformTransform().setTransform(betterView);  //this will give better view from beggining 
         simpleU.addBranchGraph(scena);
     }

     public static void main(String[] args) 
     {
        System.setProperty("sun.awt.erasebackground", "true");

        CylinderArm test = new CylinderArm();
  // test.addKeyListener(test);


        new MainFrame(test, 1000, 800);
    }
    
}
