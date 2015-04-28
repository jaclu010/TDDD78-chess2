package se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbilityCharacteristic;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbilityType;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.AbstractAbility;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;

public class DoubleDamage extends AbstractAbility
{
    private int dmg;

    public DoubleDamage() {
        this.dmg = 2;
        setCost(2);
        setaC(AbilityCharacteristic.OFFENSIVE);
        setaT(AbilityType.DOUBLE_DAMAGE);
    }

    public int getDmg() {
        return dmg;
    }

    @Override public void use(int y, int x, ChessPiece[][] board) {

    }

    @Override public void rule() {

    }
}
