package se.liu.ida.jaclu010carfo452.tddd78.chess2;

import javax.swing.*;

/**
 * Global variables
 * @author jaclu010, carfo452
 */
public final class GlobalVars
{
    private static int squareSide = 70;
    private static final int CHAR_ADD = 64;
    private static int width = 10, height = 10, abilityPoints = 0;
    private static boolean showRegularMoves = true;
    private static String guiTheme = "original";
    private static boolean animationRunning = false;
    private static boolean endAnimRunning = false;
    private static boolean cheatsEnabled = false;

    private GlobalVars() {}

    public static int getsquareside() {
	return squareSide;
    }

    public static int getcharadd() {
	return CHAR_ADD;
    }

    public static int getWidth() {
	return width;
    }

    public static int getHeight() {
	return height;
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

    public static void setSquareSide(final int squareSide) {
        GlobalVars.squareSide = squareSide;
    }

    public static void setWidth(final int width) {
        GlobalVars.width = width;
    }

    public static void setHeight(final int height) {
        GlobalVars.height = height;
    }

    public static boolean isCheatsEnabled() {
        return cheatsEnabled;
    }

    public static void setCheatsEnabled(final boolean cheatsEnabled) {
        GlobalVars.cheatsEnabled = cheatsEnabled;
    }

    public static int getaP() {
        return abilityPoints;
    }

    public static void setaP(final int aP) {
        GlobalVars.abilityPoints = aP;
    }

    public static String getLetter(int n){
	n += CHAR_ADD;
        char a = (char) n;
	return Character.toString(a);
    }
}
