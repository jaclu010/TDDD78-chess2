package chess2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class StatusComponent extends JComponent implements ChessBoardListener
{
    private ChessBoard cB;
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
	return new Dimension(3*GlobalVars.getSquareSide(), GlobalVars.getHeight()*GlobalVars.getSquareSide());
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	if (cB.getSelected() != null) {
	    final int fontSize = 12;
	    g2d.setFont(new Font("SansSerif", Font.BOLD, fontSize));
	    g2d.drawString("Selected Piece: ", squareSide/2, squareSide);

	    try {
		URL url = this.getClass().getResource("/resources/" + GlobalVars.imgPicker(cB.getSelected()) + ".png");
		final BufferedImage image = ImageIO.read(url);
		g2d.drawImage(image, 2*squareSide,squareSide/2, squareSide, squareSide, this);
	    } catch (IOException e) {
		e.printStackTrace();
	    }

	    try {
		URL url = this.getClass().getResource("/resources/hp.png");
		final BufferedImage image = ImageIO.read(url);
		g2d.drawImage(image, squareSide/2,squareSide*2, squareSide/3, squareSide/3, this);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    g2d.drawString("HP: " + cB.getSelected().getHP(), squareSide,squareSide*2+squareSide/5);

	    try {
		URL url = this.getClass().getResource("/resources/lvl.png");
		final BufferedImage image = ImageIO.read(url);
		g2d.drawImage(image, squareSide/2,squareSide*2+squareSide/2, squareSide/3, squareSide/3, this);
	    } catch (IOException e) {
		e.printStackTrace();
	    }

	    g2d.drawString("LVL: " + cB.getSelected().getLvl(), squareSide,squareSide*2+squareSide/2+squareSide/4);
	}

    }
}
