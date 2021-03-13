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
    System.out.println("\n--------------------\n");

    Vector3d ball1X = new Vector3d(2, 5, 0);
    Vector3d ball1V = new Vector3d(1, 2, 1);

    CelestialBody ball1 = new CelestialBody(100000000, ball1X, ball1V);

    Vector3d ball2X = new Vector3d(10, 8, 0);
    Vector3d ball2V = new Vector3d(1, 2, 1);

    CelestialBody ball2 = new CelestialBody(100000000, ball2X, ball2V);

    Vector3d ball3X = new Vector3d(9, 8, 5);
    Vector3d ball3V = new Vector3d(5, 4, 3);

    CelestialBody ball3 = new CelestialBody(100000000, ball3X, ball3V);

    System.out.println();

    CelestialBody[] bodies = {ball1, ball2, ball3};

    ball1.calculateF(bodies);

    System.out.println("\n--------------------\n");
  }
}
