package c;

import org.abstractica.javacsg.Geometry3D;

public interface BrickFactory {
    public Geometry3D createRectangleBrick(int xSize, int ySize, int zSize, boolean roundCorners);

    public Geometry3D createAngleBrick(int length, int height);

    public Geometry3D createCornerBrick(int height, boolean isInsideCorner);
}



