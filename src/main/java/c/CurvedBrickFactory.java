package c;

import org.abstractica.clicksystem.ClickSystem;
import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

import java.util.ArrayList;
import java.util.List;

public class CurvedBrickFactory {

    private final JavaCSG csg;
    private final ClickSystem cs;

    public CurvedBrickFactory(ClickSystem cs) {
        this.csg = cs.getJavaCSG();
        this.cs = cs;
    }

    public Geometry3D createQuarterCircle(int length, int width){
        Geometry2D createQuarterCircle2D = csg.circleSegment2D(((2 * width) * cs.getUnit()), csg.degrees(0), csg.degrees(90),256);
        Geometry3D quarterCircle = csg.linearExtrude((length*cs.getUnit()),false,createQuarterCircle2D);

        quarterCircle = csg.rotate3DX(csg.degrees(90)).transform(quarterCircle);
        quarterCircle = csg.translate3DY(length * cs.getUnit()).transform(quarterCircle);

        return quarterCircle;
    }


    private Geometry3D createHole(){
            Geometry3D hole = cs.getTurnHole((0.5 * cs.getUnit()),false,true,1,false);
            return hole;
    }


    private Geometry3D createHoles(int length){
        Geometry3D hole = createHole();
        List<Geometry3D> holes = new ArrayList<>();
        for(int y = 0; y < (length * cs.getUnit()); y++){
            Geometry3D holeCopy = csg.translate3D((0.5 * cs.getUnit()), ((y * cs.getUnit()) + (0.5 * cs.getUnit())), 0).transform(hole);
            holes.add(holeCopy);
        }
        return csg.union3D(holes);
    }

    public Geometry3D createCurvedBrick(int length, int width){
        Geometry3D quarterCircle = createQuarterCircle(length, width);
        Geometry3D holes = createHoles(length);
        Geometry3D curvedBrick = csg.difference3D(quarterCircle,holes);
        curvedBrick = csg.rotate3D(csg.degrees(0),csg.degrees(-90),csg.degrees(-90)).transform(curvedBrick);

        return curvedBrick;
    }

    public Geometry3D createCurvedCornerBrick(boolean isInsideCorner){
        if(isInsideCorner){
            return  createCurvedInsideBrick();
        }
        else {
            return createCurvedOutsideBrick();
        }
    }

    private Geometry3D createCurvedInsideBrick() {
        Geometry3D insideCorner = createInsideCurvedCorner();
        Geometry3D holes = createHoles(2);
        Geometry3D hole = createHole();
        hole = csg.translate3D((1.5 * cs.getUnit()),(0.5 * cs.getUnit()),0).transform(hole);
        Geometry3D inSideCornerBrick = csg.difference3D(insideCorner,holes, hole);

        inSideCornerBrick = csg.rotate3DX(csg.degrees(90)).transform(inSideCornerBrick);
        inSideCornerBrick = csg.translate3DY(cs.getUnit()).transform(inSideCornerBrick);

        return inSideCornerBrick;
    }

    private Geometry3D createCurvedOutsideBrick() {
        Geometry3D outSideCorner = createOutsideCurvedCorner();
        Geometry3D hole = createHole();
        hole = csg.translate3D((0.5 * cs.getUnit()),(0.5 * cs.getUnit()),0).transform(hole);
        Geometry3D outSideCornerBrick = csg.difference3D(outSideCorner,hole);

        outSideCornerBrick = csg.rotate3DX(csg.degrees(90)).transform(outSideCornerBrick);
        outSideCornerBrick = csg.translate3DY(cs.getUnit()).transform(outSideCornerBrick);

        return outSideCornerBrick;
    }

    private Geometry3D createInsideCurvedCorner(){
        Geometry3D quarterCircle1 = createQuarterCircle(2, 2);
        Geometry3D quarterCircle2 = createQuarterCircle(2, 2);
        quarterCircle2 = csg.rotate3DZ(csg.degrees(90)).transform(quarterCircle2);
        quarterCircle2 = csg.translate3DX((2*cs.getUnit())).transform(quarterCircle2);
        Geometry3D curvedInsideCornerBrick = csg.union3D(quarterCircle1,quarterCircle2);

        return curvedInsideCornerBrick;
    }

    private Geometry3D createOutsideCurvedCorner(){
        Geometry3D quarterCircle1 = createQuarterCircle(2,2);
        Geometry3D quarterCircle2 = createQuarterCircle(2,2);
        quarterCircle2 = csg.rotate3DZ(csg.degrees(90)).transform(quarterCircle2);
        quarterCircle2 = csg.translate3DX((2*cs.getUnit())).transform(quarterCircle2);
        Geometry3D curvedOutsideCornerBrick = csg.intersection3D(quarterCircle1,quarterCircle2);

        return curvedOutsideCornerBrick;
    }
}
