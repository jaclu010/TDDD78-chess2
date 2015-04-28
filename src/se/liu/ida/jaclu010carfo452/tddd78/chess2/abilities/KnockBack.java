package se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.GlobalVars;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.PieceType;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.rules.Point;

import java.util.List;

/**
 * The ability class that represents the knock back ability
 * @author jaclu010, carfo452
 */
public class KnockBack extends AbstractAbility
{
    public KnockBack() {
	setKnockBack(2);
	setCost(2);
	setaT(AbilityType.KNOCK_BACK);
	setaC(AbilityCharacteristic.OFFENSIVE);
    }

    @Override public ChessPiece[][] use(Point targetCoords, Point selectedCoords, ChessPiece[][] board, List<Point> moves) {
	int y = targetCoords.getY();
	int x = targetCoords.getX();
	int i = 1;
	if(board[selectedCoords.getY()][selectedCoords.getX()].getPlayer()) i = -1;
	board[y+i*getKnockBack()][x] = board[y][x];
	board[y][x] = new ChessPiece(PieceType.EMPTY);

	setMsg(board[selectedCoords.getY()][selectedCoords.getX()].getpT().name()+ " knocked back "
	       + board[y+i*getKnockBack()][x].getpT().name()+" to "+ GlobalVars.getLetter(x) + (GlobalVars.getHeight() - 1 - (y + i * getKnockBack())));

	return board;
    }
}
