package se.liu.ida.jaclu010carfo452.tddd78.chess2;

/**
 * Starts the game
 * @author jaclu010, carfo452
 */
public final class Main
{
    public static void main(String[] args) {
	ChessBoard cb = new ChessBoard();
	cb.fillBoard();
	ChessFrame cf = new ChessFrame(cb);
    }
}
