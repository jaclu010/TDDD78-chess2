package se.liu.ida.jaclu010carfo452.tddd78.chess2;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * An Animation object that is used to show for the player that an ability was used
 * @author jaclu010, carfo452
 */
public class AnimateAbilityImpact
{
    private int animationY;
    private Timer timer;
    private float opacity = 0.8f;
    private String ability;

    private static final int DELAY = 50;
    private static final int PPF = 5;
    private static final float OPF = 0.05f;

    public AnimateAbilityImpact(ChessComponent gameArea, ChessBoard cB) {
        animationY = cB.getTargetY() * GlobalVars.getsquareside();
        ability = cB.getSelected().getAbility().getAT().name();

        final Action doEndAnim = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e) {

                animationY -= PPF;

                if (opacity - OPF > 0) {
                    opacity -= OPF;

                } else {
                    opacity = 0.0f;
                    stopAnimation();
                }
                gameArea.repaint();
            }
        };

        timer = new Timer(DELAY, doEndAnim);
        timer.setCoalesce(true);
    }

    public void startAnimation(){
        timer.start();
    }

    private void stopAnimation(){
        timer.stop();
        GlobalVars.setEndAnimRunning(false);
    }

    public double getAnimationY() {
        return animationY;
    }

    public float getOpacity() {
        return opacity;
    }

    public String getAbility() {
        return ability;
    }
}
