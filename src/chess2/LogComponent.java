package chess2;

import javax.swing.*;
import java.awt.*;

public class LogComponent extends JTextArea implements ChessBoardListener
{
    private ChessBoard cB;
    private int squareSide = GlobalVars.getSquareSide();
    private String latestMsg = "", oldMsg = "";

    public LogComponent(ChessBoard cB) {
	this.cB = cB;
	this.setFont(new Font("Sans Serif", Font.BOLD, 12));
	this.setEditable(false);
    }

    @Override
    public void chessBoardChanged(){
	oldMsg = latestMsg;
	latestMsg = cB.getLogMsg();
	if (!oldMsg.equals(latestMsg)){
	    this.append(latestMsg+"\n");
	}
    }

    @Override public Dimension getPreferredScrollableViewportSize(){
	/*
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final double percentOfScreenWidth = 0.35, percentOfScreenHeight = 0.7;
	int compWidth = (int)(screenSize.getWidth()* percentOfScreenWidth);
	int compHeight = (int)(screenSize.getHeight()*percentOfScreenHeight);
	Dimension preferredSize = new Dimension(compWidth, compHeight);
	return preferredSize;*/
	return new Dimension(GlobalVars.getWidth()*GlobalVars.getSquareSide(), 2*GlobalVars.getSquareSide());
    }

}
