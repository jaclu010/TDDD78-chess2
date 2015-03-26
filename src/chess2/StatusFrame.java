package chess2;

import javax.swing.*;
import java.awt.*;

public class StatusFrame extends JFrame
{
    private ChessBoard cB;
    public StatusFrame(ChessBoard cB) throws HeadlessException {
	this.cB = cB;
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


}
