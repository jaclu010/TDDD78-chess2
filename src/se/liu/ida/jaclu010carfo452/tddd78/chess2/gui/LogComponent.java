package se.liu.ida.jaclu010carfo452.tddd78.chess2.gui;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessBoard;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessBoardListener;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.GlobalVars;

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
	super.getPreferredScrollableViewportSize();
	return new Dimension(GlobalVars.getWidth()*GlobalVars.getsquareside(), 2*GlobalVars.getsquareside());
    }

    public void clearLog(){
	this.setText("");
    }
}
