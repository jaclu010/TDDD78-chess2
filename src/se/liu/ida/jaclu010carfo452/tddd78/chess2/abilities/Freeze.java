package se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbilityCharacteristic;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbilityType;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbstractAbility;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;

public class Freeze extends AbstractAbility
{
    int frezzeTime;

    public Freeze() {
	this.frezzeTime = 6;
	setCost(2);
	setaC(AbilityCharacteristic.OFFENSIVE);
	setaT(AbilityType.FREEZE);
    }

    public int getFrezzeTime() {
	return frezzeTime;
    }

    @Override public void use(int y, int x, ChessPiece[][] board) {

    }

    @Override public void rule() {

    }
}

