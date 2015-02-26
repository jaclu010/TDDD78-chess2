package chess2;

import javax.swing.*;
import java.awt.*;

public class ChessFrame extends JFrame
{
    public ChessFrame(ChessBoard chessBoard) throws HeadlessException {

	final ChessComponent gameArea = new ChessComponent(chessBoard);
	final JFrame frame = new JFrame("Chess 2");
	frame.setLayout(new BorderLayout());
	frame.add(gameArea, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);

	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
