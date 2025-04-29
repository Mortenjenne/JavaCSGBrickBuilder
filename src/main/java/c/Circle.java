package c;

import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Circle {
    private final JavaCSG csg;

    public Circle(JavaCSG csg) {
        this.csg = csg;
    }

    public Geometry2D createCircle(double diameter, int angleResolution){

        List<Vector2D> circle = new ArrayList<>();
        double da = Math.PI * 2 / angleResolution;
        double radius = diameter / 2;

        for(int i = 0; i < angleResolution; i++){
            double x = Math.cos(i * da) * radius;
            double y = Math.sin(i * da) * radius;
            Vector2D point = csg.vector2D(x,y);
            circle.add(point);

        }
        return csg.polygon2D(circle);

    }

    public static void main(String[] args) {

    }
}
