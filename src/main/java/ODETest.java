import titan.ODEFunctionInterface;
import titan.StateInterface;
import src.main.java.titan.Vector3dInterface;

import java.util.Arrays;

public class ODETest{

    private final int steps = 11;

    private final double w0 = 1;

    public ODETest(double h) {
        ODEFunction f = new ODEFunction();
        runRKTest(f, w0, h, steps);
    }

    private void runRKTest(ODEFunction f, double w0, double h, int steps) {
        System.out.println("Running Runge-Kutta test: ");
        double[] ws = new double[steps];
        ws[0] = w0;
        double t = 0;
        for(int i = 1; i < steps; i++) {
            ws[i] = RKstep(f, t, ws[i-1], h);
            t = t + h;
        }
        System.out.println(Arrays.toString(ws));
    }

    public double RKstep(ODEFunction f, double t, double y, double h) {
        // Creates rate objects for each k of the RK method
        double ki1 = f.simpleCall(t, y);
        double ki2 = f.simpleCall(t + 0.5*h, y + ((h*0.5)*ki1));
        double ki3 = f.simpleCall(t + 0.5*h, y + ((h*0.5)*ki2));
        double ki4 = f.simpleCall(t + h, y + (h * ki3));

        // Combines the ki's
        double kitot = (ki1 + (2*ki2) + (2*ki3) + (ki4))*(1.0/6.0);
        return y + (h * kitot);
    }
}
