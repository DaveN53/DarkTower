import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by David on 9/20/2014.
 */
public class playerButton extends JButton
{
    int val;
    playerButton(final int value)
    {
        val = value;
        setText(value + " player");
    }
}