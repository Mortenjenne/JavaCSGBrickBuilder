package c;

import org.abstractica.clicksystem.ClickSystem;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

public class BrickFactoryImpl implements BrickFactory {
    private final JavaCSG csg;
    private final ClickSystem cs;
    private final RectangleBrickFactory rectangleBrickFactory;
    private final AngleBrickFactory angleBrickFactory;
    private final CurvedBrickFactory curvedBrickFactory;

    public BrickFactoryImpl(ClickSystem cs) {
        this.csg = cs.getJavaCSG();
        this.cs = cs;
        this.rectangleBrickFactory = new RectangleBrickFactory(cs);
        this.angleBrickFactory = new AngleBrickFactory(cs);
        this.curvedBrickFactory = new CurvedBrickFactory(cs);

    }

    @Override
    public Geometry3D createRectangleBrick(int xSize, int ySize, int zSize, boolean roundCorners) {
        return rectangleBrickFactory.createBrick(xSize, ySize, zSize, roundCorners);
    }

    @Override
    public Geometry3D createAngleBrick(int length, int height) {
        return angleBrickFactory.createAngleBrick(length, height);
    }

    @Override
    public Geometry3D createCornerBrick(int height, boolean isInside) {
        return angleBrickFactory.createAngleCornerBrick(height, isInside);
    }

    public Geometry3D createCurvedBrick(int length,int width){
        return this.curvedBrickFactory.createCurvedBrick(length,width);
    }

    public Geometry3D createCurvedCornerBrick(boolean isInsideCorner){
        return this.curvedBrickFactory.createCurvedCornerBrick(isInsideCorner);
    }
}
