package se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbilityCharacteristic;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbilityType;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbstractAbility;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;

public class Heal extends AbstractAbility
{
    private int heal;

    public Heal() {
	this.heal = 2;
	setCost(2);
	setaC(AbilityCharacteristic.DEFENSIVE);
	setaT(AbilityType.HEAL);
    }

    public int getHeal() {
	return heal;
    }

    @Override public void use(int y, int x, ChessPiece[][] board) {

    }

    @Override public void rule() {

    }
}
