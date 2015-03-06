package chess2;

import chess2.pieces.Outside;
import jdk.nashorn.internal.objects.Global;

import javax.swing.*;
import java.awt.*;

public class ChessComponent extends JComponent implements ChessBoardListener
{
    private ChessBoard cB;
    private static final int LETTER_COMP_Y = GlobalVars.getSquareSide()/2 +4;
    private static final int LETTER_COMP_X = GlobalVars.getSquareSide()/2 -4;

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
	//final Graphics2D g2 = (Graphics2D) g;

	int squareSide = GlobalVars.getSquareSide();


	for (int y = 0; y < GlobalVars.getHeight(); y++) {
	    for (int x = 0; x < GlobalVars.getWidth(); x++) {
		if(cB.getPiece(y,x) instanceof Outside){
		    g2d.setColor(Color.BLACK);
		    g2d.fill(new Rectangle(x*squareSide, y*squareSide, squareSide, squareSide));
		    g2d.setColor(Color.WHITE);
		    g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
		    if ((y != 0 && y != GlobalVars.getHeight()-1) || (x != 0 && x != GlobalVars.getWidth()-1)) {
			g2d.drawString(getLetter(y, x), x * squareSide + LETTER_COMP_X, y * squareSide + LETTER_COMP_Y);
		    }

		}else if ((y % 2 == 0 && x % 2 == 0) || (y % 2 == 1 && x % 2 == 1)){
		    g2d.setColor(Color.LIGHT_GRAY);
		    g2d.fill(new Rectangle(x*squareSide, y*squareSide, squareSide, squareSide));
		} else {
		    g2d.setColor(Color.DARK_GRAY);
		    g2d.fill(new Rectangle(x*squareSide, y*squareSide, squareSide, squareSide));
		}
	    }
	}
	for (int y = 1; y < GlobalVars.getHeight()-1; y++) {
	    for (int x = 1; x < GlobalVars.getWidth()-1; x++) {
		if (cB.getPiece(y,x) != null) {
		    if (!(cB.getPiece(y, x) instanceof Outside)) {
			g2d.setColor(cB.getPiece(y, x).getColor());
			g2d.fillOval(x * squareSide + squareSide / 4, y * squareSide + squareSide / 4, squareSide / 2,
				     squareSide / 2);
		    }
		    if (!cB.getPiece(y, x).getPlayer()) {
			g2d.setColor(new Color(0, 0, 0, 150));
			g2d.fillOval(x * squareSide + squareSide / 4, y * squareSide + squareSide / 4, squareSide / 2,
				     squareSide / 2);
		    }
		    if ((cB.getPiece(y, x)).equals(cB.getSelected())) {
			g2d.setColor(new Color(255, 255, 255, 200));
			g2d.fillOval(x * squareSide + squareSide / 4, y * squareSide + squareSide / 4, squareSide / 2,
				     squareSide / 2);
		    }
		}

	    }
	}

    }

    public String getLetter(int y, int x){
	if ((x == 0 || x == GlobalVars.getWidth()-1)){
	    return Integer.toString(9-y);
	} else if ((y == 0 || y == GlobalVars.getHeight()-1)){
	    x += GlobalVars.getCharAdd();
	    char a = (char) x;
	    return Character.toString(a);
	}
	return "";
    }
}
