package chess2.pieces;

import chess2.AbstractPiece;
import chess2.Rule;
import chess2.RuleList;

import java.awt.Color;
import java.util.List;

public class Pawn extends AbstractPiece
{
    public Pawn(final boolean player) {
	super(player, Color.YELLOW);
    }

    @Override
    public List<Rule> fetchRules(){
        return RuleList.getInstance().extractRulesForPawn(player);
    }
}
