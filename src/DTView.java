import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Created by David on 9/20/2014.
 */
public class DTView extends JFrame {

    private JPanel mainPanel = new JPanel();

    JLabel mainLabel = new JLabel("Main");
    JLabel GameLabel = new JLabel();

    private Font font;

    java.util.List<playerButton> playerSelectButtons = new ArrayList<playerButton>();
    List<difficultyButton> difficultyButtons = new ArrayList<difficultyButton>();
    List<gameButton> gameButtons = new ArrayList<gameButton>();
    JButton okButton = new JButton("OK");

    //GAME SETUP
    public DTView()
    {
        super("DarkTower");

        // setResizable(false);
        setLayout(new BorderLayout());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Use These when exporting for the Pi
        //
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        mainPanel.setBackground(Color.black);
        setupFont();
        setupGame();
        add(mainPanel);

        setSize(320, 240);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupFont()
    {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("Font/alarm clock.ttf")).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Font/alarm clock.ttf")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setupGame()
    {
        setupLabel();
        setupButtons();
        setupGameButtons();
        setupDiffButtons();
    }

    private void setupLabel()
    {
        mainPanel.setLayout(new SpringLayout());
        ImageIcon test = new ImageIcon("image/logo.png");
        mainLabel.setIcon(test);
        mainLabel.setVerticalAlignment(JLabel.TOP);
        mainLabel.setVisible(true);
        mainPanel.add(mainLabel);
    }

    private void setupButtons()
    {
        for(int i=1;i<5;i++)
        {
            playerButton pb= new playerButton(i);
            pb.setVisible(false);
            mainPanel.add(pb);
            playerSelectButtons.add(pb);
        }
    }

    private void setupDiffButtons()
    {
        for(int i=1;i<4;i++)
        {
            difficultyButton db = new difficultyButton(i);
            db.setVisible(false);
            difficultyButtons.add(db);
        }
    }

    public void setupGameButtons()
    {
        for(int i=1;i<10;i++)
        {
            gameButton gb = new gameButton(i);
            gb.setVisible(false);
            gameButtons.add(gb);
        }
    }

    //START UP THE GAME
    public void RunGame()
    {
        mainPanel.remove(mainLabel);
        mainPanel.setLayout(new GridLayout(0, 2));
        for(playerButton pb: playerSelectButtons)
            pb.setVisible(true);
    }

    //DISPLAY DIFFERENT VIEWS
    public void DisplayDifficultySelection()
    {
        for(playerButton pb: playerSelectButtons)
            pb.setVisible(false);
        removePlayerButtons();

        for(difficultyButton db: difficultyButtons)
        {
            db.setVisible(true);
            mainPanel.add(db);
        }
        mainPanel.setLayout(new GridLayout(0, 1));
    }

    public void DisplayGameButtons()
    {
        hideEvent();
        okButton.setVisible(false);
        mainPanel.remove(okButton);
        mainPanel.setLayout(new GridLayout(0, 3));
        mainPanel.repaint();

        for(gameButton gb: gameButtons) {
            gb.setVisible(true);
            mainPanel.add(gb);
        }
    }

    public void DisplayPlayerTurn(int player)
    {
        displayEvent(null, "0" + player);
        okButton.setVisible(true);
        mainPanel.add(okButton);
    }

    private void displayEvent(ImageIcon gameIcon, String text)
    {
        hideEvent();

        mainPanel.setLayout(new FlowLayout());

        if(gameIcon != null)
            GameLabel.setIcon(gameIcon);
        else
            GameLabel.setIcon(new ImageIcon());

        GameLabel.setText(text);
        GameLabel.setForeground(Color.red);
        GameLabel.setHorizontalTextPosition(JLabel.CENTER);
        GameLabel.setVerticalTextPosition(JLabel.BOTTOM);
        GameLabel.setFont(font);
        GameLabel.setVisible(true);
        mainPanel.add(GameLabel);

    }

    public void DisplayNothing()
    {
        displayEvent(null,"");
    }

    public void DisplayDragon()
    {
        ImageIcon dragon = new ImageIcon("image/dragon.jpg");
        displayEvent(dragon,"DRAGON");
    }

    public void DisplaySword()
    {
        ImageIcon sword = new ImageIcon("image/dragon.jpg");
        displayEvent(sword,"");
    }

    public void DisplayLost()
    {
        ImageIcon lost = new ImageIcon("image/lost.jpg");
        displayEvent(lost,"LOST");
    }

    public void DisplayScout()
    {
        ImageIcon scout = new ImageIcon("image/scout.jpg");
        displayEvent(scout,"LOST");
    }

    public void DisplayPlague()
    {
        ImageIcon plague = new ImageIcon("image/plague.jpg");
        displayEvent(plague,"PLAGUE");
    }

    public void DisplayHealer()
    {
        ImageIcon plagueHealer = new ImageIcon("image/healer.jpg");
        displayEvent(plagueHealer, "HEALER");
    }

    public void DisplayBrigands(int num)
    {
        ImageIcon brigands = new ImageIcon("image/brigands.jpg");
        String text = "";
        if(num < 10)
            text = "0";
        displayEvent(brigands,text + num);
    }

    public void DisplayWarriors(int num)
    {
        ImageIcon warriors = new ImageIcon("image/warriors.jpg");
        String text = "";
        if(num < 10)
            text = "0";
        displayEvent(warriors,text + num);
    }

    //HIDE DIFFERENT VIEWS
    private void removePlayerButtons()
    {
        for(playerButton pb: playerSelectButtons)
            mainPanel.remove(pb);
    }

    public void RemoveDifficultyButtons()
    {
        for(difficultyButton db: difficultyButtons)
            db.setVisible(false);
        for(difficultyButton db: difficultyButtons)
            mainPanel.remove(db);

    }

    private void hideEvent()
    {
        GameLabel.setVisible(false);
        mainPanel.remove(GameLabel);
    }

    public void HideGameButtons()
    {
        for(gameButton gb: gameButtons)
        {
            gb.setVisible(false);
            mainPanel.remove(gb);
        }
    }

}
