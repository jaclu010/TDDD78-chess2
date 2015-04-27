package se.liu.ida.jaclu010carfo452.tddd78.chess2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.awt.event.MouseEvent;

/**
 * The engine class that 'is' the game
 * @author jaclu010, carfo452
 */
public class ChessBoard
{
    private Boolean activePlayer;
    private boolean gameOver;
    private ChessPiece[][] chessPieces;
    private ChessPiece selected;
    private String logMsg = "";
    private int selectedX, selectedY, height, width, turn, targetX, targetY;
    private Point selectedCords;
    private Collection<ChessBoardListener> chessBoardListeners = new ArrayList<>();
    private Collection<AnimationListener> animationListeners = new ArrayList<>();
    private List<ChessPiece> frozenPieces = new ArrayList<>();
    private List<ChessPiece> killedBlackPieces = new ArrayList<>();
    private List<ChessPiece> killedWhitePieces = new ArrayList<>();
    private RuleController rC;

    public ChessBoard() {
	this.height = GlobalVars.getHeight();
	this.width = GlobalVars.getWidth();
	this.chessPieces = new ChessPiece[height][width];
	this.rC = new RuleController();
	this.activePlayer = true;
	this.selected = null;
	this.gameOver = false;
	this.turn = 1;

	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		if ((y == 0 || y == height-1) || (x == 0 || x == width -1)){
		    chessPieces[y][x] = new ChessPiece(PieceType.OUTSIDE);
		} else {
		    chessPieces[y][x] = new ChessPiece(PieceType.EMPTY);
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

    private void notifyAnimationListeners(){
	animationListeners.forEach(AnimationListener::animateAction);
    }

    public void clearMoveLists(){
	rC.clearMoves();
    }

    public void clearKills(){
	killedBlackPieces.clear();
	killedWhitePieces.clear();
    }

    public void checkMouseClick(MouseEvent e){
	int mouseY = e.getY()/GlobalVars.getsquareside();
	int mouseX = e.getX()/GlobalVars.getsquareside();
	if (mouseY < height && mouseX < width && chessPieces[mouseY][mouseX].getpT() != PieceType.OUTSIDE) {
	    testMovement(mouseY, mouseX);
	}
    }

    public void testMovement(int mouseY, int mouseX){
	if (selected == null && chessPieces[mouseY][mouseX].getpT() != PieceType.EMPTY &&
	    Objects.equals(chessPieces[mouseY][mouseX].getPlayer(), activePlayer)) {
	    // Select a chesspiece
	    clearMoveLists();
	    select(mouseY, mouseX);
	    checkRules();


	} else if (selected != null && chessPieces[mouseY][mouseX].getpT() == PieceType.EMPTY && !frozenPieces.contains(selected)){
	    // Press on a empty piece
	    checkRules();
	    pieceAction(mouseY, mouseX);

	} else if (selected != null && Objects.equals(chessPieces[mouseY][mouseX].getPlayer(), selected.getPlayer())) {
	    // Press on a piece of same player
	    if (!GlobalVars.isShowRegularMoves()){
		updateTarget(mouseY, mouseX);
		useAbility(mouseY, mouseX);
	    } else {
		clearMoveLists();
		select(mouseY, mouseX);
		checkRules();
	    }

	} else if (selected != null && chessPieces[mouseY][mouseX].getpT() != PieceType.EMPTY && !frozenPieces.contains(selected)) {
	    // Press on a enemy piece
	    pieceAction(mouseY, mouseX);
	}
	notifyListeners();
    }

    public void select(int y, int x){
	selected = chessPieces[y][x];
	selectedX = x;
	selectedY = y;
    }

    public void checkRules(){
	rC.checkRules(chessPieces, selected, selectedY, selectedX, frozenPieces, activePlayer);
	notifyListeners();
    }

    public void useAbility(int y, int x) {
	List<Point> abilityMoves = rC.getAbilityMoves();
	abilityMoves.stream().filter(possibleAbilityMove -> possibleAbilityMove.getX() == x && possibleAbilityMove.getY() == y)
		.forEach(possibleAbilityMove -> {
		    payCost();
		    notifyAnimationListeners();
		});
	notifyListeners();
    }

    public void chooseEndAction(){
    	if (GlobalVars.isShowRegularMoves()) {
	    if (chessPieces[targetY][targetX].getpT() == PieceType.EMPTY) {
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
		    if (selected.getpT() == PieceType.KING) {
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

	chessPieces[y+i*knockBack][x] = chessPieces[y][x];
	chessPieces[y][x] = new ChessPiece(PieceType.EMPTY);

	printKnockBackMSG(y, x, i, knockBack);
	changeActivePlayer();
	notifyListeners();
    }

    private void freezePiece(int y, int x, int freezeTime){
	chessPieces[y][x].setFreezeTime(freezeTime);
	frozenPieces.add(chessPieces[y][x]);
	printFreezeMSG(y, x);
	changeActivePlayer();
    }

    private void useLaser(){
	hurtPiece(targetY, targetX, selected.getAbility().getDmg());
    }

    public void spawnProtectionForKing(){
	List<Point> abilityMoves = rC.getAbilityMoves();
	for (Point abilityMove : abilityMoves) {
	    chessPieces[abilityMove.getY()][abilityMove.getX()] = new ChessPiece(activePlayer, PieceType.PAWN);
	}
	printProtectionMSG(selectedY, selectedX);
	changeActivePlayer();
	notifyListeners();
    }

    public void payCost(){
	selected.setaP(-selected.getAbility().getCost());
    }

    public void healPiece(int y, int x, int heal){
	chessPieces[y][x].doHeal(heal);
	changeActivePlayer();
	notifyListeners();
    }

    public void updateTarget(int y, int x){
	targetX = x;
	targetY = y;
    }

    public void pieceAction(int y, int x){
	updateTarget(y, x);
	if(!GlobalVars.isShowRegularMoves()){
	    useAbility(y, x);
	    notifyListeners();
	    return;
	}
	List<Point> possibleMoves = rC.getPossibleMoves();
	possibleMoves.stream().filter(possibleMove -> possibleMove.getX() == x && possibleMove.getY() == y)
		.forEach(possibleMove -> notifyAnimationListeners());
	notifyListeners();
    }

    public void movePiece(int y, int x){
	selected.setInitialPos(false);
	chessPieces[y][x] = selected;
	chessPieces[selectedY][selectedX] = new ChessPiece(PieceType.EMPTY);
	printPieceMovement(y, x);
	changeActivePlayer();
    }

    public void hurtPiece(int y, int x, int dmg){
	chessPieces[y][x].doDMG(dmg);
	if(GlobalVars.isShowRegularMoves()){
	    selected.setaP(1);
	}
	if (chessPieces[y][x].getHP() <= 0){
	    if (chessPieces[y][x].getPlayer()){
		killedWhitePieces.add(chessPieces[y][x]);
	    } else {
		killedBlackPieces.add(chessPieces[y][x]);
	    }
	    printKill(y, x);

	    if(chessPieces[y][x].getpT() == PieceType.KING){
		//movePiece(y, x);
	    	gameOver = true;
	    }
	    if(selected.getpT() == PieceType.QUEEN && !GlobalVars.isShowRegularMoves()) {
		chessPieces[y][x] = new ChessPiece(PieceType.EMPTY);
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
	List<ChessPiece> stillFrozen = new ArrayList<>();
	for (ChessPiece frozenPiece: frozenPieces){
	    frozenPiece.reduceFreezeTime(1);
	    if(frozenPiece.getFreezeTime()>0){
		stillFrozen.add(frozenPiece);
	    }
	}
	frozenPieces = stillFrozen;
    }

    public void printDidDMG(int y, int x, int dmg){
	logMsg = (selected.getpT().name()+ " did "+dmg+" damage to "+ chessPieces[y][x].getpT().name())+ " at "+ getLetter(x) + (height-1-y);
    }

    public void printKill(int y, int x){
	logMsg = (selected.getpT().name()+ " killed "+ chessPieces[y][x].getpT().name());
	notifyListeners();
    }

    public void printPieceMovement(int y, int x){
	logMsg = (selected.getpT().name()+" from: "+ getLetter(selectedX)+ (width-1-selectedY)+ " -> " + getLetter(x) + (height-1-y));
    }

    public void printProtectionMSG(int y, int x){
	logMsg = (PieceType.KING+" activated protection barrier at " + getLetter(x)+ (height-1-y));
    }

    public void printFreezeMSG(int y, int x){
	logMsg = (selected.getpT().name()+" froze "+ chessPieces[y][x].getpT().name()+" for "+selected.getAbility().getFreezeTime()+" turns at "+getLetter(x) + (height-1-y));
    }

    public void printKnockBackMSG(int y, int x, int i, int knockBack){
	logMsg = (selected.getpT().name()+" knocked back "+ chessPieces[y+i*knockBack][x].getpT().name()+" to "+ getLetter(x)+(height-1-(y+i*knockBack)));
    }

    public String getLetter(int n){
	n += GlobalVars.getcharadd();
	char a = (char) n;
	return Character.toString(a);
    }

    public void fillBoard(){
	//False = black piece
	//Adds the pawns at right position

	for (int x = 1; x < width-1; x++) {
	    chessPieces[2][x] = new ChessPiece(false, PieceType.PAWN);
	    chessPieces[height-3][x] = new ChessPiece(true, PieceType.PAWN);
	}

	// Adds all black pieces
	chessPieces[1][1] = new ChessPiece(false, PieceType.ROOK);
	chessPieces[1][2] = new ChessPiece(false, PieceType.KNIGHT);
	chessPieces[1][3] = new ChessPiece(false, PieceType.BISHOP);
	chessPieces[1][4] = new ChessPiece(false, PieceType.QUEEN);
	chessPieces[1][5] = new ChessPiece(false, PieceType.KING);
	chessPieces[1][6] = new ChessPiece(false, PieceType.BISHOP);
	chessPieces[1][7] = new ChessPiece(false, PieceType.KNIGHT);
	chessPieces[1][8] = new ChessPiece(false, PieceType.ROOK);


	// Adds all white pieces
	chessPieces[height-2][1] = new ChessPiece(true, PieceType.ROOK);
	chessPieces[height-2][2] = new ChessPiece(true, PieceType.KNIGHT);
	chessPieces[height-2][3] = new ChessPiece(true, PieceType.BISHOP);
	chessPieces[height-2][4] = new ChessPiece(true, PieceType.QUEEN);
	chessPieces[height-2][5] = new ChessPiece(true, PieceType.KING);
	chessPieces[height-2][6] = new ChessPiece(true, PieceType.BISHOP);
	chessPieces[height-2][7] = new ChessPiece(true, PieceType.KNIGHT);
	chessPieces[height-2][8] = new ChessPiece(true, PieceType.ROOK);
    }

    public void newGame() {
	for (int y = 1; y < height-1; y++) {
	    for (int x = 1; x < width-1; x++) {
		chessPieces[y][x] = new ChessPiece(PieceType.EMPTY);
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
	GlobalVars.setShowRegularMoves(true);
	selected = null;
	clearMoveLists();
	notifyListeners();
    }

    public ChessPiece getPiece(final int y, final int x){
	return chessPieces[y][x];
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

    public List<ChessPiece> getFrozenPieces() {
	return frozenPieces;
    }

    public RuleController getRC() {
	return rC;
    }
}