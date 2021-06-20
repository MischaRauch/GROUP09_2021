public class LandingThrust {
    private final double maxThrust = 3e7;
    private final double landerMass = 6e3;
    private final double vE = 2e4;
    private double fuelMass;
    private double velocity;

    public LandingThrust(double initialFuelMass) {
        this.fuelMass = initialFuelMass;
    }

    public void calculateThrust(double percentage){
        double thrust = maxThrust * percentage;
        double totalMass = landerMass + fuelMass;
        double acceleration  = thrust / totalMass;
        setVelocity(velocity + acceleration);
        double usedMass = thrust / vE;
        fuelMass -= usedMass;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getFuelMass() {
        return fuelMass;
    }

    public void setFuelMass(double fuelMass) {
        this.fuelMass = fuelMass;
    }
}
