package chess2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ChessFrame extends JFrame implements MouseListener
{
    private ChessBoard cB;


    public ChessFrame(ChessBoard cB) throws HeadlessException {
	this.cB = cB;

	final JFrame frame = new JFrame("Chess 2");
	final ChessComponent gameArea = new ChessComponent(cB);
	final StatusComponent statusArea = new StatusComponent(cB);
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
	cB.addChessBoardListener(statusArea);
	cB.addChessBoardListener(logArea);

	gameArea.addMouseListener(this);
	statusArea.addMouseListener(this);

	logScroll.createVerticalScrollBar();


	try {
	    URL url = this.getClass().getResource("/resources/W_ROOK.png");
	    final BufferedImage image = ImageIO.read(url);
	    frame.setIconImage(image);
	} catch (IOException e) {
	    e.printStackTrace();
	}

	frame.setLayout(new BorderLayout());
	frame.add(topBar, BorderLayout.PAGE_START);
	frame.add(gameArea, BorderLayout.CENTER);
	frame.add(killedPiecesArea, BorderLayout.WEST);
	frame.add(statusArea, BorderLayout.EAST);
	frame.add(logScroll, BorderLayout.SOUTH);
	createMenues(frame);

	frame.pack();
	frame.setVisible(true);
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Creates the menues
     */
    private void createMenues(JFrame frame){
	final JMenu file = new JMenu("File");
	final JMenuItem newGame = new JMenuItem("New Game", 'N');
	final JMenuItem exit = new JMenuItem("Exit", 'E');
	final JMenuItem changePlayer = new JMenuItem("Change active player", 'C');

	file.add(newGame);
	file.add(changePlayer);
	file.add(exit);
	final ActionListener menuActions = event -> {
	    if(event.getSource().equals(newGame)){
		cB.clearBoard();
	    } else if(event.getSource().equals(exit)){
		System.exit(0);
	    } else if(event.getSource().equals(changePlayer)){
		cB.setActivePlayer(!cB.getActivePlayer());
		cB.setSelected(null);
		cB.clearPossibleMoves();
		cB.notifyListeners();
	    }
	};
	changePlayer.addActionListener(menuActions);
	newGame.addActionListener(menuActions);
	exit.addActionListener(menuActions);

	final JMenuBar menuBar = new JMenuBar();
	menuBar.add(file);
	frame.setJMenuBar(menuBar);
    }

    @Override
    public void mouseClicked(MouseEvent e){
	cB.checkMouseClick(e);
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
