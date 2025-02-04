/* Copyright material for students working on assignments */

import java.awt.Font;

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.geometry.Box;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.java3d.utils.geometry.Cylinder;
import org.jogamp.java3d.utils.geometry.Primitive;
import org.jogamp.vecmath.*;

public abstract class BaseShapesYE {
	protected TransformGroup objTG = new TransformGroup(); // use 'objTG' to position an object

	protected abstract Node create_Object();               // allow derived classes to create different objects
	
	public TransformGroup position_Object() {	           // retrieve 'objTG' to which 'obj_shape' is attached
		return objTG;   
	}
	
	protected Appearance app;                              // allow each object to define its own appearance
	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // A3: attach the next transformGroup to 'objTG'
	}
}
class BoxShape extends BaseShapesYE{
	public BoxShape() {
		//creates a translation transform
		
		Transform3D translator = new Transform3D();
		translator.setTranslation(new Vector3d(0.12, 0.52, 0.0));//setting position of the box
		objTG = new TransformGroup(translator);
		
		objTG.addChild(create_Object());//added object to transform group
		

	}
	@Override 
	protected Node create_Object() {
		//setting appearance (color) and creating thee box object
		app = CommonsYE.set_Appearance(CommonsYE.Cyan);
		return new Box(0.26f, 0.06f, 0.12f, Primitive.GENERATE_NORMALS, app);
	}
}
class SquareShape extends BaseShapesYE {
	//created a translation transform 
	public SquareShape() {
		Transform3D translator = new Transform3D();
		translator.setTranslation(new Vector3d(0.0, -0.54, 0));//to set the position of the squew
		objTG = new TransformGroup(translator);            // down half of the tower (sphere) and base's heights

		objTG.addChild(create_Object());                   // attach the object to 'objTG'
	}
	
	protected Node create_Object() {
		app = CommonsYE.set_Appearance(CommonsYE.White);   // set the appearance for the base
		return new Box(0.5f, 0.04f, 0.5f, Primitive.GENERATE_NORMALS, app);
	}
}

class CylinderShape extends BaseShapesYE{
	public CylinderShape() {
		Transform3D translator = new Transform3D();
		translator.setTranslation(new Vector3d(0.0, 0.0, 0.0));//position of the cylinder shape at origin
		//base
		objTG = new TransformGroup(translator);
		objTG.addChild(create_Object());//to add object to transform group
			
	}

	@Override
	protected Node create_Object() {
		app = CommonsYE.set_Appearance(CommonsYE.Orange);//setting appearance to orange
		return new Cylinder(0.12f, 1.0f, Primitive.GENERATE_NORMALS, app);
	}
}

class SphereShape extends BaseShapesYE{
	public SphereShape() {//translation transform created
	
		Transform3D translator = new Transform3D();
		translator.setTranslation(new Vector3d(0.0, 0.52, 0.0)); //position of translator
		objTG = new TransformGroup(translator);
		objTG.addChild(create_Object());
	
	}
	
	@Override
	protected Node create_Object() {
		//setting appearance to red
		app = CommonsYE.set_Appearance(CommonsYE.Red);
		return new Sphere(0.12f, Primitive.GENERATE_NORMALS,30, app);
	}
}
class RotorBladeShape extends BaseShapesYE{
	public RotorBladeShape() {
		objTG = new TransformGroup();
		
		TransformGroup sphereTG = new TransformGroup();
		
		//created and positioned rotor hub (sphere)
		Transform3D spherePos = new Transform3D();
		spherePos.setTranslation(new Vector3d(0.37, 0.69, 0.0));//position for sphere
		sphereTG.setTransform(spherePos);
		sphereTG.addChild(create_RotorHub());//added it to transofmr group
		
		
		TransformGroup bladeTG = new TransformGroup();//for the creation and position of rotor blade 
		//the box
		Transform3D bladePos = new Transform3D();
		bladePos.setTranslation(new Vector3d(0.44, 0.69, 0.0));//the position
		bladeTG.setTransform(bladePos);
		bladeTG.addChild(create_RotorBlade())
		;
		objTG.addChild(sphereTG);//added rotor hub to main transform group
		objTG.addChild(bladeTG);//added rotor blade to main transform group
		
	}
	private Node create_RotorHub() {
		//crearing rotpr hub and setting appearance
		app = CommonsYE.set_Appearance(CommonsYE.Red);
        return new Sphere(0.06f, Primitive.GENERATE_NORMALS, 30, app);
    }

    private Node create_RotorBlade() {
    	//creating blade and setting appearance
        app = CommonsYE.set_Appearance(CommonsYE.Magenta);
        return new Box(0.01f, 0.06f, 0.5f, Primitive.GENERATE_NORMALS, app);
    }

    @Override
    protected Node create_Object() {
        return null; // Not used as custom components are directly added
    }
}
/* a derived class to create a string label and place it to the bottom of the self-made cone */
class ColorString extends BaseShapesYE {
	private String str;
	private Color3f clr;
	private double scl;
	private Point3f pos;                                           // make the label adjustable with parameters
	public ColorString(String str_ltrs, Color3f str_clr, double s, Point3f p) {
		str = str_ltrs;	
		clr = str_clr;
		scl = s;
		pos = p;

		Transform3D scaler = new Transform3D();
		scaler.setScale(scl);                              // scaling 4x4 matrix 
		Transform3D rotator = new Transform3D();           // 4x4 matrix for rotation
		rotator.rotY(Math.PI);
		Transform3D trfm = new Transform3D();              // 4x4 matrix for composition
		trfm.mul(rotator);                                 // apply rotation second
		trfm.mul(scaler);                                  // apply scaling first
		objTG = new TransformGroup(trfm);                  // set the combined transformation
		objTG.addChild(create_Object());                   // attach the object to 'objTG'		
	}
	protected Node create_Object() {
		Font my2DFont = new Font("Arial", Font.PLAIN, 1);  // font's name, style, size
		FontExtrusion myExtrude = new FontExtrusion();
		Font3D font3D = new Font3D(my2DFont, myExtrude);	
		Text3D text3D = new Text3D(font3D, str, pos);      // create 'text3D' for 'str' at position of 'pos'
		
		Appearance app = CommonsYE.set_Appearance(clr);    // use appearance to specify the string color
		return new Shape3D(text3D, app);
	}
}// return a string label with the appea


