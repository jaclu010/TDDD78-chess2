package chess2;

import javax.swing.*;
import java.awt.*;

public class ChessComponent extends JComponent implements ChessBoardListener
{
    private ChessBoard chessBoard;
    private static final int SQUARE_SIDE = 40;

    public ChessComponent(final ChessBoard chessBoard) {
	this.chessBoard = chessBoard;
    }

    @Override
    public void chessBoardChanged(){
	repaint();
    }

    @Override public Dimension getPreferredSize(){
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final double percentOfScreenWidth = 0.35, percentOfScreenHeight = 0.7;
	int compWidth = (int)(screenSize.getWidth()* percentOfScreenWidth);
	int compHeight = (int)(screenSize.getHeight()*percentOfScreenHeight);
	Dimension preferredSize = new Dimension(compWidth, compHeight);
	return preferredSize;
    }
    
    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	for (int y = 0; y < ChessBoard.getHeight(); y++) {
	    for (int x = 0; x < ChessBoard.getWidth(); x++) {
		if ((y % 2 == 0 && x % 2 == 0) || (y % 2 == 1 && x % 2 == 1)){
		    g2d.setColor(Color.LIGHT_GRAY);
		} else {
		    g2d.setColor(Color.DARK_GRAY);
		}
		g2d.fill(new Rectangle(x*SQUARE_SIDE, y*SQUARE_SIDE, SQUARE_SIDE, SQUARE_SIDE));
	    }
	}
    }
}
