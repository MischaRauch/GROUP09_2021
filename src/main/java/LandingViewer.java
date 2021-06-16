import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.Timer;

public class LandingViewer {

	public static void main(String[] args) {

		// Creates a frame and sets its size, title and default operation.
		JFrame frame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width, screenSize.height);
		//frame.setSize(1000, 1000);
		frame.setTitle("Landing on Titan");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Creates the component of the LandingComponent class and adds it to the frame.
		LandingComponent component = new LandingComponent();
		frame.add(component);

		WindModel wM = new WindModel();
        SingleState[] states = wM.calculateFall(0.1, 471.1, new Vector3d(467.5,150000,0), new Vector3d(0,0,0));
		//SingleState[] states = wM.performFullFalling(0.1, 471.1, new Vector3d(467.5,150000,0), new Vector3d(0,0,0));


		// AnimationListener Listener
		class AnimationListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				component.moveComponent(states);
				frame.repaint();
			}
		}

		// Timer for the CheckboxListener
		AnimationListener animationListener = new AnimationListener();
		final int TIME = 5;	// TIME in milliseconds
		Timer t = new Timer(TIME, animationListener); 	// Every 500 ms (TIME), it runs the actionPerformed method of the AnimationListener inner class.
		t.start();

		// Makes the frame visible.
		frame.setVisible(true);

	}

}