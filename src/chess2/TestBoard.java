package chess2;

public final class TestBoard
{
    private TestBoard() {}

    public static void main(String[] args) {
	ChessBoard cb = new ChessBoard();
	cb.fillBoard();
	ChessFrame cf = new ChessFrame(cb);
    }
}
