package se.liu.ida.jaclu010carfo452.tddd78.chess2.rules;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities.AbilityType;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.GlobalVars;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.PieceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Extended object for ChessBord that houses the methods for checking and updating potential moves
 * @author jaclu010, carfo452
 */
public class RuleController
{
    private List<Point> possibleMoves = new ArrayList<>();
    private List<Point> healingMoves = new ArrayList<>();
    private List<Point> abilityMoves = new ArrayList<>();
    private Boolean activePlayer = null;
    private ChessPiece selected = null;
    private ChessPiece[][] chessPieces = null;
    private int selectedY, selectedX, height = GlobalVars.getHeight(), width = GlobalVars.getWidth();


    public void updateVars(ChessPiece[][] chessPieces, ChessPiece selectedPiece, Point selectedCoords, Boolean activePlayer){
	this.chessPieces = chessPieces;
	this.selected = selectedPiece;
	this.selectedX = selectedCoords.getX();
	this.selectedY = selectedCoords.getY();
	this.activePlayer = activePlayer;
    }

    public void clearMoves(){
	healingMoves.clear();
	abilityMoves.clear();
	possibleMoves.clear();
    }

    public void checkRules(ChessPiece[][] chessPieces, ChessPiece selectedPiece, Point selectedCoords, Collection<ChessPiece> frozenPieces, Boolean activePlayer){
	updateVars(chessPieces, selectedPiece, selectedCoords, activePlayer);

	if(!frozenPieces.contains(selected)) {
	    checkByRules();
	    checkByAbility();
	}
    }

    public void checkByRules(){
	if (possibleMoves.isEmpty()) {
	    for (Rule rule : selected.fetchRules()) {
		if (selected.getpT() == PieceType.PAWN){
		    pawnAbleToMove(rule);
		} else if (rule.isUntilBlockCollission()) {
		    ableToMoveManySteps(rule);
		} else {
		    ableToMoveOneStep(rule);
		}
	    }
	}
    }

    public void checkByAbility(){
	if(selected.getaP() >= selected.getAbility().getCost() && abilityMoves.isEmpty()) {
	    switch (selected.getAbility().getaC()) {
		case OFFENSIVE:
		    checkByPossibleMoves();
		    if(selected.getAbility().getaT().equals(AbilityType.KNOCK_BACK)){
			checkForKnockBack();
		    }
		    break;
		case DEFENSIVE:
		    copyHealingMoves();
		    break;
		case SPECIAL:
		    if(selected.getAbility().getaT() == AbilityType.SUMMON_DEFENCE){
			checkFreePositionsAroundSelected();
		    } else {
			checkTargetForLaser();
		    }
		    break;
	    }
	}
    }

    private void checkTargetForLaser(){
	final int laserLenght = chessPieces[selectedY][selectedX].getAbility().getLaserRadius();
	int startY = selectedY-laserLenght;
	int startX = selectedX-laserLenght;
	int endY = selectedY+laserLenght;
	int endX = selectedX+laserLenght;
	for(int y = startY; y <= endY; y++){
	    for (int x = startX; x <= endX; x++) {
		if(y > 0 && y < height-1 && x > 0 && x < width-1){
		    if(chessPieces[y][x].getpT() != PieceType.EMPTY && chessPieces[y][x].getPlayer() == !selected.getPlayer()){
			abilityMoves.add(new Point(y, x));
		    }
		}
	    }
	}
    }

    private void checkByPossibleMoves(){
	abilityMoves.addAll(possibleMoves.stream()
				    .filter(possibleMove -> chessPieces[possibleMove.getY()][possibleMove.getX()].getPlayer() != null)
				    .collect(Collectors.toList()));
    }

    private void checkForKnockBack(){
	Iterable<Point> temp = new ArrayList<>(abilityMoves);
	abilityMoves.clear();
	int kB = selected.getAbility().getKnockBack();
	for (Point tempMove : temp) {
	    if((activePlayer && tempMove.getY()-kB >=0  && chessPieces[tempMove.getY()-kB][tempMove.getX()].getpT() == PieceType.EMPTY)
	       || (!activePlayer && tempMove.getY()+kB < GlobalVars.getHeight() && chessPieces[tempMove.getY()+kB][tempMove.getX()].getpT() == PieceType.EMPTY)){

		abilityMoves.add(tempMove);
	    }
	}
    }

    private void checkFreePositionsAroundSelected(){
	abilityMoves.addAll(possibleMoves.stream()
				    .filter(possibleMove -> chessPieces[possibleMove.getY()][possibleMove.getX()].getpT() ==
							    PieceType.EMPTY).collect(Collectors.toList()));
    }

    private void copyHealingMoves(){
	abilityMoves = new ArrayList<>(healingMoves);
    }

    private void pawnAbleToMove(Rule rule) {
	int y = selectedY + rule.getPoint().getY();
	int x = selectedX + rule.getPoint().getX();

	if (selected.getPlayer().equals(rule.getPlayer())) {
	    if (chessPieces[y][x].getpT() != PieceType.OUTSIDE && !Objects.equals(chessPieces[y][x].getPlayer(), selected.getPlayer())) {

		// We can't collapse these if brances because their is to many things to check for then
		if (rule.doRequiresInitialPos() && selected.isInitialPos() && chessPieces[y][x].getpT() == PieceType.EMPTY &&
		    chessPieces[y - (rule.getPoint().getY() / Math.abs(rule.getPoint().getY()))][x].getpT() == PieceType.EMPTY) {
		    possibleMoves.add(new Point(y, x));

		} else if ((rule.isHurtMove() && chessPieces[y][x].getpT() != PieceType.EMPTY) ||
			   (!rule.isHurtMove() && chessPieces[y][x].getpT() == PieceType.EMPTY && !rule.doRequiresInitialPos())) {

		    possibleMoves.add(new Point(y, x));
		}
	    }
	}
    }

    private boolean isWithinMovementsLimitations(int y, int x){
	return (y < height && y > 0 && x < width && x > 0 &&
		     chessPieces[y][x].getpT() != PieceType.OUTSIDE);
    }

    private void ableToMoveOneStep(Rule rule){
	int y = selectedY+rule.getPoint().getY();
	int x = selectedX+rule.getPoint().getX();
	if (isWithinMovementsLimitations(y, x)) {
	    addPointToRightListAtCoords(y, x);
	}
    }

    private void ableToMoveManySteps(Rule rule){
	int tempY = selectedY+rule.getPoint().getY();
	int tempX = selectedX+rule.getPoint().getX();
	while (isWithinMovementsLimitations(tempY, tempX)){
	    if (chessPieces[tempY][tempX].getPlayer() == null){
		possibleMoves.add(new Point(tempY, tempX));
		tempY += rule.getPoint().getY();
		tempX += rule.getPoint().getX();
	    } else {
		addPointToRightListAtCoords(tempY, tempX);
		break;
	    }

	}
    }

    private void addPointToRightListAtCoords(int y, int x){
	if (!Objects.equals(chessPieces[y][x].getPlayer(), selected.getPlayer())) {
	    possibleMoves.add(new Point(y, x));
	} else {
	    healingMoves.add(new Point(y, x));
	}
    }

    public List<Point> getPossibleMoves() {
	return possibleMoves;
    }

    public List<Point> getAbilityMoves() {
	return abilityMoves;
    }
}
