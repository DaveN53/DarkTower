import java.awt.BorderLayout;
import javax.swing.*;

/**
 * Created by David on 8/19/2014.
 */
public class DarkTowerView extends JFrame{

    public DarkTowerView()
    {
    	super("DarkTower");
    	
        setResizable(false);
        setLayout(new BorderLayout());
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Use These when exporting for the Pi
        //
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        
        add(new DarkTowerPane());
        setSize(320,240);
        setLocationRelativeTo(null);
        setVisible(true);


        
    }

}
