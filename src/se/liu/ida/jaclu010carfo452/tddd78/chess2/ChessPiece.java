package se.liu.ida.jaclu010carfo452.tddd78.chess2;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities.DoubleDamage;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities.Freeze;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities.Heal;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities.KnockBack;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities.Laser;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities.SummonDefence;

/**
 * Creates a Chesspiece with variables depending on PieceType
 * @author jaclu010, carfo452
 */
public class ChessPiece
{
    private int hP, aP, freezeTime;
    private Boolean player = null;
    private boolean underAnimation = false;
    private PieceType pT;
    private AbstractAbility ability = null;
    private boolean initialPos;

    /**
     * Contruktor for all chess pieces except outside and empty
     * @param player: which team the chess piece belongs to
     * @param pT: what type of chesspiece it is
     */
    public ChessPiece(final boolean player, final PieceType pT) {
	this.player = player;
        this.pT = pT;
        this.hP = decideHP(pT);
        this.aP = GlobalVars.getaP();
        this.initialPos = true;
        this.ability = decideAbility(pT);
        //this.ability = Ability.getAbility(pT);
        this.freezeTime = 0;
    }

    /**
     * Contruktor for outside and empty chess pieces
     * @param pT: what type of chesspiece it is
     */
    public ChessPiece(final PieceType pT) {
        this.pT= pT;
    }

    private int decideHP(PieceType pT){
        switch(pT){
            case PAWN: return 2;
            case BISHOP: return 3;
            case KNIGHT: return 3;
            case ROOK: return 3;
            case QUEEN: return 4;
            case KING: return 5;
            case EMPTY: return 0;
            case OUTSIDE: return 0;
            default: return 0;
        }
    }

    private AbstractAbility decideAbility(PieceType pT){
        switch(pT){
            case PAWN: return new DoubleDamage();
            case BISHOP: return new Heal();
            case KNIGHT: return new KnockBack();
            case ROOK: return new Freeze();
            case QUEEN: return new Laser();
            case KING: return new SummonDefence();
            case EMPTY: return null;
            case OUTSIDE: return null;
            default: return null;
        }
    }

    public void useAbility(int y, int x){
        ability.use(y,x);

    }

    public Iterable<Rule> fetchRules(){
        return RuleList.getInstance().extractRulesFor(pT);
    }

    public int getHP(){
        return hP;
    }

    public int getaP() {
        return aP;
    }

    public Boolean getPlayer(){
        return player;
    }

    public PieceType getpT() {
        return pT;
    }

    public boolean isInitialPos() {
        return initialPos;
    }

    public void setInitialPos(final boolean initialPos) {
        this.initialPos = initialPos;
    }

    public void doDMG(int dmg){
        this.hP -= dmg;
    }

    public void doHeal(int heal){
        this.hP += heal;
    }

    public void setaP(final int aP) {
        this.aP += aP;
    }

    public AbstractAbility getAbility() {
        return ability;
    }

    public int getFreezeTime() {
        return freezeTime;
    }

    public void setFreezeTime(final int frezzeTime){
        this.freezeTime = frezzeTime;
    }

    public void reduceFreezeTime(final int freezeTime) {
        this.freezeTime -= freezeTime;
    }

    public boolean isUnderAnimation() {
        return underAnimation;
    }

    public void setUnderAnimation(final boolean underAnimation) {
        this.underAnimation = underAnimation;
    }
}
