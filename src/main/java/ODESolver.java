import java.util.Arrays;

import src.main.java.titan.Vector3dInterface;
import src.main.java.Vector3d;
import titan.ODESolverInterface;
import titan.ProbeSimulatorInterface;
import titan.StateInterface;
import titan.ODEFunctionInterface;

import src.main.java.titan.RateInterface;

/**
 * Class for calculating the trajectories of only the planets. Contains all
 * the starting coordinates and velocities for the planets and calculates the
 * trajectories from those starting coordinates/velocities.
 */
public class ODESolver implements ODESolverInterface, ProbeSimulatorInterface {

    // Starting coordinates for all the planets - PROVIDED VALUES
    private final Vector3dInterface sunC = new Vector3d(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06);
    private final Vector3dInterface mercuryC = new Vector3d(6.047855986424127e+06, -6.801800047868888e+10, -5.702742359714534e+09);
    private final Vector3dInterface venusC = new Vector3d(-9.435345478592035e+10, 5.350359551033670e+10, 6.131453014410347e+09);
    private final Vector3dInterface earthC = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);
    private final Vector3dInterface moonC = new Vector3d(-1.472343904597218e+11, -2.822578361503422e+10, 1.052790970065631e+07);
    private final Vector3dInterface marsC = new Vector3d(-3.615638921529161e+10, -2.167633037046744e+11, -3.687670305939779e+09);
    private final Vector3dInterface jupiterC = new Vector3d(1.781303138592153e+11, -7.551118436250277e+11, -8.532838524802327e+08);
    private final Vector3dInterface saturnC = new Vector3d(6.328646641500651e+11, -1.358172804527507e+12, -1.578520137930810e+09);
    private final Vector3dInterface titanC = new Vector3d(6.332873118527889e+11, -1.357175556995868e+12, -2.134637041453660e+09);
    private final Vector3dInterface uranusC = new Vector3d(2.395195786685187e+12, 1.744450959214586e+12, -2.455116324031639e+10);
    private final Vector3dInterface neptuneC = new Vector3d(4.382692942729203e+12, -9.093501655486243e+11, -8.227728929479486e+10);
    private Vector3dInterface probeC = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10-6371e3, 8.278183193596080e+06);
    /*
    // Starting coordinates for all the planets - NASA HORIZON VALUES
    private final Vector3dInterface sunC = new Vector3d((-6.807847851469811E+05*1000), 1.079960529004681E+06*1000, 6.577801896302961E+03*1000);
    private final Vector3dInterface mercuryC = new Vector3d(5.941270492988122E+03*1000, 6.801804551422262E+07*1000, -5.702728562917408E+06*1000);
    private final Vector3dInterface venusC = new Vector3d(-9.435356118127899E+07*1000, 5.350355062360455E+07*1000, 6.131466820352264E+06*1000);
    private final Vector3dInterface earthC = new Vector3d(-1.471923166635424E+08*1000, -2.861000299246477E+07*1000, 8.291942464411259E+03*1000);
    private final Vector3dInterface moonC = new Vector3d(-1.472344969594165E+08*1000, -2.822582844526653E+07*1000, 1.054166983666271E+04*1000);
    private final Vector3dInterface marsC = new Vector3d(-3.615638921529161E+07*1000, -2.167633037046744E+08*1000, -3.687670305939779E+06*1000);
    private final Vector3dInterface jupiterC = new Vector3d(1.781303138592156E+08*1000, -7.551118436250294E+08*1000, -8.532838524470329E+05*1000);
    private final Vector3dInterface saturnC = new Vector3d(6.328646641500651E+08*1000, -1.358172804527507E+09*1000, -1.578520137930810E+06*1000);
    private final Vector3dInterface titanC = new Vector3d(6.332873118527889E+08*1000, -1.357175556995868E+09*1000, -2.134637041453660E+06*1000);
    private final Vector3dInterface uranusC = new Vector3d(2.395195716207961E+09*1000, 1.744451068474438E+09*1000, -2.455128792996490E+07*1000);
    private final Vector3dInterface neptuneC = new Vector3d(4.382692942729912E+09*1000, -9.093501655460005E+08*1000, -8.227728929321569E+07*1000);
    private Vector3dInterface probeC = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10-6371e3, 8.278183193596080e+06);
    */

    // Starting velocities for all the planets - PROVIDED VALUES
    private final Vector3dInterface sunV = new Vector3d(-1.420511669610689e+01, -4.954714716629277e+00, 3.994237625449041e-01);
    private final Vector3dInterface mercuryV = new Vector3d(3.892585189044652e+04, 2.978342247012996e+03, -3.327964151414740e+03);
    private final Vector3dInterface venusV = new Vector3d(-1.726404287724406e+04, -3.073432518238123e+04, 5.741783385280979e-04);
    private final Vector3dInterface earthV = new Vector3d(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01);
    private final Vector3dInterface moonV = new Vector3d(4.433121605215677e+03, -2.948453614110320e+04, 8.896598225322805e+01);
    private final Vector3dInterface marsV = new Vector3d(2.481551975121696e+04, -1.816368005464070e+03, -6.467321619018108e+02);
    private final Vector3dInterface jupiterV = new Vector3d(1.255852555185220e+04, 3.622680192790968e+03, -2.958620380112444e+02);
    private final Vector3dInterface saturnV = new Vector3d(8.220842186554890e+03, 4.052137378979608e+03, -3.976224719266916e+02);
    private final Vector3dInterface titanV = new Vector3d(3.056877965721629e+03, 6.125612956428791e+03, -9.523587380845593e+02);
    private final Vector3dInterface uranusV = new Vector3d(-4.059468635313243e+03, 5.187467354884825e+03, 7.182516236837899e+01);
    private final Vector3dInterface neptuneV = new Vector3d(1.068410720964204e+03, 5.354959501569486e+03, -1.343918199987533e+02);
    private Vector3dInterface probeV = new Vector3d(5.427193405797901e+03, -2.931056622265021e+04-30e3, 6.575428158157592e-01);
    /*
    // Starting velocities for all the planets - NASA HORIZON VALUES
    private final Vector3dInterface sunV = new Vector3d(-1.420511174031879E-02*1000, -4.954720611047453E-03*1000, 3.994075214250549E-04*1000);
    private final Vector3dInterface mercuryV = new Vector3d(3.892585186728442E+01*1000, 2.978342169553609E+00*1000, -3.327964322084350E+00*1000);
    private final Vector3dInterface venusV = new Vector3d(-1.726404291136308E+01*1000, -3.073432516609996E+01*1000, 5.741783064386929E-01*1000);
    private final Vector3dInterface earthV = new Vector3d(5.427193376018815E+00*1000, -2.931056623471520E+01*1000, 6.575114893578871E-04*1000);
    private final Vector3dInterface moonV = new Vector3d(4.433121575882713E+00*1000, -2.948453616031408E+01*1000, 8.896594867343843E-02*1000);
    private final Vector3dInterface marsV = new Vector3d(2.481551975121696E+01*1000, -1.816368005464070E+00*1000, -6.467321619018108E-01*1000);
    private final Vector3dInterface jupiterV = new Vector3d(1.255852554663750E+01*1000, 3.622680206123269E+00*1000, -2.958620404125016E-01*1000);
    private final Vector3dInterface saturnV = new Vector3d(8.220842186554890E+00*1000, 4.052137378979608E+00*1000, -3.976224719266916E-01*1000);
    private final Vector3dInterface titanV = new Vector3d(3.056877965721629E+00*1000, 6.125612956428791E+00*1000, -9.523587380845593E-01*1000);
    private final Vector3dInterface uranusV = new Vector3d(-4.059468388338293E+00*1000, 5.187467342275506E+00*1000, 7.182537902566288E-02*1000);
    private final Vector3dInterface neptuneV = new Vector3d(1.068410753078312E+00*1000, 5.354959504463812E+00*1000, -1.343918419749561E-01*1000);
    private Vector3dInterface probeV = new Vector3d(5.427193405797901e+03, -2.931056622265021e+04-30e3, 6.575428158157592e-01);
    */

    // Array containing the starting coordinates for all planets
    private Vector3dInterface[] coordinates = {sunC, mercuryC, venusC, earthC, moonC, marsC, jupiterC, saturnC, titanC, uranusC, neptuneC, probeC};
    // Array containing the starting velocities for all planets
    private Vector3dInterface[] velocities = {sunV, mercuryV, venusV, earthV, moonV, marsV, jupiterV, saturnV, titanV, uranusV, neptuneV, probeV};
    // Instance field which will contain the states for each time step
    private StateInterface[] states;

    // private final int SecondsInYear = 31536000;
    private final double SecondsInYear = (365.25*(24*60*60));

    private double smallestDistRocketTitan;

    /**
     * Constructor for the ODESolver class. Creates starting state and sets states instance field using
     * the solve method.
     *
     * @param   h the size of step to be taken
     */
    public ODESolver(double h) {
        StateInterface y0 = new State(coordinates, velocities, 0);
        ODEFunctionInterface f = new ODEFunction();
        states = solve(f, y0, SecondsInYear, h); //31536000
        //trajectory(new Vector3d(0, -6371e3, 0), new Vector3d(0, -60e3, 0), SecondsInYear, h);
        thrustTrajectory(new Vector3d(0, -6371e3, 0), new Vector3d(0, -60e3, 0), SecondsInYear, h);
    }
    public ODESolver(){}

    public double getSmallestDistRocketTitan() {
        return smallestDistRocketTitan;
    }

    /**
     * Getter method
     * @return  the states instance field containing the states for each time step
     */
    public StateInterface[] getStates() {
        return states;
    }
    /**
     * Getter method
     * @return  the inital coordinates of each planet
     */
    public Vector3dInterface[] getCoordinates() {return coordinates;}
    /**
     * Getter method
     * @return  the inital velocities of each planet
     */
    public Vector3dInterface[] getVelocities() {return velocities;}

    /**
     * Solve the differential equation by taking multiple steps.
     *
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   ts      the times at which the states should be output, with ts[0] being the initial time
     * @return  an array of size ts.length with all intermediate states along the path
     */
    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        return new StateInterface[0];
    }

    /**
     * Solve the differential equation by taking multiple steps of equal size, starting at time 0.
     * The final step may have a smaller size, if the step-size does not exactly divide the solution time range
     *
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   tf      the final time
     * @param   h       the size of step to be taken
     * @return  an array of size round(tf/h)+1 including all intermediate states along the path
     */
    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {

        long start_time = System.nanoTime();

        int t = 0;

        StateInterface[] states = new StateInterface[(int) ((tf/h)+2)];
        states[0] = y0;

        for(int i = 1; i < states.length; i++) {
            t += h;
            states[i] = step(f, t, states[i-1], h);
        }

        long end_time = System.nanoTime();

        System.out.println(((end_time - start_time) / 1e6) + " ms.");

        return states;
    }

    /**
     * Update rule for one step.
     *
     * @param   f   the function defining the differential equation dy/dt=f(t,y)
     * @param   t   the time
     * @param   y   the state
     * @param   h   the step size
     * @return  the new state after taking one step
     */
    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        RateInterface r = (Rate) f.call(t, y);
        return y.addMul(h, r);
    }

    /**
     * Update rule for one Runge-Kutta step.
     *
     * @param f     the function defining the differential equation dy/dt=f(t,y)
     * @param t     the time
     * @param y     the state
     * @param h     the step size
     * @return the new state after taking one step
     */
    public StateInterface RKstep(ODEFunctionInterface f, double t, StateInterface y, double h) {
        // Creates rate objects for each k of the RK method
        Rate ki1 = (Rate) f.call(t, y);
        Rate ki2 = (Rate) f.call(t + 0.5*h, y.addMul(h*0.5, ki1));
        Rate ki3 = (Rate) f.call(t + 0.5*h, y.addMul(h*0.5, ki2));
        Rate ki4 = (Rate) f.call(t + h, y.addMul(h, ki3));

        // Combines the ki's
        RateInterface kitot = (ki1.addMul(2, ki2).addMul(2, ki3).addMul(1, ki4)).mul(1.0/6.0);
        return y.addMul(h, kitot);
    }

    public StateInterface verletStep(ODEFunctionInterface f, double t, StateInterface y, double h) {
        Vector3dInterface[] accelerations = ((ODEFunction) f).callA(t,y);
        State state = (State) y;

        return state.addMulVerlet(h, accelerations, f);
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        return new Vector3dInterface[0];
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        Vector3dInterface[] probeCoordinates = new Vector3dInterface[(int) ((tf/h)+1)];
        ODEFunctionInterface f = new ODEFunction();
        probeCoordinates[0] = p0;
        coordinates[11] = coordinates[3].add(p0);
        velocities[11] = velocities[3].add(v0);
        states = new StateInterface[(int) ((tf/h)+1)];
        states[0] = new State(coordinates, velocities, 0);
        smallestDistRocketTitan = Double.MAX_VALUE;

        int t = 0;

        for(int i = 1; i < states.length; i++) {
            t += h;
            states[i] = verletStep(f, t, states[i-1], h);
            State state = (State) states[i];
            probeCoordinates[i] = state.getCoordinates()[11];
            double distRocketTitan = probeCoordinates[i].dist(state.getCoordinates()[8]);
            if(distRocketTitan < smallestDistRocketTitan) {
                smallestDistRocketTitan = distRocketTitan;
            }
        }

        return probeCoordinates;
    }

    public Vector3dInterface[] thrustTrajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h){
        Vector3dInterface[] probeCoordinates = new Vector3dInterface[(int) ((tf/h)+1)];
        ODEFunctionInterface f = new ODEFunction();
        SpaceShuttle s = new SpaceShuttle(7e4,7e4, p0, velocities[3].add(v0));
        probeCoordinates[0] = p0;
        coordinates[11] = coordinates[3].add(p0);
        //initial thrust
        s.calculateThrust(1);
        velocities[11] = s.getVelocity();
        states = new StateInterface[(int) ((tf/h)+1)];
        states[0] = new State(coordinates, velocities, 0);
        smallestDistRocketTitan = Double.MAX_VALUE;

        int t = 0;

        for(int i = 1; i < states.length; i++) {
            t += h;
            states[i] = verletStep(f, t, states[i-1], h);
            State state = (State) states[i];
            probeCoordinates[i] = state.getCoordinates()[11];
            s.setVelocity(state.getVelocities()[11]);
            //Second thrust
            if(i == 10){
                //System.out.println("The velocity before thrust is: "+((State) states[i]).getVelocities()[11]);
                s.calculateThrust(0.7);
                ((State) states[i]).getVelocities()[11] = s.getVelocity();
                //System.out.println("The velocity after thrust is: "+((State) states[i]).getVelocities()[11]);
            }
            //Third thrust
            if(i == 50){
                //System.out.println("The velocity before thrust is: "+((State) states[i]).getVelocities()[11]);
                s.calculateThrust(0.9);
                ((State) states[i]).getVelocities()[11] = s.getVelocity();
                //System.out.println("The velocity after thrust is: "+((State) states[i]).getVelocities()[11]);
            }
            double distRocketTitan = probeCoordinates[i].dist(state.getCoordinates()[8]);
            if(distRocketTitan < smallestDistRocketTitan) {
                smallestDistRocketTitan = distRocketTitan;
            }
        }

        return probeCoordinates;
    }
}
