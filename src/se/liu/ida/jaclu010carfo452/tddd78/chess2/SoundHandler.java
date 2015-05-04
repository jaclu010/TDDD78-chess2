package se.liu.ida.jaclu010carfo452.tddd78.chess2;


import javax.sound.sampled.Clip;

public final class SoundHandler
{
    private static final SoundHandler INSTANCE = new SoundHandler();
    private final Clip

    private SoundHandler() {

    }

    public static SoundHandler getInstance() {
	return INSTANCE;
    }


}
