package chess2;

import java.util.List;

public class AbstractPiece implements Piece
{
    protected int hP, lvl;
    protected boolean player;
    protected PieceType pT;

    public AbstractPiece(final boolean player, final PieceType pT) {
	this.player = player;
        this.pT = pT;
        this.hP = 1;
    }

    public AbstractPiece(final PieceType pT) {
        this.pT= pT;
    }

    public List<Rule> fetchRules(){
        return RuleList.getInstance().extractRulesFor(pT);
    }

    public int getHP(){
        return hP;
    }

    public boolean getPlayer(){
        return player;
    }

    public PieceType getPieceType() {
        return pT;
    }
}
