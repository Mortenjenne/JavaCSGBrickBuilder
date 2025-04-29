package c;

import org.abstractica.clicksystem.ClickSystem;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

public class BrickFactoryImpl implements BrickFactory {
    private final JavaCSG csg;
    private final ClickSystem cs;
    private final RectangleBrickFactory rectangleBrickFactory;

    public BrickFactoryImpl(ClickSystem cs) {
        this.csg = cs.getJavaCSG();
        this.cs = cs;
        this.rectangleBrickFactory = new RectangleBrickFactory(cs);
    }

    @Override
    public Geometry3D createRectangleBrick(int xSize, int ySize, int zSize, boolean roundCorners) {
        return rectangleBrickFactory.createBrick(xSize, ySize, zSize, roundCorners);
    }
}
