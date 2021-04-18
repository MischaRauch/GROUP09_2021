import titan.ProbeSimulatorInterface;
import titan.StateInterface;
import titan.Vector3dInterface;

public class ProbeSimulator implements ProbeSimulatorInterface {

    private Vector3dInterface[] coordinatesProbe;

    private final StateInterface[] states;
    private final double mass = 15e3;
    private final double G = 6.674 * Math.pow(10, -11);

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
        coordinates[0] = p0;

        for(int i = 1; i < coordinates.length; i++) {
            t += h;
            Vector3dInterface newCoordinates = coordinates[i-1];
            State state = (State) states[i];
            Vector3dInterface force = calculateF(newCoordinates, state.getCoordinates());
            v0 = v0.add(calculateRate(force, h));
            newCoordinates = newCoordinates.addMul(h, v0);
            coordinates[i] = newCoordinates;
        }

        return coordinates;
    }
}
