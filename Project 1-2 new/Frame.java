import titan.StateInterface;
import titan.Vector3dInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {

    private Timer timer = new Timer(20, this);
    private JLayeredPane lPane = new JLayeredPane();
    private int stepCount = 0;

    private PlanetComponent sun;
    private PlanetComponent mercury;
    private PlanetComponent venus;
    private PlanetComponent earth;
    private PlanetComponent moon;
    private PlanetComponent mars;
    private PlanetComponent jupiter;
    private PlanetComponent saturn;
    private PlanetComponent titan;
    private PlanetComponent uranus;
    private PlanetComponent neptune;

    private State[] states;

    public static double scale = 0.000000001;
    public static double xOffset = 700;
    public static double yOffset = 400;

    public Frame(StateInterface[] s) {
        this.setSize(2560, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);

        lPane.setOpaque(true);
        lPane.setBackground(Color.BLACK);
        lPane.setLayout(null);

        states = new State[s.length];

        for(int i = 0; i < s.length; i++) {
            states[i] = (State) s[i];
        }

        sun = new PlanetComponent("Sun", states[0].coordinates[0].mul(scale).getX()+xOffset, states[0].coordinates[0].mul(scale).getY()+yOffset, 696342e5*scale, new Color(232, 138, 37));
        mercury = new PlanetComponent("Mercury", states[0].coordinates[1].mul(scale).getX()+xOffset, states[0].coordinates[1].mul(scale).getY()+yOffset, 4879.4e5*scale, new Color(112, 109, 107));
        venus = new PlanetComponent("Venus", states[0].coordinates[2].mul(scale).getX()+xOffset, states[0].coordinates[2].mul(scale).getY()+yOffset, 12104e5*scale, new Color(196, 165, 143));
        earth = new PlanetComponent("Earth", states[0].coordinates[3].mul(scale).getX()+xOffset, states[0].coordinates[3].mul(scale).getY()+yOffset, 12742e5*scale, new Color(38, 120, 60));
        moon = new PlanetComponent("Moon", states[0].coordinates[4].mul(scale).getX()+xOffset, states[0].coordinates[4].mul(scale).getY()+yOffset, 3474.2e5*scale, new Color(171, 169, 167));
        mars = new PlanetComponent("Mars", states[0].coordinates[5].mul(scale).getX()+xOffset, states[0].coordinates[5].mul(scale).getY()+yOffset, 6779e5*scale, new Color(199, 114, 30));
        jupiter = new PlanetComponent("Jupiter", states[0].coordinates[6].mul(scale).getX()+xOffset, states[0].coordinates[6].mul(scale).getY()+yOffset, 139820e5*scale, new Color(209, 142, 84));
        saturn = new PlanetComponent("Saturn", states[0].coordinates[7].mul(scale).getX()+xOffset, states[0].coordinates[7].mul(scale).getY()+yOffset, 116460e5*scale, new Color(212, 169, 131));
        titan = new PlanetComponent("Titan", states[0].coordinates[8].mul(scale).getX()+xOffset, states[0].coordinates[8].mul(scale).getY()+yOffset, 5149.5e5*scale, new Color(115, 191, 135));
        uranus = new PlanetComponent("Uranus", states[0].coordinates[9].mul(scale).getX()+xOffset, states[0].coordinates[9].mul(scale).getY()+yOffset, 50724e5*scale, new Color(190, 232, 237));
        neptune = new PlanetComponent("Neptune", states[0].coordinates[10].mul(scale).getX()+xOffset, states[0].coordinates[10].mul(scale).getY()+yOffset, 49224e5*scale, new Color(55, 86, 212));

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

        this.add(lPane);
        this.revalidate();
        this.setVisible(true);

        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Each action event caused by the timer -> increase stepCount by 1 and set coordinates accordingly
        stepCount += 1;
        sun.setCoordinates(states[stepCount].coordinates[0].mul(scale).getX()+xOffset, states[stepCount].coordinates[0].mul(scale).getY()+yOffset);
        mercury.setCoordinates(states[stepCount].coordinates[1].mul(scale).getX()+xOffset, states[stepCount].coordinates[1].mul(scale).getY()+yOffset);
        venus.setCoordinates(states[stepCount].coordinates[2].mul(scale).getX()+xOffset, states[stepCount].coordinates[2].mul(scale).getY()+yOffset);
        earth.setCoordinates(states[stepCount].coordinates[3].mul(scale).getX()+xOffset, states[stepCount].coordinates[3].mul(scale).getY()+yOffset);
        moon.setCoordinates(states[stepCount].coordinates[4].mul(scale).getX()+xOffset, states[stepCount].coordinates[4].mul(scale).getY()+yOffset);
        mars.setCoordinates(states[stepCount].coordinates[5].mul(scale).getX()+xOffset, states[stepCount].coordinates[5].mul(scale).getY()+yOffset);
        jupiter.setCoordinates(states[stepCount].coordinates[6].mul(scale).getX()+xOffset, states[stepCount].coordinates[6].mul(scale).getY()+yOffset);
        saturn.setCoordinates(states[stepCount].coordinates[7].mul(scale).getX()+xOffset, states[stepCount].coordinates[7].mul(scale).getY()+yOffset);
        titan.setCoordinates(states[stepCount].coordinates[8].mul(scale).getX()+xOffset, states[stepCount].coordinates[8].mul(scale).getY()+yOffset);
        uranus.setCoordinates(states[stepCount].coordinates[9].mul(scale).getX()+xOffset, states[stepCount].coordinates[9].mul(scale).getY()+yOffset);
        neptune.setCoordinates(states[stepCount].coordinates[10].mul(scale).getX()+xOffset, states[stepCount].coordinates[10].mul(scale).getY()+yOffset);

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

        // Repaint
        repaint();

        // If end of calculations has been reached, reset animation
        if(stepCount == states.length-1) {
            stepCount = 0;
        }
    }
}
