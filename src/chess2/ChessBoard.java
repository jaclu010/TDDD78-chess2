package chess2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.awt.event.MouseEvent;

public class ChessBoard
{
    private Boolean activePlayer;
    private boolean gameOver;
    private ChessPiece[][] cB;
    private ChessPiece selected;
    private String logMsg = "";
    private int selectedX, selectedY, height, width, turn, targetX, targetY;
    private Collection<ChessBoardListener> chessBoardListeners = new ArrayList<>();
    private Collection<AnimationListener> animationListeners = new ArrayList<>();
    private List<Point> possibleMoves = new ArrayList<>();
    private List<Point> healingMoves = new ArrayList<>();
    private List<Point> abilityMoves = new ArrayList<>();
    private List<ChessPiece> frozenPieces = new ArrayList<>();
    private List<ChessPiece> killedBlackPieces = new ArrayList<>();
    private List<ChessPiece> killedWhitePieces = new ArrayList<>();

    public ChessBoard() {
	this.height = GlobalVars.getHeight();
	this.width = GlobalVars.getWidth();
	this.cB = new ChessPiece[height][width];
	this.activePlayer = true;
	this.selected = null;
	this.gameOver = false;
	this.turn = 1;
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		if ((y == 0 || y == height-1) || (x == 0 || x == width -1)){
		    cB[y][x] = new ChessPiece(PieceType.OUTSIDE);
		} else {
		    cB[y][x] = new ChessPiece(PieceType.EMPTY);
		}
	    }
	}
    }

    public void addAnimationListener(AnimationListener aL){
	animationListeners.add(aL);
    }

    public void addChessBoardListener(ChessBoardListener cBL){
    	chessBoardListeners.add(cBL);
        }

    public void notifyListeners(){
    	chessBoardListeners.forEach(ChessBoardListener::chessBoardChanged);
        }

    public void notifyAnimationListeners(){
	animationListeners.forEach(AnimationListener::animateAction);
    }


    public void clearMoveLists(){
	healingMoves.clear();
	abilityMoves.clear();
	possibleMoves.clear();
    }

    public void clearKills(){
	killedBlackPieces.clear();
	killedWhitePieces.clear();
    }

    public void checkMouseClick(MouseEvent e){
	int mouseY = e.getY()/GlobalVars.getSquareSide();
	int mouseX = e.getX()/GlobalVars.getSquareSide();
	if (mouseY < height && mouseX < width && cB[mouseY][mouseX].getPieceType() != PieceType.OUTSIDE) {
	    testMovement(mouseY, mouseX);
	}
    }

    public void testMovement(int mouseY, int mouseX){
	if (selected == null && cB[mouseY][mouseX].getPieceType() != PieceType.EMPTY &&
	    Objects.equals(cB[mouseY][mouseX].getPlayer(), activePlayer)) {
	    // Select a chesspiece
	    clearMoveLists();
	    select(mouseY, mouseX);
	    checkRules();

	} else if (selected != null && cB[mouseY][mouseX].getPieceType() == PieceType.EMPTY && !frozenPieces.contains(selected)){
	    // Press on a empty piece
	    checkRules();
	    pieceAction(mouseY, mouseX);

	} else if (selected != null && Objects.equals(cB[mouseY][mouseX].getPlayer(), selected.getPlayer())) {
	    // Press on a piece of same player
	    if (GlobalVars.isShowAbilityMoves()){
		updateTarget(mouseY, mouseX);
		useAbility(mouseY, mouseX);
	    } else {
		clearMoveLists();
		select(mouseY, mouseX);
		checkRules();
	    }

	} else if (selected != null && cB[mouseY][mouseX].getPieceType() != PieceType.EMPTY && !frozenPieces.contains(selected)) {
	    // Press on a enemy piece
	    pieceAction(mouseY, mouseX);
	}
	notifyListeners();
    }

    public void select(int y, int x){
	selected = cB[y][x];
	selectedX = x;
	selectedY = y;
    }

    public void checkRules(){
	if(!frozenPieces.contains(selected)) {
	    checkByRules();
	    checkByAbility();
	    notifyListeners();
	}
    }

    public void checkByRules(){
	if (possibleMoves.isEmpty()) {
	    for (Rule rule : selected.fetchRules()) {
		if (selected.getPieceType() == PieceType.PAWN){
		    pawnAbleToMove(rule);
		} else if (rule.getUntilBlockCollission()) {
		    ableToMoveManySteps(rule);
		} else {
		    ableToMoveOneStep(rule);
		}
	    }
	}
	notifyListeners();
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
		    if(selected.getPieceType() == PieceType.KING){
			checkFreePositionsAroundKing();
		    } else {
			checkTargetForLaser();
		    }
		    break;
	    }
	}
	notifyListeners();
    }

    public void checkTargetForLaser(){
	final int laserLenght = 8;
	int startY = selectedY-laserLenght;
	int startX = selectedX-laserLenght;
	int endY = selectedY+laserLenght;
	int endX = selectedX+laserLenght;
	for(int y = startY; y <= endY; y++){
	    for (int x = startX; x <= endX; x++) {
		if(y > 0 && y < height-1 && x > 0 && x < width-1){
		    if(cB[y][x].getPieceType() != PieceType.EMPTY && cB[y][x].getPlayer() == !selected.getPlayer()){
			abilityMoves.add(new Point(y, x));
		    }
		}
	    }
	}
    }

    public void checkByPossibleMoves(){
	for (Point possibleMove : possibleMoves) {
	    if (cB[possibleMove.getY()][possibleMove.getX()].getPlayer() != null){
		abilityMoves.add(possibleMove);
	    }
	}
    }

    public void checkForKnockBack(){
	Iterable<Point> temp = new ArrayList<>(abilityMoves);
	abilityMoves.clear();
	int kB = selected.getAbility().getKnockBack();
	for (Point tempMove : temp) {
	    if(activePlayer && cB[tempMove.getY()-kB][tempMove.getX()].getPieceType() == PieceType.EMPTY){
		abilityMoves.add(tempMove);
	    } else if (!activePlayer && cB[tempMove.getY()+kB][tempMove.getX()].getPieceType() == PieceType.EMPTY){
		abilityMoves.add(tempMove);
	    }
	}
    }

    public void checkFreePositionsAroundKing(){
	for (Point possibleMove : possibleMoves) {
	    if (cB[possibleMove.getY()][possibleMove.getX()].getPieceType() == PieceType.EMPTY){
		abilityMoves.add(possibleMove);
	    }
	}
    }

    public void copyHealingMoves(){
	abilityMoves = new ArrayList<>(healingMoves);
    }

    public void pawnAbleToMove(Rule rule) {
	int y = selectedY + rule.getPoint().getY();
	int x = selectedX + rule.getPoint().getX();

	if (selected.getPlayer().equals(rule.getPlayer())) {
	    if (cB[y][x].getPieceType() != PieceType.OUTSIDE && !Objects.equals(cB[y][x].getPlayer(), selected.getPlayer())) {

		if (rule.doRequiresInitialPos() && selected.isInitialPos() && cB[y][x].getPieceType() == PieceType.EMPTY &&
		    cB[y - (rule.getPoint().getY() / Math.abs(rule.getPoint().getY()))][x].getPieceType() == PieceType.EMPTY) {
		    possibleMoves.add(new Point(y, x));

		} else if ((rule.isHurtMove() && cB[y][x].getPieceType() != PieceType.EMPTY) ||
			   (!rule.isHurtMove() && cB[y][x].getPieceType() == PieceType.EMPTY && !rule.doRequiresInitialPos())) {

		    possibleMoves.add(new Point(y, x));
		}
	    }
	}
    }

    public void ableToMoveOneStep(Rule rule){
	int y = selectedY+rule.getPoint().getY();
	int x = selectedX+rule.getPoint().getX();
	if (y < height && y > 0 && x < width && x > 0 &&
	     cB[y][x].getPieceType() != PieceType.OUTSIDE) {
	    if (!Objects.equals(cB[y][x].getPlayer(), selected.getPlayer())) {
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
	       cB[tempY][tempX].getPieceType() != PieceType.OUTSIDE){
	    if (cB[tempY][tempX].getPlayer() == null){
		possibleMoves.add(new Point(tempY, tempX));
		tempY += rule.getPoint().getY();
		tempX += rule.getPoint().getX();
	    } else {
		if (!Objects.equals(cB[tempY][tempX].getPlayer(), selected.getPlayer())){
		    possibleMoves.add(new Point(tempY, tempX));
		} else {
		    healingMoves.add(new Point(tempY, tempX));
		}
		break;
	    }

	}
    }

    public void useAbility(int y, int x) {
	for (Point possibleAbilityMove : abilityMoves) {
	    if(possibleAbilityMove.getX() == x && possibleAbilityMove.getY() == y) {
		payCost();
		notifyAnimationListeners();
	    }
	}
	notifyListeners();
    }

    public void chooseEndAction(){
    	if (GlobalVars.isShowRegularMoves()) {
	    if (cB[targetY][targetX].getPieceType() == PieceType.EMPTY) {
		movePiece(targetY, targetX);
	    } else {
		hurtPiece(targetY, targetX, 1);
	    }
	} else {

	    switch (selected.getAbility().getAC()) {
		case OFFENSIVE:
		    if (selected.getAbility().getFreezeTime() > 0) {
			freezePiece(targetY, targetX, selected.getAbility().getFreezeTime());
		    } else if (selected.getAbility().getKnockBack() > 0) {
			knockBack(targetY, targetX, selected.getAbility().getKnockBack());
		    } else {
			hurtPiece(targetY, targetX, selected.getAbility().getDmg());
		    }
		    break;
		case DEFENSIVE:
		    healPiece(targetY, targetX, selected.getAbility().getHeal());
		    break;
		case SPECIAL:
		    if (selected.getPieceType() == PieceType.KING) {
			spawnProtectionForKing();
		    } else {
			useLaser();
		    }
		    break;
	    }
	}
    }

    private void knockBack(int y, int x, int knockBack){
	int i = 1;
	if(activePlayer) i = -1;

	cB[y+i*knockBack][x] = cB[y][x];
	cB[y][x] = new ChessPiece(PieceType.EMPTY);

	printKnockBackMSG(y, x, i, knockBack);
	changeActivePlayer();
	notifyListeners();
    }

    private void freezePiece(int y, int x, int freezeTime){
	cB[y][x].setFreezeTime(freezeTime);
	frozenPieces.add(cB[y][x]);
	printFreezeMSG(y, x);
	changeActivePlayer();
    }

    private void useLaser(){
	hurtPiece(targetY, targetX, selected.getAbility().getDmg());
    }


    public void spawnProtectionForKing(){
	for (Point abilityMove : abilityMoves) {
	    cB[abilityMove.getY()][abilityMove.getX()] = new ChessPiece(activePlayer, PieceType.PAWN);
	}
	printProtectionMSG(selectedY, selectedX);
	changeActivePlayer();
	notifyListeners();
    }

    public void payCost(){
	selected.setaP(-selected.getAbility().getCost());
    }

    public void healPiece(int y, int x, int heal){
	cB[y][x].doHEAL(heal);
	changeActivePlayer();
	notifyListeners();
    }

    public void updateTarget(int y, int x){
	targetX = x;
	targetY = y;
    }

    public void pieceAction(int y, int x){
	updateTarget(y, x);
	if(GlobalVars.isShowAbilityMoves()){
	    useAbility(y, x);
	    notifyListeners();
	    return;
	}
	for (Point possibleMove : possibleMoves){
	    if (possibleMove.getX() == x && possibleMove.getY() == y){
		notifyAnimationListeners();
		/*
		if (cB[y][x].getPieceType() != PieceType.EMPTY) {
		    hurtPiece(y, x, 1);
		    break;
		} else {
		    movePiece(y, x);
		    break;
		}
		*/
	    }
	}
	notifyListeners();
    }

    public void movePiece(int y, int x){
	selected.setInitialPos(false);
	cB[y][x] = selected;
	cB[selectedY][selectedX] = new ChessPiece(PieceType.EMPTY);
	printPieceMovement(y, x);
	changeActivePlayer();
    }

    public void hurtPiece(int y, int x, int dmg){
	cB[y][x].doDMG(dmg);
	if(GlobalVars.isShowRegularMoves()){
	    selected.setaP(1);
	}
	if (cB[y][x].getHP() <= 0){
	    if (cB[y][x].getPlayer()){
		killedWhitePieces.add(cB[y][x]);
	    } else {
		killedBlackPieces.add(cB[y][x]);
	    }
	    printKill(y, x);

	    if(cB[y][x].getPieceType() == PieceType.KING){
		//movePiece(y, x);
	    	gameOver = true;
	    }
	    if(selected.getPieceType() == PieceType.QUEEN && GlobalVars.isShowAbilityMoves()) {
		cB[y][x] = new ChessPiece(PieceType.EMPTY);
		changeActivePlayer();
	    } else {
		movePiece(y, x);
	    }
	} else {
	    printDidDMG(y, x, dmg);
	    changeActivePlayer();
	}
    }

    private void updateFrozenPieces(){
	// Reduces the freezetime for all frozen chessPieces by 1
	ArrayList<ChessPiece> stillFrozen = new ArrayList<>();
	for (ChessPiece frozenPiece: frozenPieces){
	    frozenPiece.reduceFreezeTime(1);
	    if(frozenPiece.getFreezeTime()>0){
		stillFrozen.add(frozenPiece);
	    }
	}
	frozenPieces = stillFrozen;
    }

    public void printDidDMG(int y, int x, int dmg){
	logMsg = (selected.getPieceType().name()+ " did "+dmg+" damage to "+ cB[y][x].getPieceType().name())+ " at "+ getLetter(x) + (height-1-y);
    }

    public void printKill(int y, int x){
	logMsg = (selected.getPieceType().name()+ " killed "+cB[y][x].getPieceType().name());
	notifyListeners();
    }

    public void printPieceMovement(int y, int x){
	logMsg = (selected.getPieceType().name()+" from: "+ getLetter(selectedX)+ (width-1-selectedY)+ " -> " + getLetter(x) + (height-1-y));
    }

    public void printProtectionMSG(int y, int x){
	logMsg = (PieceType.KING+" activated protection barrier at " + getLetter(x)+ (height-1-y));
    }

    public void printFreezeMSG(int y, int x){
	logMsg = (selected.getPieceType().name()+" froze "+cB[y][x].getPieceType().name()+" for "+selected.getAbility().getFreezeTime()+" turns at "+getLetter(x) + (height-1-y));
    }

    public void printKnockBackMSG(int y, int x, int i, int knockBack){
	logMsg = (selected.getPieceType().name()+" knocked back "+cB[y+i*knockBack][x].getPieceType().name()+" to "+ getLetter(x)+(height-1-(y+i*knockBack)));
    }

    public String getLetter(int n){
	n += GlobalVars.getCharAdd();
	char a = (char) n;
	return Character.toString(a);
    }

    public void fillBoard(){
	//False = black piece
	//Adds the pawns at right position

	for (int x = 1; x < width-1; x++) {
	    cB[2][x] = new ChessPiece(false, PieceType.PAWN);
	    cB[height-3][x] = new ChessPiece(true, PieceType.PAWN);
	}

	// Adds all black pieces
	cB[1][1] = new ChessPiece(false, PieceType.ROOK);
	cB[1][2] = new ChessPiece(false, PieceType.KNIGHT);
	cB[1][3] = new ChessPiece(false, PieceType.BISHOP);
	cB[1][4] = new ChessPiece(false, PieceType.QUEEN);
	cB[1][5] = new ChessPiece(false, PieceType.KING);
	cB[1][6] = new ChessPiece(false, PieceType.BISHOP);
	cB[1][7] = new ChessPiece(false, PieceType.KNIGHT);
	cB[1][8] = new ChessPiece(false, PieceType.ROOK);


	// Adds all white pieces
	cB[height-2][1] = new ChessPiece(true, PieceType.ROOK);
	cB[height-2][2] = new ChessPiece(true, PieceType.KNIGHT);
	cB[height-2][3] = new ChessPiece(true, PieceType.BISHOP);
	cB[height-2][4] = new ChessPiece(true, PieceType.QUEEN);
	cB[height-2][5] = new ChessPiece(true, PieceType.KING);
	cB[height-2][6] = new ChessPiece(true, PieceType.BISHOP);
	cB[height-2][7] = new ChessPiece(true, PieceType.KNIGHT);
	cB[height-2][8] = new ChessPiece(true, PieceType.ROOK);
    }

    public void newGame() {
	for (int y = 1; y < height-1; y++) {
	    for (int x = 1; x < width-1; x++) {
		cB[y][x] = new ChessPiece(PieceType.EMPTY);
	    }
	}
	activePlayer = true;
	turn = 1;
	clearMoveLists();
	clearKills();
	selected = null;
	gameOver = false;
	fillBoard();
	notifyListeners();
    }

    public void changeActivePlayer(){
	updateFrozenPieces();
	turn+=1;
	activePlayer = !activePlayer;
	GlobalVars.setShowAbilityMoves(false);
	GlobalVars.setShowRegularMoves(true);
	selected = null;
	clearMoveLists();
	notifyListeners();
    }

    public ChessPiece getPiece(final int y, final int x){
	return cB[y][x];
    }

    public ChessPiece getSelected() {
	return selected;
    }

    public int getSelectedX() {
	return selectedX;
    }

    public int getSelectedY() {
    	return selectedY;
    }

    public int getTargetX() {
	return targetX;
    }

    public int getTargetY() {
	return targetY;
    }

    public void setSelected(final ChessPiece selected) {
	this.selected = selected;
    }

    public Iterable<Point> getPossibleMoves() {
	return possibleMoves;
    }

    public void clearPossibleMoves() {
	clearMoveLists();
    }

    public boolean isGameOver() {
	return gameOver;
    }

    public Boolean getActivePlayer() {
	return activePlayer;
    }

    public int getTurn() {
	return turn;
    }

    public String getLogMsg() {
	return logMsg;
    }

    public List<ChessPiece> getKilledWhitePieces() {
	return killedWhitePieces;
    }

    public List<ChessPiece> getKilledBlackPieces() {
	return killedBlackPieces;
    }

    public List<Point> getAbilityMoves() {
	return abilityMoves;
    }

    public List<ChessPiece> getFrozenPieces() {
	return frozenPieces;
    }
}