import src.main.java.titan.Vector3dInterface;

public class SpaceShuttle{
    private double fuelMass;
    private double probeMass;
    private final Vector3dInterface maxThrust = new Vector3d(15000,15000,15000);
    private double totalM;
    private boolean noMass;
    private Vector3dInterface velocity;
    private Vector3dInterface coordinate;
    //private double burnRate = 0.0000005;
    //private Vector3dInterface ve = new Vector3d(2e4,2e4,2e4); // effective exhaust velocity
    //private int stepsize;
    //private int secondsInYear = 31536000;

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
        //System.out.println("The thrust is: "+ thrust);
        //System.out.println("The acceleration is: "+ acceleration);
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
        //System.out.println("The used fuel mass is: " + usedMass);
        fuelMass = fuelMass - usedMass;
        if(fuelMass==0 || fuelMass<0){
            noMass = true;
        }
        totalM = probeMass + fuelMass;
        //System.out.println("The total mass is: " + totalM);
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
    /**
     * Computes the total mass of the shuttle (the mass of the probe + the mass of the fuel).
     *
    public double totalMass(double massOfProbe, double massOfFuel) {
        return massOfProbe + massOfFuel;
    }

    /** Computes the mass flow rate (ṁ) using the formulas:
        m(t) = M + m * (1 - burnRate)^t; ṁ = dm/dt = m * (-1) * (1 - burnRate)^t * ln((1 - burnRate))
     *
     *
    public double[] massFlowRate(double fuelMass, double burnRate, int secondsInYear) {

        double[] ṁ = new double[stepsize];

        for(int i = 0; i<stepsize; i++) {
            ṁ[i] = fuelMass * (-1) * Math.pow((1 - burnRate), secondsInYear) * Math.log((1 - burnRate));
            //System.out.println(ṁ[i]);
            fuelMass = fuelMass * (1 - burnRate);
        }

        return ṁ;
    }

    /**
     * Computes the thrust of the probe by using the formula t = ve * ṁ.
     *
    public Vector3dInterface[] calculateF() {

        double[] ṁ = massFlowRate(fuelMass, burnRate, secondsInYear);
        thrust = new Vector3dInterface[stepsize];

        for(int i = 0; i<ṁ.length; i++) {
        	thrust[i] = ve.mul(ṁ[i]);
        }

        return thrust;

    }

    /**
     * Computes the accelerations.
     *
    public Vector3dInterface[] calculateAccelerations() {

       	calculateF();
        Vector3dInterface[] a  = new Vector3dInterface[thrust.length];
        double totalMass = totalMass(probeMass, fuelMass);

        for(int i = 0; i<a.length; i++) {
            a[i] = new Vector3d(thrust[i].getX()/totalMass, thrust[i].getY()/totalMass, thrust[i].getZ()/totalMass);
 			totalMass = probeMass + fuelMass*Math.pow((1 - burnRate), i+1);
 			//System.out.println(a[i]);
        }

        return a;
    }

    /**
     * Computes the thrust by using the formula Ma = F.
     *
     public Vector3dInterface[] computeThrust() {

        fuelMass = fuelMass * (1 - burnRate); //notice that fuelMass will change at each step.
        double allMass = probeMass + fuelMass;
        thrust = new Vector3dInterface[acceleration.length];

        for(int i = 0; i<acceleration.length; i++) {
        thrust[i] = acceleration[i].mul(allMass);
        //System.out.println(thrust[i].toString());
        }

        return thrust;
    }*/
}
