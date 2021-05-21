//package titan;
import src.main.java.titan.Vector3dInterface;
import src.main.java.Vector3d;

import titan.ProbeSimulatorInterface;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import titan.StateInterface;


class ODESolverTester {

    static final double ACCURACY = 1E11; // 1 meter (might need to tweak that)


    @Test void sunOneYearX() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface xValue = value[0];
        double x366 = -1.082901546284588E+06*1000; // reference implementation
        assertEquals(x366, xValue.getX(), ACCURACY); // delta +-ACCURACY
    }
    @Test void sunOneYearY() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface yValue = value[0];
        double y366 = 8.147721163745786E+05*1000; // reference implementation
        assertEquals(y366, yValue.getY(), ACCURACY); // delta +-ACCURACY
    }
    @Test void sunOneYearZ() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface zValue = value[0];
        double z366 = 1.863744464428420E+04*1000; // reference implementation
        assertEquals(z366, zValue.getZ(), ACCURACY); // delta +-ACCURACY
    }

    @Test void mercuryOneYearX() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface xValue = value[1];
        double x366 = 3.950842791655454E+07*1000; // reference implementation
        assertEquals(x366, xValue.getX(), ACCURACY); // delta +-ACCURACY
    }
    @Test void mercuryOneYearY() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface yValue = value[1];
        double y366 = -4.720264660931208E+07*1000; // reference implementation
        assertEquals(y366, yValue.getY(), ACCURACY); // delta +-ACCURACY
    }
    @Test void mercuryOneYearZ() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface zValue = value[1];
        double z366 = -7.628642664193494E+06*1000; // reference implementation
        assertEquals(z366, zValue.getZ(), ACCURACY); // delta +-ACCURACY
    }

    @Test void venusOneYearX() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface xValue = value[2];
        double x366 = 1.037791016188344E+08*1000; // reference implementation
        assertEquals(x366, xValue.getX(), ACCURACY); // delta +-ACCURACY
    }
    @Test void venusOneYearY() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface yValue = value[2];
        double y366 = 2.825331531868221E+07*1000; // reference implementation
        assertEquals(y366, yValue.getY(), ACCURACY); // delta +-ACCURACY
    }
    @Test void venusOneYearZ() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface zValue = value[2];
        double z366 = -5.655906754344899E+06*1000; // reference implementation
        assertEquals(z366, zValue.getZ(), ACCURACY); // delta +-ACCURACY
    }

    @Test void earthOneYearX() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface xValue = value[3];
        double x366 = -1.477129564888797E+08*1000; // reference implementation
        assertEquals(x366, xValue.getX(), ACCURACY); // delta +-ACCURACY
    }
    @Test void earthOneYearY() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface yValue = value[3];
        double y366 = -2.821064238692064E+07*1000; // reference implementation
        assertEquals(y366, yValue.getY(), ACCURACY); // delta +-ACCURACY
    }
    @Test void earthOneYearZ() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface zValue = value[3];
        double z366 = 2.033107664331608E+04*1000; // reference implementation
        assertEquals(z366, zValue.getZ(), ACCURACY); // delta +-ACCURACY
    }

    @Test void mooonOneYearX() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface xValue = value[4];
        double x366 = -1.479155194643604E+08*1000; // reference implementation
        assertEquals(x366, xValue.getX(), ACCURACY); // delta +-ACCURACY
    }
    @Test void moonOneYearY() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface yValue = value[4];
        double y366 = -2.851137900054520E+07*1000; // reference implementation
        assertEquals(y366, yValue.getY(), ACCURACY); // delta +-ACCURACY
    }
    @Test void moonOneYearZ() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface zValue = value[4];
        double z366 = 2.937109212716483E+04*1000; // reference implementation
        assertEquals(z366, zValue.getZ(), ACCURACY); // delta +-ACCURACY
    }

    @Test void marsOneYearX() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface xValue = value[5];
        double x366 = -8.471846525675179E+07*1000; // reference implementation
        assertEquals(x366, xValue.getX(), ACCURACY); // delta +-ACCURACY
    }
    @Test void marsOneYearY() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface yValue = value[5];
        double y366 = 2.274355520083190E+08*1000; // reference implementation
        assertEquals(y366, yValue.getY(), ACCURACY); // delta +-ACCURACY
    }
    @Test void marsOneYearZ() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface zValue = value[5];
        double z366 = 6.819319353400901E+06*1000; // reference implementation
        assertEquals(z366, zValue.getZ(), ACCURACY); // delta +-ACCURACY
    }

    @Test void jupiterOneYearX() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface xValue = value[6];
        double x366 = 5.299051386136684E+08*1000; // reference implementation
        assertEquals(x366, xValue.getX(), ACCURACY); // delta +-ACCURACY
    }
    @Test void jupiterOneYearY() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface yValue = value[6];
        double y366 = -5.398930284706271E+08*1000; // reference implementation
        assertEquals(y366, yValue.getY(), ACCURACY); // delta +-ACCURACY
    }
    @Test void jupiterOneYearZ() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface zValue = value[6];
        double z366 = -9.615587266077518E+06*1000; // reference implementation
        assertEquals(z366, zValue.getZ(), ACCURACY); // delta +-ACCURACY
    }

    @Test void saturnOneYearX() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface xValue = value[7];
        double x366 = 8.778981939996121E+08*1000; // reference implementation
        assertEquals(x366, xValue.getX(), ACCURACY); // delta +-ACCURACY
    }
    @Test void saturnOneYearY() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface yValue = value[7];
        double y366 = -1.204478262290766E+09*1000; // reference implementation
        assertEquals(y366, yValue.getY(), ACCURACY); // delta +-ACCURACY
    }
    @Test void saturnOneYearZ() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface zValue = value[7];
        double z366 = -1.400829719307184E+07*1000; // reference implementation
        assertEquals(z366, zValue.getZ(), ACCURACY); // delta +-ACCURACY
    }

    @Test void titanOneYearX() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface xValue = value[8];
        double x366 = 8.789384100968744E+08*1000; // reference implementation
        assertEquals(x366, xValue.getX(), ACCURACY); // delta +-ACCURACY
    }
    @Test void titanOneYearY() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface yValue = value[8];
        double y366 = -1.204002291074617E+09*1000; // reference implementation
        assertEquals(y366, yValue.getY(), ACCURACY); // delta +-ACCURACY
    }
    @Test void titanOneYearZ() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface zValue = value[8];
        double z366 = -1.435729928774685E+07*1000; // reference implementation
        assertEquals(z366, zValue.getZ(), ACCURACY); // delta +-ACCURACY
    }

    @Test void uranusOneYearX() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface xValue = value[9];
        double x366 = 2.261199091016792E+09*1000; // reference implementation
        assertEquals(x366, xValue.getX(), ACCURACY); // delta +-ACCURACY
    }
    @Test void uranusOneYearY() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface yValue = value[9];
        double y366 = 1.903465617218642E+09*1000; // reference implementation
        assertEquals(y366, yValue.getY(), ACCURACY); // delta +-ACCURACY
    }
    @Test void uranusOneYearZ() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface zValue = value[9];
        double z366 = -2.222474353840995E+07*1000; // reference implementation
        assertEquals(z366, zValue.getZ(), ACCURACY); // delta +-ACCURACY
    }

    @Test void neptuneOneYearX() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface xValue = value[10];
        double x366 = 4.413142564759285E+09*1000; // reference implementation
        assertEquals(x366, xValue.getX(), ACCURACY); // delta +-ACCURACY
    }
    @Test void neptuneOneYearY() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface yValue = value[10];
        double y366 = -7.398714862901397E+08*1000; // reference implementation
        assertEquals(y366, yValue.getY(), ACCURACY); // delta +-ACCURACY
    }
    @Test void neptuneOneYearZ() {
        Vector3dInterface[] value = simulateOneYear();
        Vector3dInterface zValue = value[10];
        double z366 = -8.646907932385075E+07*1000; // reference implementation
        assertEquals(z366, zValue.getZ(), ACCURACY); // delta +-ACCURACY
    }




    public static Vector3dInterface[] simulateOneYear() {

        double day = 24*60*60;
        double halfDay = 12*60*60;
        double oneHour = 60*60;
        double year = 365.25*day;
        double stepSize = 48;
        ODESolver solver = new ODESolver(halfDay);
        StateInterface[] allStates = solver.getStates();
        System.out.println("LENGTH "+allStates.length);
        Vector3dInterface[] lastPosition = ((State) allStates[allStates.length-1]).getCoordinates();
        return lastPosition;

    }

}
