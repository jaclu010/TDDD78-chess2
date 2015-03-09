package chess2;

import java.util.ArrayList;
import java.util.List;

public class RuleFactory
{
    // Create Rules
    public static List<Rule> getRules(){
        List<Rule> ruleList = new ArrayList<Rule>();

        // White Pawn
        ruleList.add(new Rule(0,-1, PieceType.PAWN, false, true));
        ruleList.add(new Rule(0,-2, PieceType.PAWN, false, true));

        // Black Pawn
        ruleList.add(new Rule(0,1, PieceType.PAWN, false, false));
        ruleList.add(new Rule(0,2, PieceType.PAWN, false, false));

        // Knight
        ruleList.add(new Rule(1,2, PieceType.KNIGHT, false));
        ruleList.add(new Rule(-1,2, PieceType.KNIGHT, false));
        ruleList.add(new Rule(1,-2, PieceType.KNIGHT, false));
        ruleList.add(new Rule(-1,-2, PieceType.KNIGHT, false));
        ruleList.add(new Rule(2,1, PieceType.KNIGHT, false));
        ruleList.add(new Rule(-2,1, PieceType.KNIGHT, false));
        ruleList.add(new Rule(2,-1, PieceType.KNIGHT, false));
        ruleList.add(new Rule(-2,-1, PieceType.KNIGHT, false));

        // King
        ruleList.add(new Rule(1,0, PieceType.KING, false));
        ruleList.add(new Rule(-1,0, PieceType.KING, false));
        ruleList.add(new Rule(0,1, PieceType.KING, false));
        ruleList.add(new Rule(0,-1, PieceType.KING, false));
        ruleList.add(new Rule(1,1, PieceType.KING, false));
        ruleList.add(new Rule(-1,1, PieceType.KING, false));
        ruleList.add(new Rule(1,-1, PieceType.KING, false));
        ruleList.add(new Rule(-1,-1, PieceType.KING, false));

        // True 'untilBlockCollission' constructor stmt

        // Bishop
        ruleList.add(new Rule(1,1, PieceType.BISHOP, true));
        ruleList.add(new Rule(1,-1, PieceType.BISHOP, true));
        ruleList.add(new Rule(-1,1, PieceType.BISHOP, true));
        ruleList.add(new Rule(-1,-1, PieceType.BISHOP, true));

        // Rook
        ruleList.add(new Rule(0,1, PieceType.ROOK, true));
        ruleList.add(new Rule(0,-1, PieceType.ROOK, true));
        ruleList.add(new Rule(1,0, PieceType.ROOK, true));
        ruleList.add(new Rule(-1,0, PieceType.ROOK, true));

        // Queen
        ruleList.add(new Rule(1,1, PieceType.QUEEN, true));
        ruleList.add(new Rule(1,-1, PieceType.QUEEN, true));
        ruleList.add(new Rule(-1,1, PieceType.QUEEN, true));
        ruleList.add(new Rule(-1,-1, PieceType.QUEEN, true));
        ruleList.add(new Rule(0,1, PieceType.QUEEN, true));
        ruleList.add(new Rule(0,-1, PieceType.QUEEN, true));
        ruleList.add(new Rule(1,0, PieceType.QUEEN, true));
        ruleList.add(new Rule(-1,0, PieceType.QUEEN, true));

        // Special Rules
        // ToBeContinued.....

        return ruleList;
    }
}
