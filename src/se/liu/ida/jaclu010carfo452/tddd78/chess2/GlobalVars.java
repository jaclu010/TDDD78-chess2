package se.liu.ida.jaclu010carfo452.tddd78.chess2;

import javax.swing.*;

/**
 * Global variables
 * @author jaclu010, carfo452
 */
public final class GlobalVars
{
    private static final int SQUARE_SIDE = 70;
    private static final int CHAR_ADD = 64;
    private static final int WIDTH = 10, HEIGHT = 10;
    private static boolean showRegularMoves = true;
    private static String guiTheme = "original";
    private static boolean animationRunning = false;
    private static boolean endAnimRunning = false;

    private GlobalVars() {}

    public static int getsquareside() {
	return SQUARE_SIDE;
    }

    public static int getcharadd() {
	return CHAR_ADD;
    }

    public static int getWidth() {
	return WIDTH;
    }

    public static int getHeight() {
	return HEIGHT;
    }

    public static String imgPicker(ChessPiece aP){
	StringBuilder result = new StringBuilder();
	if (aP.getPlayer()){
	    result.append("W_");
	} else {
	    result.append("B_");
	}
	result.append(aP.getpT().name());
	return result.toString();
    }

    public static boolean isShowRegularMoves() {
        return showRegularMoves;
    }

    public static void setShowRegularMoves(final boolean showRegularMoves) {
        GlobalVars.showRegularMoves = showRegularMoves;
    }

    public static String getGuiTheme() {
        return guiTheme;
    }

    public static void setGuiTheme(final String guiTheme) {
        GlobalVars.guiTheme = guiTheme;
    }

    public static ImageIcon getIMG(ChessPiece cP){ return new ImageIcon("assets/theme/" + guiTheme + "/" + imgPicker(cP) + ".png"); }

    public static ImageIcon getIMG(String s){
        return new ImageIcon("assets/theme/" +guiTheme+"/"+s+".png");
    }

    public static boolean isAnimationRunning() {
        return animationRunning;
    }

    public static void setAnimationRunning(final boolean animationRunning) {
        GlobalVars.animationRunning = animationRunning;
    }

    public static boolean isEndAnimRunning() {
        return endAnimRunning;
    }

    public static void setEndAnimRunning(final boolean endAnimRunning) {
        GlobalVars.endAnimRunning = endAnimRunning;
    }
}
