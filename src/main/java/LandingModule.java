import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.*;

public class LandingModule {

    private double xLeft;
    private double yTop;

    /**
    Constructs the top left coordinates of the body of the landing module.
    @param x the x position of the top left coordinates of the body of the landing module.
    @param y the y position of the top left coordinates of the body of the landing module.
    */
    public LandingModule(double x, double y) { 
        xLeft = x; 
        yTop = y; 
    }

  	/**
  	Creates the components of the landing module and draws them.
  	*/
    public void draw(Graphics2D g2) {

        // Creates a rectangle and an ellipse that represent the body and the pod of the landing module respectively.
		Rectangle2D.Double body = new Rectangle2D.Double(xLeft, yTop, 65, 40);
		Ellipse2D.Double pod = new Ellipse2D.Double(xLeft - 7.5, yTop - 60, 80, 60);

		// Creates four points that represent the top and the bottom of the left leg, and the top and the bottom of the right leg respectively.
		Point2D.Double r1 = new Point2D.Double(xLeft, yTop);
        Point2D.Double r2 = new Point2D.Double(xLeft - 30, yTop + 60);
        Point2D.Double r3 = new Point2D.Double(xLeft + 65, yTop);
        Point2D.Double r4 = new Point2D.Double(xLeft + 95, yTop + 60);

        // Creates two lines that represent the left and the right leg of the landing module respectively.
        Line2D.Double leftLeg = new Line2D.Double(r1, r2);
        Line2D.Double rightLeg = new Line2D.Double(r3, r4);

        // Fills the body in yellow and the pod in light gray, and draws the left leg and the right leg of the landing module.
        g2.setPaint(Color.YELLOW);
        g2.fill(body);
        g2.setPaint(Color.LIGHT_GRAY);
        g2.fill(pod);
        g2.draw(leftLeg);
        g2.draw(rightLeg);  

    }

}