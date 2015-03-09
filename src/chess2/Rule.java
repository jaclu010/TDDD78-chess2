package chess2;

public class Rule
{
    private Point point;
    private PieceType pT;
    private Boolean untilBlockCollission, player;

    public Rule(final int x, final int y, final PieceType pT, Boolean untilBlockCollission, Boolean player) {
	this.point = new Point(y, x);
	this.pT = pT;
        this.untilBlockCollission = untilBlockCollission;
        this.player = player;
    }

    public Rule(final int x, final int y, final PieceType pT, Boolean untilBlockCollission) {
	this.point = new Point(y, x);
	this.pT = pT;
        this.untilBlockCollission = untilBlockCollission;
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

    public Boolean getUntilBlockCollission() {
        return untilBlockCollission;
    }
}
