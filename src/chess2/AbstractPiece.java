package chess2;

import java.util.List;

public class AbstractPiece implements Piece
{
    protected int hP, lvl;
    protected Boolean player;
    protected PieceType pT;
    protected boolean initialPos;

    public AbstractPiece(final boolean player, final PieceType pT) {
	this.player = player;
        this.pT = pT;
        this.hP = 1;
        this.initialPos = true;
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

    public Boolean getPlayer(){
        return player;
    }

    public PieceType getPieceType() {
        return pT;
    }

    public boolean isInitialPos() {
        return initialPos;
    }

    public void setInitialPos(final boolean initialPos) {
        this.initialPos = initialPos;
    }
}
