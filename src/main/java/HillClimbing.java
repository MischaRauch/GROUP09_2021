import java.util.ArrayList;

/**
 * Hill climbing class used for finding a trajectory to Titan.
 */
public class HillClimbing {

    // The initial state from which the hill climbing algorithm starts searching, the position vector is decided here.
    private RocketState startState = new RocketState(new Vector3d(0, -6371e3, 0), new Vector3d(0,0,0));

    /**
     * Constructor which starts the algorithm.
     * @param solver    solver used for performing simulations
     * @param tf        the final time for each simulation
     * @param h         the step size to use in each simulation
     */
    public HillClimbing(ODESolver solver, double tf, double h) {
        RocketState finalState = hillClimb(solver, startState, tf, h);
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

        ArrayList<Double> bestDistances = new ArrayList<Double>();

        int randomAttemptCount;
        boolean foundBetterState = true;
        int totCount = 0;

        double value = 30000;

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
                    bestDistances.add(curDist);
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
