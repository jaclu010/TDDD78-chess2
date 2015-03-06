package chess2;

import chess2.pieces.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.MouseEvent;
public class ChessBoard
{
    private boolean activePlayer;
    private Piece[][] cB;
    private Piece selected;
    private int selectedX, selectedY, height, width;
    private Random rnd = new Random();
    private List<ChessBoardListener> chessBoardListeners = new ArrayList<ChessBoardListener>();
    private ArrayList<Point> possibleMoves = new ArrayList<Point>();

    public ChessBoard() {
	this.height = GlobalVars.getHeight();
	this.width = GlobalVars.getWidth();
	this.cB = new Piece[height][width];
	this.activePlayer = true;
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		if ((y == 0 || y == height-1) || (x == 0 || x == width -1)){
		    cB[y][x] = new Outside();
		}
	    }
	}
    }

    public void addChessBoardListener(ChessBoardListener cBL){
	chessBoardListeners.add(cBL);
    }

    public void notifyListeners(){
	for (ChessBoardListener cBL : chessBoardListeners) {
	    cBL.chessBoardChanged();
	}
    }

    public void checkMouseClick(MouseEvent e){
	int mouseY = e.getY()/GlobalVars.getSquareSide();
	int mouseX = e.getX()/GlobalVars.getSquareSide();
	if (mouseY < height && mouseX < width && !(cB[mouseY][mouseX] instanceof Outside)) {
	    testMovement(mouseY, mouseX);
	}
    }

    public void testMovement(int mouseY, int mouseX){
	if (selected == null && cB[mouseY][mouseX] != null) {
	    selected = cB[mouseY][mouseX];
	    selectedX = mouseX;
	    selectedY = mouseY;
	    checkByRules();
	} else if (selected != null && cB[mouseY][mouseX] == null){
	    checkByRules();
	    movePiece(mouseY, mouseX);

	} else if (selected != null && cB[mouseY][mouseX].getPlayer() == selected.getPlayer()) {
	    selected = cB[mouseY][mouseX];
	    selectedX = mouseX;
	    selectedY = mouseY;
	    checkByRules();
	} else if (cB[mouseY][mouseX] == null){

	} else {
	    checkByRules();
	    movePiece(mouseY, mouseX);
	}
	notifyListeners();
    }

    public void checkByRules(){
	possibleMoves.clear();
	for (Rule rule : selected.fetchRules()) {
	    int y = selectedY+rule.getPoint().getY();
	    int x = selectedX+rule.getPoint().getX();
	    if (y < height && x < width){ //&& !(cB[y][x] instanceof Outside) && cB[y][x].getPlayer() != selected.getPlayer()){
		possibleMoves.add(new Point(y, x));
	    }
	}
    }

    public void movePiece(int y, int x){
	for (Point possibleMove : possibleMoves) {
	    if (possibleMove.getY() == y && possibleMove.getX() == x){
		cB[y][x] = selected;
		cB[selectedY][selectedX] = null;
		System.out.println(pieceMovement(y, x));
		selected = null;
	    }
	}
	possibleMoves.clear();
	notifyListeners();

    }

    public String pieceMovement(int y, int x){
	return (selected.getClass().getSimpleName()+" From: "+ getLetter(selectedX)+ (width-1-selectedY)+ " -> " + getLetter(x) + (height-1-y));
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

	//for (int x = 1; x < width-1; x++) {
	//    cB[2][x] = new Pawn(false);
	//cB[height-3][x] = new Pawn(true);
    //}

    // Adds all black pieces
	cB[1][1] = new Rook(false);
	cB[1][2] = new Knight(false);
	cB[1][3] = new Bishop(false);
	cB[1][4] = new Queen(false);
	cB[1][5] = new King(false);
	cB[1][6] = new Bishop(false);
	cB[1][7] = new Knight(false);
	cB[1][8] = new Rook(false);

    // Adds all white pieces
	cB[height-2][1] = new Rook(true);
	cB[height-2][2] = new Knight(true);
	cB[height-2][3] = new Bishop(true);
	cB[height-2][4] = new Queen(true);
	cB[height-2][5] = new King(true);
	cB[height-2][6] = new Bishop(true);
	cB[height-2][7] = new Knight(true);
	cB[height-2][8] = new Rook(true);
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

    public Piece getPiece(final int y, final int x){
	return cB[y][x];
    }

    public Piece getSelected() {
	return selected;
    }

    public ArrayList<Point> getPossibleMoves() {
	return possibleMoves;
    }
}