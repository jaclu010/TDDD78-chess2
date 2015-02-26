package chess2;

public class ChessBoard
{
    private final static int WIDTH = 10, HEIGHT = 10;
    private ChessPiece[][] cB;

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

    public void fillBoard(){

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
