package c;

import org.abstractica.clicksystem.ClickSystem;
import org.abstractica.clicksystem.ClickSystemFactory;
import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

public class Main {
    public static void main(String[] args) {
        JavaCSG csg = JavaCSGFactory.createDefault();

        ClickSystem cs = ClickSystemFactory.system_12_8_6_medium(csg);
        BrickFactory factory = new BrickFactoryImpl(cs);
        Geometry3D brick = factory.createRectangleBrick(4,2,2,true);
        AngleBrickFactory angle = new AngleBrickFactory(cs);

        Geometry3D roof = factory.createAngleBrick(4, 2);
        Geometry3D cornerBrick = factory.createCornerBrick(2, true);

        csg.view(cornerBrick);
        //csg.view(roof);

    }
}