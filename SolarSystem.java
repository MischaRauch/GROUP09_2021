/*
 * @author Matthijs Vossen
 * @version 0.99.0
 *
 * This class is an implementation of the Vector3dInterface, used to represent vectors.
 */

import titan.FunctionInterface;
import titan.Vector3dInterface;
import titan.SolverInterface;
import java.lang.Math;

public class SolarSystem
{
  private static CelestialBody earth = new CelestialBody(20, new Vector3d(1, 1, 1), new Vector3d(-1, 1, -1));
  private static CelestialBody sun = new CelestialBody(100, new Vector3d(3, 3, 3), new Vector3d(0, 0, 0));

  public SolarSystem()
  {

  }

}
