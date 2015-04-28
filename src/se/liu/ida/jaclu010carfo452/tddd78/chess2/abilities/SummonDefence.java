package se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.GlobalVars;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.PieceType;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.rules.Point;

import java.util.List;


public class SummonDefence extends AbstractAbility
{
    public SummonDefence() {
	setCost(3);
	setaT(AbilityType.SUMMON_DEFENCE);
    }

    @Override public ChessPiece[][] use(Point targetCoords, Point selectedCoords, ChessPiece[][] board, List<Point> moves) {
        int y = selectedCoords.getY();
        int x = selectedCoords.getX();
        for (Point move : moves) {
            board[move.getY()][move.getX()] = new ChessPiece(board[y][x].getPlayer(), PieceType.PAWN);
        }
        setMsg(board[y][x].getpT().name() + " activated protection barrier at " +
               GlobalVars.getLetter(x)+ (GlobalVars.getHeight()-1-y));
        return board;
    }
}
