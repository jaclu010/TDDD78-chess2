package chess2;

import java.awt.Color;
import java.util.List;

public abstract class AbstractPiece implements Piece
{
    protected int hP, lvl;
    protected boolean player;
    protected Color color;
    protected List<Rule> pieceRules;

    public AbstractPiece(final boolean player, final Color color) {
	this.player = player;
        this.color = color;
        this.hP = 1;
        this.pieceRules = fetchRules();
    }

    public AbstractPiece(final Color color) {
        this.color = color;
    }

    public List<Rule> fetchRules(){
        return RuleList.getInstance().extractRulesFor(this.getClass().getSimpleName());
    }

    public Color getColor(){
        return color;
    }

    public int getHP(){
        return hP;
    }

    public boolean getPlayer(){
        return player;
    }

    public List<Rule> getPieceRules() {
        return pieceRules;
    }
}
