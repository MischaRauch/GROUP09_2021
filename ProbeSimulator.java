import titan.*;

public class ProbeSimulator implements ProbeSimulatorInterface {

    private Vector3dInterface[] coordinatesProbe;

    private final StateInterface[] states;
    private static final double mass = 15e3;
    private static final double G = 6.674 * Math.pow(10, -11);

    private double[] masses = new double[11];

    public ProbeSimulator(StateInterface[] states, double h) {
        this.states = states;
        State state = (State) states[0];
        this.masses = state.masses;

        this.coordinatesProbe = trajectory(new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10+6371e3, 8.278183193596080e+06), new Vector3d(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01), 31536000, h);
    }

    public Vector3dInterface[] getCoordinatesProbe() {
        return coordinatesProbe;
    }

    private Vector3dInterface calculateF(Vector3dInterface coordinatesProbe, Vector3dInterface[] coordinates) {
        Vector3dInterface force = new Vector3d(0,0,0);
        // Loops through all the bodies and adds the force of each body on the probe to the total force
        for(int i = 0; i < coordinates.length; i++) {
            double mm = (masses[i] * mass);
            double distance = coordinatesProbe.dist(coordinates[i]);
            Vector3dInterface unitVector = (coordinatesProbe.sub(coordinates[i])).mul(1/distance);
            force = force.add(unitVector.mul(-G * mm/Math.pow(distance, 2)));
        }
        return force;
    }

    private Vector3dInterface calculateRate(Vector3dInterface force, double h) {
        Vector3dInterface rate = new Vector3d(force.getX()/mass, force.getY()/mass, force.getZ()/mass);
        rate = rate.mul(h);
        return rate;
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        return new Vector3dInterface[0];
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        int t = 0;
        Vector3dInterface[] coordinates = new Vector3dInterface[(int) ((tf/h)+1)];
        Vector3dInterface v = v0;
        coordinates[0] = p0;

        for(int i = 1; i < coordinates.length; i++) {
            t += h;
            State state = (State) states[i-1];
            Vector3dInterface force = calculateF(coordinates[i-1], state.getCoordinates());
            Vector3dInterface rate = calculateRate(force, h);
            v = v.add(rate);
            coordinates[i] = coordinates[i-1].addMul(h, v);
        }

        return coordinates;
    }

    public StateInterface RKstep(ODEFunctionInterface f, double t, StateInterface y, double h) {
        // Creates rate objects for each k of the RK method
        Rate ki1 = (Rate) f.call(t, y);
        Rate ki2 = (Rate) f.call(t + 0.5*h, y.addMul(h*0.5, ki1));
        Rate ki3 = (Rate) f.call(t + 0.5*h, y.addMul(h*0.5, ki2));
        Rate ki4 = (Rate) f.call(t + h, y.addMul(h, ki3));

        // Combines the ki's
        RateInterface kitot = (ki1.addMul(2, ki2).addMul(2, ki3).addMul(1, ki4)).mul(1.0/6.0);
        return y.addMul(h, kitot);
    }

    /* EULER'S METHOD
        int t = 0;

        Vector3dInterface[] coordinates = new Vector3dInterface[(int) ((tf/h)+1)];
        Vector3dInterface v = v0;
        coordinates[0] = p0;

        for(int i = 1; i < coordinates.length; i++) {
            t += h;
            State state = (State) states[i-1];
            Vector3dInterface force = calculateF(coordinates[i-1], state.getCoordinates());
            Vector3dInterface rate = calculateRate(force, h);
            v = v.add(rate);
            coordinates[i] = coordinates[i-1].addMul(h, v);
        }

        return coordinates;
     */

    /* BAD RK
        int t = 0;

        Vector3dInterface[] coordinates = new Vector3dInterface[(int) ((tf/h)+1)];
        Vector3dInterface v = v0;
        coordinates[0] = p0;

        for(int i = 1; i < coordinates.length; i++) {
            t += h;
            State state = (State) states[i-1];
            Vector3dInterface force = calculateF(coordinates[i-1], state.getCoordinates());
            Vector3dInterface rate1 = calculateRate(force, h);
            Vector3dInterface cTemp = coordinates[i-1].addMul(h*0.5, rate1);
            force = calculateF(cTemp, state.getCoordinates());
            Vector3dInterface rate2 = calculateRate(force, h*1.5);
            cTemp = coordinates[i-1].addMul(h*0.5, rate2);
            force = calculateF(cTemp, state.getCoordinates());
            Vector3dInterface rate3 = calculateRate(force, h*1.5);
            cTemp = coordinates[i-1].addMul(h, rate3);
            force = calculateF(cTemp, state.getCoordinates());
            Vector3dInterface rate4 = calculateRate(force, h*2);

            Vector3dInterface rateTot = rate1.addMul(2, rate2).addMul(2, rate3).addMul(1, rate4).mul(1.0/6.0);

            v = v.add(rateTot);
            coordinates[i] = coordinates[i-1].addMul(h, v);
        }

        return coordinates;
     */

    /* ATTEMPT AT GOOD RK
        int t = 0;

        StateInterface[] statesWithProbe = new StateInterface[(int) ((tf/h)+1)];

        Vector3dInterface[] probeCoordinates = new Vector3dInterface[(int) ((tf/h)+1)];

        State s0 = (State) states[0];
        Vector3dInterface[] coordinates = s0.getCoordinates();
        Vector3dInterface[] velocities = s0.getVelocities();

        Vector3dInterface[] coordinatesWithProbe = new Vector3dInterface[coordinates.length+1];
        Vector3dInterface[] velocitiesWithProbe = new Vector3dInterface[velocities.length+1];

        System.arraycopy(coordinates, 0, coordinatesWithProbe, 0, coordinates.length);
        System.arraycopy(velocities, 0, velocitiesWithProbe, 0, velocities.length);

        coordinatesWithProbe[coordinatesWithProbe.length-1] = p0;
        velocitiesWithProbe[velocitiesWithProbe.length-1] = v0;

        probeCoordinates[0] = coordinatesWithProbe[coordinatesWithProbe.length-1];
        s0 = new State(coordinatesWithProbe, velocitiesWithProbe, 0);
        statesWithProbe[0] = s0;
        ODEFunctionInterface f = new ODEFunction();

        for(int i = 1; i < states.length; i++) {
            t += h;
            statesWithProbe[i] = RKstep(f, t, statesWithProbe[i-1], h);
            State curState = (State) statesWithProbe[i];
            Vector3dInterface[] curCoordinates = curState.getCoordinates();
            probeCoordinates[i] = curCoordinates[curCoordinates.length-1];
        }

        return probeCoordinates;
     */
}
