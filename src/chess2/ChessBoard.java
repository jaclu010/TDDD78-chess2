package chess2;

public class ChessBoard
{
    private final static int WIDTH = 8, HEIGHT = 8;
    private ChessPiece[][] board;

    public ChessBoard() {
	board = new ChessPiece[HEIGHT][WIDTH];
    }

    public void fillBoard(){

    }

    public static int getWidth() {
	return WIDTH;
    }

    public static int getHeight() {
	return HEIGHT;
    }
}
