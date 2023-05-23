import java.awt.Color;
import java.awt.Graphics2D;

public class brickGreen extends Brick {

	public brickGreen() { super(); }
	
	public brickGreen(int X, int Y) {
		super(X, Y);
		
		this.Lives = 3;
		this.points = 10;
	}
	
	public void effect() {
		
	}
	
	public void paint(Graphics2D drawer) {
		if (this.Lives == 3) {
			drawer.setColor(Color.decode("#00CC00"));
			
			drawer.fillRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
		}
		else if (this.Lives == 2) {
			drawer.setColor(Color.GREEN);
			
			drawer.fillRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
		}
		else {
			drawer.setColor(Color.decode("#A9FFA9"));
			
			drawer.fillRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
		}
		
		drawer.setColor(Color.BLACK);
		
		drawer.drawRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
	}
}