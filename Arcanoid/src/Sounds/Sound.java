package Sounds;

import java.applet.Applet;
import java.applet.AudioClip;

@SuppressWarnings("all")
public class Sound {
	
	public static final AudioClip BACKGROUND = Applet.newAudioClip(Sound.class.getResource("background.wav"));
	public static final AudioClip GAMEOVER = Applet.newAudioClip(Sound.class.getResource("gameOver.wav"));
	public static final AudioClip GLITCH = Applet.newAudioClip(Sound.class.getResource("glitch.wav"));
	public static final AudioClip LOSELIVE = Applet.newAudioClip(Sound.class.getResource("les1Live.wav"));
	public static final AudioClip NEXTLEVEL = Applet.newAudioClip(Sound.class.getResource("nextLevel.wav"));
	public static final AudioClip CONTINUEPLAY = Applet.newAudioClip(Sound.class.getResource("continue.wav"));
	
	public static final AudioClip BRICKHIT = Applet.newAudioClip(Sound.class.getResource("brickHit.wav"));
	public static final AudioClip BREAKBRICK = Applet.newAudioClip(Sound.class.getResource("breakBrick.wav"));
	public static final AudioClip BRICKFALL = Applet.newAudioClip(Sound.class.getResource("brickFall.wav"));
	
	public static final AudioClip BONUSFALL = Applet.newAudioClip(Sound.class.getResource("bonusFall.wav"));

	public static final AudioClip BALLBOUNCE = Applet.newAudioClip(Sound.class.getResource("ballBounce.wav"));

	public static final AudioClip CONGRATS = Applet.newAudioClip(Sound.class.getResource("success.wav"));

	public static final AudioClip ELIMINATEBRICKS = Applet.newAudioClip(Sound.class.getResource("destroyBricks.wav"));

	public static final AudioClip BUY = Applet.newAudioClip(Sound.class.getResource("buy.wav"));
	public static final AudioClip NOTENOUGHMONEY = Applet.newAudioClip(Sound.class.getResource("notEnoughMoney.wav"));
	public static final AudioClip MAXED = Applet.newAudioClip(Sound.class.getResource("maxed.wav"));
}