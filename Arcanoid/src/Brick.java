import java.awt.Graphics2D;
import java.awt.Rectangle;

abstract public class Brick {
	
	protected int X;
	protected int Y;
	protected int Lives;
	protected int points;
	protected final int WIDTH = 100;
	protected final int HEIGHT = 30;
	
	public Brick() { }
	
	public Brick(int X, int Y) {
		this.X = X;
		this.Y = Y;
	}

	public int getX() {
		return this.X;
	}
	
	public void setX(int X) {
		this.X = X;
	}

	public int getLives() {
		return this.Lives;
	}
	
	public void setLives(int Lives) {
		this.Lives = Lives;
	}
	
	public int getY() {
		return this.Y;
	}
	
	public void setY(int Y) {
		this.Y = Y;
	}
	
	public int getWidth() {
		return this.WIDTH;
	}
	
	public int getHeight() {
		return this.HEIGHT;
	}
	
	public int getPoints() {
		return this.points;
	}

	public void destroy() {
		this.Lives = this.Lives - 1;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(X, Y, WIDTH, HEIGHT); //Returns the hitbox of the racket
	}
	
	public int generateCoin() {
		int randomNumber = (int) (Math.random()*5 + 1);
		
		if (randomNumber == 5) {
			return (int) (Math.random()*3 + 1);
		}
		return 0;
	}
	
	//All child classes MUST have this methods
	abstract public void effect();
	abstract public void paint(Graphics2D drawer);
}