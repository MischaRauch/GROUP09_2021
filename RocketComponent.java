/*
 * @author Group09
 * @version 0.99.0
 *
 * This class is for testing code
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class RocketComponent extends JComponent
{
  private double x;
  private double y;
  private double width;
  private double height;
  private JLabel label;
  private ArrayList<JPanel> rocketA = new ArrayList<JPanel>();
  private double initScale = 0.000000001;
  private double xOffset = 700;
  private double yOffset = 400;

  public RocketComponent(double x, double y, double width, double height)
  {
    this.setBounds(0,0,2560, 1000);
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    label = new JLabel("Probe");
    label.setForeground(Color.WHITE);
    label.setVisible(true);
    label.setBounds((int) (x-18), (int) (y-62), 100, 100);
    this.add(label);
  }

  public void setSize(double width, double height)
  {
    this.width = width;
    this.height = height;
  }

  public void setCoordinates(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  public void draw(Graphics2D g2)
  {
    label.setBounds((int) (x-18), (int) (y-62), 100, 100);
    Rectangle2D.Double rocket = new Rectangle2D.Double(x-(0.5*width), y-(0.5*height), width, height);
    g2.setStroke(new BasicStroke(3));
    g2.setColor(Color.GRAY);
    g2.fill(rocket);
    g2.draw(rocket);
  }

  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    draw(g2);
    paintTrajectory(g2);
  }

  public void paintTrajectory(Graphics2D g)
  {
    SystemFrame sF = new SystemFrame();
    int oldStepCount = 0;
    if(sF.stepCount % 150 == 0)
    {
      int tmp;
      //if scale change delete all plotted points
      if (initScale != sF.scale || xOffset != sF.xOffset || yOffset != sF.yOffset)
      {
        for (int i = 0; i < rocketA.size(); i++)
        {
          rocketA.get(i).setBounds(0,0,0,0);
        }
        rocketA.clear();
      }
      for (tmp = oldStepCount; tmp < sF.stepCount; tmp+=500) 
      {
          JPanel rocket = new JPanel();
          rocket.setBackground(new Color(21, 239, 21));
          double w = sF.locations[1][sF.stepCount].mul(sF.scale).getX()+sF.xOffset; 
          double v = sF.locations[1][sF.stepCount].mul(sF.scale).getY()+sF.yOffset;
          rocket.setBounds((int) x ,(int) y, 5, 5);
          rocketA.add(rocket);
          sF.lPane.add(rocket);
        }
        oldStepCount = sF.stepCount;
        initScale = sF.scale;
        xOffset = sF.xOffset;
        yOffset = sF.yOffset;
      }
    }
}
