/*
 * @author Group09
 * @version 0.99.0
 *
 * This class is an implementation of the Vector3dInterface, used to represent vectors.
 */

import titan.Vector3dInterface;
import titan.ODESolverInterface;
import titan.ODEFunctionInterface;
import titan.ProbeSimulatorInterface;
import titan.StateInterface;
import java.lang.Math;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SolarSystem implements ODESolverInterface, ProbeSimulatorInterface
{
  private static final boolean DEBUG = false;

  private CelestialBody sun = new CelestialBody("Sun", 1.988500e30, new Vector3d(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06), new Vector3d(-1.420511669610689e+01, -4.954714716629277e+00, 3.994237625449041e-01));
  private CelestialBody mercury = new CelestialBody("Mercury", 3.302e23, new Vector3d(6.047855986424127e+06, -6.801800047868888e+10, -5.702742359714534e+09), new Vector3d(3.892585189044652e+04, 2.978342247012996e+03, -3.327964151414740e+03));
  private CelestialBody venus = new CelestialBody("Venus", 4.8685e24, new Vector3d(-9.435345478592035e+10, 5.350359551033670e+10, 6.131453014410347e+09), new Vector3d(-1.726404287724406e+04, -3.073432518238123e+04, 5.741783385280979e-04));
  private CelestialBody earth = new CelestialBody("Earth", 5.97219e24, new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06), new Vector3d(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01));
  private CelestialBody moon = new CelestialBody("Moon", 7.349e22, new Vector3d(-1.472343904597218e+11, -2.822578361503422e+10, 1.052790970065631e+07), new Vector3d(4.433121605215677e+03, -2.948453614110320e+04, 8.896598225322805e+01));
  private CelestialBody mars = new CelestialBody("Mars", 6.4171e23, new Vector3d(-3.615638921529161e+10, -2.167633037046744e+11, -3.687670305939779e+09), new Vector3d(2.481551975121696e+04, -1.816368005464070e+03, -6.467321619018108e+02));
  private CelestialBody jupiter = new CelestialBody("Jupiter", 1.89813e27, new Vector3d(1.781303138592153e+11, -7.551118436250277e+11, -8.532838524802327e+08), new Vector3d(1.255852555185220e+04, 3.622680192790968e+03, -2.958620380112444e+02));
  private CelestialBody saturn = new CelestialBody("Saturn", 5.6834e26, new Vector3d(6.328646641500651e+11, -1.358172804527507e+12, -1.578520137930810e+09), new Vector3d(8.220842186554890e+03, 4.052137378979608e+03, -3.976224719266916e+02));
  private CelestialBody titan = new CelestialBody("Titan", 1.34553e23, new Vector3d(6.332873118527889e+11, -1.357175556995868e+12, -2.134637041453660e+09), new Vector3d(3.056877965721629e+03, 6.125612956428791e+03, -9.523587380845593e+02));
  private CelestialBody uranus = new CelestialBody("Uranus", 8.6813e25, new Vector3d(2.395195786685187e+12, 1.744450959214586e+12, -2.455116324031639e+10), new Vector3d(-4.059468635313243e+03, 5.187467354884825e+03, 7.182516236837899e+01));
  private CelestialBody neptune = new CelestialBody("Neptune", 1.02413e26, new Vector3d(4.382692942729203e+12, -9.093501655486243e+11, -8.227728929479486e+10), new Vector3d(1.068410720964204e+03, 5.354959501569486e+03, -1.343918199987533e+02));
  private CelestialBody probe = new CelestialBody("Rocket", 150e2, new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06), new Vector3d(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01));
  //private CelestialBody probe = new CelestialBody("Rocket", 150e2, new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06), new Vector3d(44549.654063717346,-50989.135821972195,13767.334325743603));

  private CelestialBody[] bodies = {sun, mercury, venus, earth, moon, mars, jupiter, saturn, titan, uranus, neptune, probe};
  private int nSteps;
  private double h;
  private Vector3dInterface[][] locations;
  private Vector3dInterface[] probeTrajectory;
  public double probesBestVelocity;
  public Vector3dInterface probeVel;
  private final double G = 6.674 * Math.pow(10, -11);
  private double smallestDistance;

  // Constructor
  public SolarSystem(double h, int nSteps)
  {
    this.nSteps = nSteps;
    this.h = h;
    locations = new Vector3dInterface[bodies.length][nSteps];

    calculateProbeAngle();
    trajectory(new Vector3d(0,0,0), new Vector3d(0,0,0), 120e6, h);

    locations = new Vector3dInterface[bodies.length][nSteps];

    System.out.println();
    locations = new Vector3dInterface[bodies.length][nSteps];
    resetValues();
    calculateProbeAngle();
    trajectory(new Vector3d(0,0,0), new Vector3d(0,0,0), 120e6, h);
  }

  // Constructor for trajectory brute force
  public SolarSystem(double h, int nSteps, int iterations)
  {
    this.nSteps = nSteps;
    this.h = h;
    resetValues();
    locations = new Vector3dInterface[bodies.length][nSteps];
    probesBestVelocity = 5E20;
    bruteForce(iterations);
  }

  /*
   * Getter function, returns the coordinates for all bodies
   *
   * @return  a Vector3dInterface matrix containing the coordinates for all bodies
   */
  public Vector3dInterface[][] getLocations()
  {
    return locations;
  }
  /*
   * Reset the coordinates for all bodies
   */
  public void resetValues()
  {
    sun = new CelestialBody("Sun", 1.988500e30, new Vector3d(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06), new Vector3d(-1.420511669610689e+01, -4.954714716629277e+00, 3.994237625449041e-01));
    mercury = new CelestialBody("Mercury", 3.302e23, new Vector3d(6.047855986424127e+06, -6.801800047868888e+10, -5.702742359714534e+09), new Vector3d(3.892585189044652e+04, 2.978342247012996e+03, -3.327964151414740e+03));
    venus = new CelestialBody("Venus", 4.8685e24, new Vector3d(-9.435345478592035e+10, 5.350359551033670e+10, 6.131453014410347e+09), new Vector3d(-1.726404287724406e+04, -3.073432518238123e+04, 5.741783385280979e-04));
    earth = new CelestialBody("Earth", 5.97219e24, new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06), new Vector3d(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01));
    moon = new CelestialBody("Moon", 7.349e22, new Vector3d(-1.472343904597218e+11, -2.822578361503422e+10, 1.052790970065631e+07), new Vector3d(4.433121605215677e+03, -2.948453614110320e+04, 8.896598225322805e+01));
    mars = new CelestialBody("Mars", 6.4171e23, new Vector3d(-3.615638921529161e+10, -2.167633037046744e+11, -3.687670305939779e+09), new Vector3d(2.481551975121696e+04, -1.816368005464070e+03, -6.467321619018108e+02));
    jupiter = new CelestialBody("Jupiter", 1.89813e27, new Vector3d(1.781303138592153e+11, -7.551118436250277e+11, -8.532838524802327e+08), new Vector3d(1.255852555185220e+04, 3.622680192790968e+03, -2.958620380112444e+02));
    saturn = new CelestialBody("Saturn", 5.6834e26, new Vector3d(6.328646641500651e+11, -1.358172804527507e+12, -1.578520137930810e+09), new Vector3d(8.220842186554890e+03, 4.052137378979608e+03, -3.976224719266916e+02));
    titan = new CelestialBody("Titan", 1.34553e23, new Vector3d(6.332873118527889e+11, -1.357175556995868e+12, -2.134637041453660e+09), new Vector3d(3.056877965721629e+03, 6.125612956428791e+03, -9.523587380845593e+02));
    uranus = new CelestialBody("Uranus", 8.6813e25, new Vector3d(2.395195786685187e+12, 1.744450959214586e+12, -2.455116324031639e+10), new Vector3d(-4.059468635313243e+03, 5.187467354884825e+03, 7.182516236837899e+01));
    neptune = new CelestialBody("Neptune", 1.02413e26, new Vector3d(4.382692942729203e+12, -9.093501655486243e+11, -8.227728929479486e+10), new Vector3d(1.068410720964204e+03, 5.354959501569486e+03, -1.343918199987533e+02));
    probe = new CelestialBody("Rocket", 150e2, new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06), new Vector3d(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01));

    bodies[0] = sun;
    bodies[1] = mercury;
    bodies[2] = venus;
    bodies[3] = earth;
    bodies[4] = moon;
    bodies[5] = mars;
    bodies[6] = jupiter;
    bodies[7] = saturn;
    bodies[8] = titan;
    bodies[9] = uranus;
    bodies[10] = neptune;
    bodies[11] = probe;
  }

  /*
   * Method for calculating an initial angle and velocity for the probe and its starting
   * coordinates
   */
  public void calculateProbeAngle()
  {
    Vector3d earthXy = new Vector3d(earth.getX(), earth.getY(), 0);
    Vector3d titanXy = new Vector3d(titan.getX(), titan.getY(), 0);

    Vector3d earthYz = new Vector3d(0, earth.getY(), earth.getZ());
    Vector3d titanYz = new Vector3d(0, titan.getY(), titan.getZ());

    Vector3d earthXz = new Vector3d(earth.getX(), 0, earth.getZ());
    Vector3d titanXz = new Vector3d(titan.getX(), 0, titan.getZ());

    double xyAngle = earthXy.angle(titanXy);
    double yzAngle = earthYz.angle(titanYz);
    double xzAngle = earthXz.angle(titanXz);

    double xAdd = Math.cos(xyAngle) * 6371e3;
    probe.setX(probe.getX() + xAdd);

    double yAdd = Math.sin(xyAngle) * 6371e3;
    probe.setY(probe.getY() + yAdd);

    double zAdd = Math.sin(xzAngle) * 6371e3;
    probe.setZ(probe.getZ() + zAdd);

    Vector3dInterface coordAdd = new Vector3d(xAdd, yAdd, zAdd);
    if (DEBUG) System.out.println("Start coordinates relative to earth: " + coordAdd);

    if (DEBUG) System.out.println("Start coordinates: " + probe.getCoord());

    double xvAdd = (Math.cos(xyAngle) * 59990);
    double yvAdd = (Math.sin(xyAngle) * 59990);
    double zvAdd = (Math.sin(xzAngle) * 59990);

    Vector3dInterface vAdd = new Vector3d(xvAdd, yvAdd, zvAdd);
    if (DEBUG) System.out.println("Velocity: " + vAdd);

    probe.setVel(probe.getVel().add(vAdd));

    if (DEBUG) System.out.println("Rocket velocity relative to earth: " + Math.abs(earth.getVel().sub(probe.getVel()).norm()));
    if (DEBUG) System.out.println("Velocity relative to earth: " + earth.getVel().sub(probe.getVel()).norm());

    if (DEBUG) System.out.println("Velocity of probe: " + probe.getVel());

    if(Math.abs(earth.getVel().sub(probe.getVel()).norm()) > 60000)
    {
      if (DEBUG) System.out.println("Vel too high");
    }
  }

  /*
   * Calcualtes the force on a CelestialBody
   *
   * @param   body    the body on which to calculate the force
   * @return  a Vector3dInterface containing the force in Newton in each dimension
   */
  public Vector3dInterface calculateF(CelestialBody body)
  {
    Vector3dInterface totalF = new Vector3d(0, 0, 0);

    for(int i = 0; i < bodies.length; i++)
    {
      if(bodies[i] != body)
      {
        totalF = totalF.add((body.getCoord().sub(bodies[i].getCoord())).mul(1/(Math.pow(body.getCoord().sub(bodies[i].getCoord()).norm(), 3))).mul(-G * body.getMass() * bodies[i].getMass()));
        //ystem.out.println("Cord: "+body.getCoord());
      }
    }
    return totalF;
  }

  /*
   * Solve the differential equation by taking multiple steps.
   *
   * @param   f       the function defining the differential equation dy/dt=f(t,y)
   * @param   y0      the starting state
   * @param   ts      the times at which the states should be output, with ts[0] being the initial time
   * @return  an array of size ts.length with all intermediate states along the path
   */
  public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts)
  {
    StateInterface[] temp = new StateInterface[0];
    return temp;
  }

  /*
   * Solve the differential equation by taking multiple steps of equal size, starting at time 0.
   * The final step may have a smaller size, if the step-size does not exactly divide the solution time range
   *
   * @param   f       the function defining the differential equation dy/dt=f(t,y)
   * @param   y0      the starting state
   * @param   tf      the final time
   * @param   h       the size of step to be taken
   * @return  an array of size round(tf/h)+1 including all intermediate states along the path
   */
  public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h)
  {
    StateInterface[] temp = new StateInterface[0];
    return temp;
  }

  /*
   * Update rule for one step.
   *
   * @param   f   the function defining the differential equation dy/dt=f(t,y)
   * @param   t   the time
   * @param   y   the state
   * @param   h   the step size
   * @return  the new state after taking one step
   */
  public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h)
  {
    return (StateInterface) f.call(h, y);
  }

  /*
   * Simulate the solar system, including a probe fired from Earth at 00:00h on 1 April 2020.
   *
   * @param   p0      the starting position of the probe, relative to the earth's position.
   * @param   v0      the starting velocity of the probe, relative to the earth's velocity.
   * @param   ts      the times at which the states should be output, with ts[0] being the initial time.
   * @return  an array of size ts.length giving the position of the probe at each time stated,
   *          taken relative to the Solar System barycentre.
   */
  public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts)
  {
    Vector3dInterface[] temp = new Vector3dInterface[0];
    return temp;
  }

  /*
   * Simulate the solar system with steps of an equal size.
   * The final step may have a smaller size, if the step-size does not exactly divide the solution time range.
   *
   * @param   tf      the final time of the evolution.
   * @param   h       the size of step to be taken
   * @return  an array of size round(tf/h)+1 giving the position of the probe at each time stated,
   *          taken relative to the Solar System barycentre
   */
  public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h)
  {
    double minDist = 10e12;

    for(int i = 0; i < bodies.length; i++)
    {
      locations[i][0] = bodies[i].getCoord();
    }

    for(int i = 1; i < nSteps; i++)
    {
      for(int j = 0; j < bodies.length; j++)
      {
        Vector3dInterface force = calculateF(bodies[j]);

        if(DEBUG) System.out.println("Gravitation force on " + bodies[j] + ":" + force);
        Vector3dInterface acceleration = new Vector3d(force.getX()/bodies[j].getMass(), force.getY()/bodies[j].getMass(), force.getZ()/bodies[j].getMass());
        if(DEBUG) System.out.println("Acceleration of " + bodies[j] + ": " + acceleration);
        bodies[j].setVel(bodies[j].getVel().add(acceleration.mul(h)));
      }
      // For loop for calculating the new coordinates
      for(int j = 0; j < bodies.length; j++)
      {
        bodies[j].setCoord((Vector3dInterface) step(bodies[j], 0, (StateInterface) bodies[j].getCoord(), h));
        locations[j][i] = bodies[j].getCoord();
      }
      if(locations[11][i].dist(locations[8][i]) < minDist)
      {
        minDist = locations[11][i].dist(locations[8][i]);
      }
    }
    smallestDistance = minDist;

    if (DEBUG) System.out.println("Distance between probe and Titan: " + locations[11][nSteps-1].dist(locations[8][nSteps-1]));
    if (locations[11][nSteps-1].dist(locations[8][nSteps-1]) < probesBestVelocity)
    {
      probesBestVelocity = locations[11][nSteps-1].dist(locations[8][nSteps-1]);
      probeVel = bodies[11].getVel();
    }

    Vector3dInterface[] trajectory = new Vector3dInterface[nSteps+1];
    trajectory[0] = p0;
    for(int i = 1; i < nSteps; i++)
    {
      trajectory[i] = locations[11][i-1];
    }

    return trajectory;
  }


  /*
   * Brute force method for calculating optimal start velocities for the rocket
   *
   * @param   iterations       the amount of iterations which the method should do
   */
  public void bruteForce(int iterations)
  {
    Vector3dInterface initV = new Vector3d(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01);

    for (int i = 0; i<iterations; i++)
    {
      calculateProbeAngle();
      if (probesBestVelocity > 5E20)
      {
        double x = probeVel.getX()*Math.random()*15e+2;
        double y = probeVel.getY()*Math.random()*15e+2;
        double z = probeVel.getZ()*Math.random()*15e+2;
        probe.setVel(new Vector3d((int) x,(int) y,(int) z));
      }
      else
      {
        double x = Math.random()*30e+2;
        double y = Math.random()*30e+2;
        double z = Math.random()*30e+2;
        probe.setVel(new Vector3d((int) x,(int) y,(int) z));
      }
      trajectory(new Vector3d(0,0,0), new Vector3d(0,0,0), 120e6, h);
      if (DEBUG) System.out.println("BEST "+probesBestVelocity);
      resetValues();
    }
    if (DEBUG) System.out.println("\n BEST "+probesBestVelocity);
    if (DEBUG) System.out.println("BEST Velocity"+probeVel);
    resetValues();
    bodies[11].setVel(probeVel);


  }
}
