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

public class RocketComponent extends JComponent
{
  private double x;
  private double y;
  private double width;
  private double height;
  private JLabel label;

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
  }
}
