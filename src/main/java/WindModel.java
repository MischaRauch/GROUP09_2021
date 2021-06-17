import src.main.java.titan.Vector3dInterface;

public class WindModel {

    //private final double g = 9.807;
    //private final double g = 10;
    private final double g = 1.352;

    private int windMax120kmTo100km = 120;
    private int windMax60kmTo40km = 20;
    private int windMax40kmTo3km = 2;
    private int eastWind = -1;
    private int westWind = 1;
    private int windMax = 70;
    private int windMin = 30;

    boolean thrusterCheck = false;
    private double thrusterChange = 0;

    //thought about splitting the progress up into different parts - to complicated for now
    /*
    public SingleState[] performFullFalling(double h, double tf, Vector3d x0, Vector3d v0) {
        SingleState[] states = calculateFall(h,tf,x0,v0);
        SingleState[] newStates = addThruster(states,h,tf);
        return states;
    }
    /*

     */
    /**
     * Function which returns the positions after each step (h) for the timespan of tf
     * @param h - the timestep
     * @param tf - the final time
     * @return an State array storing the positions of the Landing Module
     */
    public SingleState[] calculateFall(double h, double tf, Vector3d x0, Vector3d v0) {
        SingleState[] states = new SingleState[(int) ((tf/h))];
        double step = h;
        System.out.println("States Length: "+states.length);
        Vector3dInterface initalVelocity = v0;
        Vector3dInterface initalPosition = x0;
        states[0] = new SingleState(x0, v0, step);
        step += h;
        System.out.println(x0.getY());
        for (int i = 1; i < states.length; i++) {
            //calculate new Velocity
            Vector3dInterface newVelocity = initalVelocity.add(new Vector3d(0, g*step,0));
            //calculate new position - altitude
            double tmp = initalVelocity.getY()*step;
            Vector3dInterface tmpVelocity = new Vector3d(initalVelocity.getX(), tmp, initalVelocity.getZ());
            double initAlitude = initalPosition.getY();
            double newAlitude = initAlitude-((Math.pow(step,2)*g)*0.5);
            Vector3dInterface finalPositon = tmpVelocity.add(new Vector3d(states[i-1].getCoordinates().getX(),newAlitude,0));

            //before storing the new position apply some wind
            double windForce = applyWind(finalPositon);
            //System.out.println("before: "+finalPositon.getX());
            finalPositon.setX(finalPositon.getX()+windForce);
            //System.out.println("after: "+finalPositon.getX());

            //take thruster into account
            double newY = applyThruster(finalPositon.getY(), h);
            //System.out.println("ADDED Y"+newY);
            finalPositon.setY(finalPositon.getY()+newY);



            //correct trajectory with feedback controler
            if ((i % 20) == 0) {
                //double correction = correctTrajectory(finalPositon.getX(), states[0].getCoordinates().getX());
                //double correction = correctTrajectory(finalPositon.getX(), initalPosition.getX());
                //finalPositon.setX(finalPositon.getX()+correction);
            }
            System.out.println("POSITION: "+finalPositon.getY());
            states[i] = new SingleState(finalPositon,newVelocity,step);
            step+=h;
        }
        System.out.println("States prev prev: "+states[states.length-2].getCoordinates().getY());
        System.out.println("States prev: "+states[states.length-1].getCoordinates().getY());
        System.out.println("States change of x: "+states[states.length-1].getCoordinates().getX());
        System.out.println("States change of x in km: "+states[states.length-1].getCoordinates().getX()/1000);
        System.out.println("Velocity 1: "+states[2].getVelocity());
        System.out.println("Velocity last: "+states[states.length-1].getVelocity());
        return states;
    }

    /**
     * Calculate the wind force based on the given altitude
     * @param currentPosition - the altitude in meters
     * @return a double value representing the wind strength
     */
    public double applyWind(Vector3dInterface currentPosition  ) {
        double strength = Math.random()*(windMax-windMin+1)+windMin;
        strength = strength/100;
        //System.out.println(strength);
        double changeOfPosition = 0;
        //System.out.println("POSITION "+currentPosition.getY());
        //check which altitude we have and calculate wind Strength
        if (120000 >= currentPosition.getY() && currentPosition.getY() >= 100000) {
            changeOfPosition = strength*windMax120kmTo100km;
            changeOfPosition = changeOfPosition*westWind;
        }
        else if (60000 >= currentPosition.getY() && currentPosition.getY() >= 40000) {
            changeOfPosition = strength*windMax60kmTo40km;
            changeOfPosition = changeOfPosition*eastWind;
        }
        else if (40000 > currentPosition.getY() && currentPosition.getY() > 3000) {
            changeOfPosition = strength*windMax40kmTo3km;
            changeOfPosition = changeOfPosition*westWind;
        }
        if (changeOfPosition != 0) {
            //System.out.println("Change: "+changeOfPosition);
        }
        return changeOfPosition;
    }

    public double correctTrajectory(double xPos, double initalPosition) {
        double adjustment = 0;
        //calculate error term
        double eror = initalPosition - xPos;
        System.out.println("Error: "+eror);
        double stepSize = 0.1;

        if (eror > 0) {
            adjustment = -1*(eror*stepSize); //20 m/s
        }
        else if (eror < 0) {
            adjustment = (eror*stepSize); //20 m/s
        }
        return adjustment;
    }

    /*
    public SingleState[] addThruster(SingleState[] currentStates, double h, double tf) {
        SingleState[] newPositions = new SingleState[currentStates.length];
        //calculate the ammount of position change based on the stepsize
        double changePerSecond = 3;
        changePerSecond = changePerSecond*h;
        for (int i = 0; i < currentStates.length; i++) {
            if (currentStates[i].getCoordinates().getY() < (60000)) {
                //change position
                double prevPosition = currentStates[i-1].getCoordinates().getY();
                currentStates[i].getCoordinates().setY(prevPosition+changePerSecond);
                System.out.println("Pos Y:"+ prevPosition+changePerSecond);
            }
        }
        return newPositions;
    }
    */

    /**
     * Method to start the engines to slow down the falling process
     * @param yPosition - position of the landing module
     * @param h - stepSize to calculate the change per step size in meters
     * @return - the change of meters which have to be added to the inital position
     */
    public double applyThruster(double yPosition, double h) {
        double finalChange = 0;

        if(yPosition < (60000)) {
            thrusterCheck = true;
        }
        if (thrusterCheck) {
           //Thruster power in meters per second
            double changePerSecond = 999;
            //calculate the change in meters per second depening on h (stepsize)
            changePerSecond = changePerSecond * h;
            finalChange = changePerSecond * thrusterChange;
            thrusterChange += h;
        }
        return finalChange;
    }


}
