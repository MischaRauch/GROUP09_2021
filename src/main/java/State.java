
import src.main.java.titan.RateInterface;
import titan.ODEFunctionInterface;
import titan.StateInterface;
import src.main.java.titan.Vector3dInterface;

import src.main.java.Vector3d;

import javax.rmi.ssl.SslRMIClientSocketFactory;

/**
 * Class which represents the state of a system. Contains the coordinates and velocities of all objects in the system.
 */
public class State implements StateInterface {

    // Arrays containing the coordinates and the velocities of all objects at a specific time
    private Vector3dInterface[] coordinates;
    private Vector3dInterface[] velocities;
    private final double time;                  // The time which the state represents

    // The masses for all planets
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
    private final double probeM = 15e3;

    // Array containing the masses for all planets
    public final double[] masses = {sunM, mercuryM, venusM, earthM, moonM, marsM, jupiterM, saturnM, titanM, uranusM, neptuneM, probeM};

    /**
     * Constructor for the State class.
     * @param coordinates   array containing the coordinate vectors of each object in the system
     * @param velocities    the velocity vectors of each object in the system
     * @param time          the time which the state represents
     */
    public State(Vector3dInterface[] coordinates, Vector3dInterface[] velocities, double time) {
        this.coordinates = coordinates;
        this.velocities = velocities;
        this.time = time;
    }

    /**
     * Getter method
     * @return an array containing the coordinate vectors of each object in the system at time this.time
     */
    public Vector3dInterface[] getCoordinates() {
        return coordinates;
    }

    /**
     * Getter method
     * @return an array containing the velocity vectors of each object in the system at time this.time
     */
    public Vector3dInterface[] getVelocities() {
        return coordinates;
    }

    /**
     * Getter method
     * @return the time of the State object it is called on.
     */
    public double getTime() {
        return time;
    }

    /**
     * Getter method
     * @return an array containing the masses of all objects in the system.
     */
    public double[] getMasses() {
        return masses;
    }

    /**
     * Update a state to a new state computed by: this + step * rate
     *
     * @param step      The time-step of the update
     * @param r         The average rate-of-change over the time-step. Has dimensions of [state]/[time].
     * @return The new state after the update.
     */
    @Override
    public StateInterface addMul(double step, RateInterface r) {

        Rate rate = (Rate) r;

        // Creates copies of the previous states coordinates
        Vector3dInterface[] newCoordinates = new Vector3d[coordinates.length];
        System.arraycopy(this.coordinates, 0, newCoordinates, 0, newCoordinates.length);
        Vector3dInterface[] newVelocities = new Vector3d[velocities.length];
        System.arraycopy(this.velocities, 0, newVelocities, 0, newVelocities.length);

        Vector3dInterface[] rates = rate.getRates(); // Retrieves the rates-of-change and stores them inside an array

        // For loop which calculates the new velocities for each body in the system and updates the newVelocities array
        for(int i = 0; i < newVelocities.length; i++) {
            newVelocities[i] = newVelocities[i].add(rates[i]);
        }

        // For loop which calculates the new coordinates for each body in the system and updates the newCoordinates array
        for(int i = 0; i < newCoordinates.length; i++) {
            //newCoordinates[i] = newCoordinates[i].addMul(step, newVelocities[i]);
            newCoordinates[i] = newCoordinates[i].addMul(step,velocities[i]);
        }

        return new State(newCoordinates, newVelocities, this.time+step);    // Returns the new state after the update
    }

    public StateInterface addMulVerlet(double step, Vector3dInterface[] accelerations, ODEFunctionInterface f) {

        // Creates copies of the previous states coordinates
        Vector3dInterface[] newCoordinates = new Vector3d[coordinates.length];
        System.arraycopy(this.coordinates, 0, newCoordinates, 0, newCoordinates.length);
        Vector3dInterface[] newVelocities = new Vector3d[velocities.length];
        System.arraycopy(this.velocities, 0, newVelocities, 0, newVelocities.length);


        for(int i = 0; i < newCoordinates.length; i++) {
            //newCoordinates[i] = newCoordinates[i].addMul(step, velocities[i]).addMul(0.5*Math.pow(step,2), accelerations[i]);
            newCoordinates[i] = coordinates[i].addMul(step,velocities[i]).addMul(0.5*Math.pow(step,2), accelerations[i]);
        }

        Vector3dInterface[] accelerationsNew = ((ODEFunction) f).callA(this.time+step,this);

        for(int i = 0; i < newVelocities.length; i++) {
            //newVelocities[i] = newVelocities[i].add(((accelerations[i].add(accelerationsNew[i])).mul(0.5)).mul(step));
            newVelocities[i] = velocities[i].add(((accelerations[i].add(accelerationsNew[i])).mul(0.5).mul(step)));
        }

        return new State(newCoordinates, newVelocities, this.time+step);
    }
}
