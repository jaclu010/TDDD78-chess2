package chess2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel implements ChessBoardListener
{
    private ChessBoard cB;
    private final JButton useAbility = new JButton("Use ability");
    private final JButton showPossible = new JButton("Switch move/ability");

    public StatusPanel(ChessBoard cB) throws HeadlessException {
	this.cB = cB;

	final JPanel buttonPanel = new JPanel();
	final JButton lvlupSelected = new JButton("Upgrade selected");
	final JButton changePlayer = new JButton("Change player");
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
	final StatusComponent statusArea = new StatusComponent(cB);

	cB.addChessBoardListener(statusArea);


	buttonPanel.add(lvlupSelected);
	buttonPanel.add(showPossible);
	buttonPanel.add(changePlayer);
	buttonPanel.add(useAbility);

	this.add(statusArea);
	this.add(buttonPanel);
	//this.setLayout(new BorderLayout());
	//this.add(buttonPanel, BorderLayout.SOUTH);

	useAbility.setVisible(false);
	this.setVisible(true);

	ButtonAction showAction = new ButtonAction(showPossible);
	ButtonAction changePAction = new ButtonAction(changePlayer);
	ButtonAction abilityAction = new ButtonAction(useAbility);
	ButtonAction lvlUpAction = new ButtonAction(lvlupSelected);

	showPossible.addActionListener(showAction);
	changePlayer.addActionListener(changePAction);
	useAbility.addActionListener(abilityAction);
	lvlupSelected.addActionListener(lvlUpAction);
    }

    public void chessBoardChanged(){
	ChessPiece selected = cB.getSelected();

	if(selected != null && selected.getPieceType() != PieceType.KING){
	    useAbility.setVisible(false);
	} else if (selected != null){
	    useAbility.setVisible(true);
	}
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

	} else if (button.getText().equals("Change player")){
	    cB.changeActivePlayer();

	} else if (button.getText().equals("Switch move/ability")){
	    if(cB.getSelected() != null) {
		if (GlobalVars.isShowRegularMoves()) {
		    GlobalVars.setShowRegularMoves(false);
		    GlobalVars.setShowAbilityMoves(true);
		} else {

		    GlobalVars.setShowRegularMoves(true);
		    GlobalVars.setShowAbilityMoves(false);
		}
		cB.checkRules();
	    }
	}
    }


}



}
