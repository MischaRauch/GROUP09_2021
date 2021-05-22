import src.main.java.Vector3d;
import src.main.java.titan.Vector3dInterface;

public class HillClimbing {

    private RocketState startState = new RocketState(new Vector3d(0, -6371e3, 0), new Vector3d(0,0,0));

    public HillClimbing(ODESolver solver, double tf, double h) {
        RocketState finalState = hillClimb(solver, startState, tf, h);
    }

    private RocketState hillClimb(ODESolver solver, RocketState startState, double tf, double h) {
        RocketState previousState = startState;
        RocketState bestState;
        double previousBestDist;

        int randomAttemptCount;
        boolean foundBetterState = true;

        while(foundBetterState) {

            RocketState[] states = new RocketState[3];
            states[0] = new RocketState(startState.getP0(), previousState.getV0().add(new Vector3d(100,0,0)));
            states[1] = new RocketState(startState.getP0(), previousState.getV0().add(new Vector3d(0,100,0)));
            states[2] = new RocketState(startState.getP0(), previousState.getV0().add(new Vector3d(0,0,100)));

            double[] distances = new double[3];

            distances[0] = -1;
            distances[1] = -1;
            distances[2] = -1;

            if(states[0].getV0().norm() < 60e3) {
                solver.trajectory(states[0].getP0(), states[0].getV0(), tf, h);
                distances[0] = solver.getSmallestDistRocketTitan();
                System.out.println(distances[0]);
            }
            if(states[1].getV0().norm() < 60e3) {
                solver.trajectory(states[1].getP0(), states[1].getV0(), tf, h);
                distances[1] = solver.getSmallestDistRocketTitan();
                System.out.println(distances[1]);
            }
            if(states[2].getV0().norm() < 60e3) {
                solver.trajectory(states[2].getP0(), states[2].getV0(), tf, h);
                distances[2] = solver.getSmallestDistRocketTitan();
                System.out.println(distances[2]);
            }

            int bestStateNr = -1;
            previousBestDist = distances[1];

            for(int i = 0; i < distances.length; i++) {
                if(distances[i] < previousBestDist) {
                    previousBestDist = distances[i];
                    bestStateNr = i;
                }
            }

            if(bestStateNr == -1) {
                foundBetterState = false;
            }
            else {
                previousState = states[bestStateNr];
                System.out.println(bestStateNr);
            }
        }

        return startState;
    }
}
