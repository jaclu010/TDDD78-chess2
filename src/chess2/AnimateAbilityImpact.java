package chess2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AnimateAbilityImpact
{
    private int animationY;
    private Timer timer;
    private float opacity = 1.0f;
    private String ability;

    private static final int DELAY = 10;
    private static final int PPF = 10;
    private static final float OPF = 0.1f;

    public AnimateAbilityImpact(ChessComponent gameArea, ChessBoard cB) {
        animationY = cB.getTargetY();
        ability = cB.getSelected().getAbility().getAT().name();

        final Action doEndAnim = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                stopAnimation();
                animationY+=PPF;
                opacity -=OPF;
                if(opacity>0) {
                    gameArea.repaint();
                } else {
                    stopAnimation();
                }
            }
        };

        timer = new Timer(DELAY, doEndAnim);
        timer.setCoalesce(true);
    }

    public void startAnimation(){
        timer.start();
    }

    public void stopAnimation(){
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
