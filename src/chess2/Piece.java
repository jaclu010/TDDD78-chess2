package chess2;

import java.util.List;

public interface Piece
{
    int getHP();

    PieceType getPieceType();

    Boolean getPlayer();

    List<Rule> fetchRules();

    void setInitialPos(final boolean initialPos);

    boolean isInitialPos();

    void doDMG(int dmg);
}
