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
        // Loops through all the other bodies and adds the force of each body on body i to the total force
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
        StateInterface[] states = new StateInterface[(int) ((tf/h)+1)];
        coordinates[0] = p0;
        Vector3dInterface[] p = {p0};
        Vector3dInterface[] v = {v0};
        states[0] = new State(p, v, t);
        ODEFunctionInterface f = new ODEFunction();

        for(int i = 1; i < coordinates.length; i++) {
            t += h;
            states[i] = RKstep(f, t, states[i-1], h);
            coordinates[i] = ((State)states[i]).getCoordinates()[0];
        }

        return coordinates;
    }

    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        RateInterface r = (Rate) f.call(t, y);
        return y.addMul(h, r);
    }

    public StateInterface RKstep(ODEFunctionInterface f, double t, StateInterface y, double h) {
        Rate ki1 = (Rate) f.call(t, y);
        Rate ki2 = (Rate) f.call(t + 0.5*h, y.addMul(h*0.5, ki1));
        Rate ki3 = (Rate) f.call(t + 0.5*h, y.addMul(h*0.5, ki2));
        Rate ki4 = (Rate) f.call(t + h, y.addMul(h, ki3));

        RateInterface kitot = (ki1.addMul(2, ki2).addMul(2, ki3).addMul(1, ki4)).mul(1.0/6.0);
        return y.addMul(h, kitot);
    }
}
