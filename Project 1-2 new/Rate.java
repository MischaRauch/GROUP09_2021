import titan.ODEFunctionInterface;
import titan.RateInterface;
import titan.StateInterface;
import titan.Vector3dInterface;

public class Rate implements RateInterface, ODEFunctionInterface {

    public Vector3dInterface sunR;
    public Vector3dInterface mercuryR;
    public Vector3dInterface venusR;
    public Vector3dInterface earthR;
    public Vector3dInterface moonR;
    public Vector3dInterface marsR;
    public Vector3dInterface jupiterR;
    public Vector3dInterface saturnR;
    public Vector3dInterface titanR;
    public Vector3dInterface uranusR;
    public Vector3dInterface neptuneR;

    private final double G = 6.674 * Math.pow(10, -11);

    public Rate() {

    }

    private Vector3dInterface[] calculateF(Vector3dInterface[] coordinates, double[] masses) {
        Vector3dInterface[] forces = new Vector3dInterface[11];

        // Loops through all the bodies, where i is the body on which the total force is being calculated
        for(int i = 0; i < forces.length; i++) {

            Vector3dInterface totalF = new Vector3d(0,0,0);

            // Loops through all the other bodies and adds the force of each body on body i to the total force
            for(int j = 0; j < forces.length; j++) {
                if(i != j) {
                    double mm = (masses[i] * masses[j]);
                    double distance = coordinates[i].dist(coordinates[j]);
                    Vector3dInterface unitVector = (coordinates[i].sub(coordinates[j])).mul(1/distance);
                    totalF = totalF.add(unitVector.mul(-G * mm/Math.pow(distance, 2)));
                }
            }
            forces[i] = totalF;
        }
        return forces;
    }

    private Vector3dInterface[] calculateAccelerations(Vector3dInterface[] forces, double[] masses) {
        Vector3dInterface[] accelerations = new Vector3dInterface[11];
        for(int i = 0; i < accelerations.length; i++) {
            accelerations[i] = new Vector3d(forces[i].getX()/masses[i], forces[i].getY()/masses[i], forces[i].getZ()/masses[i]);
        }
        return accelerations;
    }

    private Vector3dInterface[] calculateRates(Vector3dInterface[] accelerations, double dt) {
        Vector3dInterface[] rates = new Vector3dInterface[11];
        for(int i = 0; i < rates.length; i++) {
            rates[i] = accelerations[i].mul(dt);
        }
        return rates;
    }

    @Override
    public RateInterface call(double t, StateInterface y) {
        //System.out.println("Performing call method.");

        State state = (State) y;

        Vector3dInterface[] forces = calculateF(state.coordinates, state.masses);
        Vector3dInterface[] accelerations = calculateAccelerations(forces, state.masses);
        Vector3dInterface[] rates = calculateRates(accelerations, t);

        sunR = rates[0];
        mercuryR = rates[1];
        venusR = rates[2];
        earthR = rates[3];
        moonR = rates[4];
        marsR = rates[5];
        jupiterR = rates[6];
        saturnR = rates[7];
        titanR = rates[8];
        uranusR = rates[9];
        neptuneR = rates[10];

        return this;
    }
}
