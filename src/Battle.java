import java.util.Random;

/**
 * Created by David on 8/29/2014.
 */
public class Battle {

    private Random random;
    private int difficulty;
    private int warriors;
    private int brigands;
    public boolean playerWon;
    public boolean roundWon;
    public boolean battleOver;

    public Battle(int difficulty)
    {
        playerWon = false;
        roundWon = false;
        battleOver = false;
        this.difficulty = difficulty;
        random = new Random(System.currentTimeMillis());
    }

    public void BeginBattle(int warriors)
    {
        this.warriors = warriors;
        calculateBrigands();
        playerWon = false;
    }

    public void BeginDarkTower(int warriors)
    {
        this.warriors = warriors;
        calculateDarkTower();
        playerWon = false;
    }

    private void calculateBrigands()
    {
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

    public void Fight()
    {
        int result = randRange(1,5);
        if(result <= 3)
        {
            roundWon = true;
            if(brigands == 1)
                brigands = 0;
            else {
                int dead = brigands / 2;
                brigands -= dead;
            }
        }
        else
        {
            roundWon = false;
            warriors -= 1;
        }

        if(brigands == 0 || warriors == 0)
        {
            battleOver = true;
            if(brigands == 0)
                playerWon = true;
        }

    }

    public int getWarriors()
    {
        return  warriors;
    }

    public int getBrigands()
    {
        return brigands;
    }
}
