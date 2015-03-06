package chess2;

import java.util.ArrayList;
import java.util.List;

public class RuleFactory
{
    // Create Rules
    public static List<Rule> getRules(){
        List<Rule> ruleList = new ArrayList<Rule>();

        // White Pawn
        ruleList.add(new Rule(0,-1,"Pawn", false, true));
        ruleList.add(new Rule(0,-2,"Pawn", false, true));

        // Black Pawn
        ruleList.add(new Rule(0,1,"Pawn", false, false));
        ruleList.add(new Rule(0,2, "Pawn", false, false));

        // Knight
        ruleList.add(new Rule(1,2, "Knight", false));
        ruleList.add(new Rule(-1,2, "Knight", false));
        ruleList.add(new Rule(1,-2, "Knight", false));
        ruleList.add(new Rule(-1,-2, "Knight", false));
        ruleList.add(new Rule(2,1, "Knight", false));
        ruleList.add(new Rule(-2,1, "Knight", false));
        ruleList.add(new Rule(2,-1, "Knight", false));
        ruleList.add(new Rule(-2,-1, "Knight", false));

        // King
        ruleList.add(new Rule(1,0, "King", false));
        ruleList.add(new Rule(-1,0, "King", false));
        ruleList.add(new Rule(0,1, "King", false));
        ruleList.add(new Rule(0,-1, "King", false));
        ruleList.add(new Rule(1,1, "King", false));
        ruleList.add(new Rule(-1,1, "King", false));
        ruleList.add(new Rule(1,-1, "King", false));
        ruleList.add(new Rule(-1,-1, "King", false));

        // True 'untilBlockCollission' constructor stmt

        // Bishop
        ruleList.add(new Rule(1,1,"Bishop", true));
        ruleList.add(new Rule(1,-1,"Bishop", true));
        ruleList.add(new Rule(-1,1,"Bishop", true));
        ruleList.add(new Rule(-1,-1,"Bishop", true));

        // Rook
        ruleList.add(new Rule(0,1,"Rook", true));
        ruleList.add(new Rule(0,-1,"Rook", true));
        ruleList.add(new Rule(1,0,"Rook", true));
        ruleList.add(new Rule(-1,0,"Rook", true));

        // Queen
        ruleList.add(new Rule(1,2, "Queen", true));
        ruleList.add(new Rule(-1,2, "Queen", true));
        ruleList.add(new Rule(1,-2, "Queen", true));
        ruleList.add(new Rule(-1,-2, "Queen", true));
        ruleList.add(new Rule(2,1, "Queen", true));
        ruleList.add(new Rule(-2,1, "Queen", true));
        ruleList.add(new Rule(2,-1, "Queen", true));
        ruleList.add(new Rule(-2,-1, "Queen", true));

        // Special Rules
        // ToBeContinued.....

        return ruleList;
    }
}
