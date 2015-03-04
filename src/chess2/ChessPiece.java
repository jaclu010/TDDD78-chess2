package chess2;

import java.awt.Color;

public abstract class ChessPiece implements PieceInterface
{
    protected int hP;
    protected boolean player;
    protected Color color;

    public ChessPiece(final boolean player, final Color color) {
	this.player = player;
        this.color = color;
        this.hP = 1;
    }

    public ChessPiece(final Color color) {
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
