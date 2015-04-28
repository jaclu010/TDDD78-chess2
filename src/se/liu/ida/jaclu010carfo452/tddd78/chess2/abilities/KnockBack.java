package se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbilityCharacteristic;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbilityType;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbstractAbility;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.PieceType;

public class KnockBack extends AbstractAbility
{
    private int knockBack;

    public KnockBack() {
	this.knockBack = 2;
	setCost(2);
	setaC(AbilityCharacteristic.OFFENSIVE);
	setaT(AbilityType.KNOCK_BACK);
    }

    public int getKnockBack() {
	return knockBack;
    }

    @Override public void use(int y, int x, ChessPiece[][] board) {
	int i = 1;
	if() i = -1;
	board[y+i*knockBack][x] = board[y][x];
	board[y][x] = new ChessPiece(PieceType.EMPTY);

	printKnockBackMSG(y, x, i, knockBack);
    }

    @Override public void rule() {

    }
}
