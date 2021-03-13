/*
 * @author Matthijs Vossen
 * @version 0.99.0
 *
 * This class is for testing code
 */

import titan.Vector3dInterface;

public class TestCode
{
  public static void main(String[] args)
  {
    Vector3d ballX = new Vector3d(0, 0, 0);
    Vector3d ballV = new Vector3d(1, 2, 1);

    CelestialBody ball = new CelestialBody(0, ballX, ballV);

    Vector3dInterface[] movement = ball.getMovement();

  }
}
