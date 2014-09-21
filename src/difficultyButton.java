import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by David on 9/20/2014.
 */
public class difficultyButton extends JButton
{
    int val;
    difficultyButton(int value)
    {
        val = value;
        String text = "Level " + value;
        switch(value)
        {
            case 1:
                text += ":    17-32";
                break;
            case 2:
                text += ":    33-64";
                break;
            case 3:
                text += ":    17-64";
                break;
        }
        setText(text);
    }
}