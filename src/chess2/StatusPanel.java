package chess2;

import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class StatusPanel extends JPanel implements ChessBoardListener
{
    private ChessBoard cB;
    private final JButton useAbility = new JButton("Use ability");


    public StatusPanel(ChessBoard cB) throws HeadlessException {
	this.cB = cB;

	final JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

	final JButton lvlupSelected = new JButton("Upgrade selected");
	final JButton changePlayer = new JButton("Change player");
 	final JButton showPossible = new JButton("Switch move/abilit");
 	final JButton changeGUI = new JButton("Switch GUI");


	final StatusComponent statusArea = new StatusComponent(cB);

	cB.addChessBoardListener(statusArea);

	buttonPanel.add(changeGUI);
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

	final ActionListener buttonListener = event -> {
	    if (event.getSource().equals(lvlupSelected)){
	   	    if(cB.getSelected() != null) {
	   		cB.getSelected().setaP(5);
	   		cB.notifyListeners();
	   	    }
	   	} else if (event.getSource().equals(useAbility)){
			cB.spawnProtectionForKing();
	   	} else if (event.getSource().equals(changePlayer)){
	   	    	cB.changeActivePlayer();
	   	} else if (event.getSource().equals(showPossible)){
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
	   	} else if (event.getSource().equals(changeGUI)){
			if (Objects.equals(GlobalVars.getGuiTheme(), "troll")){
			    GlobalVars.setGuiTheme("original");
			} else {
			    GlobalVars.setGuiTheme("troll");
			}
			cB.notifyListeners();
	    }
	};

	changeGUI.addActionListener(buttonListener);
	showPossible.addActionListener(buttonListener);
	changePlayer.addActionListener(buttonListener);
	useAbility.addActionListener(buttonListener);
	lvlupSelected.addActionListener(buttonListener);
    }

    public void chessBoardChanged(){
	ChessPiece selected = cB.getSelected();

	if(selected != null && selected.getPieceType() == PieceType.KING && GlobalVars.isShowAbilityMoves()){
	    useAbility.setVisible(true);
	} else {
	    useAbility.setVisible(false);
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
}
