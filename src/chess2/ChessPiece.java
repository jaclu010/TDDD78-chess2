package chess2;

public abstract class ChessPiece
{
    protected int y,x, hP;
    protected boolean player;
    protected PieceType pieceType;

    public ChessPiece(final boolean player, final PieceType pieceType) {
	this.player = player;
	this.pieceType = pieceType;
    }
}
