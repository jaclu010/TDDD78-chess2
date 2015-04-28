package se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.rules.Point;

import java.util.List;

public class Heal extends AbstractAbility
{
    private int heal;

    public Heal() {
	this.heal = 2;
	setCost(2);
	setaT(AbilityType.HEAL);
    }

    @Override public ChessPiece[][] use(Point targetCoords, Point selectedCoords, ChessPiece[][] board, List<Point> moves) {
        int y = targetCoords.getY();
        int x = targetCoords.getX();
        board[y][x].doHeal(heal);

    	setMsg(board[selectedCoords.getY()][selectedCoords.getX()].getpT().name()+
               " healed "+  board[y][x].getpT().name()+ " for " + heal + " HP");

        return board;
    }
}
