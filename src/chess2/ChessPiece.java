package chess2;

import java.awt.Color;

public abstract class ChessPiece implements PieceInterface
{
    protected int y,x, hP;
    protected boolean player;
    protected Color color;

    public ChessPiece(final boolean player, final Color color) {
	this.player = player;
        this.color = color;
    }

    @Override
    public Color getColor(){
        return color;
    }

    @Override
    public int getY(){
        return y;
    }

    @Override
    public int getX(){
        return x;
    }

    @Override
    public int getHP(){
        return hP;
    }
}
