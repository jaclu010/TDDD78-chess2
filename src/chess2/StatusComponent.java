package chess2;

import javax.swing.*;
import java.awt.*;

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
	return new Dimension(3*squareSide, squareSide*7);
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	ChessPiece selected = cB.getSelected();


	if (selected != null) {
	    Ability selectedAbility = selected.getAbility();
	    final int fontSize = 12;
	    g2d.setFont(new Font("SansSerif", Font.BOLD, fontSize));
	    g2d.drawString("Selected Piece: ", squareSide/2, squareSide);


	    g2d.drawImage((GlobalVars.getIMG(selected)).getImage(), 2*squareSide,squareSide/2, squareSide, squareSide, this);


	    // Shows the health symbol and healthpoints
	    g2d.drawImage((GlobalVars.getIMG("hp")).getImage(), squareSide/2,squareSide*2, squareSide/2, squareSide/2, this);

	    g2d.drawString("    HP: " + selected.getHP(), squareSide,squareSide*2+squareSide/3);

	    // Shows the ability symbol and abiltypoints
	    g2d.drawImage((GlobalVars.getIMG("lvl")).getImage(), squareSide/2,squareSide*2+squareSide/2, squareSide/2, squareSide/2, this);

	    g2d.drawString("    AP: " + selected.getaP(), squareSide,squareSide*2+squareSide/2+squareSide/3);

	    if(cB.getFrozenPieces().contains(selected)){

		g2d.drawImage((GlobalVars.getIMG("frozen")).getImage(), squareSide/2,squareSide*2+squareSide, squareSide/2, squareSide/2, this);

		g2d.drawString("    Frozen for: " + selected.getFreezeTime() + " turns", squareSide,squareSide*2+squareSide/2+squareSide/3+squareSide/2);
	    }

	    g2d.drawImage(GlobalVars.getIMG(selectedAbility.getAT().name()).getImage(), squareSide, squareSide*4, squareSide, squareSide, this);
	    g2d.drawString("Ability: " + selectedAbility.getAT().name(), squareSide / 2, squareSide * 5 + (squareSide / 5));
	    g2d.drawString("Cost: "+selectedAbility.getCost(),squareSide / 2, squareSide * 5 + (squareSide / 5)*2);
	    g2d.drawString("Damage: "+selectedAbility.getDmg(),squareSide / 2, squareSide * 5 + (squareSide / 5)*3);
	    g2d.drawString("Heal: "+selectedAbility.getHeal(),squareSide / 2, squareSide * 5 + (squareSide / 5)*4);
	    g2d.drawString("Freeze time: " + selectedAbility.getFreezeTime(),squareSide / 2, squareSide * 5 + (squareSide / 5)*5);
	    g2d.drawString("Knockback: " + selectedAbility.getKnockBack(),squareSide / 2, squareSide * 5 + (squareSide / 5)*6);
	    if(selected.getPieceType() == PieceType.KING) {
		g2d.drawString("'Use Ability' to use",squareSide / 2, squareSide * 5 + (squareSide / 5)*7);
	    }
	}

    }
}
