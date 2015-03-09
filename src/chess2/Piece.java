package chess2;

import java.util.List;

public interface Piece
{
    int getHP();

    PieceType getPieceType();

    boolean getPlayer();

    List<Rule> fetchRules();
}
