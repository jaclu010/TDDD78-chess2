package chess2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class KilledPiecesComponent extends JComponent implements ChessBoardListener
{

    private ChessBoard cB;
    private int squareSide = GlobalVars.getSquareSide();
    private String guiTheme = GlobalVars.getGuiTheme();


    public KilledPiecesComponent(ChessBoard cB) {
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

	Iterable<ChessPiece> killedWhitePieces = cB.getKilledWhitePieces();
	Iterable<ChessPiece> killedBlackPieces = cB.getKilledBlackPieces();

	if (killedWhitePieces != null) {
	    final int fontSize = 12;
	    g2d.setFont(new Font("SansSerif", Font.BOLD, fontSize));
	    g2d.drawString("Killed Pieces: ", squareSide/2, squareSide/2);
	    int i = 0;
	    for (ChessPiece killedWhitePiece : killedWhitePieces) {
		i += 1;
		try {
		    URL url = this.getClass().getResource("/"+GlobalVars.getGuiTheme()+"/" + GlobalVars.imgPicker(killedWhitePiece) + ".png");
		    final BufferedImage image = ImageIO.read(url);
		    g2d.drawImage(image, squareSide,squareSide/2*i, squareSide/3, squareSide/3, this);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	if (killedBlackPieces != null) {
	    final int fontSize = 12;
	    g2d.setFont(new Font("SansSerif", Font.BOLD, fontSize));
	    g2d.drawString("Killed Pieces: ", squareSide/2, squareSide/2);
	    int i = 0;
	    for (ChessPiece killedBlackPiece : killedBlackPieces) {
		i += 1;
		try {
		    URL url = this.getClass().getResource("/"+GlobalVars.getGuiTheme()+"/" + GlobalVars.imgPicker(killedBlackPiece) + ".png");
		    final BufferedImage image = ImageIO.read(url);
		    g2d.drawImage(image, squareSide*2,squareSide/2*i, squareSide/3, squareSide/3, this);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}

	int whiteKills = cB.getKilledWhitePieces().size();
	int blackKills = cB.getKilledBlackPieces().size();
	g2d.drawString("White Kills: "+whiteKills+", Black Kills: "+blackKills, squareSide/5,squareSide*GlobalVars.getHeight()-squareSide);
    }
}
