package c;

import org.abstractica.clicksystem.ClickSystem;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

import java.util.ArrayList;
import java.util.List;

public class FrameFactory {
    private final JavaCSG csg;
    private final ClickSystem cs;

    public FrameFactory(ClickSystem cs) {
        this.cs = cs;
        this.csg = cs.getJavaCSG();
    }


    public Geometry3D createFrame(double x, double y, double z){
        double unit = cs.getUnit();
        double margin = unit/2;


        double width = x * unit;
        double height = y * unit;
        double depth = z * unit;


        Geometry3D outer = csg.box3D(width, height, depth, false);
        Geometry3D inner = csg.box3D(width-margin, height-margin*2-6, depth * 0.3, false);
        Geometry3D innerDoorStop = csg.box3D((width-margin) - 2, (height-margin*2-6), depth * 0.75, false);

        innerDoorStop = csg.translate3DZ(depth * 0.3).transform(innerDoorStop);

        Geometry3D frame = csg.difference3D(outer,inner, innerDoorStop);
        frame = csg.translate3D(width / 2, height / 2, 0).transform(frame);

        // Create holes for door / window attachment
        double holeRadius = unit/10;
        double holeHeight = height;

        Geometry3D hole1 = csg.cylinder3D(holeRadius, holeHeight, 64,false);
        hole1 = csg.translate3D(unit*0.33, (depth * 0.15), -holeHeight).transform(hole1);
        hole1 = csg.rotate3DX(csg.degrees(90)).transform(hole1);

        Geometry3D hole2 = csg.cylinder3D(holeRadius, holeHeight, 64,false);
        hole2 = csg.translate3D((width - (unit * 0.33)), (depth * 0.15), -holeHeight).transform(hole2);
        hole2 = csg.rotate3DX(csg.degrees(90)).transform(hole2);

        // Connect holes to frame
        frame = csg.difference3D(frame, hole1,hole2);


        Geometry3D holes = createDoorFrameHoles(width,height);
        holes = csg.rotate3D(csg.degrees(-90),csg.degrees(0),csg.degrees(0)).transform(holes);
        holes = csg.translate3DZ(unit).transform(holes);
        frame = csg.difference3D(frame,holes);


        return frame;

    }

    private Geometry3D createHole() {
        Geometry3D hole = cs.getTurnHole((0.5 * cs.getUnit()), false, true, 1, false);
        return hole;
    }

    private Geometry3D createDoorFrameHoles(double width, double height) {
        Geometry3D hole = createHole();
        List<Geometry3D> holes = new ArrayList<>();
        List<Geometry3D> holesTop = new ArrayList<>();

        double unit = cs.getUnit();

        for (double x = 0; x < (width / unit); x++) {
            double y = 0.5 * unit;
            holes.add(csg.translate3D(((x * unit) + (unit * 0.5)), y, 0).transform(hole));
        }

        holes.remove(holes.getLast());
        holes.remove(holes.getFirst());

        for (Geometry3D h: holes){
            holesTop.add(h);
        }

        Geometry3D holesTopUnionized = csg.union3D(holesTop);

        holesTopUnionized = csg.rotate3DX(csg.degrees(180)).transform(holesTopUnionized);
        holesTopUnionized = csg.translate3D(0, unit, height).transform(holesTopUnionized);

        holes.add(holesTopUnionized);

        return csg.union3D(holes);
    }

}
