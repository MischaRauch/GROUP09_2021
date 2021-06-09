import src.main.java.titan.Vector3dInterface;

public class Engine{
    private double probeMass;
    private double fuelMass;
    private double totalMass;
    private boolean noMass;
    private Vector3dInterface velocity;
    private Vector3dInterface coordinate;
    private final double burnRate = 0.0000005;
    private final double oneStepSeconds = 600;

    public Engine(double probeMass, double initialFuelMass, Vector3dInterface initialCoordinate, Vector3dInterface initialVelocity) {
        this.probeMass = probeMass;
        this.fuelMass = initialFuelMass;
        totalMass = probeMass + fuelMass;
        coordinate = initialCoordinate;
        velocity = initialVelocity;
        noMass = false;
    }

    public void calculateThrust(Vector3dInterface acceleration) {
        if (!noMass) {
            Vector3dInterface newVelocity = getVelocity().add(acceleration);
            System.out.println(newVelocity);
            setVelocity(newVelocity);
            System.out.println("The new velocity is " + newVelocity);
            reduceMass(oneStepSeconds);
        } else {
            System.out.println("The fuel has run out");
        }
    }

    //Computes the mass flow rate (ṁ) using the formulas:
    //m(t) = M + m * (1 - burnRate)^t; ṁ = dm/dt = m * (-1) * (1 - burnRate)^t * ln((1 - burnRate))
    public void reduceMass(double time){
        double ṁ = fuelMass * (-1) * Math.pow((1 - burnRate), time) * Math.log((1 - burnRate));
        double usedMass = ṁ * time;
        //System.out.println("The used mass is " + usedMass);
        fuelMass = fuelMass - usedMass;
        //System.out.println(fuelMass);
        if(fuelMass==0 || fuelMass<0){
            noMass = true;
        }else {
            totalMass = probeMass + fuelMass;
        }
    }

    public double getFuelMass(){
        return fuelMass;
    }

    public double getTotalMass(){
        return totalMass;
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
