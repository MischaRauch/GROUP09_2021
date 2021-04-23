import titan.RateInterface;
import titan.StateInterface;
import titan.Vector3dInterface;

public class State implements StateInterface {

    private Vector3dInterface[] coordinates;
    private Vector3dInterface[] velocities;

    private final double sunM = 1.988500e30;
    private final double mercuryM = 3.302e23;
    private final double venusM = 4.8685e24;
    private final double earthM = 5.97219e24;
    private final double moonM = 7.349e22;
    private final double marsM = 6.4171e23;
    private final double jupiterM = 1.89813e27;
    private final double saturnM = 5.6834e26;
    private final double titanM = 1.34553e23;
    private final double uranusM = 8.6813e25;
    private final double neptuneM = 1.02413e26;
    private final double time;

    public final double[] masses = {sunM, mercuryM, venusM, earthM, moonM, marsM, jupiterM, saturnM, titanM, uranusM, neptuneM};

    public State(Vector3dInterface[] coordinates, Vector3dInterface[] velocities, double time) {
        this.coordinates = coordinates;
        this.velocities = velocities;
        this.time = time;
    }

    public Vector3dInterface[] getCoordinates() {
        return coordinates;
    }

    public double getTime() {
        return time;
    }

    @Override
    public StateInterface addMul(double step, RateInterface r) {

        Rate rate = (Rate) r;

        Vector3dInterface[] newCoordinates = new Vector3d[11];
        System.arraycopy(this.coordinates, 0, newCoordinates, 0, newCoordinates.length);
        Vector3dInterface[] newVelocities = new Vector3d[11];
        System.arraycopy(this.velocities, 0, newVelocities, 0, newVelocities.length);

        Vector3dInterface[] rates = rate.getRates();

        for(int i = 0; i < newVelocities.length; i++) {
            newVelocities[i] = newVelocities[i].add(rates[i]);
        }

        for(int i = 0; i < newCoordinates.length; i++) {
            newCoordinates[i] = newCoordinates[i].addMul(step, newVelocities[i]);
        }

        return new State(newCoordinates, newVelocities, this.time+step);
    }

    public StateInterface addMulRK(double step, RateInterface r) {

        Rate rate = (Rate) r;

        Vector3dInterface[] newCoordinates = new Vector3d[11];
        System.arraycopy(this.coordinates, 0, newCoordinates, 0, newCoordinates.length);
        Vector3dInterface[] newVelocities = new Vector3d[11];
        System.arraycopy(this.velocities, 0, newVelocities, 0, newVelocities.length);

        Vector3dInterface[] rates = rate.getRates();

        for(int i = 0; i < newVelocities.length; i++) {
            newVelocities[i] = newVelocities[i].add(rates[i]);
        }

        for(int i = 0; i < newCoordinates.length; i++) {
            newCoordinates[i] = newCoordinates[i].addMul(step, newVelocities[i]);
        }

        return new State(newCoordinates, newVelocities, this.time+time);
    }
}
