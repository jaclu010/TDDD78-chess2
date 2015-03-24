package chess2;

import java.util.List;

public class Ability
{
    private int cost, dmg, freezeTime, heal, knockBack;
    private boolean summonDefence;
    private AbilityCharacteristic aC;
    private AbilityType aT;


    public Ability(AbilityType aT) {
        setVars(aT);
    }

    public void setVars(AbilityType aT){
        switch(aT){
            case DOUBLE_DAMAGE:
                cost = 2;
                dmg = 2;
                aC = AbilityCharacteristic.OFFENSIVE;
                break;
            case HEAL:
                cost = 2;
                heal = 2;
                aC = AbilityCharacteristic.DEFENSIVE;
                break;
            case PUSH_BACK:
                cost = 2;
                knockBack = 2;
                aC = AbilityCharacteristic.OFFENSIVE;
                break;
            case FREEZE:
                cost = 2;
                freezeTime = 4;
                aC = AbilityCharacteristic.OFFENSIVE;
                break;
            case LASER:
                cost = 3;
                dmg = 3;
                knockBack = 1;
                aC = AbilityCharacteristic.SPECIAL;
                break;
            case SUMMON_DEFENCE:
                cost = 3;
                summonDefence = true;
                aC = AbilityCharacteristic.SPECIAL;
                break;
        }
    }

    public static Ability getAbility(PieceType pT){
        switch (pT){
            case PAWN:
                return new Ability(AbilityType.DOUBLE_DAMAGE);
            case ROOK:
                return new Ability(AbilityType.FREEZE);
            case BISHOP:
                return new Ability(AbilityType.HEAL);
            case KNIGHT:
                return new Ability(AbilityType.PUSH_BACK);
            case QUEEN:
                return new Ability(AbilityType.LASER);
            case KING:
                return new Ability(AbilityType.SUMMON_DEFENCE);
            default:
                return null;
        }
    }

    public AbilityCharacteristic getAC() {
        return aC;
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

    public boolean isSummonDefence() {
        return summonDefence;
    }
}
