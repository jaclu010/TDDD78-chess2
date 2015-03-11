package chess2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.awt.image.BufferedImage;

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
	/*
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final double percentOfScreenWidth = 0.35, percentOfScreenHeight = 0.7;
	int compWidth = (int)(screenSize.getWidth()* percentOfScreenWidth);
	int compHeight = (int)(screenSize.getHeight()*percentOfScreenHeight);
	Dimension preferredSize = new Dimension(compWidth, compHeight);
	return preferredSize;*/
	return new Dimension(GlobalVars.getWidth()*GlobalVars.getSquareSide(), GlobalVars.getHeight()*GlobalVars.getSquareSide());
    }
    
    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	//final Graphics2D g2 = (Graphics2D) g;

	int squareSide = GlobalVars.getSquareSide();
	Iterable<Point> possibleMoves = cB.getPossibleMoves();

	// Paints the emptyBoard
	for (int y = 0; y < GlobalVars.getHeight(); y++) {
	    for (int x = 0; x < GlobalVars.getWidth(); x++) {
		if(cB.getPiece(y,x).getPieceType() == PieceType.OUTSIDE){
		    g2d.setColor(Color.BLACK);
		    g2d.fill(new Rectangle(x*squareSide, y*squareSide, squareSide, squareSide));
		    g2d.setColor(Color.WHITE);
		    final int fontSizeSideLetter = 12;
		    g2d.setFont(new Font("SansSerif", Font.BOLD, fontSizeSideLetter));
		    if ((y != 0 && y != GlobalVars.getHeight()-1) || (x != 0 && x != GlobalVars.getWidth()-1)) {
			g2d.drawString(getLetter(y, x), x * squareSide + LETTER_COMP_X, y * squareSide + LETTER_COMP_Y);
		    }

		}else if ((Math.abs(y) % 2 == 0 && Math.abs(x) % 2 == 0) || (y % 2 != 0 && x % 2 != 0)){
		    g2d.setColor(Color.LIGHT_GRAY);
		    g2d.fill(new Rectangle(x*squareSide, y*squareSide, squareSide, squareSide));
		} else {
		    g2d.setColor(Color.DARK_GRAY);
		    g2d.fill(new Rectangle(x*squareSide, y*squareSide, squareSide, squareSide));
		}
	    }
	}

	// Paints the chesspieces
	for (int y = 1; y < GlobalVars.getHeight()-1; y++) {
	    for (int x = 1; x < GlobalVars.getWidth()-1; x++) {
		if (cB.getPiece(y, x).getPieceType() != PieceType.EMPTY) {
		    if (cB.getPiece(y, x).getPieceType() != PieceType.OUTSIDE) {
			try {
			    URL url = this.getClass().getResource("/resources/" +GlobalVars.imgPicker(cB.getPiece(y,x))+".png");
			    final BufferedImage image = ImageIO.read(url);
			    g2d.drawImage(image, x*squareSide, y*squareSide, squareSide, squareSide, this);
			} catch (IOException e) {
			    e.printStackTrace();
			}

			// Creates the healthbars
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fill(new Rectangle(squareSide*x+squareSide/10, squareSide*y+squareSide-squareSide/6, squareSide-squareSide/7, squareSide/9));
			g2d.setColor(Color.RED);
			g2d.fill(new Rectangle(squareSide*x+squareSide/10, squareSide*y+squareSide-squareSide/6, ((squareSide-squareSide/7)/5)*cB.getPiece(y, x).getHP(), squareSide/9));
		    }
		    // Makes the selected piece red
		    if ((cB.getPiece(y, x)).equals(cB.getSelected())) {
			g2d.setColor(new Color(255, 50, 50, 90));
			g2d.fill(new Rectangle(x * squareSide, y * squareSide, squareSide, squareSide));
		    }
		}
	    }
	}
	// Colors the possible places for the selected piece to move
	g2d.setColor(new Color(0, 255, 0, 100));
	if (possibleMoves != null) {
	    for (Point possibleMove : possibleMoves) {
		g2d.fill(new Rectangle(possibleMove.getX()*GlobalVars.getSquareSide(), possibleMove.getY()*GlobalVars.getSquareSide(), squareSide, squareSide));
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
