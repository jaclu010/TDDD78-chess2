package se.liu.ida.jaclu010carfo452.tddd78.chess2;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.gui.ChessFrame;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Starts the game
 * @author jaclu010, carfo452
 */
public final class Main
{
    private static Logger logger = Logger.getLogger(Main.class.getName());
    private Main() {}

    public static void main(String[] args) {
	initLogger();
	loadProperties();
	ChessBoard cb = new ChessBoard();
	ChessFrame cf = new ChessFrame(cb);
    }

    private static void initLogger(){
	try {
	    FileHandler fileHandler = new FileHandler("assets/logs/loadConfigurationlog.txt");
	    logger.addHandler(fileHandler);
	    logger.setLevel(Level.ALL);
	} catch(IOException e){
	    logger.log(Level.WARNING, "No file with that name is found", e);
	}
	assert logger != null: "Logger was not initialised";
    }

    private static void loadProperties(){
	Properties prop = new Properties();
	InputStream input = null;
	try {
	    input = new FileInputStream("assets/config/config.properties");
	    prop.load(input);
	    writeProperties(prop);
	    logger.info("Configuration is loaded");

	} catch (IOException ex) {
	    logger.log(Level.WARNING, "Configuration not loaded correctly", ex);
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
	    logger.info("Configuration of theme done");
	}
	if(squareSide > 0) {
	    GlobalVars.setSquareSide(squareSide);
	    logger.info("Configuration of squareSide done");
	}
	if(width >= 10 && height >= 10) {
	    GlobalVars.setWidth(width);
	    GlobalVars.setHeight(height);
	    logger.info("Configuration of width and height done");
	}

	// Can be allowed to be negative!
	GlobalVars.setaP(startAP);
	GlobalVars.setShowRegularMoves(regMo);
	GlobalVars.setCheatsEnabled(cheatsEnabled);
    }
}
