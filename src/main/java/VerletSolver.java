
import java.util.Arrays;
import src.main.java.titan.Vector3dInterface;
import titan.StateInterface;

public class VerletSolver {
    Vector3dInterface[] coordinates;
    Vector3dInterface[] velocity;
    double[] masses;
    Vector3dInterface[] forces;
    Vector3dInterface[] acceleration;
    double stepSize;
    StateInterface[] states;
    ODEFunction ode = new ODEFunction();


    public VerletSolver(Vector3dInterface[] coordinates, Vector3dInterface[] velocity, double[] masses) {
        this.coordinates = coordinates;
        this.masses = masses;
        this.velocity = velocity;
    }

    public StateInterface[] solve(double t, double dt) {
        stepSize = dt;
        double steps = (double) t/dt+2;
        states = new StateInterface[(int)steps];
        System.out.println("Steps "+steps);
        preCalculation();

        //store state 0
        states[0] = new State(coordinates,velocity,0);

        //Loop through all time steps
        for (int i = 0; i < states.length; i++) {
            //For each time step loop through all planets
            Vector3dInterface[] allNewPositions = new Vector3dInterface[coordinates.length];
            Vector3dInterface[] allNewVelocitys = new Vector3dInterface[coordinates.length];
            Vector3dInterface[] allNewAccelerations = new Vector3dInterface[coordinates.length];
            for (int j = 0; j < coordinates.length; j++) {
                //calculate new Position
                //System.out.println("VEL "+velocity[j]+" j "+j);
                //System.out.println("ACC "+acceleration[j]+" j "+j);
                Vector3dInterface new_pos = coordinates[j].addMul(stepSize,velocity[j]).addMul((Math.pow(stepSize, 2))*0.5,acceleration[j]);
                //calculate new acceleration
                Vector3dInterface[] new_Acc = calculateAcc(new_pos);
                //calculate new velocity
                Vector3dInterface tmp = acceleration[j].add(new_Acc[j]);
                Vector3dInterface tmp2 = tmp.mul((stepSize*0.5));
                Vector3dInterface new_vel = velocity[j].add(tmp2);

                allNewPositions[j] = new_pos;
                allNewVelocitys[j] = new_vel;
                allNewAccelerations[j] = new_Acc[j];
            }
            coordinates = allNewPositions;
            velocity = allNewVelocitys;
            acceleration = allNewAccelerations;
            states[i] = new State(allNewPositions, allNewVelocitys, (stepSize*i));
            //dt+= stepSize;
            
        }
        
        return states;
    }
    public Vector3dInterface[] calculateAcc(Vector3dInterface currentPosition) {
        Vector3dInterface[] new_forces = ode.calculateF(coordinates, masses);
        Vector3dInterface[] new_acceleration = ode.calculateAccelerations(new_forces, masses);
        return new_acceleration;
    }
    public void preCalculation() {
        forces = ode.calculateF(coordinates, masses);
        acceleration = ode.calculateAccelerations(forces, masses);
    }

}
