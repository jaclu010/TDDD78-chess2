package se.liu.ida.jaclu010carfo452.tddd78.chess2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * The frame that holds the game
 * @author jaclu010, carfo452
 */
public class ChessFrame extends JFrame implements MouseListener
{
    private ChessBoard cB;
    private StatusPanel statusPanel;


    public ChessFrame(ChessBoard cB) throws HeadlessException {
	super("Chess 2");
	this.cB = cB;

	statusPanel = new StatusPanel(cB);
	final ChessComponent gameArea = new ChessComponent(cB);
	final LogComponent logArea = new LogComponent(cB);
	final KilledPiecesComponent killedPiecesArea= new KilledPiecesComponent(cB);
	final TopBarComponent topBar = new TopBarComponent(cB);
	final JScrollPane logScroll = new JScrollPane();
	final JViewport logScrollHeader = new JViewport();

	logScrollHeader.setView(new JLabel("Log: "));
	logScroll.setViewportView(logArea);
	logScroll.setColumnHeader(logScrollHeader);

	cB.addChessBoardListener(topBar);
	cB.addChessBoardListener(killedPiecesArea);
	cB.addChessBoardListener(gameArea);
	cB.addChessBoardListener(statusPanel);
	cB.addChessBoardListener(logArea);

	cB.addAnimationListener(gameArea);

	gameArea.addMouseListener(this);

	logScroll.createVerticalScrollBar();

	this.setIconImage(GlobalVars.getIMG("W_ROOK").getImage());


	this.setLayout(new BorderLayout());
	this.add(statusPanel, BorderLayout.EAST);
	this.add(topBar, BorderLayout.PAGE_START);
	this.add(gameArea, BorderLayout.CENTER);
	this.add(killedPiecesArea, BorderLayout.WEST);

	this.add(logScroll, BorderLayout.SOUTH);
	createMenues(this);

	this.pack();
	this.setVisible(true);
	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Creates the menues
     */
    private void createMenues(JFrame frame){
	final JMenu file = new JMenu("File");
	file.setMnemonic(KeyEvent.VK_F);
	final JMenuItem newGame = new JMenuItem("New Game", KeyEvent.VK_N);
	final JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_E);
	file.add(newGame);
	file.add(exit);


	final JMenu help = new JMenu("Help");
	help.setMnemonic(KeyEvent.VK_H);
	final JMenuItem howToPlay = new JMenuItem("How to play", KeyEvent.VK_H);
	help.add(howToPlay);


	final ActionListener menuActions = event -> {
	    if(event.getSource().equals(newGame)){
		cB.newGame();
	    } else if(event.getSource().equals(exit)){
		System.exit(0);
	    }else if(event.getSource().equals(howToPlay)){
		HelpFrame helpFrame = new HelpFrame();
		helpFrame.setAlwaysOnTop(true);
	    }
	};

	newGame.addActionListener(menuActions);
	exit.addActionListener(menuActions);
	howToPlay.addActionListener(menuActions);

	final JMenuBar menuBar = new JMenuBar();
	menuBar.add(file);
	menuBar.add(help);
	frame.setJMenuBar(menuBar);
    }

    @Override
    public void mouseClicked(MouseEvent e){
	if(!GlobalVars.isAnimationRunning()) {
	    cB.checkMouseClick(e);
	}
	statusPanel.checkCoordsForCheat(e);
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
}
