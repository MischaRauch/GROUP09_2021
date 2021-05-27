/**
 * Class containing main method. Only variable specified here is the step size.
 *
 *      - Creates an object of the ODESolver class to calculate all the different
 *        coordinates for the planets.
 *      - Creates an object of the ProbeSimulator class to calculate all the different
 *        coordinates for the probe.
 *      - Creates an object of the Frame class through which the animations are shown.
 */


public class RunCode {

    private static final double stepSize = 100;
    //private static final double stepSize = (24*60*60);

    public static void main(String[] args) {
        ODESolver solver = new ODESolver(stepSize); // 6000

        //HillClimbing climber = new HillClimbing(solver, 365.25*(24*60*60),stepSize);

        Frame frame = new Frame(solver.getStates());
    }

}
