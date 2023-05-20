import java.awt.Color;
import java.awt.Graphics2D;

public class brickGray extends Brick{

	private Window game;
	
	public brickGray () { super(); }
	
	public brickGray(int X, int Y, Window game) {
		super(X, Y);
		
		this.game = game;
		this.Lives = 1;
		this.points = 0;
	}

	public void effect() {
		
		int actualX = this.X;
		
		for (int x = 0; x < game.arrayBricks.size(); x++) {
			if (game.arrayBricks.get(x).getX() == actualX) {
				
				if (!(game.arrayBricks.get(x) instanceof brickGray) && !(game.arrayBricks.get(x) instanceof brickBrown)) {
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

	@Override
	public void paint(Graphics2D drawer) {
		drawer.setColor(Color.GRAY);
		
		drawer.fillRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
		
		drawer.setColor(Color.BLACK);
		
		drawer.drawRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
		
	}
}
