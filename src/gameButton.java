import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by David on 9/20/2014.
 */
public class gameButton extends JButton
{
    int val;
    public gameButton(int value)
    {
        val = value;
        String text ="";

        switch(value)
        {
            case 1:
                //text = "Yes";
                setEnabled(false);
                break;
            case 2:
                text = "Bazaar";
                break;
            case 3:
                //text = "No";
                setEnabled(false);
                break;
            case 4:
                text = "Tomb / Ruin";
                break;
            case 5:
                text = "Move";
                break;
            case 6:
                text = "Sanctuary / Citadel";
                break;
            case 7:
                text = "Dark Tower";
                break;
            case 8:
                text = "Frontier";
                break;
            case 9:
                text = "Inventory";
                break;
        }

        setText(text);
    }
}