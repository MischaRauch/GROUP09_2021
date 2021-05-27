import src.main.java.titan.Vector3dInterface;

/**
 * This class represents the rockets engines and can be used for utilising the engines during steps of a simulation.
 */
public class SpaceShuttle{
    private double fuelMass;
    private double probeMass;
    private final Vector3dInterface maxThrust = new Vector3d(15000,15000,15000);
    private double totalM;
    private boolean noMass;
    private Vector3dInterface velocity;
    private Vector3dInterface coordinate;

    public SpaceShuttle(double massOfProbe, double initialFuelMass, Vector3dInterface coordinate, Vector3dInterface velocity) {
        probeMass = massOfProbe;
        fuelMass = initialFuelMass;
        totalM = probeMass + fuelMass;
        noMass = false;
        this.coordinate = coordinate;
        this.velocity = velocity;
    }

    /**
     * Computes the acceleration for one step.
     */
    public Vector3dInterface calculateThrust(double percentage){
        if(noMass) {
            System.out.println("The fuel has run out");
            return null;
        }
        Vector3dInterface thrust = maxThrust.mul(percentage);
        Vector3dInterface acceleration = thrust.mul(1/totalM);
        Vector3dInterface newVelocity = velocity.add(acceleration);
        this.setVelocity(newVelocity);
        reduceMass(thrust, newVelocity);
        return acceleration;
    }

    /**
     * Computes the used mass of the fuel by using the formula: m = F/V
     */
    public void reduceMass(Vector3dInterface force, Vector3dInterface velocity){
        double usedMass = force.norm() / velocity.norm();
        fuelMass = fuelMass - usedMass;
        if(fuelMass==0 || fuelMass<0){
            noMass = true;
        }
        totalM = probeMass + fuelMass;
    }

    public double getTotalMass(){
        return totalM;
    }

    public double getFuelMass(){
        return fuelMass;
    }

    public Vector3dInterface getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3dInterface velocity) {
        this.velocity = velocity;
    }

    public Vector3dInterface getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Vector3dInterface coordinate) {
        this.coordinate = coordinate;
    }
}
