import src.main.java.titan.Vector3dInterface;

/**
 * This class represents the rockets engines and can be used for utilising the engines during steps of a simulation.
 */
public class SpaceShuttle{
    private final double maxThrust = 3e7;
    private final int secondsPreStep = 100;
    //private double vE = 2e4;
    private double fuelMass;
    private double probeMass;
    private Vector3dInterface velocity;
    private Vector3dInterface coordinate;
    private Vector3dInterface direction;

    public SpaceShuttle(double massOfProbe, double initialFuelMass, Vector3dInterface coordinate, Vector3dInterface velocity) {
        this.fuelMass = initialFuelMass;
        this.probeMass = massOfProbe;
        this.velocity = velocity;
        this.coordinate = coordinate;
        direction = ((Vector3d) velocity).normalize();
    }

    /**
     * Computes the acceleration for one step.
     */
    public Vector3dInterface calculateThrust(double percentage){
        double thrust = maxThrust * percentage;
        double totalMass = fuelMass + probeMass;
        direction = ((Vector3d) velocity).normalize();
        //System.out.println("the force is: "+thrust);
        //System.out.println("the total mass: " + totalMass);
        Vector3dInterface acceleration = direction.mul(thrust / totalMass);
        //System.out.println("The acceleration is :" + acceleration);
        Vector3dInterface newVelocity = getVelocity().add(acceleration);
        //System.out.println("The velocity is :" + newVelocity.norm());
        setVelocity(newVelocity);
        double usedMass = thrust / newVelocity.norm();
        //System.out.println("Used mass: " + usedMass * secondsPreStep);
        fuelMass -= usedMass * secondsPreStep;
        return acceleration;
    }

    public Vector3dInterface calculateThrustWithRotate(double percentage, Vector3dInterface v, double angle){
        double thrust = maxThrust * percentage;
        double totalMass = fuelMass + probeMass;
        //System.out.println("the force is: "+thrust);
        //System.out.println("the total mass: " + totalMass);
        Vector3dInterface acceleration = direction.mul(thrust / totalMass);
        acceleration = rotation(acceleration, v, angle);
        System.out.println("The acceleration is :" + acceleration);
        Vector3dInterface newVelocity = getVelocity().add(acceleration);
        System.out.println("The velocity is :" + newVelocity.norm());
        setVelocity(newVelocity);
        double usedMass = thrust / newVelocity.norm();
        System.out.println("Used mass: " + usedMass * secondsPreStep);
        fuelMass -= usedMass * secondsPreStep;
        return acceleration;

    }

    public Vector3dInterface getVelocity() {
        return velocity;
    }

    public double getFuelMass() {
        return fuelMass;
    }

    public void setFuelMass(double fuelMass) {
        this.fuelMass = fuelMass;
    }

    //first vector v1 be able to change direction a set number of degrees towards the second vector v2
    public Vector3dInterface rotation(Vector3dInterface v1, Vector3dInterface v2, double angle){
        Vector3dInterface c = ((Vector3d) v1).crossProduct(v2);
        c = ((Vector3d) c).normalize();
        Vector3dInterface f = ((Vector3d) c).crossProduct(v1);
        Vector3dInterface x = v1.mul(Math.cos(Math.toRadians(angle))).add(f.mul(Math.sin(Math.toRadians(angle))));
        return x;
    }
}
