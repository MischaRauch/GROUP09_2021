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

        SelectionMenu menu = new SelectionMenu();

        //ODETest test = new ODETest(0.5);
        //ODESolver solver = new ODESolver(stepSize); // 6000

        //Frame frame = new Frame(solver.getStatesYearOne());

        //Frame frame = new Frame(solver.getStates());

            // The line below can be used for running/testing the hill climbing algorithm
        //HillClimbing climber = new HillClimbing(solver, 1.5*(365.25*(24*60*60)), 1.5*stepSize);

        //LandingViewer landingViewer = new LandingViewer();

        //WindModel wM = new WindModel();
        //wM.calculateFall(0.1,471.1, new Vector3d(0,150000,0), new Vector3d(0,0,0));
        //wM.performFullFalling(0.1,471.1, new Vector3d(0,150000,0), new Vector3d(0,0,0));
    }

    public static void runFrame2() {
        Frame frame2 = new Frame(ODESolver.getStates());
    }
}
