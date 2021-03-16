import graphics.*;
import titan.Vector3dInterface;
import physics.*;
import graphics.*;

//import SolarSystem;
public class Test {
    public static void main(String[] args){
        SolarSystem sol = new SolarSystem(600, 5256);
        Vector3dInterface[][] locations = sol.getLocations();
        System.out.println(locations[0][1]);
        new myFrame("solar");
    }
}
