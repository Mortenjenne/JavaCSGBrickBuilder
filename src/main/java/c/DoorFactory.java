package c;

import org.abstractica.clicksystem.ClickSystem;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

import java.util.ArrayList;
import java.util.List;

public class DoorFactory {
    private final JavaCSG csg;
    private final ClickSystem cs;

    public DoorFactory(ClickSystem cs) {
        this.cs = cs;
        this.csg = cs.getJavaCSG();
    }

    public void createRectangle(int width, int depth) {

    }

    public Geometry3D createDoorFrame(double x, double y, double z){
        double unit = cs.getUnit();
        double margin = unit/2;

        double width = x * unit;
        double height = y * unit;
        double depth = z * unit/2;


        Geometry3D outer = csg.box3D(width, height, depth, false);
        Geometry3D inner = csg.box3D(width-margin, height-margin*2-6, depth, false);

        Geometry3D frame = csg.difference3D(outer,inner);
        frame = csg.translate3D(width / 2, height / 2, 0).transform(frame);


        double holeRadius = unit/10;
        double holeHeight = height;


        Geometry3D hole1 = csg.cylinder3D(holeRadius, holeHeight, 64,false);
        hole1 = csg.translate3D(4, 5, -holeHeight).transform(hole1);
        hole1 = csg.rotate3DX(csg.degrees(90)).transform(hole1);

        Geometry3D hole2 = csg.cylinder3D(holeRadius, holeHeight, 64,false);
        hole2 = csg.translate3D(width-4, 5, -holeHeight).transform(hole2);
        hole2 = csg.rotate3DX(csg.degrees(90)).transform(hole2);


        frame = csg.difference3D(frame, hole1,hole2);
        Geometry3D holes = createDoorFrameHoles(width);
        holes = csg.rotate3D(csg.degrees(-90),csg.degrees(0),csg.degrees(0)).transform(holes);
        holes = csg.translate3DZ(unit).transform(holes);
        frame = csg.difference3D(frame,holes);





        return frame;

    }

    private Geometry3D createHole() {
        Geometry3D hole = cs.getTurnHole((0.5 * cs.getUnit()), false, true, 1, false);
        return hole;
    }

    private Geometry3D createDoorFrameHoles(double length) {
        Geometry3D hole = createHole();
        List<Geometry3D> holes = new ArrayList<>();

        double unit = cs.getUnit();
        double spacing = unit;

        for (double x = 0.5 * unit*2; x < length; x += spacing) {
            double y = 0.5 * unit;
            holes.add(csg.translate3D(x, y, 0).transform(hole));
        }

        return csg.union3D(holes);
    }


}
