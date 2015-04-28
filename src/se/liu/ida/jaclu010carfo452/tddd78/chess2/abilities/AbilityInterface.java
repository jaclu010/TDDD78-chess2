package se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.rules.Point;

import java.util.List;

/**
 * The interface that defines the methods in the ability classes
 * @author jaclu010, carfo452
 */
public interface AbilityInterface
{
    public void setaT(final AbilityType aT);

    public AbilityCharacteristic getaC();

    public AbilityType getaT();

    public int getCost();

    public ChessPiece[][] use(Point targetCoords, Point selectedCoords, ChessPiece[][] board, List<Point> moves);

    public String getMsg();
}
