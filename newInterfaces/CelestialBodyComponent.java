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


public class CelestialBodyComponent extends JComponent
{
  private double x;
  private double y;
  private double d;
  private Color c;
  private JLabel label;
  private String name;
  private ArrayList<JPanel> mercuryA = new ArrayList<JPanel>();
  private ArrayList<JPanel> venusA = new ArrayList<JPanel>();
  private ArrayList<JPanel> earthA = new ArrayList<JPanel>();
  private ArrayList<JPanel> moonA = new ArrayList<JPanel>();
  private ArrayList<JPanel> marsA = new ArrayList<JPanel>();
  private ArrayList<JPanel> jupiterA = new ArrayList<JPanel>();
  private ArrayList<JPanel> saturnA = new ArrayList<JPanel>();
  private ArrayList<JPanel> titanA = new ArrayList<JPanel>();
  private ArrayList<JPanel> uranusA = new ArrayList<JPanel>();
  private ArrayList<JPanel> neptunA = new ArrayList<JPanel>();
  private double initScale = 0.000000001;
  private double xOffset = 700;
  private double yOffset = 400;

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
        for (int i = 0; i < mercuryA.size(); i++)
        {
          mercuryA.get(i).setBounds(0,0,0,0);
        }
        for (int i = 0; i < venusA.size(); i++)
        {
          venusA.get(i).setBounds(0,0,0,0);
        }
        for (int i = 0; i < earthA.size(); i++)
        {
          earthA.get(i).setBounds(0,0,0,0);
        }
        for (int i = 0; i < moonA.size(); i++)
        {
          moonA.get(i).setBounds(0,0,0,0);
        }
        for (int i = 0; i < marsA.size(); i++)
        {
          marsA.get(i).setBounds(0,0,0,0);
        }
        for (int i = 0; i < jupiterA.size(); i++)
        {
          jupiterA.get(i).setBounds(0,0,0,0);
        }
        for (int i = 0; i < saturnA.size(); i++)
        {
          saturnA.get(i).setBounds(0,0,0,0);
        }
        for (int i = 0; i < titanA.size(); i++)
        {
          titanA.get(i).setBounds(0,0,0,0);
        }
        for (int i = 0; i < uranusA.size(); i++)
        {
          uranusA.get(i).setBounds(0,0,0,0);
        }
        for (int i = 0; i < neptunA.size(); i++)
        {
          neptunA.get(i).setBounds(0,0,0,0);
        }
        mercuryA.clear();
        venusA.clear();
        earthA.clear();
        moonA.clear();
        marsA.clear();
        jupiterA.clear();
        saturnA.clear();
        titanA.clear();
        uranusA.clear();
        neptunA.clear();
      }
      for (tmp = oldStepCount; tmp < sF.stepCount; tmp+=500) 
      {
        if(this.name == "Mercury")
        {
          JPanel mercury = new JPanel();
          mercury.setBackground(new Color(112, 109, 107));
          double w = sF.locations[1][sF.stepCount].mul(sF.scale).getX()+sF.xOffset; 
          double v = sF.locations[1][sF.stepCount].mul(sF.scale).getY()+sF.yOffset;
          mercury.setBounds((int) x ,(int) y, 5, 5);
          mercuryA.add(mercury);
          sF.lPane.add(mercury);
        }
        if(this.name == "Venus")
        {
          JPanel venus = new JPanel();
          venus.setBackground(new Color(196, 165, 143));
          double w = sF.locations[2][sF.stepCount].mul(sF.scale).getX()+sF.xOffset; 
          double v = sF.locations[2][sF.stepCount].mul(sF.scale).getY()+sF.yOffset;
          venus.setBounds((int) w,(int) v, 5, 5);
          venusA.add(venus);
          sF.lPane.add(venus);
        }
        if(this.name == "Earth")
        {
          JPanel earth = new JPanel();
          earth.setBackground(new Color(38, 120, 60));
          double w = sF.locations[3][sF.stepCount].mul(sF.scale).getX()+sF.xOffset; 
          double v = sF.locations[3][sF.stepCount].mul(sF.scale).getY()+sF.yOffset;
          earth.setBounds((int)w,(int)v, 5, 5);
          earthA.add(earth);
          sF.lPane.add(earth);
        }
        if(this.name == "Moon")
        {
          JPanel moon = new JPanel();
          moon.setBackground(new Color(171, 169, 167));
          double w = sF.locations[4][sF.stepCount].mul(sF.scale).getX()+sF.xOffset; 
          double v = sF.locations[4][sF.stepCount].mul(sF.scale).getY()+sF.yOffset;
          moon.setBounds((int)w,(int)v, 5, 5);
          moonA.add(moon);
          sF.lPane.add(moon);
        }
        if(this.name == "Mars")
        {
          JPanel mars = new JPanel();
          mars.setBackground(new Color(199, 114, 30));
          double w = sF.locations[5][sF.stepCount].mul(sF.scale).getX()+sF.xOffset; 
          double v = sF.locations[5][sF.stepCount].mul(sF.scale).getY()+sF.yOffset;
          mars.setBounds((int)w,(int)v, 5, 5);
          marsA.add(mars);
          sF.lPane.add(mars);
        }
        if(this.name == "Jupiter")
        {
          JPanel jupiter = new JPanel();
          jupiter.setBackground(new Color(209, 142, 84));
          double w = sF.locations[6][sF.stepCount].mul(sF.scale).getX()+sF.xOffset; 
          double v = sF.locations[6][sF.stepCount].mul(sF.scale).getY()+sF.yOffset;
          jupiter.setBounds((int)w,(int)v, 5, 5);
          jupiterA.add(jupiter);
          sF.lPane.add(jupiter);
        }
        if(this.name == "Saturn")
        {
          JPanel saturn = new JPanel();
          saturn.setBackground(new Color(212, 169, 131));
          double w = sF.locations[7][sF.stepCount].mul(sF.scale).getX()+sF.xOffset; 
          double v = sF.locations[7][sF.stepCount].mul(sF.scale).getY()+sF.yOffset;
          saturn.setBounds((int)w,(int)v, 5, 5);
          saturnA.add(saturn);
          sF.lPane.add(saturn);
        }
        if(this.name == "Titan")
        {
          JPanel titan = new JPanel();
          titan.setBackground(new Color(115, 191, 135));
          double w = sF.locations[8][sF.stepCount].mul(sF.scale).getX()+sF.xOffset; 
          double v = sF.locations[8][sF.stepCount].mul(sF.scale).getY()+sF.yOffset;
          titan.setBounds((int)w,(int)v, 5, 5);
          titanA.add(titan);
          sF.lPane.add(titan);
        }
        if(this.name == "Uranus")
        {
          JPanel uranus = new JPanel();
          uranus.setBackground(new Color(190, 232, 237));
          double w = sF.locations[9][sF.stepCount].mul(sF.scale).getX()+sF.xOffset; 
          double v = sF.locations[9][sF.stepCount].mul(sF.scale).getY()+sF.yOffset;
          uranus.setBounds((int)w,(int)v, 5, 5);
          uranusA.add(uranus);
          sF.lPane.add(uranus);
        }
        if(this.name == "Neptune")
        {
          JPanel neptune = new JPanel();
          neptune.setBackground(new Color(55, 86, 212));
          double w = sF.locations[10][sF.stepCount].mul(sF.scale).getX()+sF.xOffset; 
          double v = sF.locations[10][sF.stepCount].mul(sF.scale).getY()+sF.yOffset;
          neptune.setBounds((int)w,(int)v, 5, 5);
          neptunA.add(neptune);
          sF.lPane.add(neptune);
        }
        oldStepCount = sF.stepCount;
        initScale = sF.scale;
        xOffset = sF.xOffset;
        yOffset = sF.yOffset;
      }
    }
  }
}
