package se.liu.ida.jaclu010carfo452.tddd78.chess2;

import javax.swing.*;
import java.awt.*;

/**
 * Prints the logs of events
 * @author jaclu010, carfo452
 */
public class LogComponent extends JTextArea implements ChessBoardListener
{
    private ChessBoard cB;
    private String latestMsg = "";

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
	String oldMsg = latestMsg;
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
	return new Dimension(GlobalVars.getWidth()*GlobalVars.getsquareside(), 2*GlobalVars.getsquareside());
    }

    public void clearLog(){
	this.setText("");
    }
}
