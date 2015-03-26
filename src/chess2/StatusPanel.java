package chess2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel
{
    private ChessBoard cB;

    public StatusPanel(ChessBoard cB) throws HeadlessException {
	this.cB = cB;

	final JPanel buttonPanel = new JPanel(new BorderLayout());
	final JButton useAbility = new JButton("Use ability");
	final JButton lvlupSelected = new JButton("Upgrade selected");
	final StatusComponent statusArea = new StatusComponent(cB);
	cB.addChessBoardListener(statusArea);

	buttonPanel.add(lvlupSelected, BorderLayout.NORTH);
	buttonPanel.add(useAbility, BorderLayout.CENTER);

	this.add(statusArea);
	this.add(buttonPanel);
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

    public void actionPerformed(ActionEvent e){
	if (button.getText().equals("Upgrade selected")){
	    if(cB.getSelected() != null) {
		cB.getSelected().setaP(5);
		cB.notifyListeners();
	    }


	} else if (button.getText().equals("Use ability")){

	}
    }


}



}
