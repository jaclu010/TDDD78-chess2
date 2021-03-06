package se.liu.ida.jaclu010carfo452.tddd78.chess2.animation;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessBoard;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.ChessPiece;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.GlobalVars;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.SoundHandler;
import se.liu.ida.jaclu010carfo452.tddd78.chess2.gui.ChessComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Creates a temporary object that handles movement animation for the ChessComponent
 * @author jaclu010, carfo452
 */
public class AnimateMovement
{
    private int targetY;
    private int targetX;
    private double animationX, animationY, moveY, moveX;
    private ChessPiece selected;
    private Timer timer;
    private AnimateAbilityImpact endAnim;

    private static final int DELAY = 50;
    private static final int PPF = 10;

    public AnimateMovement(ChessComponent gameArea, ChessBoard cB, AnimateAbilityImpact endAnim) {
        this.endAnim = endAnim;

        final int selectedX = cB.getSelectedCoords().getX();
        final int selectedY = cB.getSelectedCoords().getY();

        selected = cB.getSelected();

        animationX = selectedX;
        animationY = selectedY;

        targetX = cB.getTargetCoords().getX();
        targetY = cB.getTargetCoords().getY();

        moveX = (targetX-(double) selectedX)/ PPF;
        moveY = (targetY-(double) selectedY)/ PPF;


        final Action doAnimationTowardsTarget = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                animationY+=moveY;
                animationX+=moveX;
                if(moveY < 0) {
                    // Animation moving uppward
                    if (animationY <= targetY) {
                        stopAnimation();
                        cB.chooseEndAction();

                    }
                } else if (moveY > 0){
                    // Animation moving downward
                    if (animationY >= targetY) {
                        stopAnimation();
                        cB.chooseEndAction();

                    }
                } else{
                    // Animation moving sideways
                    if(moveX > 0){
                        // Animation moving to the right
                        if(animationX >= targetX){
                            stopAnimation();
                            cB.chooseEndAction();
                        }
                    }else{
                        // Animation moving to the left
                        if(animationX <= targetX){
                            stopAnimation();
                            cB.chooseEndAction();
                        }
                    }
                }
                gameArea.repaint();
            }
        };


        timer = new Timer(DELAY, doAnimationTowardsTarget);
        timer.setCoalesce(true);
        timer.start();
        GlobalVars.setAnimationRunning(true);
        selected.setUnderAnimation(true);

        if(endAnim != null){
       	    SoundHandler.getInstance().playSound(selected.getAbility().getaT().name());
            assert endAnim != null: "Internal error: endAnim was changed to null";
       	}
    }

    private void stopAnimation(){
        if (endAnim != null){
            animateImpact();
            assert endAnim != null: "Internal error: endAnim was changed to null";
        }
        timer.stop();
        GlobalVars.setAnimationRunning(false);
        selected.setUnderAnimation(false);
    }

    private void animateImpact(){
        GlobalVars.setEndAnimRunning(true);
        endAnim.startAnimation();
    }

    public double getAnimationX() {
        return animationX;
    }

    public double getAnimationY() {
        return animationY;
    }
}
