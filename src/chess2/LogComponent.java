package chess2;

import javax.swing.*;
import java.awt.*;

public class LogComponent extends JTextArea implements ChessBoardListener
{
    private ChessBoard cB;
    private String latestMsg = "", oldMsg = "";

    public LogComponent(ChessBoard cB) {
	this.cB = cB;
	final int fontSize = 12;
	this.setFont(new Font("Sans Serif", Font.BOLD, fontSize));
	this.setEditable(false);
    }

    @Override
    public void chessBoardChanged(){
	if (cB.getTurn() == 1){
	    clearLog();
	}
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

    public void clearLog(){
	this.setText("");
    }
}
