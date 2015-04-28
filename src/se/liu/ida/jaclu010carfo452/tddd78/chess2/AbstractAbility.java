package se.liu.ida.jaclu010carfo452.tddd78.chess2;

public abstract class AbstractAbility implements AbilityInterface
{
    private int cost;
    private AbilityCharacteristic aC = null;
    private AbilityType aT = null;

    public AbilityCharacteristic getAC() {
            return aC;
        }

    public AbilityType getAT() {
            return aT;
        }

    public int getCost(){
	return cost;
    }

    public void setCost(final int cost){
	this.cost = cost;
    }

    public void setaC(final AbilityCharacteristic aC) {
	this.aC = aC;
    }

    public void setaT(final AbilityType aT) {
	this.aT = aT;
    }
}
