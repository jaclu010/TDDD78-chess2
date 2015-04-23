package chess2;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by Carl on 2015-04-23.
 */
public class HelpFrame extends JFrame
{
    public HelpFrame() throws HeadlessException {
	super("Help");
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
	    File file = new File("assets/helpfiles/help");
	    try (Reader re = new FileReader(file)) {
		char[] chars = new char[(int) file.length()];
		re.read(chars);
		text = new String(chars);
		re.close();
	    }
	} catch (IOException e){
	    e.printStackTrace();
	}
	textArea.setText(text);
    }
}
