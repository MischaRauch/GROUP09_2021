import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionMenu{

    private JLayeredPane lPane = new JLayeredPane();
    private ODESolver solver = new ODESolver(100);
    private static JFrame frame = new JFrame();

    public SelectionMenu() {
        frame.setSize(260, 120);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Simulation selector");

        JButton run_sim1 = new JButton("Simulate trajectory to Titan.");
        JButton run_sim2 = new JButton("Simulate trajectory to Earth.");
        JButton run_sim3 = new JButton("Simulate landing on Titan.");

        run_sim1.setBounds(20,0,220,30);
        run_sim2.setBounds(20,30,220,30);
        run_sim3.setBounds(20,60,220,30);

        run_sim1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                SelectionMenu.frame.setVisible(false);
                Frame frame = new Frame(solver.getStatesSim1());
            }
        });
        run_sim2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                SelectionMenu.frame.setVisible(false);
                Frame frame = new Frame(solver.getStatesSim2());
            }
        });
        run_sim3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                SelectionMenu.frame.setVisible(false);
                LandingViewer viewer = new LandingViewer();
            }
        });

        lPane.add(run_sim1);
        lPane.add(run_sim2);
        lPane.add(run_sim3);

        frame.add(lPane);

        frame.setVisible(true);
    }

    public static void returnToFrame() {
        SelectionMenu.frame.setVisible(true);
    }

}
