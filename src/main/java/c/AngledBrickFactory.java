package c;

import org.abstractica.clicksystem.ClickSystem;
import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

import java.util.ArrayList;
import java.util.List;

public class AngleBrickFactory {

    private final JavaCSG csg;
    private final ClickSystem cs;


    public AngleBrickFactory(ClickSystem cs) {
        this.csg = cs.getJavaCSG();
        this.cs = cs;
    }

    public Geometry3D createAngleBrick(int length, int height) {
        Geometry3D triangle = createTriangle(length, height);
        Geometry3D holes = createHoles(length);
        Geometry3D res = csg.difference3D(triangle, holes);
        res = csg.rotate3D(csg.degrees(0), csg.degrees(-90), csg.degrees(-90)).transform(res);

        return res;
    }


    public Geometry3D createAngleCornerBrick(int height, boolean isInsideCorner) {
        if (isInsideCorner) {
            return createInsideCornerBrick(height);
        } else {
            return createOutsideCornerBrick(height);
        }
    }

    private Geometry3D createInsideCornerBrick(int height) {
        Geometry3D insideCorner = createInsideCorner(height);
        Geometry3D holes = createHoles(2);
        Geometry3D hole = createHole();
        hole = csg.translate3D((1.5 * cs.getUnit()), (0.5 * cs.getUnit()), 0).transform(hole);
        Geometry3D inSideCornerBrick = csg.difference3D(insideCorner, holes, hole);

        inSideCornerBrick = csg.rotate3DX(csg.degrees(90)).transform(inSideCornerBrick);
        inSideCornerBrick = csg.translate3DY(cs.getUnit()).transform(inSideCornerBrick);

        return inSideCornerBrick;
    }


    private Geometry3D createOutsideCornerBrick(int height) {
        Geometry3D outSideCorner = createOutsideCorner(height);
        Geometry3D hole = createHole();
        hole = csg.translate3D((0.5 * cs.getUnit()), (0.5 * cs.getUnit()), 0).transform(hole);
        Geometry3D outSideCornerBrick = csg.difference3D(outSideCorner, hole);

        outSideCornerBrick = csg.rotate3DX(csg.degrees(90)).transform(outSideCornerBrick);
        outSideCornerBrick = csg.translate3DY(cs.getUnit()).transform(outSideCornerBrick);

        return outSideCornerBrick;
    }


    private Geometry3D createInsideCorner(int height) {
        Geometry3D triangle1 = createTriangle(2, height);
        Geometry3D triangle2 = createTriangle(2, height);

        triangle2 = csg.rotate3DZ(csg.degrees(90)).transform(triangle2);
        triangle2 = csg.translate3D((2 * cs.getUnit()), 0, 0).transform(triangle2);
        Geometry3D cornerBrick = csg.union3D(triangle1, triangle2);

        return cornerBrick;
    }


    private Geometry3D createOutsideCorner(int height) {
        Geometry3D triangle1 = createTriangle(2, height);
        Geometry3D triangle2 = createTriangle(2, height);
        triangle2 = csg.rotate3DZ(csg.degrees(90)).transform(triangle2);
        triangle2 = csg.translate3D((2 * cs.getUnit()), 0, 0).transform(triangle2);
        Geometry3D cornerBrick = csg.intersection3D(triangle1, triangle2);

        return cornerBrick;
    }


    private Geometry3D createTriangle(int length, int height) {
        Geometry2D triangle = csg.rightTriangle2D((2 * cs.getUnit()), height * cs.getUnit());
        Geometry3D triangle3D = csg.linearExtrude(length * cs.getUnit(), false, triangle);
        triangle3D = csg.rotate3DX(csg.degrees(90)).transform(triangle3D);
        triangle3D = csg.translate3DY(length * cs.getUnit()).transform(triangle3D);

        return triangle3D;
    }

    private Geometry3D createHole() {
        Geometry3D hole = cs.getTurnHole(6, false, true, 1, false);
        return hole;
    }

    private Geometry3D createHoles(int length) {
        Geometry3D hole = createHole();
        List<Geometry3D> holes = new ArrayList<>();
        for (int y = 0; y < (length * cs.getUnit()); y++) {
            Geometry3D holeCopy = csg.translate3D((0.5 * cs.getUnit()), ((y * cs.getUnit()) + (0.5 * cs.getUnit())), 0).transform(hole);
            holes.add(holeCopy);
        }
        return csg.union3D(holes);
    }
}

