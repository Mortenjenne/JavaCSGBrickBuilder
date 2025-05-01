package c;

import org.abstractica.clicksystem.ClickSystem;
import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

import java.util.ArrayList;
import java.util.List;

public class RoofSupportFactory  {

    private final JavaCSG csg;
    private final ClickSystem cs;

    public RoofSupportFactory(ClickSystem cs) {
        this.csg = cs.getJavaCSG();
        this.cs = cs;
    }


    // Methods for standard sized supports
    public Geometry3D createSupportT(){

        Geometry3D rect1 = getRectangleBrick(6,2,1);
        Geometry3D rect2 = getRectangleBrick(2,2,3);
        rect2 = csg.translate3DX((2 * cs.getUnit())).transform(rect2);

        Geometry3D t = csg.union3D(rect1, rect2);

        return t;
    }

    public Geometry3D createSupportL(){
        Geometry3D rectBottom = getRectangleBrick(4, 2 , 1);
        Geometry3D rectTop = getRectangleBrick(2,2,3);

        Geometry3D res = csg.union3D(rectBottom,rectTop);
        return res;
    }

    public Geometry3D createBrick(double xSize, double ySize, double zSize){
        Geometry3D brick = getRectangleBrick(xSize, ySize , zSize);
        return brick;
    }


    public Geometry3D getRectangleBrick(double xSize, double ySize, double zSize)
    {
        if (    xSize < 1 || xSize != Math.floor(xSize) ||
                ySize < 1 || ySize != Math.floor(ySize) ||
                (zSize*2 < 1 || zSize*2 != Math.floor(zSize*2)))
        {
            throw new IllegalArgumentException("Invalid brick size");
        }
        double unit = cs.getUnit();
        Geometry3D hole = cs.getTurnHole(0.5*unit, true, true, (int) (zSize*2), false);
        Geometry3D printGeometry = generateBrick(xSize, ySize, zSize, hole);
        return printGeometry;
    }


    private Geometry3D generateBrick(double xSize, double ySize, double zSize, Geometry3D hole)
    {
        double unit = cs.getUnit();
        Geometry3D brick = csg.box3D(unit*xSize, unit*ySize, unit*zSize, false);
        brick = csg.translate3D(0.5*unit*xSize, 0.5*unit*ySize, 0).transform(brick);
        List<Geometry3D> holes = new ArrayList<>();
        for(int y = 0; y < ySize; ++y)
        {
            for(int x = 0; x < xSize; ++x)
            {
                Geometry3D holeCopy = csg.translate3D((x+0.5)*unit, (y+0.5)*unit, 0).transform(hole);
                holes.add(holeCopy);
            }
        }
        brick = csg.difference3D(brick, holes);
        return brick;
    }
}
