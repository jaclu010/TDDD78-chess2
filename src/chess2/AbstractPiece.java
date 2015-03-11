package chess2;

import java.util.List;

/**
 * This class creates a abstract representation of a chesspiece
 */
public class AbstractPiece implements Piece
{
    protected int hP, lvl;
    protected Boolean player = null;
    protected PieceType pT;
    protected boolean initialPos;

    public AbstractPiece(final boolean player, final PieceType pT) {
	this.player = player;
        this.pT = pT;
        this.hP = 3;
        this.lvl = 0;
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

    public int getLvl() {
        return lvl;
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

    public void doDMG(int dmg){
        this.hP -= dmg;
    }

    public void setLvl(final int lvl) {
        this.lvl += lvl;
    }
}
