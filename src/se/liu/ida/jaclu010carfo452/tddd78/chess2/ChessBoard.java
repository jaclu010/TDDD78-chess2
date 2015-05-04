package se.liu.ida.jaclu010carfo452.tddd78.chess2;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities.AbilityType;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.animation.AnimationListener;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.rules.Point;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.rules.RuleController;

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
    private Point targetCoords = new Point(5,5);
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
	loggerInit();
	fillBoard();
    }

    private void loggerInit(){
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
	    //checkRules();
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
	for (Point abilityMove : abilityMoves) {
	    if(abilityMove.getX() == x && abilityMove.getY() == y){
		payCost();
		notifyAnimationListeners();
		break;
	    }
	}
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
	}
    }
    public void executeAbilityModifiers(){
	board = selected.getAbility().use(targetCoords, selectedCoords, board, rC.getAbilityMoves());
	printAbility();
	checkForKill(targetCoords.getY(), targetCoords.getX(), selected.getAbility().getDmg());
	checkForFreeze();
	notifyListeners();
    }

    private void checkForFreeze(){
	if(board[targetCoords.getY()][targetCoords.getX()].getFreezeTime() > 0){
	    frozenPieces.add(board[targetCoords.getY()][targetCoords.getX()]);
	}
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
	checkForKill(y, x, dmg);
    }

    private void checkForKill(int y, int x, int dmg){
	if (board[y][x].getpT() != PieceType.EMPTY && board[y][x].getHP() <= 0){
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
	    } else if(selected.getAbility().getaT() == AbilityType.LASER && !GlobalVars.isShowRegularMoves()) {
		board[y][x] = new ChessPiece(PieceType.EMPTY);
		changeActivePlayer();
	    } else {
		movePiece(y, x);
	    }
	} else {
	    if (dmg > 0) {
		printDidDMG(y, x, dmg);
	    }
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

    private void printAbility(){
	logMsg = selected.getAbility().getMsg();
	logger.info(logMsg);
	notifyListeners();
    }
    private void printDidDMG(int y, int x, int dmg){
	logMsg = (selected.getpT().name()+ " did "+dmg+" damage to "+ board[y][x].getpT().name())+ " at "+ GlobalVars.getLetter(
		x) + (height-1-y);
	logger.info(logMsg);
    }

    private void printKill(int y, int x){
	logMsg = (selected.getpT().name()+ " killed "+ board[y][x].getpT().name() + " at "+ GlobalVars.getLetter(
			x) + (height-1-y));
	logger.info(logMsg);
    }

    private void printPieceMovement(int y, int x){
	logMsg = (selected.getpT().name()+" from: "+ GlobalVars.getLetter(selectedCoords.getX())+ (width-1- selectedCoords.getY())+ " -> " + GlobalVars.getLetter(
		x) + (height-1-y));
	logger.info(logMsg);
    }

    public void fillBoard(){
	//False = black piece
	//True = white pieces
	//Adds the pawns at right position

	assert width > 3: "Internal error: width has been changed";
	assert height > 3: "Internal error: height has been changed";
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