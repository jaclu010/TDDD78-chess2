package chess2;

import java.awt.Color;

public abstract class ChessPiece implements PieceInterface
{
    protected int y,x, hP;
    protected boolean player;
    protected Color color;

    public ChessPiece(final boolean player, final Color color, final int y, final int x) {
	this.player = player;
        this.color = color;
        this.y = y;
        this.x = x;
    }

    public ChessPiece(final boolean player, final Color color) {
	this.player = player;
        this.color = color;
    }

    public ChessPiece(final Color color) {
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public int getY(){
        return y;
    }

    public int getX(){
        return x;
    }

    public int getHP(){
        return hP;
    }

    public void setY(final int y){
        this.y = y;
    }

    public void setX(final int x){
        this.x = x;
    }
}
