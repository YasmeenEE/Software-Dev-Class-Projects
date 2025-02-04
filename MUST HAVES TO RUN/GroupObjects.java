import org.jogamp.java3d.Node;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.BranchGroup;

public class GroupObjects {

    // Creates a scene graph combining the alterable and shape branch groups.
    public static BranchGroup scene_Group(BranchGroup a_BG, BranchGroup s_BG) {
        BranchGroup sceneBG = new BranchGroup(); // The root of the scene graph

        // Allow the alterable branch group to dynamically change its children
        a_BG.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        a_BG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);

        // Allow the shape branch group to be detached if needed
        s_BG.setCapability(BranchGroup.ALLOW_DETACH);

        // Add the shape branch group as a child of the alterable branch group
        a_BG.addChild(s_BG);

        // Create a transform group to manage transformations for the scene
        TransformGroup sceneTG = new TransformGroup();
        sceneTG.addChild(a_BG); // Attach the alterable branch group to the transform group

        // Add rotation behavior to the transform group
        sceneBG.addChild(CommonsYE.rotate_Behavior(7500, sceneTG));

        // Start the rotation behavior
        CommonsYE.control_Rotation(true);

        // Add the transform group to the scene graph
        sceneBG.addChild(sceneTG);

        // Return the completed scene graph
        return sceneBG;
    }

    // A branch group that holds the shape object
    protected BranchGroup shapeBG = new BranchGroup();

    // Returns the branch group containing the shape object
    public BranchGroup get_ShapeBG() {
        return shapeBG;
    }

    // Constructor that accepts a single Shape3D and adds it to the shape branch group
    public GroupObjects(Shape3D shape) {
        shapeBG.addChild(shape); // Add the shape to the shape branch group
    }

    // Constructor that accepts two Shape3D objects and adds them to the shape branch group
    public GroupObjects(Shape3D shape1, Shape3D shape2) {
        shapeBG.addChild(shape1); // Add the first shape
        shapeBG.addChild(shape2); // Add the second shape
    }
}
