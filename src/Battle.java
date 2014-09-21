import java.util.Random;

/**
 * Created by David on 8/29/2014.
 */
public class Battle {

    private Random random;
    private int difficulty;
    private Player player;
    private int brigands;
    public boolean playerWon = false;

    public Battle(int difficulty)
    {
        this.difficulty = difficulty;
        random = new Random();
    }

    public void BeginBattle(Player p)
    {
        player = p;
        calculateBrigands();
        playerWon = false;
    }

    public void BeginDarkTower(Player p)
    {
        player = p;
        calculateDarkTower();
        playerWon = false;
    }

    private void calculateBrigands()
    {
        int warriors = player.GetWarriors();
        int brigands = warriors;
        int rand = random.nextInt(4)-2;
        brigands += rand;
        this.brigands = brigands;
    }

    private void calculateDarkTower()
    {
        switch(difficulty)
        {
            case 1:
                brigands = randRange(17,32);
                break;
            case 2:
                brigands = randRange(33,64);
                break;
            case 3:
                brigands = randRange(17,64);
                break;
        }
    }

    private int randRange(int min, int max)
    {
        int num = random.nextInt((max-min) + 1) + min;
        return num;
    }



}
