package chess2;

import chess2.pieces.Outside;

public class PieceConstructor
{
    public static AbstractPiece getPiece(PieceType pT){
	switch (pT){
	    case OUTSIDE: return typeO();
	    default: return null;

	}
    }

    public static AbstractPiece typeO(){
	AbstractPiece newPiece = new Outside();
	return newPiece;
    }
}
