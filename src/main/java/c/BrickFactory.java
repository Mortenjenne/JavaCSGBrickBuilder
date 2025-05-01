package c;

import org.abstractica.javacsg.Geometry3D;

public interface BrickFactory {
    public Geometry3D createRectangleBrick(int xSize, int ySize, int zSize, boolean roundCorners);

    public Geometry3D createAngledBrick(int length, int height);

    public Geometry3D createAngledCornerBrick(int height, boolean isInsideCorner);

    public Geometry3D createCurvedBrick(int length,int width);

    public Geometry3D createCurvedCornerBrick(boolean isInsideCorner);

    public Geometry3D createSupportT();

    public Geometry3D createSupportL();

    public Geometry3D createBrick(double xSize, double ySize, double zSize);

    public Geometry3D createDoor (int height, int width, boolean isRightSided);

    public Geometry3D createDoorHandle();

    public Geometry3D createFrame(double x, double y, double z);
}



