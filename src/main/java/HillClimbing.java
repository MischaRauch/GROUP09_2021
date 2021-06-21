import titan.ODEFunctionInterface;
import titan.StateInterface;
import src.main.java.titan.Vector3dInterface;

/**
 * Hill climbing class used for finding a trajectory to Titan.
 */
public class HillClimbing {

    // The initial state from which the hill climbing algorithm starts searching, the position vector is decided here.
    //private RocketState startState = new RocketState(new Vector3d(0,-6371e3,0), new Vector3d(29062.557220458984,-40935.659408569336,-546.1549758911133));
    private RocketState startState = new RocketState(new Vector3d(8.12300223421109E11,-1.2533483211800303E12,-1.0025476743448586E10), new Vector3d(0,0,0));

    /**
     * Constructor which starts the algorithm.
     * @param solver    solver used for performing simulations
     * @param tf        the final time for each simulation
     * @param h         the step size to use in each simulation
     */
    public HillClimbing(ODESolver solver, double tf, double h) {
        RocketState finalState = hillClimb(solver, startState, tf, h);
        //Vector3dInterface finalVelocity = hillClimbReturn(solver, tf, h);
    }


    private Vector3dInterface hillClimbReturn(ODESolver solver, double tf, double h) {
        StateInterface stateClosestToTitan = solver.getStateClosestToTitan();
        Vector3dInterface bestVelocity = new Vector3d(0,0,0);
        double curDist = Double.MAX_VALUE;
        ODEFunctionInterface f = new ODEFunction();

        int totCount = 0;

        System.out.println("- started hill climb algo -");

        double value = 30000;

        while(totCount < 50) {

            boolean increase = true;

            while(increase) {
                System.out.println("Trying with best velocity: " + bestVelocity);

                Vector3dInterface[] velocities = new Vector3dInterface[6];
                velocities[0] = bestVelocity.add(new Vector3d(value,0,0));
                velocities[1] = bestVelocity.add(new Vector3d(0,value,0));
                velocities[2] = bestVelocity.add(new Vector3d(0,0,value));
                velocities[3] = bestVelocity.add(new Vector3d(-value,0,0));
                velocities[4] = bestVelocity.add(new Vector3d(0,-value,0));
                velocities[5] = bestVelocity.add(new Vector3d(0,0,-value));

                double[] distances = new double[6];
                for(int i = 0; i < distances.length; i++) {
                    distances[i] = -1;
                }

                for(int i = 0; i < velocities.length; i++) {
                    if(velocities[i].norm() < 45e3) {
                        solver.solveProbe(f, stateClosestToTitan, tf, h, velocities[i]);
                        distances[i] = solver.getSmallestDistRocketEarth();
                        System.out.println("Running simulation with step type " + i + ".");
                    }
                }

                increase = false;

                for(int i = 0; i < distances.length; i++) {
                    if(distances[i] != -1 && distances[i] < curDist) {
                        curDist = distances[i];
                        bestVelocity = velocities[i];
                        increase = true;
                    }
                }


                if(!increase) {
                    System.out.println("Found new best distance: " + curDist);
                    System.out.println("Related velocity: " + bestVelocity);
                }
            }

            value = value * 0.5;

            System.out.println("Lowered value to: " + value);

            totCount++;
        }

        System.out.println(bestVelocity);

        return bestVelocity;
    }

    /**
     * Performs simulations while slightly altering the velocity every time to find a trajectory to titan.
     * @param solver        solver used for performing simulations
     * @param startState    the initial state from which the algorithm will start
     * @param tf            the final time for each simulation
     * @param h             the step size to use in each simulation
     * @return the starting state which got the probe/rocket closest to Titan
     */
    private RocketState hillClimb(ODESolver solver, RocketState startState, double tf, double h) {
        RocketState bestState = startState;
        double curDist = Double.MAX_VALUE;

        int totCount = 0;

        double value = 2000;

        while(totCount < 50) {

            boolean increase = true;

            while(increase) {
                System.out.println("Trying with best state: " + bestState);

                RocketState[] states = new RocketState[6];
                states[0] = new RocketState(startState.getP0(), bestState.getV0().add(new Vector3d(value,0,0)));
                states[1] = new RocketState(startState.getP0(), bestState.getV0().add(new Vector3d(0,value,0)));
                states[2] = new RocketState(startState.getP0(), bestState.getV0().add(new Vector3d(0,0,value)));
                states[3] = new RocketState(startState.getP0(), bestState.getV0().add(new Vector3d(-value,0,0)));
                states[4] = new RocketState(startState.getP0(), bestState.getV0().add(new Vector3d(0,-value,0)));
                states[5] = new RocketState(startState.getP0(), bestState.getV0().add(new Vector3d(0,0,-value)));

                double[] distances = new double[6];
                for(int i = 0; i < distances.length; i++) {
                    distances[i] = -1;
                }

                for(int i = 0; i < states.length; i++) {
                    if(states[i].getV0().norm() < 60e3) {
                        solver.trajectory(states[i].getP0(), states[i].getV0(), tf, h);
                        distances[i] = solver.getSmallestDistRocketTitan();
                    }
                }

                increase = false;

                for(int i = 0; i < distances.length; i++) {
                    if(distances[i] != -1 && distances[i] < curDist) {
                        curDist = distances[i];
                        bestState = states[i];
                        increase = true;
                    }
                }


                if(!increase) {
                    System.out.println("Found new best distance: " + curDist);
                    System.out.println("Related state: " + bestState);
                }
            }

            value = value * 0.5;

            System.out.println("Lowered value to: " + value);

            totCount++;
        }

        System.out.println(bestState);

        return bestState;
    }
}
