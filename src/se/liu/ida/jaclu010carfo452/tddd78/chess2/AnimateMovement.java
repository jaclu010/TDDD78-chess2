package se.liu.ida.jaclu010carfo452.tddd78.chess2;

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
        //final ChessComponent gameArea1 = gameArea;
        //final ChessBoard cB1 = cB;
        this.endAnim = endAnim;

        final int selectedX = cB.getSelectedX();
        final int selectedY = cB.getSelectedY();

        selected = cB.getSelected();

        animationX = selectedX;
        animationY = selectedY;

        targetX = cB.getTargetX();
        targetY = cB.getTargetY();

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
    }

    private void stopAnimation(){
        if (endAnim != null){
            animateImpact();
        }
        timer.stop();
        GlobalVars.setAnimationRunning(false);
        selected.setUnderAnimation(false);
    }

    public void animateImpact(){
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
