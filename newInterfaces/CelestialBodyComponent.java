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

public class CelestialBodyComponent extends JComponent
{
  private double x;
  private double y;
  private double d;
  private Color c;
  private JLabel label;
  private String name;

  public CelestialBodyComponent(String name, double x, double y, double d, Color c)
  {
    this.setBounds(0,0,2560, 1000);
    this.x = x;
    this.y = y;
    this.d = d;
    this.c = c;
    this.name = name;
    label = new JLabel(name);
    label.setForeground(Color.WHITE);
    label.setVisible(true);
    if(name.equals("Moon") || name.equals("Titan"))
    {
      label.setBounds((int) (x-10), (int) (y-51), 100, 100);
      label.setFont((new Font(label.getFont().getName(), Font.PLAIN, 8)));
    }
    else
    {
      label.setBounds((int) (x-18), (int) (y-62), 100, 100);
    }
    this.add(label);
  }

  public void setSize(double d)
  {
    this.d = d;
  }

  public void setCoordinates(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  public void draw(Graphics2D g2)
  {
    if(name.equals("Moon") || name.equals("Titan"))
    {
      label.setBounds((int) (x-10), (int) (y-51), 100, 100);
      label.setFont((new Font(label.getFont().getName(), Font.PLAIN, 8)));
    }
    else
    {
      label.setBounds((int) (x-18), (int) (y-62), 100, 100);
    }
    Ellipse2D.Double planet = new Ellipse2D.Double(x-(0.5*d), y-(0.5*d), d, d);
    g2.setStroke(new BasicStroke(3));
    g2.setColor(c);
    g2.fill(planet);
    g2.draw(planet);
  }

  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    draw(g2);
  }
}
