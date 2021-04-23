import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class TrajectoryComponent extends JComponent {

    private final double x;
    private final double y;
    private final Color c;

    public TrajectoryComponent(double x, double y, Color c) {
        this.setBounds(0,0,2560, 1000);
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public void draw(Graphics2D g2)
    {
        double d = 0.5;
        Ellipse2D.Double circle = new Ellipse2D.Double(x-(0.5* d), y-(0.5* d), d, d);
        g2.setStroke(new BasicStroke(3));
        g2.setColor(c);
        g2.fill(circle);
        g2.draw(circle);
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }
}
