/*
 * @author Matthijs Vossen
 * @version 0.99.0
 *
 * This class is for testing code
 */

public class TestCode
{
  public static void main(String[] args)
  {
    Vector3d v1 = new Vector3d(2, 3, 5);
    System.out.println(v1);
    System.out.println(v1.norm());

    Vector3d v2 = new Vector3d(2, 5, -4);
    Vector3d v3 = new Vector3d(-2, -3, -5);

    System.out.println(v2.dist(v3));
    System.out.println(v2.add(v3));


  }
}
