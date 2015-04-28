package se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities;

public abstract class AbstractAbility implements AbilityInterface
{
    private int cost, dmg = 0, heal = 0, knockBack = 0, freezeTime = 0;
    private String msg = "";
    private AbilityType aT = null;

    public AbilityType getAT() {
            return aT;
        }

    public void setaT(final AbilityType aT) {
        this.aT = aT;
    }

    public int getCost(){
	return cost;
    }

    public void setCost(final int cost){
	this.cost = cost;
    }


    public int getDmg() {
        return dmg;
    }

    public void setDmg(final int dmg) {
        this.dmg = dmg;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public int getKnockBack() {
        return knockBack;
    }

    public void setKnockBack(final int knockBack) {
        this.knockBack = knockBack;
    }

    public int getHeal() {
        return heal;
    }

    public void setHeal(final int heal) {
        this.heal = heal;
    }

    public int getFreezeTime() {
        return freezeTime;
    }

    public void setFreezeTime(final int freezeTime) {
        this.freezeTime = freezeTime;
    }
}
