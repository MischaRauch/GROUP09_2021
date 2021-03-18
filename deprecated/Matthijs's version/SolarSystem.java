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

  private CelestialBody[] bodies = {sun, mercury, venus, earth, moon, mars, jupiter, saturn, titan, uranus, neptune};
  private int nSteps;
  private double h;
  private Vector3dInterface[][] locations;
  private final double G = 6.674 * Math.pow(10, -11);

  public SolarSystem(double h, int nSteps)
  {
    this.nSteps = nSteps;
    this.h = h;
    locations = new Vector3dInterface[11][nSteps];
    newSolve(h, nSteps);
  }

  public Vector3dInterface[][] getLocations()
  {
    return locations;
  }

  // Does nothing, used newSolve for doing the steps because this solve is weird.
  public Vector3dInterface[] solve(FunctionInterface f, Vector3dInterface x0, double h, int nSteps)
  {
    Vector3dInterface[] woop = new Vector3d[nSteps];
    return woop;
  }

  public Vector3dInterface calculateF(CelestialBody body)
  {
    Vector3dInterface totalF = new Vector3d(0, 0, 0);

    for(int i = 0; i < bodies.length; i++)
    {
      if(bodies[i] != body)
      {
        totalF = totalF.add((body.getCoord().sub(bodies[i].getCoord())).mul(1/(Math.pow(body.getCoord().sub(bodies[i].getCoord()).norm(), 3))).mul(-G * body.getMass() * bodies[i].getMass()));
      }
    }
    return totalF;
  }

  // h = the step size
  // nSteps = number of steps
  // Method for calculating the steps
  public void newSolve(double h, int nSteps)
  {
    // For loop for each step
    for(int i = 0; i < nSteps; i++)
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
        bodies[j].setCoord(step(bodies[j], 0, bodies[j].getCoord(), h));
        locations[j][i] = bodies[j].getCoord();
      }
    }
    //printMatrix(locations);
  }

  public void printMatrix(Vector3dInterface[][] matrix)
  {
    for(int i = 0; i < matrix.length; i++)
    {
      System.out.println((locations[i][0].mul(0.000000001)));
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
