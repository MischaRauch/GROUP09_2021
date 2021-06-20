import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class LandingViewer extends JFrame{

	public LandingViewer() {
		// Creates a this and sets its size, title and default operation.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width, screenSize.height);
		this.setTitle("Landing on Titan");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Creates the component of the LandingComponent class and adds it to the frame.
		LandingComponent component = new LandingComponent();
		this.add(component);

		WindModel wM = new WindModel();
		SingleState[] states = wM.calculateFall(0.1, 476.1, new Vector3d((screenSize.width/2.0 - 32.5) * 25,150000,0), new Vector3d(0,0,0));

		// AnimationListener Listener
		class AnimationListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				component.moveComponent(states);
				repaint();
			}
		}

		// Timer for the CheckboxListener
		AnimationListener animationListener = new AnimationListener();
		final int TIME = 10;	// TIME in milliseconds
		Timer t = new Timer(TIME, animationListener); 	// Every 10 ms (TIME), it runs the actionPerformed method of the AnimationListener inner class.
		t.start();

		// Makes the this visible.
		this.setVisible(true);
	}

}