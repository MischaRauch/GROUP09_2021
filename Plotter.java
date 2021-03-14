import javax.swing.*;

import titan.Vector3dInterface;

import java.awt.*;
import java.util.Random;

public class Plotter
{
    private int width = 800;
    private int height = 800;
    Vector3dInterface[] one;
    Vector3dInterface[] two;
    int C = 10;

    public Plotter(Vector3dInterface[] one, Vector3dInterface[] two) 
    {
        this.one = one;
        this.two = two;
        Points points = new Points();
        JFrame frame = new JFrame("SolarAnimation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(points);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public class Points extends JPanel 
    {

        public void paintComponent(Graphics g) 
        {
          super.paintComponent(g);
      
          Graphics2D g2d = (Graphics2D) g;
      
          g2d.setColor(Color.red);
          g2d.setStroke(new BasicStroke(5));

          for (int i = 0; i < one.length; i++) 
          {
            g2d.drawLine((int) (one[i].getX())*C,(int) (one[i].getY())*C,(int) (one[i].getX())*C,(int) (one[i].getY())*C);

            System.out.println("i "+i);
            System.out.println("COOR-One "+one[i].getX()+" "+one[i].getY());
            System.out.println("COOR-Two "+two[i].getX()+" "+two[i].getY());
            System.out.println("Draw1 "+((int) one[i].getX())*C +" "+(int) (one[i].getY())*C);
            System.out.println("Draw2 "+((int) two[i].getX())*C +" "+(int) (two[i].getY())*C);
          }
          g2d.setColor(Color.blue);
          g2d.setStroke(new BasicStroke(5));

          for (int i = 0; i<two.length; i++) 
          {
            g2d.drawLine((int) (two[i].getX())*C,(int) (two[i].getY())*C,(int) (two[i].getX())*C,(int) (two[i].getY())*C);
          }
        }
    }
}