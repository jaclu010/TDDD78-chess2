package se.liu.ida.jaclu010carfo452.tddd78.chess2;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The engine class that 'is' the game
 * @author jaclu010, carfo452
 */
public class ChessBoard
{
    private Boolean activePlayer;
    private boolean gameOver;
    private ChessPiece[][] board;
    private ChessPiece selected;
    private String logMsg = "";
    private int height, width, turn;
    private Point selectedCoords = new Point(0,0);
    private Point targetCoords = new Point(0,0);
    private Collection<ChessBoardListener> chessBoardListeners;
    private Collection<AnimationListener> animationListeners;
    private List<ChessPiece> frozenPieces;
    private List<ChessPiece> killedBlackPieces;
    private List<ChessPiece> killedWhitePieces;
    private RuleController rC;
    private static Logger logger = Logger.getLogger(ChessBoard.class.getName());

    public ChessBoard() {
	this.height = GlobalVars.getHeight();
	this.width = GlobalVars.getWidth();
	this.board = new ChessPiece[height][width];
	this.rC = new RuleController();
	this.activePlayer = true;
	this.selected = null;
	this.gameOver = false;
	this.turn = 1;
	this.chessBoardListeners = new ArrayList<>();
	this.animationListeners = new ArrayList<>();
	this.frozenPieces = new ArrayList<>();
	this.killedBlackPieces = new ArrayList<>();
	this.killedWhitePieces = new ArrayList<>();

	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		if ((y == 0 || y == height-1) || (x == 0 || x == width -1)){
		    board[y][x] = new ChessPiece(PieceType.OUTSIDE);
		} else {
		    board[y][x] = new ChessPiece(PieceType.EMPTY);
		}
	    }
	}
	fillBoard();

	try {
	    FileHandler fileHandler = new FileHandler("assets/logs/chessBoardlog.txt");
	    logger.addHandler(fileHandler);
	    logger.setLevel(Level.ALL);
	} catch(IOException e){
	    logger.log(Level.WARNING, "No file with that name is found", e);
	}
	assert logger != null: "Logger was not initialised";

	logger.info("ChessBoard initialised");
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

    private void clearKills(){
	killedBlackPieces.clear();
	killedWhitePieces.clear();
    }

    public void checkMouseClick(MouseEvent e){
	int mouseY = e.getY()/GlobalVars.getsquareside();
	int mouseX = e.getX()/GlobalVars.getsquareside();
	if (mouseY < height && mouseX < width && board[mouseY][mouseX].getpT() != PieceType.OUTSIDE) {
	    testMovement(mouseY, mouseX);
	}
    }

    private void testMovement(int mouseY, int mouseX){
	if (selected == null && board[mouseY][mouseX].getpT() != PieceType.EMPTY &&
	    Objects.equals(board[mouseY][mouseX].getPlayer(), activePlayer)) {
	    // Select a chesspiece
	    clearMoveLists();
	    select(mouseY, mouseX);
	    checkRules();

	} else if (selected != null && board[mouseY][mouseX].getpT() == PieceType.EMPTY && !frozenPieces.contains(selected)){
	    // Press on a empty piece
	    checkRules();
	    pieceAction(mouseY, mouseX);

	} else if (selected != null && Objects.equals(board[mouseY][mouseX].getPlayer(), selected.getPlayer())) {
	    // Press on a piece of same player
	    if (!GlobalVars.isShowRegularMoves()){
		updateTarget(mouseY, mouseX);
		useAbility(mouseY, mouseX);
	    } else {
		clearMoveLists();
		select(mouseY, mouseX);
		checkRules();
	    }

	} else if (selected != null && board[mouseY][mouseX].getpT() != PieceType.EMPTY && !frozenPieces.contains(selected)) {
	    // Press on a enemy piece
	    pieceAction(mouseY, mouseX);
	}
	notifyListeners();
    }

    private void select(int y, int x){
	selected = board[y][x];
	selectedCoords.setX(x);
	selectedCoords.setY(y);
	assert selected != null: "Internal error: Selected is null";
	logger.info("A chess piece was selected");
    }

    public void checkRules(){
	rC.checkRules(board, selected, selectedCoords, frozenPieces, activePlayer);
	notifyListeners();
    }

    private void useAbility(int y, int x) {
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
	    if (board[targetCoords.getY()][targetCoords.getX()].getpT() == PieceType.EMPTY) {
		movePiece(targetCoords.getY(), targetCoords.getX());
	    } else {
		hurtPiece(targetCoords.getY(), targetCoords.getX(), 1);
	    }
	} else {
	    executeAbilityModifiers();
	    /*
	    switch (selected.getAbility().getAC()) {
		case OFFENSIVE:
		    if (selected.getAbility().getFreezeTime() > 0) {
			freezePiece(targetCoords.getY(), targetCoords.getX(), selected.getAbility().getFreezeTime());
		    } else if (selected.getAbility().getKnockBack() > 0) {
			knockBack(targetCoords.getY(), targetCoords.getX(), selected.getAbility().getKnockBack());
		    } else {
			hurtPiece(targetCoords.getY(), targetCoords.getX(), selected.getAbility().getDmg());
		    }
		    break;
		case DEFENSIVE:
		    healPiece(targetCoords.getY(), targetCoords.getX(), selected.getAbility().getHeal());
		    break;
		case SPECIAL:
		    if (selected.getpT() == PieceType.KING) {
			spawnProtectionForKing();
		    } else {
			useLaser();
		    }
		    break;
	    }
	    */
	}
    }
    private void executeAbilityModifiers(){
	board = selected.getAbility().use(targetCoords.getY(), targetCoords.getX(), board)
    }

    private void knockBack(int y, int x, int knockBack){
	int i = 1;
	if(activePlayer) i = -1;
	board[y+i*knockBack][x] = board[y][x];
	board[y][x] = new ChessPiece(PieceType.EMPTY);

	printKnockBackMSG(y, x, i, knockBack);
	changeActivePlayer();
	notifyListeners();
    }

    private void freezePiece(int y, int x, int freezeTime){
	board[y][x].setFreezeTime(freezeTime);
	frozenPieces.add(board[y][x]);
	printFreezeMSG(y, x);
	changeActivePlayer();
    }

    private void useLaser(){
	hurtPiece(targetCoords.getY(), targetCoords.getX(), selected.getAbility().getDmg());
	logger.info("The queen used her laser");
    }

    public void spawnProtectionForKing(){
	List<Point> abilityMoves = rC.getAbilityMoves();
	for (Point abilityMove : abilityMoves) {
	    board[abilityMove.getY()][abilityMove.getX()] = new ChessPiece(activePlayer, PieceType.PAWN);
	}
	printProtectionMSG(selectedCoords.getY(), selectedCoords.getX());
	changeActivePlayer();
	notifyListeners();
    }

    private void healPiece(int y, int x, int heal) {
	board[y][x].doHeal(heal);
	printHeal(y, x);
	changeActivePlayer();
	notifyListeners();
    }

    private void payCost(){
	selected.setaP(-selected.getAbility().getCost());
    }

    private void updateTarget(int y, int x){
	targetCoords.setY(y);
	targetCoords.setX(x);
    }

    private void pieceAction(int y, int x){
	updateTarget(y, x);
	if(!GlobalVars.isShowRegularMoves()){
	    useAbility(y, x);
	} else{
	    List<Point> possibleMoves = rC.getPossibleMoves();
	    possibleMoves.stream().filter(possibleMove -> possibleMove.getX() == x && possibleMove.getY() == y)
		    .forEach(possibleMove -> notifyAnimationListeners());
	}
	notifyListeners();
    }

    private void movePiece(int y, int x){
	selected.setInitialPos(false);
	board[y][x] = selected;
	board[selectedCoords.getY()][selectedCoords.getX()] = new ChessPiece(PieceType.EMPTY);
	printPieceMovement(y, x);
	changeActivePlayer();
    }

    private void hurtPiece(int y, int x, int dmg){
	board[y][x].doDMG(dmg);
	if(GlobalVars.isShowRegularMoves()){
	    selected.setaP(1);
	}
	if (board[y][x].getHP() <= 0){
	    if (board[y][x].getPlayer()){
		killedWhitePieces.add(board[y][x]);
	    } else {
		killedBlackPieces.add(board[y][x]);
	    }
	    printKill(y, x);
	    notifyListeners();

	    if(board[y][x].getpT() == PieceType.KING){
		movePiece(y, x);
	    	gameOver = true;
		notifyListeners();
	    } else if(selected.getpT() == PieceType.QUEEN && !GlobalVars.isShowRegularMoves()) {
		board[y][x] = new ChessPiece(PieceType.EMPTY);
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
	// Reduces the freezetime for all frozen board by 1
	List<ChessPiece> stillFrozen = new ArrayList<>();
	assert frozenPieces != null: "Internal error: frozenPieces is null";
	for (ChessPiece frozenPiece: frozenPieces){
	    frozenPiece.reduceFreezeTime(1);
	    if(frozenPiece.getFreezeTime()>0){
		stillFrozen.add(frozenPiece);
	    }
	}
	frozenPieces = stillFrozen;
    }

    private void printDidDMG(int y, int x, int dmg){
	logMsg = (selected.getpT().name()+ " did "+dmg+" damage to "+ board[y][x].getpT().name())+ " at "+ getLetter(x) + (height-1-y);
	logger.info(logMsg);
    }

    private void printKill(int y, int x){
	logMsg = (selected.getpT().name()+ " killed "+ board[y][x].getpT().name());
	logger.info(logMsg);
    }

    private void printPieceMovement(int y, int x){
	logMsg = (selected.getpT().name()+" from: "+ getLetter(selectedCoords.getX())+ (width-1- selectedCoords.getY())+ " -> " + getLetter(x) + (height-1-y));
	logger.info(logMsg);
    }

    private void printProtectionMSG(int y, int x){
	logMsg = (PieceType.KING+" activated protection barrier at " + getLetter(x)+ (height-1-y));
	logger.info(logMsg);
    }

    private void printFreezeMSG(int y, int x){
	logMsg = (selected.getpT().name()+" froze "+ board[y][x].getpT().name()+" for "+selected.getAbility().getFreezeTime()+" turns at "+getLetter(x) + (height-1-y));
	logger.info(logMsg);
    }

    private void printKnockBackMSG(int y, int x, int i, int knockBack){
	logMsg = (selected.getpT().name()+" knocked back "+ board[y+i*knockBack][x].getpT().name()+" to "+ getLetter(x) +
		  (height - 1 - (y + i * knockBack)));
	logger.info(logMsg);
    }

    private void printHeal(int y, int x){
	logMsg = (selected.getpT().name()+" healed "+  board[y][x].getpT().name()+" for "+
		  selected.getAbility().getHeal() + " HP");
	logger.info(logMsg);
    }

    private String getLetter(int n){
	n += GlobalVars.getcharadd();
	char a = (char) n;
	return Character.toString(a);
    }

    public void fillBoard(){
	//False = black piece
	//True = white pieces
	//Adds the pawns at right position

	assert width > 0: "Internal error: width has been changed";
	assert height > 0: "Internal error: height has been changed";
	for (int x = 1; x < width-1; x++) {
	    board[2][x] = new ChessPiece(false, PieceType.PAWN);
	    board[height-3][x] = new ChessPiece(true, PieceType.PAWN);
	}

	// Adds all black pieces
	board[1][1] = new ChessPiece(false, PieceType.ROOK);
	board[1][2] = new ChessPiece(false, PieceType.KNIGHT);
	board[1][3] = new ChessPiece(false, PieceType.BISHOP);
	board[1][4] = new ChessPiece(false, PieceType.QUEEN);
	board[1][5] = new ChessPiece(false, PieceType.KING);
	board[1][6] = new ChessPiece(false, PieceType.BISHOP);
	board[1][7] = new ChessPiece(false, PieceType.KNIGHT);
	board[1][8] = new ChessPiece(false, PieceType.ROOK);


	// Adds all white pieces
	board[height-2][1] = new ChessPiece(true, PieceType.ROOK);
	board[height-2][2] = new ChessPiece(true, PieceType.KNIGHT);
	board[height-2][3] = new ChessPiece(true, PieceType.BISHOP);
	board[height-2][4] = new ChessPiece(true, PieceType.QUEEN);
	board[height-2][5] = new ChessPiece(true, PieceType.KING);
	board[height-2][6] = new ChessPiece(true, PieceType.BISHOP);
	board[height-2][7] = new ChessPiece(true, PieceType.KNIGHT);
	board[height-2][8] = new ChessPiece(true, PieceType.ROOK);
    }

    public void newGame() {
	for (int y = 1; y < height-1; y++) {
	    for (int x = 1; x < width-1; x++) {
		board[y][x] = new ChessPiece(PieceType.EMPTY);
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
	logger.info("A new game was started");
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
	return board[y][x];
    }

    public ChessPiece getSelected() {
	return selected;
    }

    public Point getSelectedCoords() {
	return selectedCoords;
    }

    public Point getTargetCoords() {
	return targetCoords;
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

    public Collection<ChessPiece> getKilledWhitePieces() {
	return killedWhitePieces;
    }

    public Collection<ChessPiece> getKilledBlackPieces() {
	return killedBlackPieces;
    }

    public Collection<ChessPiece> getFrozenPieces() {
	return frozenPieces;
    }

    public RuleController getRC() {
	return rC;
    }
}