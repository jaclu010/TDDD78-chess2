package se.liu.ida.jaclu010carfo452.tddd78.chess2.gui;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.*;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities.AbilityCharacteristic;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.abilities.AbilityType;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.animation.AnimateAbilityImpact;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.animation.AnimateMovement;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.animation.AnimationListener;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.rules.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * The component that paints the Chessboard
 * @author jaclu010, carfo452
 */
public class ChessComponent extends JComponent implements ChessBoardListener, AnimationListener
{
    private ChessBoard cB;
    private int squareSide = GlobalVars.getsquareside();
    private int letterCompY = squareSide/2 +4;
    private int letterCompX = squareSide/2 -4;
    private static final Color GREEN_TRANSPARENT = new Color(0, 255, 0, 100);
    private static final Color RED_TRANSPARENT = new Color(255, 50, 50, 90);
    private static final Color YELLOW_TRANSPARENT = new Color(255, 255, 0, 90);
    private static final Color BLUE_TRANSPARENT = new Color(10, 20, 255, 90);
    private AnimateMovement animateMovement = null;
    private AnimateAbilityImpact endAnim = null;


    public ChessComponent(final ChessBoard cB) {
	this.cB = cB;
    }

    @Override
    public void chessBoardChanged(){
	repaint();
	if(cB.isGameOver()){
	    gameOver();
	}
    }

    @Override
    public void animateAction(){
	endAnim = null;
	if(!GlobalVars.isShowRegularMoves()) {
	    endAnim = new AnimateAbilityImpact(this, cB);
	}
	animateMovement = new AnimateMovement(this, cB, endAnim);

    }

    @Override public Dimension getPreferredSize(){
	super.getPreferredSize();
	return new Dimension(GlobalVars.getWidth()*GlobalVars.getsquareside(), GlobalVars.getHeight()*GlobalVars.getsquareside());
    }
    
    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	drawBG(g2d);
	drawPieces(g2d);
	drawPossibleMoves(g2d);

	if(GlobalVars.isAnimationRunning()){
	    drawAnimation(g2d);
	}

	if(GlobalVars.isEndAnimRunning()){
	    drawImpactAnimation(g2d);
	}
	assert g2d != null: "Internal error: Something has changed the Graphics2D to null";
    }

    /**
     * Paints the empty board
     * @param g2d a Graphics2D to draw with
     */
    public void drawBG(Graphics2D g2d){
	for (int y = 0; y < GlobalVars.getHeight(); y++) {
	    for (int x = 0; x < GlobalVars.getWidth(); x++) {
		if(cB.getPiece(y,x).getpT() == PieceType.OUTSIDE){
		    g2d.setColor(Color.BLACK);
		    g2d.fill(new Rectangle(x*squareSide, y*squareSide, squareSide, squareSide));
		    g2d.setColor(Color.WHITE);
		    final int fontSizeSideLetter = 12;
		    g2d.setFont(new Font("SansSerif", Font.BOLD, fontSizeSideLetter));
		    if ((y != 0 && y != GlobalVars.getHeight()-1) || (x != 0 && x != GlobalVars.getWidth()-1)) {
			g2d.drawString(getLetter(y, x), x * squareSide + letterCompX, y * squareSide + letterCompY);
		    }
		}else if ((Math.abs(y) % 2 == 0 && Math.abs(x) % 2 == 0) || (y % 2 != 0 && x % 2 != 0)){
		    g2d.setColor(Color.LIGHT_GRAY);
		    g2d.fill(new Rectangle(x*squareSide, y*squareSide, squareSide, squareSide));
		} else {
		    g2d.setColor(Color.DARK_GRAY);
		    g2d.fill(new Rectangle(x*squareSide, y*squareSide, squareSide, squareSide));
		}
	    }
	}
    }

    /**
     * Draws the chess pieces
     * @param g2d a Graphics2d to draw with
     */
    public void drawPieces(Graphics2D g2d){
	for (int y = 1; y < GlobalVars.getHeight()-1; y++) {
	    for (int x = 1; x < GlobalVars.getWidth()-1; x++) {
		ChessPiece currentPiece = cB.getPiece(y, x);
		if (currentPiece.getpT() != PieceType.EMPTY) {
		    if ((currentPiece.getpT() != PieceType.OUTSIDE && !currentPiece.isUnderAnimation()) || (currentPiece.getAbility().getaT() == AbilityType.LASER && !GlobalVars.isShowRegularMoves())) {

			g2d.drawImage((GlobalVars.getIMG(currentPiece)).getImage(), x*squareSide, y*squareSide, squareSide, squareSide, this);

			// Creates the healthbars
			drawHealthbar(g2d, currentPiece, x, y);

			// Create Ability Points symbol
			g2d.setColor(Color.YELLOW);
			g2d.fill(new Ellipse2D.Double(x*squareSide+(double)squareSide/8, y*squareSide+(double)squareSide/6, (double)squareSide/6, (double)squareSide/6));

			g2d.setColor(Color.BLACK);
			g2d.drawString(Integer.toString(currentPiece.getaP()), x*squareSide+squareSide/6, y*squareSide+squareSide/3);
		    }
		    // Makes the selected piece red
		    if ((currentPiece).equals(cB.getSelected())) {
			g2d.setColor(RED_TRANSPARENT);
			g2d.fill(new Rectangle(x * squareSide, y * squareSide, squareSide, squareSide));
		    }
		    // Makes frozen pieces blue
		    if(cB.getFrozenPieces().contains(currentPiece)){
			g2d.setColor(BLUE_TRANSPARENT);
			g2d.fill(new Rectangle(x * squareSide, y * squareSide, squareSide, squareSide));
		    }
		}
	    }
	}
    }

    /**
     * Draws the possible move for the selected piece to move to
     * @param g2d a Graphics2d to draw with
     */
    private void drawPossibleMoves(Graphics2D g2d){
	Iterable<Point> possibleMoves = cB.getRC().getPossibleMoves();
	Iterable<Point> abilityMoves = cB.getRC().getAbilityMoves();

	// Colors the possible places for the selected piece to move
	if(GlobalVars.isShowRegularMoves()) {
	    g2d.setColor(GREEN_TRANSPARENT);
	    if (possibleMoves != null) {
		for (Point possibleMove : possibleMoves) {
		    g2d.fill(new Rectangle(possibleMove.getX() * squareSide, possibleMove.getY() * squareSide, squareSide, squareSide));
		}
	    }
	}
	else {
	    // Colors the possible places for the selected piece to use ability on
	    g2d.setColor(YELLOW_TRANSPARENT);
	    if (abilityMoves != null) {
		for (Point abilityMove : abilityMoves) {
		    g2d.fill(new Rectangle(abilityMove.getX() * squareSide, abilityMove.getY() * squareSide, squareSide, squareSide));
		}
	    }
	}
    }

    private void drawHealthbar(Graphics2D g2d, ChessPiece currentPiece, int x, int y){
	int hp = currentPiece.getHP();
	if (hp > 5){
	    // Prevents the drawed healthbar from being to big for the chesspiece
	    hp = 5;
	}
	g2d.setColor(Color.LIGHT_GRAY);
	g2d.fill(new Rectangle(squareSide*x+squareSide/10, squareSide*y+squareSide-squareSide/6, squareSide-squareSide/7, squareSide/9));
	g2d.setColor(Color.RED);
	g2d.fill(new Rectangle(squareSide*x+squareSide/10, squareSide*y+squareSide-squareSide/6, ((squareSide-squareSide/7)/5)*hp, squareSide/9));
    }

    private void drawAnimation(Graphics2D g2d){
	int realYPosition = (int)(animateMovement.getAnimationY()*squareSide);
	int realXPosition = (int)(animateMovement.getAnimationX()*squareSide);
	boolean regMo = GlobalVars.isShowRegularMoves();
	ChessPiece selected = cB.getSelected();

	if (regMo || selected.getAbility().getaC() != AbilityCharacteristic.SPECIAL) {
	    g2d.drawImage((GlobalVars.getIMG(selected)).getImage(), realXPosition, realYPosition, squareSide, squareSide, this);
	} else if(selected.getAbility().getaT() == AbilityType.LASER){
	    drawLaser(g2d);
	}
    }

    private void drawImpactAnimation(Graphics2D g2d){
	String ability = endAnim.getAbility();
	int animationY = (int)endAnim.getAnimationY();

	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, endAnim.getOpacity()));
	g2d.drawImage((GlobalVars.getIMG(ability)).getImage(), cB.getTargetCoords().getX()*squareSide+squareSide/3, animationY, squareSide/3, squareSide/3, this);
	g2d.dispose();
    }

    private void drawLaser(Graphics2D g2d){
	int targetY = cB.getTargetCoords().getY();
	int targetX = cB.getTargetCoords().getX();
	int selectY = cB.getSelectedCoords().getY();
	int selectX = cB.getSelectedCoords().getX();

	g2d.setColor(Color.RED);
	g2d.fill(new Ellipse2D.Double(targetX * squareSide, targetY * squareSide, squareSide, squareSide));
	g2d.setStroke(new BasicStroke((float)squareSide/2));
	g2d.draw(new Line2D.Double(selectX * squareSide + (double)squareSide / 2, selectY * squareSide + (double)squareSide / 2,
				  targetX * squareSide + (double)squareSide / 2, targetY * squareSide + (double)squareSide / 2));
	g2d.setColor(Color.WHITE);
	g2d.setStroke(new BasicStroke((float)squareSide/4));
	g2d.draw(new Line2D.Double(selectX * squareSide + (double)squareSide / 2, selectY * squareSide + (double)squareSide / 2,
				  targetX * squareSide + (double)squareSide/2, targetY * squareSide + (double)squareSide/2));
	g2d.setColor(Color.RED);
    }

    private String getLetter(int y, int x){
	if ((x == 0 || x == GlobalVars.getWidth()-1)){
	    return Integer.toString(9-y);
	} else if ((y == 0 || y == GlobalVars.getHeight()-1)){
	    x += GlobalVars.getcharadd();
	    char a = (char) x;
	    return Character.toString(a);
	}
	return "";
    }

    /**
     * Displays a game over message
     */
    private void gameOver(){
	String winner = "White player";
	if(cB.getActivePlayer()){
	    winner = "Black player";
	}
    	int optionChosen = JOptionPane.showOptionDialog(
                this,
		"Game Over, "+ winner + " wins!" + "\n Play again?",
                "Game over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
		null,
		null,
		null);

    	if(optionChosen == 0){
            cB.newGame();
    	} else {
            System.exit(0);
        }
    }
}
