import java.awt.Color;
import java.awt.Graphics2D;

public class brickBrown extends Brick{
	
	private Window game;
	
	public brickBrown() { super(); }
	
	public brickBrown(int X, int Y, Window game) {
		super(X, Y);
		
		this.Lives = 1;
		this.game = game;
		this.points = 0;
	}
	
	@Override
	public void paint(Graphics2D drawer) {
		drawer.setColor(Color.decode("#964b00"));
		
		drawer.fillRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
		
		drawer.setColor(Color.BLACK);
		
		drawer.drawRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
		
	}
	
	public void effect() {
		
		int actualY = this.Y;
		
		for (int x = 0; x < game.arrayBricks.size(); x++) {
			if (game.arrayBricks.get(x).getY() == actualY) {
				
				if (!(game.arrayBricks.get(x) instanceof brickBrown)) {
					x = game.circle.damageBricks(x);
					
					if (game.arrayBricks.get(x).Lives <= 0 && !(game.arrayBricks.get(x) instanceof brickRed) &&
							!(game.arrayBricks.get(x) instanceof brickBlue)) {
						game.arrayBricks.remove(x);
						
						if (x != 0) {							
							x--;
						}
					}
				}
				else {
					game.arrayBricks.remove(x);
					x--;
				}
			}
		}
	}
}