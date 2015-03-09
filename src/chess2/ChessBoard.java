package chess2;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class ChessBoard
{
    private boolean activePlayer;
    private Piece[][] cB;
    private Piece selected;
    private int selectedX, selectedY, height, width;
    private Random rnd = new Random();
    private List<ChessBoardListener> chessBoardListeners = new ArrayList<>();
    private ArrayList<Point> possibleMoves = new ArrayList<>();

    public ChessBoard() {
	this.height = GlobalVars.getHeight();
	this.width = GlobalVars.getWidth();
	this.cB = new Piece[height][width];
	this.activePlayer = true;
	this.selected = null;
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
	if (selected == null && cB[mouseY][mouseX].getPieceType() != PieceType.EMPTY && cB[mouseY][mouseX].getPlayer() == activePlayer) {
	    selected = cB[mouseY][mouseX];
	    selectedX = mouseX;
	    selectedY = mouseY;
	    checkByRules();
	} else if (selected != null && cB[mouseY][mouseX].getPieceType() == PieceType.EMPTY){
	    checkByRules();
	    movePiece(mouseY, mouseX);
	    activePlayer = !activePlayer;

	} else if (selected != null && cB[mouseY][mouseX].getPlayer() == selected.getPlayer()) {
	    selected = cB[mouseY][mouseX];
	    selectedX = mouseX;
	    selectedY = mouseY;
	    checkByRules();
	} else if (selected != null && cB[mouseY][mouseX].getPieceType() == PieceType.KING){
	    // If you attack the enemy king
	    checkByRules();
	    movePiece(mouseY, mouseX);
	    notifyListeners();
	    gameOver();
	} else if (cB[mouseY][mouseX].getPieceType() == PieceType.EMPTY){

	} else {
	    checkByRules();
	    movePiece(mouseY, mouseX);
	    activePlayer = !activePlayer;
	}
	notifyListeners();
    }

    public void checkByRules(){
	possibleMoves.clear();
	for (Rule rule : selected.fetchRules()) {
	    if(rule.getUntilBlockCollission()){
		canMoveManySteps(rule);
	    } else{
		canMoveOneStep(rule);
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

    public void canMoveOneStep(Rule rule){
	int y = selectedY+rule.getPoint().getY();
	int x = selectedX+rule.getPoint().getX();
	if (y < height && y > 0 && x < width && x > 0 &&
	    cB[y][x].getPlayer() != selected.getPlayer()
		&& cB[y][x].getPieceType() != PieceType.OUTSIDE){
	    possibleMoves.add(new Point(y, x));
	}
    }

    public void canMoveManySteps(Rule rule){
	int tempY = selectedY+rule.getPoint().getY();
	int tempX = selectedX+rule.getPoint().getX();
	while (tempY < height && tempY > 0 && tempX < width && tempX > 0 &&
	       (cB[tempY][tempX].getPieceType() != PieceType.OUTSIDE &&
		cB[tempY][tempX].getPlayer() != selected.getPlayer()
	       )){
	    possibleMoves.add(new Point(tempY, tempX));
	    tempY += rule.getPoint().getY();
	    tempX += rule.getPoint().getX();
	}
    }

    public void movePiece(int y, int x){
	for (Point possibleMove : possibleMoves){
	    if (possibleMove.getX() == x && possibleMove.getY() == y){
		cB[y][x] = selected;
		cB[selectedY][selectedX] = new AbstractPiece(PieceType.EMPTY);
		System.out.println(pieceMovement(y, x));
		selected = null;
	    }
	}
	possibleMoves.clear();
	notifyListeners();
    }

    public String pieceMovement(int y, int x){
	return (selected.getPieceType().name()+" From: "+ getLetter(selectedX)+ (width-1-selectedY)+ " -> " + getLetter(x) + (height-1-y));
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
/*
	for (int x = 1; x < width-1; x++) {
	    cB[2][x] = new AbstractPiece(false, PieceType.PAWN);
	    cB[height-3][x] = new AbstractPiece(true, PieceType.PAWN);
	}
*/
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
		cB[y][x] = null;
	    }
	}
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

    public ArrayList<Point> getPossibleMoves() {
	return possibleMoves;
    }

    public boolean getActivePlayer() {
	return activePlayer;
    }
}