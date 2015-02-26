package chess2;

public abstract class ChessPiece implements PieceInterface
{
    protected int y,x, hP;
    protected boolean player;
    protected PieceType pieceType;

    public ChessPiece(final boolean player, final PieceType pieceType) {
	this.player = player;
	this.pieceType = pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
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
}
