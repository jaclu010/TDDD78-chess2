package chess2;

import java.util.ArrayList;
import java.util.List;

public final class RuleFactory
{
    private RuleFactory() {}

    // Create Rules
    public static List<Rule> getRules(){
        List<Rule> ruleList = new ArrayList<>();

        // White Pawn
        ruleList.add(new Rule(0,-1, PieceType.PAWN, false, false, true, false));
        ruleList.add(new Rule(0,-2, PieceType.PAWN, false, false, true, true));
        ruleList.add(new Rule(1,-1, PieceType.PAWN, false, true, true, false));
        ruleList.add(new Rule(-1,-1, PieceType.PAWN, false, true, true, false));

        // Black Pawn
        ruleList.add(new Rule(0,1, PieceType.PAWN, false, false, false, false));
        ruleList.add(new Rule(0,2, PieceType.PAWN, false, false, false, true));
        ruleList.add(new Rule(1,1, PieceType.PAWN, false, true, false, false));
        ruleList.add(new Rule(-1,1, PieceType.PAWN, false, true, false, false));

        // Knight
        ruleList.add(new Rule(1,2, PieceType.KNIGHT, false, true));
        ruleList.add(new Rule(-1,2, PieceType.KNIGHT, false, true));
        ruleList.add(new Rule(1,-2, PieceType.KNIGHT, false, true));
        ruleList.add(new Rule(-1,-2, PieceType.KNIGHT, false, true));
        ruleList.add(new Rule(2,1, PieceType.KNIGHT, false, true));
        ruleList.add(new Rule(-2,1, PieceType.KNIGHT, false, true));
        ruleList.add(new Rule(2,-1, PieceType.KNIGHT, false, true));
        ruleList.add(new Rule(-2,-1, PieceType.KNIGHT, false, true));

        // King
        ruleList.add(new Rule(1,0, PieceType.KING, false, true));
        ruleList.add(new Rule(-1,0, PieceType.KING, false, true));
        ruleList.add(new Rule(0,1, PieceType.KING, false, true));
        ruleList.add(new Rule(0,-1, PieceType.KING, false, true));
        ruleList.add(new Rule(1,1, PieceType.KING, false, true));
        ruleList.add(new Rule(-1,1, PieceType.KING, false, true));
        ruleList.add(new Rule(1,-1, PieceType.KING, false, true));
        ruleList.add(new Rule(-1,-1, PieceType.KING, false, true));

        // True 'untilBlockCollission' constructor stmt

        // Bishop
        ruleList.add(new Rule(1,1, PieceType.BISHOP, true, true));
        ruleList.add(new Rule(1,-1, PieceType.BISHOP, true, true));
        ruleList.add(new Rule(-1,1, PieceType.BISHOP, true, true));
        ruleList.add(new Rule(-1,-1, PieceType.BISHOP, true, true));

        // Rook
        ruleList.add(new Rule(0,1, PieceType.ROOK, true, true));
        ruleList.add(new Rule(0,-1, PieceType.ROOK, true, true));
        ruleList.add(new Rule(1,0, PieceType.ROOK, true, true));
        ruleList.add(new Rule(-1,0, PieceType.ROOK, true, true));

        // Queen
        ruleList.add(new Rule(1,1, PieceType.QUEEN, true, true));
        ruleList.add(new Rule(1,-1, PieceType.QUEEN, true, true));
        ruleList.add(new Rule(-1,1, PieceType.QUEEN, true, true));
        ruleList.add(new Rule(-1,-1, PieceType.QUEEN, true, true));
        ruleList.add(new Rule(0,1, PieceType.QUEEN, true, true));
        ruleList.add(new Rule(0,-1, PieceType.QUEEN, true, true));
        ruleList.add(new Rule(1,0, PieceType.QUEEN, true, true));
        ruleList.add(new Rule(-1,0, PieceType.QUEEN, true, true));

        // Special Rules
        // ToBeContinued.....

        return ruleList;
    }
}
