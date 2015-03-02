package chess2;

public class PieceConstructor
{
    public static ChessPiece getPiece(PieceType pT){
	switch (pT){
	    case OUTSIDE: return typeO();
	    default: return null;

	}
    }

    public static ChessPiece typeO(){
	ChessPiece newPiece = new Outside();
	return newPiece;
    }
}
