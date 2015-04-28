package se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.rules.Point;

import java.util.List;

public class DoubleDamage extends AbstractAbility
{

    public DoubleDamage() {
        setDmg(2);
        setCost(2);
        setaT(AbilityType.DOUBLE_DAMAGE);
        setaC(AbilityCharacteristic.OFFENSIVE);
    }

    @Override public ChessPiece[][] use(Point targetCoords, Point selectedCoords, ChessPiece[][] board, List<Point> moves) {
        int y = targetCoords.getY();
        int x = targetCoords.getX();
        board[y][x].doDMG(getDmg());

        setMsg(board[selectedCoords.getY()][selectedCoords.getX()].getpT().name()+" used Double Damage on "+
               board[y][x].getpT().name());

        return board;
    }
}
