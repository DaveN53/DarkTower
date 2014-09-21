import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Created by David on 8/19/2014.
 */
public class DarkTowerView extends JFrame{

    class playerButton extends JButton
    {
        playerButton(final int value)
        {
            setText(value + " player");
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    darkTowerCore.SetPlayers(value);
                    selectDifficulty();
                }
            });
        }
    }

    class difficultyButton extends JButton
    {
        difficultyButton(final int value)
        {
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
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    darkTowerCore.SetDifficulty(value);

                    setupGameButtons();
                    startFirstTurn();
                }
            });
        }
    }

    class gameButton extends JButton
    {
        public gameButton(final int value)
        {
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

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GameEvent current = darkTowerCore.SetPlayerMove(value);
                    actOnGameEvent(current);
                }
            });
        }
    }

    DarkTowerCore darkTowerCore = new DarkTowerCore();

    JLabel TestLabel = new JLabel("Main");
    JLabel GameLabel = new JLabel();
    List<playerButton> playerSelectButtons = new ArrayList<playerButton>();
    List<difficultyButton> difficultyButtons = new ArrayList<difficultyButton>();
    List<gameButton> gameButtons = new ArrayList<gameButton>();
    JButton okButton = new JButton();

    JPanel mainPanel = new JPanel();
    Font font;

    Timer GameEventTimer;
    Timer AudioTimer;

    Timer BattleStartTimer;
    Timer BattleMiddleTimer;
    Timer BattleEndTimer;

    String audioFile;

    public DarkTowerView()
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

        okButton.setText("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPlayerTurn();
            }
        });


        GameEventTimer = new Timer(1000,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameEvent event = darkTowerCore.GetCurrentEvent();
                actOnGameEvent(event);
                GameEventTimer.stop();
            }
        });
        AudioTimer = new Timer(200,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.PlayWav(audioFile);
                AudioTimer.stop();
            }
        });
        BattleStartTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actOnGameEvent(GameEvent.ROUNDSTART);
                BattleStartTimer.stop();
            }
        });

        BattleMiddleTimer = new Timer(6000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actOnGameEvent(GameEvent.ROUNDMIDDLE);
                BattleMiddleTimer.stop();
            }
        });

        BattleEndTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actOnGameEvent(GameEvent.ROUNDEND);
                BattleEndTimer.stop();
            }
        });


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
    }

    private void setupLabel()
    {
        mainPanel.setLayout(new SpringLayout());
        ImageIcon test = new ImageIcon("image/logo.png");
        TestLabel.setIcon(test);
        TestLabel.setVerticalAlignment(JLabel.TOP);
        TestLabel.setVisible(true);
        mainPanel.add(TestLabel);
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

    private void removePlayerButtons()
    {
        for(playerButton pb: playerSelectButtons)
            mainPanel.remove(pb);
    }

    private void removeDiffButtons()
    {
        for(difficultyButton db: difficultyButtons)
            mainPanel.remove(db);
    }

    private void selectDifficulty()
    {
        for(playerButton pb: playerSelectButtons)
            pb.setVisible(false);
        removePlayerButtons();

        for(int i=1;i<4;i++)
        {
            difficultyButton db = new difficultyButton(i);
            db.setVisible(true);
            mainPanel.add(db);
            difficultyButtons.add(db);
        }
        mainPanel.setLayout(new GridLayout(0, 1));
    }

    public void setupGameButtons()
    {
        for(int i=1;i<10;i++)
        {
            gameButton gb = new gameButton(i);
            gb.setVisible(false);
            //mainPanel.add(gb);
            gameButtons.add(gb);
        }
    }

    public void startFirstTurn()
    {
        for(difficultyButton db: difficultyButtons)
            db.setVisible(false);
        removeDiffButtons();
        displayPlayerTurn();

    }

    public void startPlayerTurn()
    {
        hideEvent();
        okButton.setVisible(false);
        mainPanel.remove(okButton);
        mainPanel.setLayout(new GridLayout(0, 3));
        mainPanel.repaint();
        displayGameButtons();
    }

    private void displayPlayerTurn()
    {
        int num = darkTowerCore.GetCurrentPlayerNumber();
        displayEvent(null, "0" + num);
        okButton.setVisible(true);
        mainPanel.add(okButton);
    }

    /**
     *
     * @param gameIcon leave null if no Icon is desired
     * @param text text to display to screen
     */
    private void displayEvent(ImageIcon gameIcon, String text)
    {
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

    private void hideEvent()
    {
        GameLabel.setVisible(false);
        mainPanel.remove(GameLabel);
    }

    private void actOnGameEvent(GameEvent e)
    {
        hideGameButtons();

        switch(e)
        {
            case BATTLESTART:
                audioFile = "audio/battle.wav";
                darkTowerCore.Battle();
                AudioTimer.start();
                BattleStartTimer.start();
                BattleMiddleTimer.start();
                BattleEndTimer.start();
                break;
            case CURSE:
                break;
            case DRAGON:
                ImageIcon dragon = new ImageIcon("image/dragon.jpg");
                displayEvent(dragon,"DRAGON");
                //AudioPlayer.PlayDragon();
                audioFile = "audio/dragon.wav";
                darkTowerCore.EndPlayerTurn();
                AudioTimer.start();
                GameEventTimer.start();
                break;
            case DRAGONKILL:
                ImageIcon sword = new ImageIcon("image/dragon.jpg");
                displayEvent(sword,"");
                audioFile = "audio/dragon-kill.wav";
                darkTowerCore.EndPlayerTurn();
                AudioTimer.start();
                GameEventTimer.start();
                break;
            case LOST:
                ImageIcon lost = new ImageIcon("image/lost.jpg");
                displayEvent(lost,"LOST");
                audioFile = "audio/lost.wav";
                darkTowerCore.EndPlayerTurn();
                AudioTimer.start();
                GameEventTimer.start();
                break;
            case LOSTSCOUT:
                ImageIcon scout = new ImageIcon("image/scout.jpg");
                displayEvent(scout,"LOST");
                audioFile = "audio/lost.wav";
                darkTowerCore.EndPlayerTurn();
                AudioTimer.start();
                GameEventTimer.start();
                break;
            case PLAGUE:
                ImageIcon plague = new ImageIcon("image/plague.jpg");
                displayEvent(plague,"PLAGUE");
                audioFile ="audio/plague.wav";
                darkTowerCore.EndPlayerTurn();
                AudioTimer.start();
                GameEventTimer.start();
                break;
            case PLAGUEHEALER:
                ImageIcon plagueHealer = new ImageIcon("image/healer.jpg");
                displayEvent(plagueHealer, "HEALER");
                darkTowerCore.EndPlayerTurn();
                GameEventTimer.start();
                break;
            case ROUNDSTART:
                ImageIcon brigands = new ImageIcon("image/brigands.jpg");
                displayEvent(brigands,"BRIGANDS");
                audioFile ="audio/beep.wav";
                AudioTimer.start();
                break;
            case ROUNDMIDDLE:
                ImageIcon warriors = new ImageIcon("image/warriors.jpg");
                displayEvent(warriors,"WARRIORS");
                audioFile ="audio/beep.wav";
                AudioTimer.start();
                break;
            case ROUNDEND:
                if(darkTowerCore.WonRound())
                    audioFile = "audio/enemy-hit.wav";
                else
                    audioFile = "audio/player-hit.wav";
                AudioTimer.start();
                darkTowerCore.EndPlayerTurn();
            case SAFE:
                darkTowerCore.EndPlayerTurn();
                AudioPlayer.PlayBeep();
                displayPlayerTurn();
                break;
            case TURNOVER:
                displayPlayerTurn();
                break;
            case TREASURE:
                darkTowerCore.EndPlayerTurn();
        }
    }

    private void displayGameButtons()
    {
        for(gameButton gb: gameButtons) {
            gb.setVisible(true);
            mainPanel.add(gb);
        }
    }

    private void hideGameButtons()
    {
        for(gameButton gb: gameButtons)
        {
            gb.setVisible(false);
            mainPanel.remove(gb);
        }
    }

    public void RunGame()
    {
        mainPanel.remove(TestLabel);
        mainPanel.setLayout(new GridLayout(0, 2));
        for(playerButton pb: playerSelectButtons)
            pb.setVisible(true);
    }

}
