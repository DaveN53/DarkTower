import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 8/29/2014.
 */
public class PlayerManger {

    private List<Player> players = new ArrayList<Player>();
    private Player currentPlayer;

    public PlayerManger(int numPlayers)
    {
        for(int i = 1; i <= numPlayers; i++)
        {
            Player p = new Player();
            players.add(p);
        }
        for(int i=0;i<players.size();i++)
        {
            int next = i+1;
            if(next+1 > players.size()) next = 0;
            players.get(i).nextPlayer = players.get(next);
        }

        currentPlayer = players.get(0);
    }

    public Player GetPlayer()
    {
        return currentPlayer;
    }

    public int GetCurrentPlayerNum()
    {
        int playerNum = players.indexOf(currentPlayer);
        return playerNum;
    }

    public void AwardGold(int gold)
    {
        int playerGold = currentPlayer.GetGold();
        playerGold += gold;
        currentPlayer.SetGold(playerGold);
    }

    public void AddPlayerFood(int food)
    {
        int playerFood = currentPlayer.GetFood();
        playerFood += food;
        currentPlayer.SetFood(playerFood);
    }

    public void AddWarriors(int newWarriors)
    {
        int current = currentPlayer.GetWarriors();
        current += newWarriors;
        currentPlayer.SetWarriors(current);
    }

    public void KillWarriors(int deadWarriors)
    {
        int current = currentPlayer.GetWarriors();
        current -= deadWarriors;
        currentPlayer.SetWarriors(current);
    }

    public int GetWarriors()
    {
        return currentPlayer.GetWarriors();
    }

    public void SetWarriors(int warriors)
    {
        currentPlayer.SetWarriors(warriors);
    }

    public void EndPlayerTurn()
    {
        currentPlayer.CheckStarvation();
        currentPlayer.UpdateFood();
        getNextPlayer();
    }

    private void getNextPlayer()
    {
        Player nextPlayer = currentPlayer.nextPlayer;
        currentPlayer = nextPlayer;
    }

    public boolean PlayerHasSword()
    {
        return currentPlayer.sword;
    }

    public void AwardPlayerSword()
    {
        currentPlayer.sword = true;
    }

    public void ConsumeSword()
    {
        currentPlayer.sword = false;
    }

    public boolean PlayerHasScout()
    {
        return  currentPlayer.scout;
    }

    public boolean PlayerHasHealer()
    {
        return currentPlayer.healer;
    }

    public boolean PlayerCursed()
    {
        return currentPlayer.cursed;
    }

    public void SetCursed(boolean cursed)
    {
        currentPlayer.cursed = cursed;
    }

}
