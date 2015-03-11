package chess2;

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

    public PieceType getPieceType() {
	return pT;
    }

    public Boolean getPlayer() {
        return player;
    }

    public boolean getUntilBlockCollission() {
        return untilBlockCollission;
    }

    public boolean isHurtMove() {
        return hurtMove;
    }

    public boolean doRequiresInitialPos() {
        return requiresInitialPos;
    }
}
