package chess2;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class ChessBoard
{
    private Boolean activePlayer;
    private boolean gameOver;
    private Piece[][] cB;
    private Piece selected;
    private String logMsg = "";
    private int selectedX, selectedY, height, width, turn, blackKills, whiteKills;
    private Random rnd = new Random();
    private Collection<ChessBoardListener> chessBoardListeners = new ArrayList<>();
    private List<Point> possibleMoves = new ArrayList<>();
    private List<Piece> killedPieces = new ArrayList<>();

    public ChessBoard() {
	this.height = GlobalVars.getHeight();
	this.width = GlobalVars.getWidth();
	this.cB = new Piece[height][width];
	this.activePlayer = true;
	this.selected = null;
	this.gameOver = false;
	this.turn = 1;
	this.whiteKills = 0;
	this.blackKills = 0;
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		if ((y == 0 || y == height-1) || (x == 0 || x == width -1)){
		    cB[y][x] = new AbstractPiece(PieceType.OUTSIDE);
		} else {
		    cB[y][x] = new AbstractPiece(PieceType.EMPTY);
		}
	    }
	}
    }

    public void addChessBoardListener(ChessBoardListener cBL){
    	chessBoardListeners.add(cBL);
        }

    public void notifyListeners(){
    	chessBoardListeners.forEach(ChessBoardListener::chessBoardChanged);
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
	    possibleMoves.clear();
	    selected = cB[mouseY][mouseX];
	    selectedX = mouseX;
	    selectedY = mouseY;
	    checkByRules();
	} else if (selected != null && cB[mouseY][mouseX].getPieceType() == PieceType.EMPTY){
	    checkByRules();
	    pieceAction(mouseY, mouseX);
	} else if (selected != null && Objects.equals(cB[mouseY][mouseX].getPlayer(), selected.getPlayer())) {
	    possibleMoves.clear();
	    selected = cB[mouseY][mouseX];
	    selectedX = mouseX;
	    selectedY = mouseY;
	    checkByRules();
	} else if (selected != null && cB[mouseY][mouseX].getPieceType() != PieceType.EMPTY) {
	    checkByRules();
	    pieceAction(mouseY, mouseX);
	}
	notifyListeners();
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

    private void gameOver(){
    	Object[] options = {"Yes", "No"};
    	int optionChosen = JOptionPane.showOptionDialog(
                null,
                "Game Over!\n Play again?",
                "Game over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    	if(optionChosen == 0){
            clearBoard();
    	} else if(optionChosen == 1){
            System.exit(0);
        }
    }

    public void pawnAbleToMove(Rule rule){
	int y = selectedY+rule.getPoint().getY();
	int x = selectedX+rule.getPoint().getX();
	if (selected.getPlayer().equals(rule.getPlayer())){
	    if (cB[y][x].getPieceType() != PieceType.OUTSIDE && !Objects.equals(cB[y][x].getPlayer(), selected.getPlayer())){
		if (rule.doRequiresInitialPos() && selected.isInitialPos()
		    && cB[y][x].getPieceType() == PieceType.EMPTY &&
		    cB[y-(rule.getPoint().getY()/Math.abs(rule.getPoint().getY()))][x].getPieceType() == PieceType.EMPTY){
		    possibleMoves.add(new Point(y, x));

		} else if ((rule.isHurtMove() && cB[y][x].getPieceType() != PieceType.EMPTY)
		    || (!rule.isHurtMove() && cB[y][x].getPieceType() == PieceType.EMPTY && !rule.doRequiresInitialPos())){
		    possibleMoves.add(new Point(y, x));
		}
	    }
	}
    }

    public void ableToMoveOneStep(Rule rule){
	int y = selectedY+rule.getPoint().getY();
	int x = selectedX+rule.getPoint().getX();
	if (y < height && y > 0 && x < width && x > 0 &&
	    !Objects.equals(cB[y][x].getPlayer(), selected.getPlayer()) && cB[y][x].getPieceType() != PieceType.OUTSIDE) {
	    possibleMoves.add(new Point(y, x));
	}
    }

    public void ableToMoveManySteps(Rule rule){
	int tempY = selectedY+rule.getPoint().getY();
	int tempX = selectedX+rule.getPoint().getX();
	while (tempY < height && tempY > 0 && tempX < width && tempX > 0 &&
	       !Objects.equals(cB[tempY][tempX].getPlayer(), selected.getPlayer()) &&
	       cB[tempY][tempX].getPieceType() != PieceType.OUTSIDE){
	    if (cB[tempY][tempX].getPlayer() == null){
		possibleMoves.add(new Point(tempY, tempX));
		tempY += rule.getPoint().getY();
		tempX += rule.getPoint().getX();
	    } else {
		possibleMoves.add(new Point(tempY, tempX));
		break;
	    }

	}
    }

    public void pieceAction(int y, int x){
	for (Point possibleMove : possibleMoves){
	    if (possibleMove.getX() == x && possibleMove.getY() == y){
		if (cB[y][x].getPieceType() != PieceType.EMPTY){
		    hurtPiece(y, x, 1);
		    break;
		} else {
		    movePiece(y, x);
		    break;
		}
	    }
	}
	notifyListeners();
    }

    public void movePiece(int y, int x){
	selected.setInitialPos(false);
	cB[y][x] = selected;
	cB[selectedY][selectedX] = new AbstractPiece(PieceType.EMPTY);
	printPieceMovement(y, x);
	selected = null;
	activePlayer = !activePlayer;
	possibleMoves.clear();
	turn += 1;
    }

    public void hurtPiece(int y, int x, int dmg){
	cB[y][x].doDMG(dmg);
	if (cB[y][x].getHP() <= 0){
	    killedPieces.add(cB[y][x]);
	    if (cB[y][x].getPlayer()){
		whiteKills += 1;
	    } else {
		blackKills += 1;
	    }
	    printKill(y, x);
	    selected.setLvl(1);
	    movePiece(y, x);
	    if(cB[y][x].getPieceType() == PieceType.KING){
		gameOver = true;
	    }
	} else {
	    printDidDMG(y, x, dmg);
	    selected.setLvl(1);
	    selected = null;
	    activePlayer = !activePlayer;
	    possibleMoves.clear();
	    turn+=1;
	}
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

    public String getLetter(int n){
	n += GlobalVars.getCharAdd();
	char a = (char) n;
	return Character.toString(a);
    }
    public void fillBoard(){
	/*for (int y = 1; y < HEIGHT-1; y++) {
	for (int x = 1; x < WIDTH-1; x++) {
	cB[y][x] = randPiece(rnd.nextInt(7));
	}
	}*/
	//False = black piece
	//Adds the pawns at right position

	for (int x = 1; x < width-1; x++) {
	    cB[2][x] = new AbstractPiece(false, PieceType.PAWN);
	    cB[height-3][x] = new AbstractPiece(true, PieceType.PAWN);
	}

	// Adds all black pieces
	cB[1][1] = new AbstractPiece(false, PieceType.ROOK);
	cB[1][2] = new AbstractPiece(false, PieceType.KNIGHT);
	cB[1][3] = new AbstractPiece(false, PieceType.BISHOP);
	cB[1][4] = new AbstractPiece(false, PieceType.QUEEN);
	cB[1][5] = new AbstractPiece(false, PieceType.KING);
	cB[1][6] = new AbstractPiece(false, PieceType.BISHOP);
	cB[1][7] = new AbstractPiece(false, PieceType.KNIGHT);
	cB[1][8] = new AbstractPiece(false, PieceType.ROOK);


	// Adds all white pieces
	cB[height-2][1] = new AbstractPiece(true, PieceType.ROOK);
	cB[height-2][2] = new AbstractPiece(true, PieceType.KNIGHT);
	cB[height-2][3] = new AbstractPiece(true, PieceType.BISHOP);
	cB[height-2][4] = new AbstractPiece(true, PieceType.QUEEN);
	cB[height-2][5] = new AbstractPiece(true, PieceType.KING);
	cB[height-2][6] = new AbstractPiece(true, PieceType.BISHOP);
	cB[height-2][7] = new AbstractPiece(true, PieceType.KNIGHT);
	cB[height-2][8] = new AbstractPiece(true, PieceType.ROOK);
    }

    public void clearBoard() {
	for (int y = 1; y < height-1; y++) {
	    for (int x = 1; x < width-1; x++) {
		cB[y][x] = new AbstractPiece(PieceType.EMPTY);
	    }
	}
	activePlayer = true;
	possibleMoves.clear();
	selected = null;
	fillBoard();
	notifyListeners();
    }

    /*
    public AbstractPiece randPiece(int n){
	switch(n){
	    case 0: return new Bishop(true);
	    case 1: return new King(true);
	    case 2: return new Queen(true);
	    case 3: return new Knight(true);
	    case 4: return new Rook(true);
	    case 5: return new Pawn(true);
	    case 6: return new Empty();
	    default: return null;
	}
    }
	*/

    public Piece getPiece(final int y, final int x){
	return cB[y][x];
    }

    public Piece getSelected() {
	return selected;
    }

    public void setSelected(final Piece selected) {
	this.selected = selected;
    }

    public Iterable<Point> getPossibleMoves() {
	return possibleMoves;
    }

    public void clearPossibleMoves() {
	possibleMoves.clear();
    }

    public boolean getActivePlayer() {
	return activePlayer;
    }

    public void setActivePlayer(final boolean activePlayer) {
	this.activePlayer = activePlayer;
    }

    public boolean isGameOver() {
	return gameOver;
    }

    public int getTurn() {
	return turn;
    }

    public String getLogMsg() {
	return logMsg;
    }

    public List<Piece> getKilledPieces() {
	return killedPieces;
    }

    public int getWhiteKills() {
	return whiteKills;
    }

    public int getBlackKills() {
	return blackKills;
    }
}