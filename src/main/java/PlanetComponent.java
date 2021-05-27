import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * This class can be used for creating JComponents to represent a planet.
 */
public class PlanetComponent extends JComponent {

    // Instance fields for coordinates
    private double x;
    private double y;
    private double d;               // diameter
    private final Color c;          // colour
    private final JLabel label;     // label to display the name of the planet
    private final String name;      // String containing the name of the planet

    /**
     * Constructor, initialises all the instance fields and adds the label.
     * @param name
     * @param x
     * @param y
     * @param d
     * @param c
     */
    public PlanetComponent(String name, double x, double y, double d, Color c) {
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

    /**
     * @param d     the updated diameter
     */
    public void setSize(double d)
    {
        this.d = d;
    }

    /**
     * @param x     the updated x coordinate
     * @param y     the updated y coordinate
     */
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
