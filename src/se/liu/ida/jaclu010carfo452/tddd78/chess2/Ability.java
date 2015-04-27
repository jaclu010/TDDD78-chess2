package se.liu.ida.jaclu010carfo452.tddd78.chess2;

/**
 * Defines the special ability, depending on PieceType
 * @author jaclu010, carfo452
 */
public class Ability
{
    private int cost, dmg, freezeTime, heal, knockBack;
    private AbilityCharacteristic aC = null;
    private AbilityType aT = null;


    public Ability(PieceType pT) {
        setVars(pT);
    }

    public void setVars(PieceType pT) {
        switch (pT) {
            case PAWN:
                cost = 2;
                dmg = 2;
                knockBack = 0;
                freezeTime = 0;
                aT = AbilityType.DOUBLE_DAMAGE;
                aC = AbilityCharacteristic.OFFENSIVE;
                break;
            case BISHOP:
                cost = 2;
                heal = 2;
                aT = AbilityType.HEAL;
                aC = AbilityCharacteristic.DEFENSIVE;
                break;
            case KNIGHT:
                cost = 2;
                dmg = 0;
                knockBack = 2;
                freezeTime = 0;
                aT = AbilityType.KNOCK_BACK;
                aC = AbilityCharacteristic.OFFENSIVE;
                break;
            case ROOK:
                cost = 2;
                dmg = 0;
                knockBack = 0;
                freezeTime = 6;
                aT = AbilityType.FREEZE;
                aC = AbilityCharacteristic.OFFENSIVE;
                break;
            case QUEEN:
                cost = 3;
                dmg = 3;
                knockBack = 0;
                aT = AbilityType.LASER;
                aC = AbilityCharacteristic.SPECIAL;
                break;
            case KING:
                cost = 3;
                aT = AbilityType.SUMMON_DEFENCE;
                aC = AbilityCharacteristic.SPECIAL;
                break;
            case EMPTY:
                break;
            case OUTSIDE:
                break;
        }
    }

    public static Ability getAbility(PieceType pT) {
        return new Ability(pT);
    }

    public AbilityCharacteristic getAC() {
        return aC;
    }

    public AbilityType getAT() {
        return aT;
    }

    public int getCost() {
        return cost;
    }

    public int getDmg() {
        return dmg;
    }

    public int getFreezeTime() {
        return freezeTime;
    }

    public int getHeal() {
        return heal;
    }

    public int getKnockBack() {
        return knockBack;
    }

}
