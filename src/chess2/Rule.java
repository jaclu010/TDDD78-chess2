package chess2;

import javafx.geometry.Point2D;

public class Rule
{

    private Point2D point;
    private String piece;
    private Boolean untilBlockCollission, player;

    public Rule(final int x, final int y, final String piece, Boolean untilBlockCollission, Boolean player) {
	this.point = new Point2D(y, x);
	this.piece = piece;
        this.untilBlockCollission = untilBlockCollission;
        this.player = player;
    }

    public Rule(final int x, final int y, final String piece, Boolean untilBlockCollission) {
	this.point = new Point2D(y, x);
	this.piece = piece;
        this.untilBlockCollission = untilBlockCollission;
    }

    public Point2D getPoint(){
        return point;
    }

    public String getPiece() {
	return piece;
    }

    public Rule getRule(){
        return this;
    }

    public Boolean getPlayer() {
        return player;
    }
}
