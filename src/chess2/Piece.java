package chess2;

import java.awt.Color;
import java.util.List;

public interface Piece
{
    int getHP();

    Color getColor();

    boolean getPlayer();

    List<Rule> fetchRules();
}
