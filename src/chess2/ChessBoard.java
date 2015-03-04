package chess2;

import chess2.Pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.MouseEvent;

public class ChessBoard
{
    private final static int WIDTH = 10, HEIGHT = 10;
    private ChessPiece[][] cB;
    private ChessPiece selected, wantToMoveTo;
    private int selectedX,selectedY;
    private Random rnd = new Random();
    private List<ChessBoardListener> chessBoardListeners = new ArrayList<ChessBoardListener>();


    public ChessBoard() {
	cB = new ChessPiece[HEIGHT][WIDTH];

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if ((y == 0 || y == HEIGHT-1) || (x == 0 || x == WIDTH-1)){
                    cB[y][x] = new Outside();
                }else{
                    cB[y][x] = new Empty();
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
        int mouseY = e.getY()/ChessComponent.getSquareSide();
        int mouseX = e.getX()/ChessComponent.getSquareSide();
        System.out.println(mouseY +" - " + mouseX);
        if (mouseY < HEIGHT && mouseX < WIDTH) {
            if (!(cB[mouseY][mouseX] instanceof Empty) && !(cB[mouseY][mouseX] instanceof Outside)) {
                selected = cB[mouseY][mouseX];
                selectedX = mouseX;
                selectedY = mouseY;
                System.out.println(selected);
            } else if (selected != null) {
                movePiece(mouseY, mouseX);
            }
        }
        notifyListeners();
    }


 /*   private ChessPiece[][] cB;

    public ChessBoard() {
	cB = new ChessPiece[HEIGHT][WIDTH];

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if ((y == 0 || y == HEIGHT-1) || (x == 0 || x == WIDTH-1)){
                    cB[y][x] = PieceConstructor.getPiece(PieceType.OUTSIDE);
                }
            }
        }
    }
*/
    public void movePiece(int y, int x){
        wantToMoveTo = cB[y][x];
        if (wantToMoveTo instanceof Empty){
            cB[y][x] = selected;
            cB[selectedY][selectedX] = new Empty();
            selected = null;
        }
        notifyListeners();
    }

    public void fillBoard(){
        /*for (int y = 1; y < HEIGHT-1; y++) {
            for (int x = 1; x < WIDTH-1; x++) {
                cB[y][x] = randPiece(rnd.nextInt(7));
                cB[y][x].setY(y);
                cB[y][x].setX(x);
            }
        }*/
        //False = black piece
        //Adds the pawns at right position
        for (int x = 1; x < WIDTH-1; x++) {
            cB[2][x] = new Pawn(false);
            cB[HEIGHT-3][x] = new Pawn(true);
        }
        // Adds all black pieces
        cB[1][1] = new Rook(false);
        cB[1][2] = new Knight(false);
        cB[1][3] = new Bishop(false);
        cB[1][4] = new King(false);
        cB[1][5] = new Queen(false);
        cB[1][6] = new Bishop(false);
        cB[1][7] = new Knight(false);
        cB[1][8] = new Rook(false);

        // Adds all white pieces
        cB[HEIGHT-2][1] = new Rook(true);
        cB[HEIGHT-2][2] = new Knight(true);
        cB[HEIGHT-2][3] = new Bishop(true);
        cB[HEIGHT-2][4] = new Queen(true);
        cB[HEIGHT-2][5] = new King(true);
        cB[HEIGHT-2][6] = new Bishop(true);
        cB[HEIGHT-2][7] = new Knight(true);
        cB[HEIGHT-2][8] = new Rook(true);
    }
    public void clearBoard() {
        for (int y = 1; y < HEIGHT-1; y++) {
            for (int x = 1; x < WIDTH-1; x++) {
                cB[y][x] = new Empty();
            }
        }
        fillBoard();
        notifyListeners();
    }

    public ChessPiece randPiece(int n){
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

    public static int getWidth() {
	return WIDTH;
    }

    public static int getHeight() {
	return HEIGHT;
    }

    public ChessPiece getPiece(final int y, final int x){
        return cB[y][x];
    }

    public ChessPiece getSelected() {
        return selected;
    }
}
