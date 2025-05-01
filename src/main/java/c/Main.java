package c;

import org.abstractica.clicksystem.ClickSystem;
import org.abstractica.clicksystem.ClickSystemFactory;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

public class Main {
    public static void main(String[] args) {
        JavaCSG csg = JavaCSGFactory.createDefault();

        ClickSystem cs = ClickSystemFactory.system_12_8_6_medium(csg);
        BrickFactory factory = new BrickFactoryImpl(cs);

        FrameFactory frameFactory = new FrameFactory(cs);

        DoorFactory doorFactory = new DoorFactory(cs);

        //Differrent bricks
        Geometry3D brick = factory.createRectangleBrick(4,2,2,true);
        Geometry3D angledBrick = factory.createAngledBrick(4, 2);
        Geometry3D cornerBrick = factory.createAngledCornerBrick(2, true);
        Geometry3D curvedBrick = factory.createCurvedBrick(2,1);
        Geometry3D curvedCornerBrick = factory.createCurvedCornerBrick(false);
        Geometry3D tSupport = factory.createSupportT();
        Geometry3D lSupport = factory.createSupportL();
        Geometry3D doorFrame = frameFactory.createFrame(4,8,1);
        Geometry3D window = frameFactory.createFrame(4,4,1);
        Geometry3D doorRight = doorFactory.createDoor(8,4, true);
        Geometry3D doorLeft = doorFactory.createDoor(8,4,false);

        Geometry3D doorHandle = doorFactory.createDoorHandle();
        Geometry3D wall = factory.createBrick(1,6,1);
        
        // View
        csg.view(wall);

    }
}