package chess2;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Creates a temporary object that handles animation for it
 * @author jaclu010, carfo452
 */
public class AnimateHandler
{
    private int targetY;
    private int targetX;
    private double animationX, animationY, moveY, moveX;
    private ChessPiece selected;
    private Timer timer;
    private ChessComponent gameArea;
    private ChessBoard cB;

    private static final int DELAY = 50;
    private static final int PPF = 10;

    public AnimateHandler(ChessComponent gameArea, ChessBoard cB) {
        this.gameArea = gameArea;
        this.cB = cB;

        final int selectedX = cB.getSelectedX();
        final int selectedY = cB.getSelectedY();

        selected = cB.getSelected();

        animationX = selectedX;
        animationY = selectedY;

        targetX = cB.getTargetX();
        targetY = cB.getTargetY();

        moveX = (targetX-(double) selectedX)/ PPF;
        moveY = (targetY-(double) selectedY)/ PPF;

        timer = new Timer(DELAY, doAnimationTowardsTarget);
        timer.setCoalesce(true);
        timer.start();
        GlobalVars.setAnimationRunning(true);
        selected.setUnderAnimation(true);
    }

    /*
    public Action pickAction(){
        if (regMo && cB.getPiece(targetY, targetX).getpT() == PieceType.EMPTY) {
            return doMoveAnimation;
        } else if (regMo){
            return doHurtAnimationTowardsTarget;
        }
        return doMoveAnimation;
    }
    */

    private final Action doAnimationTowardsTarget = new AbstractAction() {
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


    private void stopAnimation(){
        timer.stop();
        GlobalVars.setAnimationRunning(false);
        selected.setUnderAnimation(false);
    }

    public double getAnimationX() {
        return animationX;
    }

    public double getAnimationY() {
        return animationY;
    }
}
