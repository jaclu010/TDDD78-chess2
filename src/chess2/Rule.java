package chess2;

public class Rule
{
    private int x, y;
    private String piece;
    private Boolean untilBlockCollission, player;

    public Rule(final int x, final int y, final String piece, Boolean untilBlockCollission, Boolean player) {
	this.x = x;
	this.y = y;
	this.piece = piece;
        this.untilBlockCollission = untilBlockCollission;
        this.player = player;
    }

    public Rule(final int x, final int y, final String piece, Boolean untilBlockCollission) {
	this.x = x;
	this.y = y;
	this.piece = piece;
        this.untilBlockCollission = untilBlockCollission;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
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
