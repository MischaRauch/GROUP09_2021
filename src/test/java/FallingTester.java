import static org.junit.jupiter.api.Assertions.*;
import src.main.java.titan.Vector3dInterface;

import org.junit.jupiter.api.Test;

class FallingTester {

    static final double ACCURACY = 1; // 1 meter (might need to tweak that)

    @Test void simulate5mFall () {
        double altitude = simulateFall(0.01, 2.7196, new Vector3d(0,5,0), new Vector3d(0,0,0));
        assertEquals(0, altitude, ACCURACY);
    }

    @Test void simulate15kmFall () {
        double altitude = simulateFall(0.1,471.1, new Vector3d(0,150000,0), new Vector3d(0,0,0));
        assertEquals(0, altitude);
    }



    public double simulateFall(double h, double tf, Vector3d start, Vector3d velocity) {
        WindModel wM = new WindModel();
        SingleState[] states = wM.calculateFall(h,tf, start, velocity);
        double finalPosition = states[states.length-1].getCoordinates().getY();
        return finalPosition;
    }
}
