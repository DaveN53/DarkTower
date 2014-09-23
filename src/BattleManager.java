/**
 * Created by David on 9/22/2014.
 */
public class BattleManager {

    private int difficulty;
    private int warriors;
    private int brigands;
    private Battle battle;

    public BattleManager(int difficulty)
    {
        this.difficulty = difficulty;
    }

    public void createBattle(int warriors)
    {
        this.warriors = warriors;
        battle = new Battle(difficulty);
        battle.BeginBattle(warriors);
    }

    public void Fight()
    {
        battle.Fight();
    }

    public boolean WonRound()
    {
        return battle.roundWon;
    }

    public boolean BattleOver()
    {
        return battle.battleOver;
    }

    public boolean PlayerWon()
    {
        return battle.playerWon;
    }

    public int GetWarriors()
    {
        return battle.getWarriors();
    }

    public int GetBrigands()
    {
        return battle.getBrigands();
    }




}
