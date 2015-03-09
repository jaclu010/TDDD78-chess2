package chess2;

import javafx.scene.input.KeyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class ChessFrame extends JFrame implements MouseListener, ChessBoardListener
{
    private ChessBoard cB;
    private JTextArea activePlayerText;

    public ChessFrame(ChessBoard cB) throws HeadlessException {
	this.cB = cB;
	final ChessComponent gameArea = new ChessComponent(cB);
	final JFrame frame = new JFrame("Chess 2");
	cB.addChessBoardListener(gameArea);
	cB.addChessBoardListener(this);
	gameArea.addMouseListener(this);
	frame.setLayout(new BorderLayout());
	frame.add(gameArea, BorderLayout.CENTER);
	createMenues(frame);
	createActivePlayerTextArea(frame);

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
	    }
	};
	changePlayer.addActionListener(menuActions);
	newGame.addActionListener(menuActions);
	exit.addActionListener(menuActions);

	final JMenuBar menuBar = new JMenuBar();
	menuBar.add(file);
	frame.setJMenuBar(menuBar);
    }

    private void createActivePlayerTextArea(JFrame frame){
	this.activePlayerText = new JTextArea("White players turn");
	activePlayerText.setFont(new Font("Arial", Font.PLAIN, 30 ));
	activePlayerText.setForeground(Color.GRAY);
	activePlayerText.setEditable(false);
	frame.add(activePlayerText, BorderLayout.PAGE_START);
    }

    private void changeActivePlayerTextArea(){
	if(cB.getActivePlayer()){
	    activePlayerText.setText("White players turn");
	    activePlayerText.setForeground(Color.GRAY);
	}else{
	    activePlayerText.setText("Black players turn");
	    activePlayerText.setForeground(Color.BLACK);
	}
    }

    @Override
    public void chessBoardChanged(){
	changeActivePlayerTextArea();
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
