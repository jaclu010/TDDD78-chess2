package se.liu.ida.jaclu010carfo452.tddd78.chess2;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class SoundHandler
{
    private static final SoundHandler INSTANCE = new SoundHandler();
    private Map<String, AudioClip> sounds = new HashMap<>();

    private SoundHandler() {
        loadSounds();
    }

    private void loadSounds() {
        try {
            URL url;
            url = new File("assets/sounds/laser.wav").toURI().toURL();
            AudioClip laserClip = Applet.newAudioClip(url);
            sounds.put("LASER", laserClip);

            url = new File("assets/sounds/freeze.wav").toURI().toURL();
            AudioClip freezeClip = Applet.newAudioClip(url);
            sounds.put("FREEZE", freezeClip);

            url = new File("assets/sounds/gun_fire.wav").toURI().toURL();
            AudioClip doubleDamageClip = Applet.newAudioClip(url);
            sounds.put("DOUBLE_DAMAGE", doubleDamageClip);

            url = new File("assets/sounds/ambulance.wav").toURI().toURL();
            AudioClip healClip = Applet.newAudioClip(url);
            sounds.put("HEAL", healClip);

            url = new File("assets/sounds/punch.wav").toURI().toURL();
            AudioClip knockBackClip = Applet.newAudioClip(url);
            sounds.put("KNOCK_BACK", knockBackClip);

            url = new File("assets/sounds/fanfare.wav").toURI().toURL();
            AudioClip summonDefenceClip = Applet.newAudioClip(url);
            sounds.put("SUMMON_DEFENCE", summonDefenceClip);


        } catch(MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void playSound(String s) {
            sounds.get(s).play();
    }

    public static SoundHandler getInstance() {
	return INSTANCE;
    }


}
