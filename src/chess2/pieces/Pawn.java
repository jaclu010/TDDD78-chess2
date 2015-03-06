package chess2.pieces;

import chess2.AbstractPiece;


import java.awt.Color;

public class Pawn extends AbstractPiece
{
    public Pawn(final boolean player) {
	super(player, Color.YELLOW);
    }
}
