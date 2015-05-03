package se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.rules.Point;

import java.util.List;


/**
 * The ability class that represents the heal ability
 * @author jaclu010, carfo452
 */
public class Heal extends AbstractAbility
{
    public Heal() {
	setHeal(2);
	setCost(2);
	setaT(AbilityType.HEAL);
        setaC(AbilityCharacteristic.DEFENSIVE);
    }

    @Override public ChessPiece[][] use(Point targetCoords, Point selectedCoords, ChessPiece[][] board, List<Point> moves) {
        int y = targetCoords.getY();
        int x = targetCoords.getX();
        board[y][x].doHeal(getHeal());

    	setMsg(board[selectedCoords.getY()][selectedCoords.getX()].getpT().name()+
               " healed "+  board[y][x].getpT().name()+ " for " + getHeal() + " HP");
        assert board != null: "Internal error: board has been changed to wrong value";
        return board;
    }
}
