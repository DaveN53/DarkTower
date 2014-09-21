/**
 * Created by David on 8/29/2014.
 */
public class Player {
    private int warriors;
    private int gold;
    private int food;
    public int frontier;
    public boolean scout;
    public boolean beast;
    public boolean sword;
    public boolean healer;
    public boolean bronzeKey;
    public boolean silverKey;
    public boolean goldKey;

    public Player nextPlayer;

    public Player()
    {
        gold = 30;
        food = 25;
        warriors = 10;
        frontier = 1;
        scout = false;
        beast = false;
        sword = false;
        healer = false;
        bronzeKey = false;
        silverKey = false;
        goldKey = false;
    }

    public int GetGold()
    {
        return gold;
    }

    public void SetGold(int gold)
    {
        this.gold = gold;
    }

    public int GetFrontier()
    {
        return frontier;
    }

    public void UpdateFrontier()
    {
        if(frontier < 4)
            frontier ++;
    }

    //When you have less than four turns of food left play sound
    //If no food left take away one warrior per turn
    //
    public boolean  CheckStarvation()
    {
        int foodPerTurn = (warriors -1) /15;
        foodPerTurn += 1;
        if((food/foodPerTurn) <= 4){
            if(food == 0 && warriors > 1)
                warriors--;
            return true;
        }
        return false;
    }

    public void UpdateFood()
    {
        int foodPerTurn = (warriors -1) /15;
        food = Math.min(food - foodPerTurn,0);
    }

    public int GetFood()
    {
        return food;
    }

    public void SetFood(int food)
    {
        this.food = food;
    }

    public int GetWarriors()
    {
        return warriors;
    }

    public void SetWarriors(int warriors)
    {
        this.warriors = warriors;
    }
}
