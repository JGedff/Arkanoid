import java.awt.Color;
import java.awt.Graphics2D;

public class brickYellow extends Brick {
	
	private static final int ADDLIVES = 1;
	private Window game;
	
	public brickYellow() { super(); }
	
	public brickYellow(int X, int Y, Window game) {
		super(X, Y);
		
		this.Lives = 1;
		this.game = game;
		this.points = 0;
	}
	
	public void effect() {
		game.setOneUpTime(game.getInitTimer());
		game.setOneUp(true);
		game.user.setLives(game.user.getLives() + ADDLIVES);
	}

	public void paint(Graphics2D drawer) {
		drawer.setColor(Color.decode("#FFFF00"));
		
		drawer.fillRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
		
		drawer.setColor(Color.BLACK);
		
		drawer.drawRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
	}
}