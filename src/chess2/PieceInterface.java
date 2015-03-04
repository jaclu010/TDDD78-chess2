package chess2;

import java.awt.Color;

public interface PieceInterface
{
    int getX();

    void setX(final int x);

    int getY();

    void setY(final int y);

    int getHP();

    Color getColor();
}
