package chess2;

public class Board
{
    private final static int WIDTH = 8, HEIGHT = 8;
    private ChessPiece[][] board;

    public Board() {
	board = new ChessPiece[HEIGHT][WIDTH];
    }
}
