package chess2;

/**
 * Starts the game
 * @author jaclu010, carfo452
 */
public final class Main
{
    private Main() {}

    public static void main(String[] args) {
	ChessBoard cb = new ChessBoard();
	cb.fillBoard();
	ChessFrame cf = new ChessFrame(cb);
    }
}
