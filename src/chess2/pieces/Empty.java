package chess2.pieces;

import chess2.AbstractPiece;


import java.awt.*;

public class Empty extends AbstractPiece
{
    public Empty() {
	super(true, new Color(0,0,0,1));
    }
}
