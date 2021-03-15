/*
 * @author Group09
 * @version 0.99.0
 *
 * This class is an implementation of the Vector3dInterface, used to represent vectors.
 */

import titan.FunctionInterface;
import titan.Vector3dInterface;
import titan.SolverInterface;
import java.lang.Math;

public class SolarSystem implements SolverInterface
{
  private static final boolean DEBUG = false;

  private CelestialBody earth = new CelestialBody("Earth", 10000, new Vector3d(14, 24.5, 0), new Vector3d(1.2, -0.4, 0));
  private CelestialBody sun = new CelestialBody("Sun", 1.988500e11, new Vector3d(15, 20, 0), new Vector3d(0, 0, 0));


  /*
  private CelestialBody sun = new CelestialBody("Sun", 1.988500e30, new Vector3d(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06), new Vector3d(-1.420511669610689e+01, -4.954714716629277e+00, 3.994237625449041e-01));
  private CelestialBody earth = new CelestialBody("Earth", 5.97219e24, new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06), new Vector3d(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01));
  */

  private CelestialBody[] bodies = {earth, sun};
  private final double G = 6.674 * Math.pow(10, -11);
  private Vector3dInterface[] positionearth;
  private Vector3dInterface[] positionsun;

  public SolarSystem()
  {
    System.out.println("Start sun coordinates: " + sun.getCoord());
    System.out.println("Start earth coordinates: " + earth.getCoord());
    newSolve(0.5, 100);
    System.out.println("End sun coordinates: " + sun.getCoord());
    System.out.println("End earth coordinates: " + earth.getCoord());
  }
  public Vector3dInterface[] getSunPosition()
  {
    return positionsun;
  }
  public Vector3dInterface[] getEarthPosition()
  {
    return positionearth;
  }


  // Does nothing, used newSolve for doing the steps because this solve is weird.
  public Vector3dInterface[] solve(FunctionInterface f, Vector3dInterface x0, double h, int nSteps)
  {
    Vector3dInterface[] locations = new Vector3d[nSteps];
    return locations;
  }

  public Vector3dInterface calculateF(CelestialBody body)
  {
    Vector3dInterface[] F = new Vector3d[bodies.length];
    Vector3dInterface totalF = new Vector3d(0, 0, 0);

    for(int i = 0; i < bodies.length; i++)
    {
      if(bodies[i] != body)
      {
        totalF = totalF.add((body.getCoord().sub(bodies[i].getCoord())).mul(1/(Math.pow(body.getCoord().sub(bodies[i].getCoord()).norm(), 3))).mul(-G * body.getMass() * bodies[i].getMass()));
        //F[i] = (x.sub(bodies[i].getCoord())).mul(1/Math.abs(Math.pow(x.dist(bodies[i].getCoord()), 3))).mul(-G * m * bodies[i].getMass());
      }
    }
    return totalF;
  }

  // h = the step size
  // nSteps = number of steps
  // Method for calculating the steps
  public void newSolve(double h, int nSteps)
  {
    positionearth = new Vector3d[nSteps];
    positionsun = new Vector3d[nSteps];
    // For loop for each step
    for(int i = 0; i < nSteps; i++)
    {
      Vector3dInterface[] gravitationalForces = new Vector3d[bodies.length];  // Array for storing gravitationalForces on each planet
      Vector3dInterface[] accelerations = new Vector3d[bodies.length];        // Array for storing accelerations of each planet
      for(int j = 0; j < bodies.length; j++)
      {
        gravitationalForces[j] = calculateF(bodies[j]);
        if(DEBUG) System.out.println("Gravitation force on " + bodies[j] + ":" + gravitationalForces[j]);
        accelerations[j] = new Vector3d(gravitationalForces[j].getX()/bodies[j].getMass(), gravitationalForces[j].getY()/bodies[j].getMass(), gravitationalForces[j].getZ()/bodies[j].getMass());
        if(DEBUG) System.out.println("Acceleration of " + bodies[j] + ": " + accelerations[j]);
        bodies[j].setVel(bodies[j].getVel().add(accelerations[j].mul(h)));
      }

      // For loop for calculating the new coordinates
      for(int j = 0; j < bodies.length; j++)
      {
        bodies[j].setCoord(step(bodies[j], 0, bodies[j].getCoord(), h));
        if(DEBUG) System.out.println("New coord for " + bodies[j] + ": " + bodies[j].getCoord());
        if(bodies[j].getMass() == 10000)
        {
          positionearth[i] = bodies[j].getCoord();
        }
        else
        {
          positionsun[i] = bodies[j].getCoord();
        }
      }
    }
  }

  /*
   * Update rule for one step.
   *
   * @param   f   the differential equation as defined in the project manual:
   *              y(t) describes the position of the system at time t
   *              f(t, y(t)) describes the derivative of y(t) with respect to time t
   * @param   t   the time
   * @param   x   the location
   * @param   h   the step size in seconds
   * @return  the new location after taking one step
   *
   */
  public Vector3dInterface step(FunctionInterface f, double t, Vector3dInterface x, double h)
  {
    return f.call(h, x);
  }
}
