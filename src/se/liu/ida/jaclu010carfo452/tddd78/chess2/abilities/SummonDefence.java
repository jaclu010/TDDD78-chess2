package se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbilityCharacteristic;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbilityType;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbstractAbility;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;

public class SummonDefence extends AbstractAbility
{
    public SummonDefence() {
	setCost(3);
	setaC(AbilityCharacteristic.SPECIAL);
	setaT(AbilityType.SUMMON_DEFENCE);
    }

    @Override public void use(int y, int x, ChessPiece[][] board) {

    }

    @Override public void rule() {

    }
}
