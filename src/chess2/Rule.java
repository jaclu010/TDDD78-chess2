package chess2;

public class Rule
{
    private Point point;
    private String piece;
    private Boolean untilBlockCollission, player;

    public Rule(final int x, final int y, final String piece, Boolean untilBlockCollission, Boolean player) {
	this.point = new Point(y, x);
	this.piece = piece;
        this.untilBlockCollission = untilBlockCollission;
        this.player = player;
    }

    public Rule(final int x, final int y, final String piece, Boolean untilBlockCollission) {
	this.point = new Point(y, x);
	this.piece = piece;
        this.untilBlockCollission = untilBlockCollission;
    }

    public Point getPoint(){
        return point;
    }

    public String getPiece() {
	return piece;
    }

    public Boolean getPlayer() {
        return player;
    }

    public Boolean getUntilBlockCollission() {
        return untilBlockCollission;
    }
}
