package chess2;

import javax.swing.*;
import java.awt.*;

public class ChessComponent extends JComponent implements ChessBoardListener
{
    private ChessBoard cB;
    private static final int SQUARE_SIDE = 40;
    private static final int LETTER_COMP_Y = 23;
    private static final int LETTER_COMP_X = 16;
    private static final int CHAR_ADD = 64;

    public ChessComponent(final ChessBoard cB) {
	this.cB = cB;
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
	final Graphics2D g2 = (Graphics2D) g;

	for (int y = 0; y < ChessBoard.getHeight(); y++) {
	    for (int x = 0; x < ChessBoard.getWidth(); x++) {
		if(cB.getPiece(y,x) instanceof Outside){
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
	for (int y = 1; y < ChessBoard.getHeight()-1; y++) {
	    for (int x = 1; x < ChessBoard.getWidth()-1; x++) {
		if (cB.getPiece(y,x) != null || !(cB.getPiece(y, x) instanceof Outside)){
		    g2.setColor(cB.getPiece(y,x).getColor());
		    g2.fillOval(x*SQUARE_SIDE+10, y*SQUARE_SIDE+10, SQUARE_SIDE/2, SQUARE_SIDE/2);
		}

	    }
	}

    }

    public String getLetter(int y, int x){
	if ((x == 0 || x == ChessBoard.getWidth()-1)){
	    return Integer.toString(9-y);
	} else if ((y == 0 || y == ChessBoard.getHeight()-1)){
	    x += CHAR_ADD;
	    char a = (char) x;
	    return Character.toString(a);
	}
	return "";
    }

}
