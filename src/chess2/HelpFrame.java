package chess2;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A frame that displays the text that is located in the file help.txt
 * @author jaclu010, carfo452
 */
public class HelpFrame extends JFrame
{
    private static final Logger LOGGER = Logger.getLogger(HelpFrame.class.getName());


    public HelpFrame() throws HeadlessException {
	super("Help");
	try {
	    FileHandler fileHandler = new FileHandler("assets/helpfiles/logs/helplog.txt");
	    LOGGER .addHandler(fileHandler);
	    LOGGER .setLevel(Level.WARNING);
	} catch(IOException e){
	    LOGGER.log(Level.WARNING, "No file with that name is found", e);
	}
	JTextArea textArea = new JTextArea();
	final int fontSize = 16;
	textArea.setFont(new Font("Sans Serif", Font.PLAIN, fontSize));
	textArea.setEditable(false);
	writeTextToTextArea(textArea);

	this.add(textArea);
	this.pack();
	this.setVisible(true);
	this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }


    private void writeTextToTextArea(JTextArea textArea){
	String text = null;
	try {
	    File file = new File("assets/helpfiles/help.txt");
	    try (Reader re = new FileReader(file)) {
		char[] chars = new char[(int) file.length()];
		re.read(chars);
		text = new String(chars);
		re.close();
	    }
	} catch (IOException e){
	    LOGGER.log(Level.WARNING, "No file with that name is found", e);
	    this.dispose();
	}
	textArea.setText(text);
    }
}
