import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;


public class DarkTowerPane extends JPanel{
	
	public DarkTowerPane()
	{
		setupLabel();
		//setupComboBox();
	}
	
	private void setupLabel()
	{
		ImageIcon test = new ImageIcon("image/logo.png");
		setLayout(new BorderLayout());
        JLabel TestLabel = new JLabel();
        TestLabel.setIcon(test);
        TestLabel.setVerticalAlignment(JLabel.TOP);
        add(TestLabel);
	}
	
	private void setupComboBox()
	{
		String[] spinnerOptions = new String[]{"1","2","3","4"};
		SpinnerListModel playerModel = new SpinnerListModel(spinnerOptions);
		JSpinner playerSelect = new JSpinner(playerModel);
		add(playerSelect);
	}


}
