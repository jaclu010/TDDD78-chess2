package se.liu.ida.jaclu010carfo452.tddd78.chess2;

import java.util.ArrayList;
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


    public void updateVars(ChessPiece[][] chessPieces, ChessPiece selectedPiece, int selectedY, int selectedX, Boolean activePlayer){
	this.chessPieces = chessPieces;
	this.selected = selectedPiece;
	this.selectedX = selectedX;
	this.selectedY = selectedY;
	this.activePlayer = activePlayer;
    }

    public void clearMoves(){
	healingMoves.clear();
	abilityMoves.clear();
	possibleMoves.clear();
    }

    public void checkRules(ChessPiece[][] chessPieces, ChessPiece selectedPiece, int selectedY, int selectedX, List<ChessPiece> frozenPieces, Boolean activePlayer){
	updateVars(chessPieces, selectedPiece, selectedY, selectedX, activePlayer);

	if(!frozenPieces.contains(selected)) {
	    checkByRules();
	    checkByAbility();
	    //notifyListeners();
	}
    }

    public void checkByRules(){
	if (possibleMoves.isEmpty()) {
	    for (Rule rule : selected.fetchRules()) {
		if (selected.getpT() == PieceType.PAWN){
		    pawnAbleToMove(rule);
		} else if (rule.getUntilBlockCollission()) {
		    ableToMoveManySteps(rule);
		} else {
		    ableToMoveOneStep(rule);
		}
	    }
	}
	//notifyListeners();
    }

    public void checkByAbility(){
	if(selected.getaP() >= selected.getAbility().getCost() && abilityMoves.isEmpty()) {
	    switch (selected.getAbility().getAC()) {
		case OFFENSIVE:
		    checkByPossibleMoves();
		    if(selected.getAbility().getAT().equals(AbilityType.KNOCK_BACK)){
			checkForKnockBack();
		    }
		    break;
		case DEFENSIVE:
		    copyHealingMoves();
		    break;
		case SPECIAL:
		    if(selected.getpT() == PieceType.KING){
			checkFreePositionsAroundKing();
		    } else {
			checkTargetForLaser();
		    }
		    break;
	    }
	}
	//notifyListeners();
    }

    public void checkTargetForLaser(){
	final int laserLenght = 2;
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

    public void checkByPossibleMoves(){
	abilityMoves.addAll(possibleMoves.stream()
				    .filter(possibleMove -> chessPieces[possibleMove.getY()][possibleMove.getX()].getPlayer() != null)
				    .collect(Collectors.toList()));
    }

    public void checkForKnockBack(){
	Iterable<Point> temp = new ArrayList<>(abilityMoves);
	abilityMoves.clear();
	int kB = selected.getAbility().getKnockBack();
	for (Point tempMove : temp) {
	    if((activePlayer && chessPieces[tempMove.getY()-kB][tempMove.getX()].getpT() == PieceType.EMPTY)
	       || (!activePlayer && chessPieces[tempMove.getY()+kB][tempMove.getX()].getpT() == PieceType.EMPTY)){

		abilityMoves.add(tempMove);
	    }
	}
    }

    public void checkFreePositionsAroundKing(){
	abilityMoves.addAll(possibleMoves.stream()
				    .filter(possibleMove -> chessPieces[possibleMove.getY()][possibleMove.getX()].getpT() ==
							    PieceType.EMPTY).collect(Collectors.toList()));
    }

    public void copyHealingMoves(){
	abilityMoves = new ArrayList<>(healingMoves);
    }

    public void pawnAbleToMove(Rule rule) {
	int y = selectedY + rule.getPoint().getY();
	int x = selectedX + rule.getPoint().getX();

	if (selected.getPlayer().equals(rule.getPlayer())) {
	    if (chessPieces[y][x].getpT() != PieceType.OUTSIDE && !Objects.equals(chessPieces[y][x].getPlayer(), selected.getPlayer())) {

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

    public void ableToMoveOneStep(Rule rule){
	int y = selectedY+rule.getPoint().getY();
	int x = selectedX+rule.getPoint().getX();
	if (y < height && y > 0 && x < width && x > 0 &&
	     chessPieces[y][x].getpT() != PieceType.OUTSIDE) {
	    if (!Objects.equals(chessPieces[y][x].getPlayer(), selected.getPlayer())) {
		possibleMoves.add(new Point(y, x));
	    } else {
		healingMoves.add(new Point(y, x));
	    }
	}
    }

    public void ableToMoveManySteps(Rule rule){
	int tempY = selectedY+rule.getPoint().getY();
	int tempX = selectedX+rule.getPoint().getX();
	while (tempY < height && tempY > 0 && tempX < width && tempX > 0 &&
	       chessPieces[tempY][tempX].getpT() != PieceType.OUTSIDE){
	    if (chessPieces[tempY][tempX].getPlayer() == null){
		possibleMoves.add(new Point(tempY, tempX));
		tempY += rule.getPoint().getY();
		tempX += rule.getPoint().getX();
	    } else {
		if (!Objects.equals(chessPieces[tempY][tempX].getPlayer(), selected.getPlayer())){
		    possibleMoves.add(new Point(tempY, tempX));
		} else {
		    healingMoves.add(new Point(tempY, tempX));
		}
		break;
	    }

	}
    }

    public List<Point> getPossibleMoves() {
	return possibleMoves;
    }

    public List<Point> getAbilityMoves() {
	return abilityMoves;
    }
}
