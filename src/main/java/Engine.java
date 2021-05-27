import src.main.java.titan.Vector3dInterface;

public class Engine{
    private double probeMass;
    private double fuelMass;
    private double totalMass;
    private boolean noMass;

    public Engine(double probeMass, double initialFuelMass) {
        this.probeMass = probeMass;
        this.fuelMass = initialFuelMass;
        noMass = false;
    }

    /**
     * Computes the thrust for one step by F = M * A.
     * Then computes the fuel mass by F = V * mdot.
     */
    public void calculateThrust(Vector3dInterface acceleration, Vector3dInterface previousVelocity){
        if(noMass) {
            System.out.println("The fuel has run out");
        }
        Vector3dInterface newVelocity = previousVelocity.add(acceleration);
        double thrustForce = totalMass * acceleration.norm();
        //System.out.println("The thrust is: "+ thrustForce);
        double mDot = thrustForce / newVelocity.norm();
        //System.out.println("The used fuel mass is: " + mDot);
        reduceMass(mDot);
    }

    public void reduceMass(double mDot){
        fuelMass = fuelMass - mDot;
        if(fuelMass==0 || fuelMass<0){
            noMass = true;
        }
        totalMass = probeMass + fuelMass;
    }

    public double getFuelMass(){
        return fuelMass;
    }

    public double getTotalMass(){
        return totalMass;
    }
}
