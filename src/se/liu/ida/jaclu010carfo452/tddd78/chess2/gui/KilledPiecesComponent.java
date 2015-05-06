package se.liu.ida.jaclu010carfo452.tddd78.chess2.gui;


import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessBoard;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessBoardListener;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.GlobalVars;

import javax.swing.*;
import java.awt.*;

/**
 * Component that displays killed pieces for the players
 * @author jaclu010, carfo452
 */
public class KilledPiecesComponent extends JComponent implements ChessBoardListener
{
    private ChessBoard cB;
    private int squareSide = GlobalVars.getsquareside();

    public KilledPiecesComponent(ChessBoard cB) {
	this.cB = cB;
    }

    @Override
    public void chessBoardChanged(){
	repaint();
    }

    @Override public Dimension getPreferredSize(){
	super.getPreferredSize();
	return new Dimension(3*GlobalVars.getsquareside(), GlobalVars.getHeight()*GlobalVars.getsquareside());
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	Iterable<ChessPiece> killedWhitePieces = cB.getKilledWhitePieces();
	Iterable<ChessPiece> killedBlackPieces = cB.getKilledBlackPieces();

	// Paints the killed white pieces
	if (killedWhitePieces != null) {
	    setFontAndTextForKilledPieces(g2d);
	    int i = 0;
	    for (ChessPiece killedWhitePiece : killedWhitePieces) {
		i += 1;
		g2d.drawImage(GlobalVars.getIMG(killedWhitePiece).getImage(), squareSide,squareSide/2*i, 2*squareSide/3, 2*squareSide/3, this);
	    }
	}
	// Paints the killed black pieces
	if (killedBlackPieces != null) {
	    setFontAndTextForKilledPieces(g2d);
	    int i = 0;
	    for (ChessPiece killedBlackPiece : killedBlackPieces) {
		i += 1;
		g2d.drawImage(GlobalVars.getIMG(killedBlackPiece).getImage(), squareSide*2,squareSide/2*i, 2*squareSide/3, 2*squareSide/3, this);
	    }
	}

	int whiteKills = cB.getKilledWhitePieces().size();
	int blackKills = cB.getKilledBlackPieces().size();
	g2d.drawString("White Kills: "+whiteKills+", Black Kills: "+blackKills, squareSide/5,squareSide*GlobalVars.getHeight()-squareSide);
    }


    private void setFontAndTextForKilledPieces(Graphics g2d){
	final int fontSize = 12;
	g2d.setFont(new Font("SansSerif", Font.BOLD, fontSize));
	g2d.drawString("Killed Pieces: ", squareSide/2, squareSide/2);
    }
}
