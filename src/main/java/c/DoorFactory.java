package c;

import org.abstractica.clicksystem.ClickSystem;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

public class DoorFactory {
    private final JavaCSG csg;
    private final ClickSystem cs;
    private final double unit;

    public DoorFactory(ClickSystem cs) {
        this.csg = cs.getJavaCSG();
        this.cs = cs;
        this.unit = cs.getUnit();
    }

    public Geometry3D createDoor (int height, int width, boolean isRightSided){
        if(isRightSided){
            return createRightSidedDoor(height, width);
        } else {
            return createLeftSidedDoor(height, width);
        }
    }

    private Geometry3D createRightSidedDoor(int height, int width){
        Geometry3D doorpost = createDoorPost(height, width);
        Geometry3D hole = createHole(height);
        Geometry3D doorPlate = createDoorPlate(height, width);
        Geometry3D window = createRightSidedWindow(height, width);
        Geometry3D doorhole = createDoorHandleHole(height, width);

        Geometry3D door = csg.union3D(doorpost, doorPlate);
        door = csg.difference3D(door,hole);
        door = csg.difference3D(door, window);
        door = csg.difference3D(door, doorhole);

        return door;
    }

    private Geometry3D createLeftSidedDoor(int height, int width){
        Geometry3D doorpost = createDoorPost(height, width);
        Geometry3D hole = createHole(height);
        Geometry3D doorPlate = createDoorPlate(height, width);
        Geometry3D window = createLeftSidedWindow(height, width);
        Geometry3D doorhole = createDoorHandleHole(height, width);

        Geometry3D door = csg.union3D(doorpost, doorPlate);
        door = csg.difference3D(door,hole);
        door = csg.difference3D(door, window);
        door = csg.difference3D(door, doorhole);

        return door;
    }

    public Geometry3D createDoorHandle(){
        Geometry3D doorpost = csg.cylinder3D(unit,unit * 0.5,256,false);

        Geometry3D hole = cs.getFixedHole(unit * 0.25,false,true,1, false);
        Geometry3D doorHandle = csg.difference3D(doorpost,hole);
        return doorHandle;

    }

    private Geometry3D createDoorPost(int height, int width){
        Geometry3D cylinder = csg.cylinder3D(unit,height * unit,256,false);
        return cylinder;
    }

    private Geometry3D createDoorPlate(int height, int width){
        double actualWidth = (width-0.5) * unit;
        Geometry3D doorPlate = csg.box3D(actualWidth-1, 0.25 * unit, height * unit, false);
        doorPlate = csg.translate3D(0.5*actualWidth, (0.5 * unit) - (0.125 * unit), 0).transform(doorPlate);

        return doorPlate;
    }

    private Geometry3D createRightSidedWindow(int height, int width){
        double doorHeight = height * unit;
        double doorWidth = width * unit;
        double windowWidth = doorWidth*0.60;

        Geometry3D window = csg.box3D(windowWidth, 0.3 * unit, windowWidth, false);
        window = csg.translate3D(doorWidth * 0.425,(0.5 * unit) - (0.125 * unit),doorHeight*0.6).transform(window);

        return window;
    }

    private Geometry3D createLeftSidedWindow(int height, int width) {
        double doorHeight = height * unit;
        double doorWidth = width * unit;
        double windowWidth = doorWidth * 0.60;

        Geometry3D window = csg.box3D(windowWidth, 0.3 * unit, windowWidth, false);
        window = csg.translate3D(doorWidth * 0.425, (0.5 * unit) - (0.125 * unit), doorHeight * 0.1).transform(window);

        return window;
    }

    private Geometry3D createHole(double height) {
        Geometry3D hole = cs.getTurnHole(unit*0.5, true, true, (int)((unit*height)*2), false);
        return hole;
    }

    private Geometry3D createDoorHandleHole(int height, int width){
        double doorHeight = height * unit;
        double doorWidth = width * unit;

        Geometry3D hole = cs.getFixedHole(unit * 0.25,true,false,1, false);

        hole = csg.rotate3DX(csg.degrees(90)).transform(hole);
        hole = csg.translate3D(doorWidth*0.675, (0.5 * unit), (doorHeight*0.6) - unit).transform(hole);

        return hole;
    }

}
