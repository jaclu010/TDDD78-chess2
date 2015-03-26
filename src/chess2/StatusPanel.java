package chess2;


import javafx.event.ActionEvent;

import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel
{
    private ChessBoard cB;

    public StatusPanel(ChessBoard cB) throws HeadlessException {
	this.cB = cB;


	final JButton useAbility = new JButton("UseAbility");
	final JButton lvlupSelected = new JButton("Upgrade selected");
	final StatusComponent statusArea = new StatusComponent(cB);
	cB.addChessBoardListener(statusArea);

	this.add(lvlupSelected);
	this.add(useAbility);
	this.add(statusArea);

	//this.setLayout(new BorderLayout());

	//this.add(buttonPanel, BorderLayout.SOUTH);

	this.setVisible(true);

	ButtonAction abilityAction = new ButtonAction(useAbility);
	ButtonAction lvlUpAction = new ButtonAction(lvlupSelected);

	useAbility.addActionListener(abilityAction);
	lvlupSelected.addActionListener(lvlUpAction);

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

//    public void buttonActions(ActionEvent e

private final class ButtonAction implements ActionListener
{
    private JButton button;
    private ButtonAction(JButton b)
    {
	button = b;
    }
    public void actionPerformed(java.awt.event.ActionEvent e){
	if (button.getText().equals("Upgrade selected")){
		cB.getSelected().setaP(5);



	} else {

	}
    }


}



}
