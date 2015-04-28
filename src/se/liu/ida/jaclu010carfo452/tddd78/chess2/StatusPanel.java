package se.liu.ida.jaclu010carfo452.tddd78.chess2;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * A holder for {@link se.liu.ida.jaclu010carfo452.tddd78.chess2.StatusComponent} and ingame buttons
 * @author jaclu010, carfo452
 */
public class StatusPanel extends JPanel implements ChessBoardListener
{
    private ChessBoard cB;
    private int cheatClicks = 0;
    private final JButton useAbility = new JButton("Use ability");
    private final JButton lvlupSelected = new JButton("Upgrade selected");
    private final JButton changePlayer = new JButton("Change player");


    public StatusPanel(ChessBoard cB) throws HeadlessException {
	this.cB = cB;

	final JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

 	final JButton showPossible = new JButton("Switch move/ability");
 	final JButton changeGUI = new JButton("Switch GUI");

	final StatusComponent statusArea = new StatusComponent(cB);

	cB.addChessBoardListener(statusArea);

	buttonPanel.add(changeGUI);
	buttonPanel.add(lvlupSelected);
	buttonPanel.add(showPossible);
	buttonPanel.add(changePlayer);
	buttonPanel.add(useAbility);

	lvlupSelected.setVisible(false);
	changePlayer.setVisible(false);

	this.add(statusArea);
	this.add(buttonPanel);

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
		    } else {
			GlobalVars.setShowRegularMoves(true);
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

    public void enterGodMode(){
	lvlupSelected.setVisible(true);
	changePlayer.setVisible(true);
    }

    @Override
    public void chessBoardChanged(){
	ChessPiece selected = cB.getSelected();
	if(selected != null && selected.getpT() == PieceType.KING && !GlobalVars.isShowRegularMoves()){
	    useAbility.setVisible(true);
	} else {
	    useAbility.setVisible(false);
	}
    }

    public void checkCoordsForCheat(MouseEvent e){
	if(e.getClickCount() > 1 && e.getPoint().getX() < GlobalVars.getsquareside() && e.getPoint().getY() < GlobalVars.getsquareside()){
	    cheatClicks++;
	    if(cheatClicks > 8){
		enterGodMode();
	    }
	}
    }

    @Override public Dimension getPreferredSize(){
	super.getPreferredSize();
	/*
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final double percentOfScreenWidth = 0.35, percentOfScreenHeight = 0.7;
	int compWidth = (int)(screenSize.getWidth()* percentOfScreenWidth);
	int compHeight = (int)(screenSize.getHeight()*percentOfScreenHeight);
	Dimension preferredSize = new Dimension(compWidth, compHeight);
	return preferredSize;*/
	return new Dimension(3*GlobalVars.getsquareside(), GlobalVars.getHeight()*GlobalVars.getsquareside());
    }
}
