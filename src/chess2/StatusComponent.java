package chess2;

import javax.swing.*;
import java.awt.*;

public class StatusComponent extends JComponent implements ChessBoardListener
{
    private ChessBoard cB;
    private String guiTheme = GlobalVars.getGuiTheme();
    private int squareSide = GlobalVars.getSquareSide();

    public StatusComponent(ChessBoard cB) {
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
	return new Dimension(3*GlobalVars.getSquareSide(), GlobalVars.getHeight()*GlobalVars.getSquareSide()/2);
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	if (cB.getSelected() != null) {
	    final int fontSize = 12;
	    g2d.setFont(new Font("SansSerif", Font.BOLD, fontSize));
	    g2d.drawString("Selected Piece: ", squareSide/2, squareSide);


	    g2d.drawImage((GlobalVars.getIMG(cB.getSelected())).getImage(), 2*squareSide,squareSide/2, squareSide, squareSide, this);


	    // Shows the health symbol and healthpoints
	    g2d.drawImage((GlobalVars.getIMG("hp")).getImage(), squareSide/2,squareSide*2, squareSide/2, squareSide/2, this);

	    g2d.drawString("    HP: " + cB.getSelected().getHP(), squareSide,squareSide*2+squareSide/3);

	    // Shows the ability symbol and abiltypoints
	    g2d.drawImage((GlobalVars.getIMG("lvl")).getImage(), squareSide/2,squareSide*2+squareSide/2, squareSide/2, squareSide/2, this);

	    g2d.drawString("    AP: " + cB.getSelected().getaP(), squareSide,squareSide*2+squareSide/2+squareSide/3);

	    if(cB.getFrozenPieces().contains(cB.getSelected())){

		g2d.drawImage((GlobalVars.getIMG("frozen")).getImage(), squareSide/2,squareSide*2+squareSide, squareSide/2, squareSide/2, this);

		g2d.drawString("    Frozen for: " + cB.getSelected().getFreezeTime() + " turns", squareSide,squareSide*2+squareSide/2+squareSide/3+squareSide/2);
	    }
	}

    }
}
