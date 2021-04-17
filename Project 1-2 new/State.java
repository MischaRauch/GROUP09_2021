import titan.RateInterface;
import titan.StateInterface;
import titan.Vector3dInterface;

public class State implements StateInterface {

    public Vector3dInterface[] coordinates;

    public Vector3dInterface[] velocities;

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

    public final double[] masses = {sunM, mercuryM, venusM, earthM, moonM, marsM, jupiterM, saturnM, titanM, uranusM, neptuneM};

    public State(Vector3dInterface[] coordinates, Vector3dInterface[] velocities) {
        this.coordinates = coordinates;
        this.velocities = velocities;
    }

    @Override
    public StateInterface addMul(double step, RateInterface r) {

        Rate rate = (Rate) r;

        Vector3dInterface[] newCoordinates = new Vector3d[11];
        for(int i = 0; i < newCoordinates.length; i++) {
            newCoordinates[i] = this.coordinates[i];
        }
        Vector3dInterface[] newVelocities = new Vector3d[11];
        for(int i = 0; i < newVelocities.length; i++) {
            newVelocities[i] = this.velocities[i];
        }

        newVelocities[0] = newVelocities[0].add(rate.sunR);
        newVelocities[1] = newVelocities[1].add(rate.mercuryR);
        newVelocities[2] = newVelocities[2].add(rate.venusR);
        newVelocities[3] = newVelocities[3].add(rate.earthR);
        newVelocities[4] = newVelocities[4].add(rate.moonR);
        newVelocities[5] = newVelocities[5].add(rate.marsR);
        newVelocities[6] = newVelocities[6].add(rate.jupiterR);
        newVelocities[7] = newVelocities[7].add(rate.saturnR);
        newVelocities[8] = newVelocities[8].add(rate.titanR);
        newVelocities[9] = newVelocities[9].add(rate.uranusR);
        newVelocities[10] = newVelocities[10].add(rate.neptuneR);

        newCoordinates[0] = newCoordinates[0].addMul(step, newVelocities[0]);
        newCoordinates[1] = newCoordinates[1].addMul(step, newVelocities[1]);
        newCoordinates[2] = newCoordinates[2].addMul(step, newVelocities[2]);
        newCoordinates[3] = newCoordinates[3].addMul(step, newVelocities[3]);
        newCoordinates[4] = newCoordinates[4].addMul(step, newVelocities[4]);
        newCoordinates[5] = newCoordinates[5].addMul(step, newVelocities[5]);
        newCoordinates[6] = newCoordinates[6].addMul(step, newVelocities[6]);
        newCoordinates[7] = newCoordinates[7].addMul(step, newVelocities[7]);
        newCoordinates[8] = newCoordinates[8].addMul(step, newVelocities[8]);
        newCoordinates[9] = newCoordinates[9].addMul(step, newVelocities[9]);
        newCoordinates[10] = newCoordinates[10].addMul(step, newVelocities[10]);

        return new State(newCoordinates, newVelocities);
    }
}
