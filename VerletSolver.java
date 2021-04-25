import java.util.Arrays;

import titan.*;

public class VerletSolver {
    Vector3dInterface[] coordinates;
    Vector3dInterface[] velocity;
    double[] masses;
    Vector3dInterface[] forces;
    Vector3dInterface[] acceleration;
    double stepSize;
    boolean firstIteration;

    public VerletSolver(Vector3dInterface[] coordinates, Vector3dInterface[] velocity, double[] masses) {
        this.coordinates = coordinates;
        this.masses = masses;
        this.velocity = velocity;
    }

    public Vector3dInterface[][] solve(int t, double dt) {
        stepSize = dt;
        double steps = (double) t/dt;
        System.out.println("Steps "+steps);
        preCalculation();
        Vector3dInterface positions[][] = new Vector3dInterface[coordinates.length][(int)steps];
        //Calculate step one
        for (int i = 0; i < coordinates.length; i++) {   
            positions[i][0] = coordinates[i];
            Vector3dInterface tmp = velocity[i].mul(dt);
            Vector3dInterface tmp2 = acceleration[i].mul(Math.pow(dt, 2)).mul(0.5);
            positions[i][1] = coordinates[i].add(tmp).add(tmp2);
        }
        dt = dt+stepSize; 
        //calculate up to n steps
        for (int i = 2; dt < t; i++) {
            for (int j = 0; j < coordinates.length; j++) {   
                System.out.println("Planet "+j);
                System.out.println("pos-1 "+positions[j][i-1]);
                System.out.println("pos-2 "+positions[j][i-2]);
                Vector3dInterface tmp = (positions[j][i-1]).mul(2).sub(positions[j][i-1]);
                Vector3dInterface tmp2 = acceleration[j].mul(Math.pow(dt, 2));
                positions[j][i] = tmp.add(tmp2);

            }
            System.out.println("dt "+dt);
            System.out.println("t "+t);
            dt = dt+stepSize;
        }
        //System.out.println(Arrays.toString(positions[1]));
        return positions;
    }

    public void preCalculation() {
        ODEFunction ode = new ODEFunction();
        forces = ode.calculateF(coordinates, masses);
        acceleration = ode.calculateAccelerations(forces, masses);
    }

}
