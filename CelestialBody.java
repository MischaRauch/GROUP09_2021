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

public class CelestialBody implements FunctionInterface, SolverInterface
{
  private double m;                       // mass of the body
  //changed it to Vector3dInterface --> where's the difference between Vector3d and Vector3dInterface?
  private Vector3dInterface x;            // xyz coordinates of the body
  private Vector3dInterface v;                     // xyz velocity of the body
  private Vector3d F;                     // Gravitational force on the body
  private Vector3dInterface[] movement;   // Place to store coordinates for testing
  private final double G = 6.674 * Math.pow(10, -11);
  private String name;
  private double h = 0.1;                 // Step size in seconds
  private int nSteps = 10;                // total number of time steps

  public CelestialBody(String name, double mass, Vector3d x0, Vector3d v0)
  {
    m = mass;
    x = x0;
    v = v0;
    this.name = name;
    //movement = solve(this, x, h, nSteps);
  }

  public Vector3dInterface[] getMovement()
  {
    return movement;
  }

  public Vector3dInterface getCoord()
  {
    return x;
  }

  public Vector3dInterface getVel()
  {
    return v;
  }

  public void setVel(Vector3dInterface v)
  {
    this.v = v;
  }

  public void setCoord(Vector3dInterface x)
  {
    this.x = x;
  }

  public double getMass()
  {
    return m;
  }

  public double getX()
  {
    return x.getX();
  }

  public double getY()
  {
    return x.getY();
  }

  public double getZ()
  {
    return x.getZ();
  }

  public double getVelX()
  {
    return v.getX();
  }

  public double getVelY()
  {
    return v.getY();
  }

  public double getVelZ()
  {
    return v.getZ();
  }

  public void calculateF(CelestialBody[] bodies)
  {
    Vector3dInterface[] F = new Vector3d[bodies.length];

    for(int i = 0; i < bodies.length; i++)
    {
      if(bodies[i] != this)
      {
        System.out.println(bodies[i].getCoord());
        F[i] = (x.sub(bodies[i].getCoord())).mul(1/(Math.pow(x.sub(bodies[i].getCoord()).norm(), 3))).mul(G * m * bodies[i].getMass());
        //F[i] = (x.sub(bodies[i].getCoord())).mul(1/Math.abs(Math.pow(x.dist(bodies[i].getCoord()), 3))).mul(-G * m * bodies[i].getMass());
        System.out.println(F[i]);
      }
    }
  }

  /*
   * Update rule for multiple steps. Time will start at zero.
   *
   * @param   f       the differential equation as defined in the project manual:
   *                  y(t) describes the position of the system at time t
   *                  f(t, y(t)) describes the derivative of y(t) with respect to time t
   * @param   x0      the starting location
   * @param   h       the step size in seconds
   * @param   nSteps  the total number of time steps
   * @return  an array of size nSteps with all intermediate locations along the path
   *
   */
  public Vector3dInterface[] solve(FunctionInterface f, Vector3dInterface x0, double h, int nSteps)
  {
    Vector3dInterface[] locations = new Vector3d[nSteps];
    double t = 0;
    for(int i = 0; i < nSteps; i++)
    {
      locations[i] = step(f, t, x0, h);
      x0 = locations[i];
      t += h;
      System.out.println("Location after step " + (i+1) + ": " + x0);
    }
    //@Matthijs maybe I'm wrong but I would say to update the position at this point with the new location
    //otherwise the object keeps staying on the same position forever
    this.setCoord(locations[locations.length-1]);
    return locations;
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

  /*
   * This is the function that represents the first derivative
   * denoted as f in your project manual. You need to implement
   * this function according to the laws of physics.
   *
   * For example, consider the state of the system at time t as
   * this linear function,
   * y(t) = a*t + b,
   * then we can choose any function f whose derivative is a.
   * The simplest such f is
   * f(t, y(t)) = a.
   *
   * @param   t   the time at which to evaluate the function
   * @param   s   the location at which to evaluate the function
   */
  public Vector3dInterface call(double t, Vector3dInterface s)
  {
    return s.add(new Vector3d(v.getX()*t, v.getY()*t, v.getZ()*t));
  }

  /*
   *Calculates the Gravity force between two Celestial Bodys
   *
   *
   *
   */
  public Vector3dInterface gForce(CelestialBody u, CelestialBody v)
  {
    //Calculate distance vector 
    Vector3dInterface r_vec3d = u.x.sub(v.x);
    //Caclulate magnitude of distance vector 
    double r_mag = r_vec3d.norm();
    //Calculate unit vector of distance vector - Direction vector 
    Vector3dInterface r_hat = r_vec3d.mul(Math.pow(r_mag, -1));
    //Caclulate force magnitude
    double force_mag = G*u.m*v.m / Math.pow(r_mag,2);
    //Calculate force Vector
    Vector3dInterface force_vec = r_hat.mul(-force_mag);
    return force_vec;
  }
  public Vector3dInterface gPosition(CelestialBody x, CelestialBody y)
  {
    //Calculate forces
    Vector3dInterface x_Force = gForce(x, y);
    Vector3dInterface y_Force = gForce(y, x);
    //Calculate momentum - momentum = mass * velocity
    Vector3dInterface x_Momentum = x.v.mul(x.m);
    Vector3dInterface y_Momentum = y.v.mul(y.m);
    //Calculate momentum
    x_Momentum = x_Momentum.add(x_Force.mul(h));
    y_Momentum = y_Momentum.add(y_Force.mul(h));
    //Update position
    Vector3dInterface x_position = x.getCoord().add(x_Momentum.mul((Math.pow(x.m*h,-1)))); 
    return x_position;
  }

  public String toString()
  {
    return name;
  }
}
