/* Copyright material for students working on assignments */

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.vecmath.*;


public class CodeA1YE extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private static final int OBJ_NUM = 6;

	/* a function to build the content branch */
	public static BranchGroup create_Scene() {
		BranchGroup sceneBG = new BranchGroup();           // create the scene' BranchGroup
		TransformGroup sceneTG = new TransformGroup();     // create the scene's TransformGroup

		BaseShapesYE[] baseShapes = new BaseShapesYE[OBJ_NUM];
		baseShapes[0] = new BoxShape();
		baseShapes[1] = new SquareShape();
		baseShapes[2] = new CylinderShape();
		baseShapes[3] = new SphereShape();
		baseShapes[4] = new RotorBladeShape();
		
		String str = "YE's A1";
		baseShapes[5] = new ColorString(str, CommonsYE.White, 0.1, 
				new Point3f(-str.length() / 2.3f, 6.5f, 1.2f));//left/right shift, up/down, in/out
		
		for (int i = 0; i < OBJ_NUM; i++)
			sceneTG.addChild(baseShapes[i].position_Object());

		sceneBG.addChild(CommonsYE.add_Lights(CommonsYE.White, 1));	
		sceneBG.addChild(CommonsYE.rotate_Behavior(7500, sceneTG));	
		sceneBG.addChild(sceneTG);                         // make 'sceneTG' continuous rotating
		return sceneBG;
	}

	/* NOTE: Keep the constructor for each of the assignments */
	public CodeA1YE(BranchGroup sceneBG) {
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(config);
		
		SimpleUniverse su = new SimpleUniverse(canvas);    // create a SimpleUniverse
		CommonsYE.define_Viewer(su, new Point3d(4.0d, 0.0d, 1.0d));

		sceneBG.compile();		                           // optimize the BranchGroup
		su.addBranchGraph(sceneBG);                        // attach the scene to SimpleUniverse

		setLayout(new BorderLayout());
		add("Center", canvas);
		frame.setSize(800, 800);                           // set the size of the JFrame
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		frame = new JFrame("YE's Assignment 1");            // NOTE: change XY to student's initials
		frame.getContentPane().add(new CodeA1YE(create_Scene()));  // create an instance of the class
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
