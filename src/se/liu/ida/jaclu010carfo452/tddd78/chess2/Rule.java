package se.liu.ida.jaclu010carfo452.tddd78.chess2;

/**
 * Defines a rule for a ChessPiece
 * @author jaclu010, carfo452
 */
public class Rule
{
    private Point point;
    private PieceType pT;
    private boolean untilBlockCollission, hurtMove, requiresInitialPos;
    private Boolean player = null;

    public Rule(final int x, final int y, final PieceType pT, boolean untilBlockCollission, boolean hurtMove, Boolean player, boolean requiresInitialPos) {
	this.point = new Point(y, x);
	this.pT = pT;
        this.untilBlockCollission = untilBlockCollission;
        this.player = player;
        this.hurtMove = hurtMove;
        this.requiresInitialPos = requiresInitialPos;
    }

    public Rule(final int x, final int y, final PieceType pT, boolean untilBlockCollission, boolean hurtMove) {
	this.point = new Point(y, x);
	this.pT = pT;
        this.untilBlockCollission = untilBlockCollission;
        this.hurtMove = hurtMove;
        this.requiresInitialPos = false;
    }

    public Point getPoint(){
        return point;
    }

    public PieceType getpT() {
	return pT;
    }

    public Boolean getPlayer() {
        return player;
    }

    public boolean isUntilBlockCollission() {
        return untilBlockCollission;
    }

    public boolean isHurtMove() {
        return hurtMove;
    }

    public boolean doRequiresInitialPos() {
        return requiresInitialPos;
    }
}
