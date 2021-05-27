

import src.main.java.titan.Vector3dInterface;
import titan.ProbeSimulatorInterface;
import titan.StateInterface;
import titan.ODEFunctionInterface;

/**
 * This simulation is the class on which we ran the provided tests. If you want to test our simulations please use
 * the second trajectory method inside this class.
 */
public class ProbeSimulator implements ProbeSimulatorInterface {

    private Vector3dInterface[] coordinatesProbe;

    private final StateInterface[] states;
    private static final double mass = 15e3;
    private static final double G = 6.674 * Math.pow(10, -11);

    private double[] masses = new double[11];

    /**
     * Constructor for running tests
     */
    public ProbeSimulator() {
        states = new StateInterface[2];
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        return new Vector3dInterface[0];
    }

    /**
     * Method on which to run the tests! Calculates the trajectory of the probe and updates the states with the recalculated trajectory.
     *
     * @param p0 starting coordinates !RELATIVE TO EARTH!
     * @param v0 starting velocity !RELATIVE TO EARTH!
     * @param tf the final time
     * @param h the sizeof step to be taken
     * @return an array of the coordinates of the probe !RELATIVE TO THE SOLAR SYSTEM BARYCENTRE!
     */
    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        //create reference Objects
        ODEFunctionInterface f = new ODEFunction();
        ODESolver ode = new ODESolver();
        //set up the data
        Vector3dInterface[] probeCoordinates = new Vector3dInterface[(int) ((tf/h)+2)];
        Vector3dInterface[] coordinates = ode.getCoordinates();
        Vector3dInterface[] velocities = ode.getVelocities();
        coordinates[11] = coordinates[3].add(p0);
        velocities[11] = velocities[3].add(v0);
        probeCoordinates[0] = coordinates[11];
        //set up the storing values for the calculation
        StateInterface[] states = new StateInterface[(int) ((tf/h)+2)];
        states = new StateInterface[(int) ((tf/h)+2)];
        states[0] = new State(coordinates, velocities, 0);

        int t = 0;
        for(int i = 1; i < states.length; i++) {
            //for each step perform the chossen step and set the value
            t += h;
            //step = Eulers; RKstep = Runge-Kutte; verletStep = Verlet solver
            states[i] = ode.RKstep(f, t, states[i-1], h);
            State state = (State) states[i];
            probeCoordinates[i] = state.getCoordinates()[11];
        }
        return probeCoordinates;
    }
}
