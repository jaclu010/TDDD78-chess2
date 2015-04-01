package chess2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public final class GlobalVars
{
    private static final int SQUARE_SIDE = 70;
    private static final int CHAR_ADD = 64;
    private static final int WIDTH = 10, HEIGHT = 10;
    private static boolean showRegularMoves = true;
    private static boolean showAbilityMoves = false;
    private static String guiTheme = "troll";

    private GlobalVars() {}

    public static int getSquareSide() {
	return SQUARE_SIDE;
    }

    public static int getCharAdd() {
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
	result.append(aP.getPieceType().name());
	return result.toString();
    }

    public static boolean isShowRegularMoves() {
        return showRegularMoves;
    }

    public static void setShowRegularMoves(final boolean showRegularMoves) {
        GlobalVars.showRegularMoves = showRegularMoves;
    }

    public static boolean isShowAbilityMoves() {
        return showAbilityMoves;
    }

    public static void setShowAbilityMoves(final boolean showAbilityMoves) {
        GlobalVars.showAbilityMoves = showAbilityMoves;
    }

    public static String getGuiTheme() {
        return guiTheme;
    }

    public static void setGuiTheme(final String guiTheme) {
        GlobalVars.guiTheme = guiTheme;
    }

    public static BufferedImage getImage(ChessPiece cP){
        try {
            final BufferedImage image = ImageIO.read(new File("assets/theme/"+guiTheme+"/"+imgPicker(cP)+"/.png"));

            return image;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

/*
    public static BufferedImage getImage(String object){
        try {
            final BufferedImage image = ImageIO.read(new File("assets/theme/"+guiTheme+"/"+object+"/.png"));

            return image;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/
    public static ImageIcon getImage(){
        return new ImageIcon("assets/theme/troll/W_KING.png");
    }


}
