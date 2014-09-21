import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by David on 8/28/2014.
 */



public class DarkTowerCore {

    DTView view;
    PlayerManger playerManger;
    GameEvent currentEvent = GameEvent.TURNOVER;
    GameEvent lastEvent = GameEvent.TURNOVER;

    long lastTime;
    long elapseTime = 3000;

    Timer gameTimer;
    Timer audioTimer;

    String audioFile;

    public DarkTowerCore()
    {
        view = new DTView();
        setupTimer();
        setupListeners();

        AudioPlayer.PlayDarkTower();
        view.RunGame();
        gameTimer.start();
    }

    public void setupTimer()
    {
        lastTime = System.currentTimeMillis();

        gameTimer = new javax.swing.Timer(100,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gameStateChanged() && timeElapsed())
                    actOnPlayerEvent();
            }
        });

        audioTimer = new Timer(200,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.PlayWav(audioFile);
                audioTimer.stop();
            }
        });

    }

    public void setupListeners()
    {
        for(playerButton b: view.playerSelectButtons)
        {
            final int num = b.val;
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SetPlayers(num);
                    view.DisplayDifficultySelection();
                }
            });
        }

        for(difficultyButton b: view.difficultyButtons)
        {
            final int num = b.val;
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SetDifficulty(num);
                    int currentPlayer = playerManger.GetCurrentPlayerNum() + 1;
                    view.RemoveDifficultyButtons();
                    view.DisplayPlayerTurn(currentPlayer);
                }
            });
        }

        for(gameButton b: view.gameButtons)
        {
            final int num = b.val;
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SetPlayerMove(num);
                    view.HideGameButtons();
                    actOnPlayerEvent();
                }
            });
        }

        view.okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.DisplayGameButtons();
            }
        });
    }

    public void SetPlayers(int num)
    {
        System.out.println(num);
        playerManger = new PlayerManger(num);
    }

    public void SetEvent(GameEvent event)
    {
        currentEvent = event;
    }

    public int GetCurrentPlayerNumber()
    {
        int playerNum = playerManger.GetCurrentPlayerNum() + 1;
        return playerNum;
    }

    public void  Battle()
    {

    }

    public boolean WonRound()
    {
        return true;
    }

    public void EndPlayerTurn()
    {
        playerManger.EndPlayerTurn();

        int currentPlayer = playerManger.GetCurrentPlayerNum() + 1;
        view.DisplayPlayerTurn(currentPlayer);

        lastEvent = currentEvent;
        currentEvent = GameEvent.TURNOVER;
    }

    public void SetDifficulty(int num)
    {
        System.out.println(num);
    }

    public GameEvent SetPlayerMove(int num)
    {
        System.out.println(num);
        switch(num)
        {
            case 2:
                //"Bazaar";
                currentEvent = GameEvent.BAZAAR;
                break;
            case 4:
                //"Tomb / Ruin";
                currentEvent = getRandomTombRuinEvent();
                break;
            case 5:
                //"Move";
                currentEvent = getRandomMoveEvent();
                break;
            case 6:
                //"Sanctuary / Citadel";
                currentEvent = GameEvent.SANCTUARY;
                break;
            case 7:
                //"Dark Tower";
                currentEvent = GameEvent.DARKTOWER;
                break;
            case 8:
                //"Frontier";
                currentEvent = GameEvent.FRONTIER;
                break;
            case 9:
                //"Inventory";
                currentEvent = GameEvent.INVENTORY;
                break;
        };
        return currentEvent;
    }

    boolean gameStateChanged()
    {
        return currentEvent != lastEvent;
    }

    boolean timeElapsed()
    {
        long delta = System.currentTimeMillis() - lastTime;
        if(delta > elapseTime)
            return true;
        return false;
    }

    void actOnPlayerEvent()
    {

        switch(currentEvent)
        {
            case BATTLESTART:
                audioFile = "audio/battle.wav";
                audioTimer.start();
                Battle();
                lastEvent = currentEvent;
                currentEvent = GameEvent.ROUNDSTART;
                break;
            case CURSE:
                lastEvent = currentEvent;
                currentEvent = GameEvent.TURNOVER;
                break;
            case DRAGON:
                audioFile = "audio/dragon.wav";
                audioTimer.start();
                view.DisplayDragon();
                lastEvent = currentEvent;
                currentEvent = GameEvent.TURNOVER;
                break;
            case DRAGONKILL:
                audioFile = "audio/dragon-kill.wav";
                audioTimer.start();
                view.DisplaySword();
                lastEvent = currentEvent;
                currentEvent = GameEvent.TURNOVER;
                break;
            case LOST:
                audioFile = "audio/lost.wav";
                audioTimer.start();
                view.DisplayLost();
                lastEvent = currentEvent;
                currentEvent = GameEvent.TURNOVER;
                break;
            case LOSTSCOUT:
                audioFile = "audio/lost.wav";
                audioTimer.start();
                view.DisplayScout();
                lastEvent = currentEvent;
                currentEvent = GameEvent.TURNOVER;
                break;
            case PLAGUE:
                audioFile = "audio/plague.wav";
                audioTimer.start();
                view.DisplayPlague();
                lastEvent = currentEvent;
                currentEvent = GameEvent.TURNOVER;
                break;
            case PLAGUEHEALER:
                audioFile = "audio/plague.wav";
                audioTimer.start();
                view.DisplayHealer();
                lastEvent = currentEvent;
                currentEvent = GameEvent.TURNOVER;
                break;
            case ROUNDSTART:
                audioFile ="audio/beep.wav";
                audioTimer.start();
                view.DisplayBrigands(5);
                lastEvent = currentEvent;
                currentEvent = GameEvent.ROUNDMIDDLE;
                break;
            case ROUNDMIDDLE:
                audioFile ="audio/beep.wav";
                audioTimer.start();
                view.DisplayWarriors(5);
                lastEvent = currentEvent;
                currentEvent = GameEvent.ROUNDEND;
                break;
            case ROUNDEND:
                if(WonRound())
                    audioFile = "audio/enemy-hit.wav";
                else
                    audioFile = "audio/player-hit.wav";
                lastEvent = currentEvent;
                currentEvent = GameEvent.TURNOVER;

                audioTimer.start();
                break;
            case SAFE:
                AudioPlayer.PlayBeep();
                EndPlayerTurn();
                break;
            case TURNOVER:
                EndPlayerTurn();
                break;
            case TREASURE:
                EndPlayerTurn();
        }
        lastTime = System.currentTimeMillis();

    }

    public GameEvent GetCurrentEvent()
    {
        return currentEvent;
    }

    GameEvent getRandomMoveEvent()
    {
        Random rand = new Random(System.currentTimeMillis());
        int val = rand.nextInt(100);
        if(val < 5)
        {
            if(playerManger.PlayerHasSword())
            {
                playerManger.ConsumeSword();
                return GameEvent.DRAGONKILL;
            }
            return  GameEvent.DRAGON;
        }
        else if(val >= 5 && val < 20)
        {
            if(playerManger.PlayerHasScout())
                return  GameEvent.LOSTSCOUT;
            return  GameEvent.LOST;
        }
        else if(val >=20 && val < 35)
        {
            if(playerManger.PlayerHasHealer())
            {
                playerManger.AddWarriors(2);
                return GameEvent.PLAGUEHEALER;
            }
            return  GameEvent.PLAGUE;
        }
        else if(val >=35 && val <60)
            return  GameEvent.BATTLESTART;
        else
            return  GameEvent.SAFE;
    }

    GameEvent getRandomTombRuinEvent()
    {
        Random rand = new Random(System.currentTimeMillis());
        int val = rand.nextInt(100);
        if(val < 50)
            return GameEvent.BATTLESTART;
        else if(val >= 50 && val < 75)
            return GameEvent.TREASURE;
        else
            return GameEvent.SAFE;
    }
}
