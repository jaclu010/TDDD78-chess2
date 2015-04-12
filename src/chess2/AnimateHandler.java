package chess2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AnimateHandler
{
    private int selectedX, selectedY, targetY, targetX;
    private double animationX, animationY, moveY, moveX;
    private ChessPiece selected;
    private Timer timer;
    private ChessComponent gameArea;
    private ChessBoard cB;

    private static final int DELAY = 100;
    private static final int STEPS_TO_MOVE_EVERY_ANIMATION_ADJUSTER = 20;

    public AnimateHandler(ChessComponent gameArea, ChessBoard cB) {
        this.gameArea = gameArea;
        this.cB = cB;
        selectedX = cB.getSelectedX();
        selectedY = cB.getSelectedY();
        selected = cB.getSelected();

        animationX = selectedX;
        animationY = selectedY;

        targetX = cB.getTargetX();
        targetY = cB.getTargetY();

        moveX = (targetX-(double)selectedX)/STEPS_TO_MOVE_EVERY_ANIMATION_ADJUSTER;
        moveY = (targetY-(double)selectedY)/STEPS_TO_MOVE_EVERY_ANIMATION_ADJUSTER;

        timer = new Timer(DELAY, doAnimationTowardsTarget);
        timer.setCoalesce(true);
        timer.start();
        GlobalVars.setAnimationRunning(true);

    }

    private final Action doAnimationTowardsTarget = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            animationY+=moveY;
            animationX+=moveX;
            if(moveY < 0) {
                // Animation moving uppward
                if (animationY <= targetY) {
                    stopAnimation();
                    cB.hurtPiece(targetY, targetX, 2);
                }
            } else if (moveY > 0){
                // Animation moving downward
                if (animationY >= targetY) {
                    stopAnimation();
                    cB.hurtPiece(targetY, targetX, 2);
                }
            } else{
                // Animation moving sideways
                if(moveX > 0){
                    // Animation moving to the right
                    if(animationX >= targetX){
                        stopAnimation();
                        cB.hurtPiece(targetY, targetX, 2);
                    }
                }else{
                    // Animation moving to the left
                    if(animationX <= targetX){
                        stopAnimation();
                        cB.hurtPiece(targetY, targetX, 2);
                    }
                }
            }
            gameArea.repaint();
        }
    };

    private void stopAnimation(){
        timer.stop();
        GlobalVars.setAnimationRunning(false);
    }

    public double getAnimationX() {
        return animationX;
    }

    public double getAnimationY() {
        return animationY;
    }
}
