import java.awt.Color;
import java.awt.Graphics2D;

public class brickWhite extends Brick {
	
	public brickWhite() { super(); }
	
	public brickWhite(int X, int Y) {
		super(X, Y);
		
		this.Lives = 1;
		this.points = 0;
	}
	
	@Override
	public void effect() {
		this.Y = this.Y + 5;
	}
	
	public void paint(Graphics2D drawer) {
		drawer.setColor(Color.WHITE);
		
		drawer.fillRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
		
		drawer.setColor(Color.BLACK);
		
		drawer.drawRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
	}
	
	public void destroy() {	}
}