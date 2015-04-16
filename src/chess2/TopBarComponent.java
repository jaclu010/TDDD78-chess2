package chess2;

import javax.swing.*;
import java.awt.*;

/**
 * Displays active player and # of the turn
 * @author jaclu010, carfo452
 */
public class TopBarComponent extends JTextArea implements ChessBoardListener
{

    private ChessBoard cB;

    public TopBarComponent(ChessBoard cB) {
	this.cB = cB;
	this.setText("                                White players turn        Turn: " + cB.getTurn());

	final int fontSizeActivePlayerText = 30;
	this.setFont(new Font("Sans Serif", Font.PLAIN, fontSizeActivePlayerText));
	this.setBackground(Color.BLACK);
	this.setForeground(Color.WHITE);
	this.setEditable(false);
    }

    @Override
    public void chessBoardChanged(){
	changeActivePlayerTextArea();
    }

    private void changeActivePlayerTextArea(){
	if(cB.getActivePlayer()){
	    this.setText("                                White players turn        Turn: " + cB.getTurn());
	    this.setBackground(Color.BLACK);
	    this.setForeground(Color.WHITE);
	}else{
	    this.setText("                                Black players turn        Turn: " + cB.getTurn());
	    this.setBackground(Color.WHITE);
	    this.setForeground(Color.BLACK);
	}
    }
}
