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

    @Override
    public PieceType getPieceType() {
        return pieceType;
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
