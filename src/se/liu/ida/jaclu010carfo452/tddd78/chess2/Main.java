package se.liu.ida.jaclu010carfo452.tddd78.chess2;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.gui.ChessFrame;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * Starts the game
 * @author jaclu010, carfo452
 */
public final class Main
{
    private Main() {}

    public static void main(String[] args) {
	loadProperties();
	ChessBoard cb = new ChessBoard();
	ChessFrame cf = new ChessFrame(cb);
    }

    private static void loadProperties(){
	Properties prop = new Properties();
	InputStream input = null;

	try {

		input = new FileInputStream("assets/config/config.properties");
		prop.load(input);
		writeProperties(prop);

	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    }

    private static void writeProperties(Properties prop){
	String theme = prop.getProperty("gui.theme");
	int squareSide = Integer.parseInt(prop.getProperty("gui.squareSide"));
	int width = Integer.parseInt(prop.getProperty("mechanics.width"));
	int height = Integer.parseInt(prop.getProperty("mechanics.height"));
	int startAP = Integer.parseInt(prop.getProperty("mechanics.startAP"));
	boolean regMo = Boolean.parseBoolean(prop.getProperty("mechanics.showRegularMoves"));
	boolean cheatsEnabled = Boolean.parseBoolean(prop.getProperty("mechanics.cheatsEnabled"));



	if(Objects.equals(theme, "troll") || Objects.equals(theme, "original")) {
	    GlobalVars.setGuiTheme(theme);
	}
	if(squareSide > 0) {
	    GlobalVars.setSquareSide(squareSide);
	}
	if(width >= 10 && height >= 10) {
	    GlobalVars.setWidth(width);
	    GlobalVars.setHeight(height);
	}

	// Can be allowed to be negative!
	GlobalVars.setaP(startAP);

	GlobalVars.setShowRegularMoves(regMo);
	GlobalVars.setCheatsEnabled(cheatsEnabled);
    }
}
