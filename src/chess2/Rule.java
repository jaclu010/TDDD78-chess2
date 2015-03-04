package chess2;

public class Rule
{
    private int x, y;
    private String piece;

    public Rule(final int x, final int y, final String piece) {
	this.x = x;
	this.y = y;
	this.piece = piece;
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
}
