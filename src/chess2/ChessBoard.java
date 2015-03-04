package chess2;

import chess2.Pieces.Bishop;
import chess2.Pieces.Empty;
import chess2.Pieces.King;
import chess2.Pieces.Knight;
import chess2.Pieces.Outside;
import chess2.Pieces.Pawn;
import chess2.Pieces.Queen;
import chess2.Pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.MouseEvent;

public class ChessBoard
{
    private final static int WIDTH = 10, HEIGHT = 10;
    private ChessPiece[][] cB;
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

    }
    
    public void moveChessPiece(final int xFrom, final int yFrom, final int xTo, final int yTo){
        ChessPiece chessPieceToMove = cB[yFrom][xFrom];
        cB[yFrom][xFrom] = new Empty();
        cB[yTo][xTo] = chessPieceToMove;
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
    public void fillBoard(){
        for (int y = 1; y < HEIGHT-1; y++) {
            for (int x = 1; x < WIDTH-1; x++) {
                cB[y][x] = randPiece(rnd.nextInt(7));
            }
        }
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
}
