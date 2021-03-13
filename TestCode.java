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

    Vector3d sunX = new Vector3d(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06);
    Vector3d sunV = new Vector3d(-1.420511669610689e+01, -4.954714716629277e+00, 3.994237625449041e-01);
    CelestialBody sun = new CelestialBody(1.988500e30, sunX, sunV);
    //CelestialBody sun = new CelestialBody(2e30, sunX, sunV);
    Vector3d earthX = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);
    Vector3d earthV = new Vector3d(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01);
    CelestialBody earth = new CelestialBody(5.97219e24, earthX, earthV);
    //CelestialBody earth = new CelestialBody(6e24, earthX, earthV);

    System.out.println(sun.gravityAcceleration(sun, earth));
    System.out.println(sun.gravityAcceleration(earth, sun));

  }
}
