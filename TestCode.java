/*
 * @author Group09
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

    /*
    Vector3d ball1X = new Vector3d(2, 5, 0);
    Vector3d ball1V = new Vector3d(1, 2, 1);

    CelestialBody ball1 = new CelestialBody("Earth", 1000, ball1X, ball1V);

    Vector3d ball2X = new Vector3d(10, 1, 4);
    Vector3d ball2V = new Vector3d(1, 2, 1);

    CelestialBody ball2 = new CelestialBody("Sun", 10000, ball2X, ball2V);

    Vector3d ball3X = new Vector3d(9, 8, 5);
    Vector3d ball3V = new Vector3d(5, 4, 3);

    CelestialBody ball3 = new CelestialBody("Whatever", 10000, ball3X, ball3V);

    System.out.println();

    CelestialBody[] bodies = {ball1, ball2, ball3};

    ball1.calculateF(bodies);

    Vector3d v1 = new Vector3d(-8,-3,0);
    Vector3d v2 = new Vector3d(1,1,5);

    System.out.println(v1.sub(v2));
    System.out.println(v1.norm());
    */

    SolarSystem sol = new SolarSystem();
    Vector3dInterface[] sun = sol.getSunPosition();
    Vector3dInterface[] earth = sol.getEarthPosition();
    //System.out.println("TEST "+sun.length);
    //System.out.println("TEST2 "+sun[0]);
    Plotter plt = new Plotter(sun,earth);


    System.out.println("\n--------------------\n");

  }
}
