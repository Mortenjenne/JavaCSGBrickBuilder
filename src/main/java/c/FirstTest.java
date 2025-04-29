package c;

import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

public class FirstTest {
    public static void main(String[] args) {
        JavaCSG csg = JavaCSGFactory.createDefault();

        Circle c = new Circle(csg);
        Geometry2D c1 = c.createCircle(30,64);
        Geometry2D c2 = c.createCircle(60,64);
        c2 = csg.translate2DY(30).transform(c2);

        Geometry2D res = csg.difference2D(c2,c1);
        Geometry3D res3dshape = csg.linearExtrude(10,false,res);

        res = csg.rotate2D(csg.degrees(45)).transform(res);

        Geometry2D box = csg.rectangle2D(60,30);

        box = csg.rotate2D(csg.degrees(90)).transform(box);

        Geometry3D box3dShape = csg.linearExtrude(10,false,box);

        Geometry2D w1 = csg.circle2D(10,64);
        Geometry3D wheel = csg.linearExtrude(5,false,w1);
        wheel = csg.rotate3D(csg.degrees(0),csg.degrees(270),csg.degrees(0)).transform(wheel);
        wheel = csg.translate3DX(-12).transform(wheel);
        wheel = csg.translate3DY(-15).transform(wheel);

        Geometry2D w2 = csg.circle2D(10,64);
        Geometry3D wheel1 = csg.linearExtrude(5,false,w2);
        wheel1 = csg.rotate3D(csg.degrees(0),csg.degrees(270),csg.degrees(0)).transform(wheel1);
        wheel1 = csg.translate3DX(-12).transform(wheel1);
        wheel1 = csg.translate3DY(15).transform(wheel1);

        Geometry2D w3 = csg.circle2D(10,64);
        Geometry3D wheel2 = csg.linearExtrude(5,false,w3);
        wheel2 = csg.rotate3D(csg.degrees(0),csg.degrees(270),csg.degrees(0)).transform(wheel2);
        wheel2 = csg.translate3DX(17).transform(wheel2);
        wheel2 = csg.translate3DY(15).transform(wheel2);

        Geometry2D w4 = csg.circle2D(10,64);
        Geometry3D wheel3 = csg.linearExtrude(5,false,w4);
        wheel3 = csg.rotate3D(csg.degrees(0),csg.degrees(270),csg.degrees(0)).transform(wheel3);
        wheel3 = csg.translate3DX(17).transform(wheel3);
        wheel3 = csg.translate3DY(-15).transform(wheel3);

        Geometry2D rect1 = csg.rectangle2D(15,10);
        rect1 = csg.translate2D(-10,3).transform(rect1);
        rect1 = csg.rotate2D(csg.degrees(35)).transform(rect1);

        Geometry2D rect2 = csg.rectangle2D(15,10);
        rect2 = csg.translate2D(10,3).transform(rect2);
        rect2 = csg.rotate2D(csg.degrees(-35)).transform(rect2);

        Geometry2D rect3 = csg.rectangle2D(15,10);

        Geometry2D carUpperPart = csg.union2D(rect1,rect2,rect3);
        carUpperPart = csg.rotate2D(csg.degrees(90)).transform(carUpperPart);
        Geometry3D carRoof = csg.linearExtrude(30,false,carUpperPart);
        carRoof = csg.translate3DX(-12).transform(carRoof);
        carRoof = csg.translate3DZ(-15).transform(carRoof);
        




        carRoof = csg.rotate3D(csg.degrees(0),csg.degrees(90),csg.degrees(0)).transform(carRoof);





        Geometry3D car = csg.union3D(carRoof,box3dShape,wheel,wheel1,wheel2,wheel3);

        csg.view(car);

    }
}
