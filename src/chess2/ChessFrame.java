package chess2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessFrame extends JFrame
{
    public ChessFrame(ChessBoard chessBoard) throws HeadlessException {

	final ChessComponent gameArea = new ChessComponent(chessBoard);
	final JFrame frame = new JFrame("Chess 2");
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
		    System.out.println("Nytt spel");
		} else if(event.getSource().equals(exit)){
		    System.exit(0);
		}
	    }
	};
	exit.addActionListener(menuActions);

	final JMenuBar menuBar = new JMenuBar();
	menuBar.add(file);
	frame.setJMenuBar(menuBar);
    }
}
