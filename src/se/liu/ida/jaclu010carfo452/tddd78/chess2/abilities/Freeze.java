package se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.GlobalVars;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.rules.Point;

import java.util.List;

public class Freeze extends AbstractAbility
{
    int freezeTime;

    public Freeze() {
	this.freezeTime = 6;
	setCost(2);
	setaT(AbilityType.FREEZE);
    }

    public int getFreezeTime() {
	return freezeTime;
    }

    @Override public ChessPiece[][] use(Point targetCoords, Point selectedCoords, ChessPiece[][] board, List<Point> moves) {
        int y = targetCoords.getY();
        int x = targetCoords.getX();
        board[y][x].setFreezeTime(freezeTime);

        setMsg(board[selectedCoords.getY()][selectedCoords.getX()].getpT().name()+" froze "+
                board[y][x].getpT().name()+" for "+freezeTime+" turns at "+ GlobalVars.getLetter(x) + (GlobalVars.getHeight()-1-y));

    	//printFreezeMSG(y, x);

        return board;
    }
}

