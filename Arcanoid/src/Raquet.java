import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Raquet {
	private static final int Y = 700;
	private static final int WITH = 100;
	private static final int HEIGHT = 15;
	
	private int X = 270;
	private int Xdirection = 0;
	private Window game;
	private boolean powerup = false;
	private int powerupTimer = 100;

	public Raquet(Window game) {
		this.game = game;
	}

	public void move() {
		if (X + Xdirection > 0 && X + Xdirection < game.getWidth() - WITH) //If the racket is inside the window
			X = X + Xdirection;
	}

	public void paint(Graphics2D drawer) {
		drawer.fillRect(X, Y, WITH, HEIGHT); //Paints the racket inside the window
	}
	
	public void keyReleased(KeyEvent e) { //When a key is released
		Xdirection = 0;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) //if the key pressed is the left key
			Xdirection = -1;
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) //if the key pressed is the right key
			Xdirection = 1;
	}

	public Rectangle getBounds() {
		return new Rectangle(X, Y, WITH, HEIGHT); //Returns the hitbox of the racket
	}

	public int getTopY() {
		return Y - HEIGHT; //Returns the bottom of the racket
	}

	public boolean isPowerup() {
		return powerup;
	}

	public void setPowerup(boolean powerup) {
		this.powerup = powerup;
	}

	public int getPowerupTimer() {
		return powerupTimer;
	}

	public void setPowerupTimer(int powerupTimer) {
		this.powerupTimer = powerupTimer;
	}

	public int getX() {
		return this.X;
	}
	
	public void setX(int X) {
		this.X = X;
	}
}