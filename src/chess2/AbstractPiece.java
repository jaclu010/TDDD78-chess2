package chess2;

import java.awt.*;

public abstract class AbstractPiece implements Piece
{
    protected int hP, lvl;
    protected boolean player;
    protected Color color;

    public AbstractPiece(final boolean player, final Color color) {
	this.player = player;
        this.color = color;
        this.hP = 1;
    }

    public AbstractPiece(final Color color) {
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public int getHP(){
        return hP;
    }

    public boolean getPlayer(){
        return player;
    }

}
