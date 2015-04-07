package chess2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AnimateHandler
{

    private final Action doOneStep = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if(!board.isGameOver()) {
                board.tick();
                score.setText("Score:\n"+ board.getScore());
            } else {
                clockTimer.stop();
                gameLossPane();
            }
        }
    };

    public void newTimer(){
        clockTimer = new Timer(timeScope, doOneStep);
        clockTimer.setCoalesce(true);
    }
}
