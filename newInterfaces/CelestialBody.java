/*
 * @author Group09
 * @version 0.99.0
 *
 * This class is an implementation of the Vector3dInterface, used to represent vectors.
 */

import titan.ODEFunctionInterface;
import titan.Vector3dInterface;
import titan.RateInterface;
import titan.StateInterface;
import java.lang.Math;

public class CelestialBody implements ODEFunctionInterface
{
  private double m;                       // mass of the body
  //changed it to Vector3dInterface --> where's the difference between Vector3d and Vector3dInterface?
  private Vector3dInterface x;            // xyz coordinates of the body
  private Vector3dInterface v;                     // xyz velocity of the body
  private final double G = 6.674 * Math.pow(10, -11);
  private String name;

  public CelestialBody(String name, double mass, Vector3d x0, Vector3d v0)
  {
    m = mass;
    x = x0;
    v = v0;
    this.name = name;
  }

  public Vector3dInterface getCoord()
  {
    return x;
  }

  public Vector3dInterface getVel()
  {
    return v;
  }

  public void setX(double x)
  {
    this.x.setX(x);
  }

  public void setY(double y)
  {
    this.x.setY(y);
  }

  public void setZ(double z)
  {
    this.x.setZ(z);
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
   * This is an interface for the function f that represents the
   * differential equation dy/dt = f(t,y).
   * You need to implement this function to represent to the laws of physics.
   *
   * For example, consider the differential equation
   *   dy[0]/dt = y[1];  dy[1]/dt=cos(t)-sin(y[0])
   * Then this function would be
   *   f(t,y) = (y[1],cos(t)-sin(y[0])).
   *
   * @param   t   the time at which to evaluate the function
   * @param   y   the state at which to evaluate the function
   * @return  The average rate-of-change over the time-step. Has dimensions of [state]/[time].
   */
  public RateInterface call(double t, StateInterface y)
  {

    return (RateInterface) y.addMul(t, (RateInterface) v);
  }

  public String toString()
  {
    return name;
  }
}
