package c;

import org.abstractica.clicksystem.ClickSystem;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

public class BrickFactoryImpl implements BrickFactory {
    private final JavaCSG csg;
    private final ClickSystem cs;
    private final RectangleBrickFactory rectangleBrickFactory;
    private final AngledBrickFactory angleBrickFactory;
    private final CurvedBrickFactory curvedBrickFactory;
    private final RoofSupportFactory roofSupportFactory;
    private final DoorFactory doorFactory;
    private final FrameFactory frameFactory;

    public BrickFactoryImpl(ClickSystem cs) {
        this.csg = cs.getJavaCSG();
        this.cs = cs;
        this.rectangleBrickFactory = new RectangleBrickFactory(cs);
        this.angleBrickFactory = new AngledBrickFactory(cs);
        this.curvedBrickFactory = new CurvedBrickFactory(cs);
        this.roofSupportFactory = new RoofSupportFactory(cs);
        this.doorFactory = new DoorFactory(cs);
        this.frameFactory = new FrameFactory(cs);

    }

    @Override
    public Geometry3D createRectangleBrick(int xSize, int ySize, int zSize, boolean roundCorners) {
        return rectangleBrickFactory.createBrick(xSize, ySize, zSize, roundCorners);
    }

    @Override
    public Geometry3D createAngledBrick(int length, int height) {
        return angleBrickFactory.createAngledBrick(length, height);
    }

    @Override
    public Geometry3D createAngledCornerBrick(int height, boolean isInside) {
        return angleBrickFactory.createAngledCornerBrick(height, isInside);
    }

    public Geometry3D createCurvedBrick(int length,int width){
        return this.curvedBrickFactory.createCurvedBrick(length,width);
    }

    public Geometry3D createCurvedCornerBrick(boolean isInsideCorner){
        return this.curvedBrickFactory.createCurvedCornerBrick(isInsideCorner);
    }

    @Override
    public Geometry3D createSupportT() {
        return this.roofSupportFactory.createSupportT();
    }

    @Override
    public Geometry3D createSupportL() {
        return this.roofSupportFactory.createSupportL();
    }

    @Override
    public Geometry3D createBrick(double xSize, double ySize, double zSize) {
        return this.roofSupportFactory.createBrick(xSize,ySize,zSize);
    }

    @Override
    public Geometry3D createDoor(int height, int width, boolean isRightSided) {
        return this.doorFactory.createDoor(height,width,isRightSided);
    }

    @Override
    public Geometry3D createDoorHandle() {
        return this.doorFactory.createDoorHandle();
    }

    @Override
    public Geometry3D createFrame(double x, double y, double z) {
        return this.frameFactory.createFrame(x,y,z);
    }
}
