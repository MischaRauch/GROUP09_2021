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

    Vector3dInterface v1 = new Vector3d(5, 2, 7);
    System.out.println(v1.norm());

    v1.setX(2);
    v1.setY(5);

    System.out.println(v1.norm());

    //SolarSystem sol = new SolarSystem(60*10, 525600/10);
    //SystemFrame frame = new SystemFrame(sol.getLocations());


    System.out.println("\n--------------------\n");

  }
}
