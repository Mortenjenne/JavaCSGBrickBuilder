package c;

import org.abstractica.clicksystem.ClickSystem;
import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

import java.util.ArrayList;
import java.util.List;

public class RectangleBrickFactory {
    private final JavaCSG csg;
    private final ClickSystem cs;

    public RectangleBrickFactory(ClickSystem cs) {
        this.csg = cs.getJavaCSG();
        this.cs = cs;
    }

    public Geometry3D createBrick(int xSize, int ySize, int zSize, boolean roundCorners){
        Geometry3D block = roundCorners ? createBlockRound (xSize, ySize, zSize) : createBlock(xSize, ySize, zSize);
        Geometry3D holes = createHoles(xSize, ySize, zSize);
        Geometry3D res = csg.difference3D(block,holes);
        return res;
    }

    private Geometry3D createBlock(int xSize, int ySize, int zSize){
        Geometry3D result = csg.box3D(xSize*12,ySize*12,zSize*6,false);
        result = csg.translate3D(xSize*6,ySize*6,0).transform(result);
        return result;
    }

    private Geometry3D createBlockRound(int xSize, int ySize, int zSize){
        Geometry2D corner = csg.circle2D(12,256);
        List<Geometry2D> corners = new ArrayList<>();
        corners.add(csg.translate2D(6,6).transform(corner));
        corners.add(csg.translate2D((xSize-1)*12+6,6).transform(corner));
        corners.add(csg.translate2D(6,(ySize-1)*12+6).transform(corner));
        corners.add(csg.translate2D((xSize-1)*12+6,(ySize-1)*12+6).transform(corner));

        Geometry2D blockProfile = csg.hull2D(corners);
        Geometry3D block = csg.linearExtrude(zSize*6,false,blockProfile);

        return block;

    }

    private Geometry3D createHole(int zSize){
        Geometry3D hole = cs.getTurnHole(6,true,true,zSize,false);
        return hole;
    }

    private Geometry3D createHoles(int xSize, int ySize, int zSize){
        Geometry3D hole = createHole(zSize);
        List<Geometry3D> holes = new ArrayList<>();
        for(int y = 0; y < ySize; y++){
            for(int x = 0; x < xSize; x++){
                Geometry3D holeCopy = csg.translate3D(x*12+6, y*12+6, zSize).transform(hole);
                holes.add(holeCopy);
            }
        }
        return csg.union3D(holes);
    }


}
