package chess2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * The frame that holds the game
 * @author jaclu010, carfo452
 */
public class ChessFrame extends JFrame implements MouseListener, KeyListener
{
    private ChessBoard cB;
    private StatusPanel statusPanel;


    public ChessFrame(ChessBoard cB) throws HeadlessException {
	this.cB = cB;

	final JFrame frame = new JFrame("Chess 2");

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
	//statusPanel.addMouseListener(this);
	gameArea.addKeyListener(this);

	logScroll.createVerticalScrollBar();

	frame.setIconImage(GlobalVars.getIMG("W_ROOK").getImage());


	frame.setLayout(new BorderLayout());
	frame.add(statusPanel, BorderLayout.EAST);
	frame.add(topBar, BorderLayout.PAGE_START);
	frame.add(gameArea, BorderLayout.CENTER);
	frame.add(killedPiecesArea, BorderLayout.WEST);

	frame.add(logScroll, BorderLayout.SOUTH);
	createMenues(frame);

	frame.pack();
	frame.setVisible(true);
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	this.setFocusTraversalKeysEnabled(false);
	this.setFocusable(true);
    }

    /**
     * Creates the menues
     */
    private void createMenues(JFrame frame){
	final JMenu file = new JMenu("File");
	final JMenuItem newGame = new JMenuItem("New Game", KeyEvent.VK_N);
	final JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_E);
	final JMenuItem chMode = new JMenuItem("Secret");


	chMode.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_F, InputEvent.ALT_DOWN_MASK));

	file.setMnemonic(KeyEvent.VK_F);

	file.add(chMode);
	file.add(newGame);
	file.add(exit);

	chMode.setVisible(false);
	final ActionListener menuActions = event -> {
	    if(event.getSource().equals(newGame)){
		cB.newGame();
	    } else if(event.getSource().equals(exit)){
		System.exit(0);
	    } else if(event.getSource().equals(chMode)){
		statusPanel.enterGodMode();
	    }
	};

	newGame.addActionListener(menuActions);
	exit.addActionListener(menuActions);
	chMode.addActionListener(menuActions);


	final JMenuBar menuBar = new JMenuBar();
	menuBar.add(file);
	frame.setJMenuBar(menuBar);
    }

    @Override
    public void mouseClicked(MouseEvent e){
	if(!GlobalVars.isAnimationRunning()) {
	    cB.checkMouseClick(e);
	}
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void keyReleased(KeyEvent e){
	if (e.getSource().equals(KeyEvent.VK_P)){
	    statusPanel.enterGodMode();
	}
	System.out.println("hej");
    }

    @Override
    public void keyPressed(KeyEvent e) {
	if (e.getSource().equals(KeyEvent.VK_P)){
	    statusPanel.enterGodMode();
	}
	System.out.println("hej");
    }

    @Override
    public void keyTyped(KeyEvent e) {
	if (e.getSource().equals(KeyEvent.VK_P)){
	    statusPanel.enterGodMode();
	}
	System.out.println("hej");

    }
}
