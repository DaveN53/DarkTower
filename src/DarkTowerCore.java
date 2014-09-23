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
    BattleManager battleManager;
    GameEvent currentEvent = GameEvent.TURNOVER;
    GameEvent lastEvent = GameEvent.TURNOVER;

    long lastTime;
    long elapseTime = 3000;
    boolean playSound = false;

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
        audioTimer.start();
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
                if(gameStateChanged() && playSound)
                {
                    AudioPlayer.PlayWav(audioFile);
                    playSound = false;
                }
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

    public int GetCurrentPlayerNumber()
    {
        int playerNum = playerManger.GetCurrentPlayerNum() + 1;
        return playerNum;
    }


    public void EndPlayerTurn()
    {
        System.out.println("ENDING PLAYER TURN");
        System.out.println("PLAYER HAS: Warriors: " + playerManger.GetWarriors());
        playerManger.EndPlayerTurn();

        int currentPlayer = playerManger.GetCurrentPlayerNum() + 1;
        view.DisplayPlayerTurn(currentPlayer);

        System.out.println("NEXT PLAYER HAS: Warriors: " + playerManger.GetWarriors());
    }

    public void SetDifficulty(int num)
    {
        battleManager = new BattleManager(num);
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

        lastEvent = currentEvent;
        switch(currentEvent)
        {
            case BATTLESTART:
                audioFile = "audio/battle.wav";
                view.DisplayNothing();
                int warriors = playerManger.GetWarriors();
                battleManager.createBattle(warriors);
                currentEvent = GameEvent.BATTLESTARTBRIGANDS;
                break;
            case BATTLESTARTBRIGANDS:
                audioFile ="audio/beep.wav";
                view.DisplayBrigands(battleManager.GetBrigands());
                currentEvent = GameEvent.ROUNDSTART;
                break;
            case BATTLEOVER:
                if(battleManager.PlayerWon())
                {
                    audioFile = "audio/beep.wav";
                }
                else
                {
                    audioFile = "audio/plague.wav";
                }
                int warriorsRemaining = battleManager.GetWarriors();
                playerManger.SetWarriors(warriorsRemaining);
                view.DisplayWarriors(warriorsRemaining);
                currentEvent = GameEvent.TURNOVER;
                break;
            case CURSE:
                currentEvent = GameEvent.TURNOVER;
                break;
            case DRAGON:
                audioFile = "audio/dragon.wav";
              //  audioTimer.start();
                view.DisplayDragon();
                currentEvent = GameEvent.TURNOVER;
                break;
            case DRAGONKILL:
                audioFile = "audio/dragon-kill.wav";
              //  audioTimer.start();
                view.DisplaySword();
                currentEvent = GameEvent.TURNOVER;
                break;
            case LOST:
                audioFile = "audio/lost.wav";
              //  audioTimer.start();
                view.DisplayLost();
                currentEvent = GameEvent.TURNOVER;
                break;
            case LOSTSCOUT:
                audioFile = "audio/lost.wav";
             //   audioTimer.start();
                view.DisplayScout();
                currentEvent = GameEvent.TURNOVER;
                break;
            case PLAGUE:
                audioFile = "audio/plague.wav";
               // audioTimer.start();
                view.DisplayPlague();
                currentEvent = GameEvent.TURNOVER;
                break;
            case PLAGUEHEALER:
                audioFile = "audio/plague.wav";
              //  audioTimer.start();
                view.DisplayHealer();
                currentEvent = GameEvent.TURNOVER;
                break;
            case ROUNDSTART:
                battleManager.Fight();
                audioFile ="audio/beep.wav";
              //  audioTimer.start();
                view.DisplayWarriors(battleManager.GetWarriors());
                currentEvent = GameEvent.ROUNDMIDDLE;
                break;
            case ROUNDMIDDLE:
                audioFile ="audio/beep.wav";
             //   audioTimer.start();
                view.DisplayBrigands(battleManager.GetBrigands());
                currentEvent = GameEvent.ROUNDEND;
                break;
            case ROUNDEND:
                if(battleManager.WonRound())
                    audioFile = "audio/enemy-hit.wav";
                else
                    audioFile = "audio/player-hit.wav";
                view.DisplayNothing();

                if(battleManager.BattleOver())
                    currentEvent = GameEvent.BATTLEOVER;
                else
                    currentEvent = GameEvent.ROUNDSTART;

              //  audioTimer.start();
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
                break;
        }
        playSound = true;
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
