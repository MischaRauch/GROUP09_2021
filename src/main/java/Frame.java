
import titan.StateInterface;
import src.main.java.titan.Vector3dInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class can be used for visualisation of the Solar System.
 */
public class Frame extends JFrame implements ActionListener {

    private Timer timer = new Timer(1, this);
    private JLayeredPane lPane = new JLayeredPane();

    private int stepCount = 0;      // keeps track of which step to show
    private int frameSkip = 100;    // amount of frames to skip per millisecond

    // The components for each planet
    private final PlanetComponent sun;
    private final PlanetComponent mercury;
    private final PlanetComponent venus;
    private final PlanetComponent earth;
    private final PlanetComponent moon;
    private final PlanetComponent mars;
    private final PlanetComponent jupiter;
    private final PlanetComponent saturn;
    private final PlanetComponent titan;
    private final PlanetComponent uranus;
    private final PlanetComponent neptune;
    private final PlanetComponent probe;

    private State[] states; // Array containing all the states

    // Instance fields for scale and offset
    public static double scale = 2.5e-10;
    public static double xOffset = 700;
    public static double yOffset = 400;
    private boolean scaleChanged = false;
    private JButton zoomInButton;
    private JButton zoomOutButton;

    // Array containing the colors for each planet
    private final Color[] colors = {new Color(232, 138, 37), new Color(112, 109, 107), new Color(196, 165, 143),
            new Color(38, 120, 60), new Color(171, 169, 167), new Color(199, 114, 30),
            new Color(209, 142, 84), new Color(212, 169, 131), new Color(115, 191, 135),
            new Color(190, 232, 237), new Color(55, 86, 212), new Color(200, 86, 150)};

    /**
     * Constructor for the frame class. Initialises all the necessary components, calculates the x/y coordinates
     * and adds them to the JLayeredPanel.
     * @param states array containing the state of the system for each step.
     */
    public Frame(StateInterface[] states) {
        this.setSize(2560, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);

        lPane.setOpaque(true);
        lPane.setBackground(Color.BLACK);
        lPane.setLayout(null);

        this.states = new State[states.length];

        // Drawing of trajectories
        for(int i = 0; i < states.length; i+=frameSkip) {
            this.states[i] = (State) states[i];
            if(i % (frameSkip*100) == 0) {
                State state = (State) this.states[i];
                Vector3dInterface[] coordinates = state.getCoordinates();
                for(int j = 1; j < coordinates.length; j++) {
                    if(j!= 4) {
                        TrajectoryComponent t = new TrajectoryComponent(coordinates[j].mul(scale).getX() + xOffset, coordinates[j].mul(scale).getY() + yOffset, colors[j]);
                        lPane.add(t);
                    }
                }
            }
        }

        // Creation of JButtons for panning/zooming
        zoomInButton = new JButton("Zoom in");
        zoomInButton.setBounds(1300,700,100,30);
        zoomInButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                scale *= 1.6;
                scaleChanged = true;
            }
        });
        lPane.add(zoomInButton);
        zoomOutButton = new JButton("Zoom out");
        zoomOutButton.setBounds(1300,660,100,30);
        zoomOutButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                scale *= 0.625;
                scaleChanged = true;
            }
        });
        lPane.add(zoomOutButton);
        JButton exitButton = new JButton("Main menu");
        exitButton.setBounds(1300,740,100,30);
        exitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Frame.this.dispose();
                SelectionMenu.returnToFrame();
            }
        });
        lPane.add(exitButton);

        State state = (State) this.states[stepCount];
        Vector3dInterface[] coordinates = state.getCoordinates();

        // Setting the coordinates for each planet.
        sun = new PlanetComponent("Sun", coordinates[0].mul(scale).getX()+xOffset, coordinates[0].mul(scale).getY()+yOffset, 696342e5*scale, colors[0]);
        mercury = new PlanetComponent("Mercury", coordinates[1].mul(scale).getX()+xOffset, coordinates[1].mul(scale).getY()+yOffset, 4879.4e5*scale, colors[1]);
        venus = new PlanetComponent("Venus", coordinates[2].mul(scale).getX()+xOffset, coordinates[2].mul(scale).getY()+yOffset, 12104e5*scale, colors[2]);
        earth = new PlanetComponent("Earth", coordinates[3].mul(scale).getX()+xOffset, coordinates[3].mul(scale).getY()+yOffset, 12742e5*scale, colors[3]);
        moon = new PlanetComponent("Moon", coordinates[4].mul(scale).getX()+xOffset, coordinates[4].mul(scale).getY()+yOffset, 3474.2e5*scale, colors[4]);
        mars = new PlanetComponent("Mars", coordinates[5].mul(scale).getX()+xOffset, coordinates[5].mul(scale).getY()+yOffset, 6779e5*scale, colors[5]);
        jupiter = new PlanetComponent("Jupiter", coordinates[6].mul(scale).getX()+xOffset, coordinates[6].mul(scale).getY()+yOffset, 139820e5*scale, colors[6]);
        saturn = new PlanetComponent("Saturn", coordinates[7].mul(scale).getX()+xOffset, coordinates[7].mul(scale).getY()+yOffset, 116460e5*scale, colors[7]);
        titan = new PlanetComponent("Titan", coordinates[8].mul(scale).getX()+xOffset, coordinates[8].mul(scale).getY()+yOffset, 5149.5e5*scale, colors[8]);
        uranus = new PlanetComponent("Uranus", coordinates[9].mul(scale).getX()+xOffset, coordinates[9].mul(scale).getY()+yOffset, 50724e5*scale, colors[9]);
        neptune = new PlanetComponent("Neptune", coordinates[10].mul(scale).getX()+xOffset, coordinates[10].mul(scale).getY()+yOffset, 49224e5*scale, colors[10]);
        probe = new PlanetComponent("Probe", coordinates[11].mul(scale).getX()+xOffset, coordinates[11].mul(scale).getY()+yOffset, 49224e5*scale, colors[11]);

        // Adds each planet to the frame
        lPane.add(mercury);
        lPane.add(venus);
        lPane.add(moon);
        lPane.add(earth);
        lPane.add(mars);
        lPane.add(jupiter);
        lPane.add(titan);
        lPane.add(saturn);
        lPane.add(uranus);
        lPane.add(sun);
        lPane.add(neptune);
        lPane.add(probe);

        this.add(lPane);
        this.revalidate();
        this.setVisible(true);

        timer.start();
    }

    // Each step update the x/y coordinates for all the components.
    @Override
    public void actionPerformed(ActionEvent e) {
        if(scaleChanged) {
            lPane.removeAll();
            lPane.add(mercury);
            lPane.add(venus);
            lPane.add(moon);
            lPane.add(earth);
            lPane.add(mars);
            lPane.add(jupiter);
            lPane.add(titan);
            lPane.add(saturn);
            lPane.add(uranus);
            lPane.add(sun);
            lPane.add(neptune);
            lPane.add(probe);
            lPane.add(zoomOutButton);
            lPane.add(zoomInButton);
            scaleChanged = false;
        }

        // Each action event caused by the timer -> increase stepCount by 1 and set coordinates accordingly
        stepCount += frameSkip;

        // If end of calculations has been reached, reset animation
        if(stepCount >= states.length-1) {
            stepCount = 0;
            timer.stop();
            dispose();
        }
        else{
            State state = (State) states[stepCount];
            Vector3dInterface[] coordinates = state.getCoordinates();

            sun.setCoordinates(coordinates[0].mul(scale).getX()+xOffset, coordinates[0].mul(scale).getY()+yOffset);
            mercury.setCoordinates(coordinates[1].mul(scale).getX()+xOffset, coordinates[1].mul(scale).getY()+yOffset);
            venus.setCoordinates(coordinates[2].mul(scale).getX()+xOffset, coordinates[2].mul(scale).getY()+yOffset);
            earth.setCoordinates(coordinates[3].mul(scale).getX()+xOffset, coordinates[3].mul(scale).getY()+yOffset);
            moon.setCoordinates(coordinates[4].mul(scale).getX()+xOffset, coordinates[4].mul(scale).getY()+yOffset);
            mars.setCoordinates(coordinates[5].mul(scale).getX()+xOffset, coordinates[5].mul(scale).getY()+yOffset);
            jupiter.setCoordinates(coordinates[6].mul(scale).getX()+xOffset, coordinates[6].mul(scale).getY()+yOffset);
            saturn.setCoordinates(coordinates[7].mul(scale).getX()+xOffset, coordinates[7].mul(scale).getY()+yOffset);
            titan.setCoordinates(coordinates[8].mul(scale).getX()+xOffset, coordinates[8].mul(scale).getY()+yOffset);
            uranus.setCoordinates(coordinates[9].mul(scale).getX()+xOffset, coordinates[9].mul(scale).getY()+yOffset);
            neptune.setCoordinates(coordinates[10].mul(scale).getX()+xOffset, coordinates[10].mul(scale).getY()+yOffset);
            probe.setCoordinates(coordinates[11].mul(scale).getX()+xOffset, coordinates[11].mul(scale).getY()+yOffset);

            // If the scale has changed due to zooming scale the size of the planets
            sun.setSize(696342e5*scale);
            mercury.setSize(4879.4e5*scale);
            venus.setSize(12104e5*scale);
            earth.setSize(12742e5*scale);
            moon.setSize(3474.2e5*scale);
            mars.setSize(6679e5*scale);
            jupiter.setSize(139820e5*scale);
            saturn.setSize(116460e5*scale);
            titan.setSize(5149.5e5*scale);
            uranus.setSize(50724e5*scale);
            neptune.setSize(49224e5*scale);

            repaint();
        }
    }
}
