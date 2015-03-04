package chess2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class ChessFrame extends JFrame implements MouseListener, ChessBoardListener
{
    private ChessBoard cB;
    private boolean chessPieceMarked;
    private int xMovePieceFrom, yMovePieceFrom;

    public ChessFrame(ChessBoard cB) throws HeadlessException {
	this.cB = cB;
	this.chessPieceMarked = false;
	final ChessComponent gameArea = new ChessComponent(cB);
	final JFrame frame = new JFrame("Chess 2");
	cB.addChessBoardListener(gameArea);
	gameArea.addMouseListener(this);
	frame.setLayout(new BorderLayout());
	frame.add(gameArea, BorderLayout.CENTER);
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
	file.add(newGame);
	file.add(exit);

	final ActionListener menuActions = new ActionListener(){
	    public void actionPerformed(ActionEvent event){
		if(event.getSource().equals(newGame)){
		    cB.clearBoard();
		} else if(event.getSource().equals(exit)){
		    System.exit(0);
		}
	    }
	};
	newGame.addActionListener(menuActions);
	exit.addActionListener(menuActions);

	final JMenuBar menuBar = new JMenuBar();
	menuBar.add(file);
	frame.setJMenuBar(menuBar);
    }

    @Override
    public void chessBoardChanged(){
	repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e){
	int squareSide = ChessComponent.getSquareSide();
	int x = (e.getX() / squareSide), y = (e.getY() / squareSide);
	if(x < ChessBoard.getWidth()-1 && x > 0 && y < ChessBoard.getHeight()-1 && y > 0) {
	    if (chessPieceMarked) {
		cB.moveChessPiece(xMovePieceFrom, yMovePieceFrom, x, y);
		chessPieceMarked = false;
	    } else {
		xMovePieceFrom = x;
		yMovePieceFrom = y;
		chessPieceMarked = true;
	    }
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

}
