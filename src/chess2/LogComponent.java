package chess2;

import javax.swing.*;
import java.awt.*;

public class LogComponent extends JComponent implements TextLogListener
{
    private ChessBoard cB;
    private int squareSide = GlobalVars.getSquareSide();
    private String latestMsg = "";

    public LogComponent(ChessBoard cB) {
	this.cB = cB;

    }

    @Override
    public void textLogChanged(String text){
	latestMsg = text;
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
	return new Dimension(GlobalVars.getWidth()*GlobalVars.getSquareSide(), 2*GlobalVars.getSquareSide());
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
	g2d.drawString(latestMsg, 0,0);
    }
}
