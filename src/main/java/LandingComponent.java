import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.geom.*;

/**
A component that draws the background, the surface of Titan and the landing module.
*/
public class LandingComponent extends JComponent {

	// Gets the dimension of the screen.
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	// The initial top left coordinates of the body of the landing module.
	double xPos = screenSize.width/2.0 - 32.5;
	double yPos = 80;

	// The index used in the moveComponent method to go over every SingleState object of the states array.
	int index = 0;

	/**
	Paints the background, the surface of Titan and the landing module.
	*/
	public void paintComponent(Graphics g) {

		// Recovers Graphics 2D.
		Graphics2D g2 = (Graphics2D) g;

		// Creates a rectangle that represents the background, sets the paint color to black and fills it.	
		Rectangle2D.Double background = new Rectangle2D.Double(0, 0, screenSize.width, screenSize.height);
		g2.setPaint(Color.BLACK);
		g2.fill(background);

		// Creates a rectangle that represents the surface of Titan, sets the paint color to orange and fills it.
		Rectangle2D.Double titanSurface = new Rectangle2D.Double(0, screenSize.height * 2.0/3.0, screenSize.width, screenSize.height * 1.0/3.0);
		g2.setPaint(Color.ORANGE);
      	g2.fill(titanSurface);

      	// Constructs the landing module, sets the drawing thickness to 3 and draws it.
      	LandingModule landingModule = new LandingModule(xPos, yPos);
      	g2.setStroke(new BasicStroke(3));
      	landingModule.draw(g2);

	}

	/**
	Changes the top left coordinates of the body of the landing module to simulate movement of the latter.
	At each execution, it takes the next SingleState object of the states array and gets the x and y positions of its coordinates Vector3d object. 
	*/
	public void moveComponent(SingleState[] states) {

		if (index < states.length) {
			xPos = states[index].getCoordinates().getX();
			yPos = states[index].getCoordinates().getY();
			index = index + 1;
		}

	}

}