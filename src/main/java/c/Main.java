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


        Geometry2D triangle = csg.rightTriangle2D(24,24);
        Geometry3D triangle3D = csg.linearExtrude(48,false,triangle);
        triangle3D = csg.rotate3DX(csg.degrees(90)).transform(triangle3D);
        triangle3D = csg.translate3DY(48).transform(triangle3D);

        csg.view(triangle3D);

    }
}