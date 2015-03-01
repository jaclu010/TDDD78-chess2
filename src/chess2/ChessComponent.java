package chess2;

import javax.swing.*;
import java.awt.*;

public class ChessComponent extends JComponent implements ChessBoardListener
{
    private ChessBoard cb;
    private static final int SQUARE_SIDE = 40;
    private static final int LETTER_COMP_Y = 23;
    private static final int LETTER_COMP_X = 16;

    public ChessComponent(final ChessBoard cb) {
	this.cb = cb;
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
		if(cb.getPiece(y,x) != null){
		    g2d.setColor(Color.BLACK);
		    g2d.fill(new Rectangle(x*SQUARE_SIDE, y*SQUARE_SIDE, SQUARE_SIDE, SQUARE_SIDE));
		    g2d.setColor(Color.WHITE);
		    g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
		    if ((y != 0 && y != ChessBoard.getHeight()-1) || (x != 0 && x != ChessBoard.getWidth()-1)) {
			g2d.drawString(getLetter(y, x), x * SQUARE_SIDE + LETTER_COMP_X, y * SQUARE_SIDE + LETTER_COMP_Y);
		    }
		}else if ((y % 2 == 0 && x % 2 == 0) || (y % 2 == 1 && x % 2 == 1)){
		    g2d.setColor(Color.LIGHT_GRAY);
		    g2d.fill(new Rectangle(x*SQUARE_SIDE, y*SQUARE_SIDE, SQUARE_SIDE, SQUARE_SIDE));
		} else {
		    g2d.setColor(Color.DARK_GRAY);
		    g2d.fill(new Rectangle(x*SQUARE_SIDE, y*SQUARE_SIDE, SQUARE_SIDE, SQUARE_SIDE));
		}

	    }
	}
    }

    public String getLetter(int y, int x){
	if ((y == 0 || y == ChessBoard.getHeight()-1)){
	    return Integer.toString(x);
	} else if ((x == 0 || x == ChessBoard.getWidth()-1)){
	    y += 64;
	    char a = (char) y;
	    return Character.toString(a);
	}

	return "L";
    }

}
