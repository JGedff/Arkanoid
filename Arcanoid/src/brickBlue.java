import java.awt.Color;
import java.awt.Graphics2D;

public class brickBlue extends Brick {
	
	private boolean bonus;
	private int YDirection = 4;
	
	public brickBlue() { super(); }
	
	public brickBlue(int X, int Y) {
		super(X, Y);
		
		this.Lives = 1;
		this.points = 40;
	}
	
	public void effect() {
		this.Y = this.Y + this.YDirection;
	}
	
	public void paint(Graphics2D drawer) {
		if (bonus) {
			drawer.setColor(Color.decode("#00FFFF"));
			
			drawer.fillOval(this.X, this.Y, this.WIDTH / 2, this.WIDTH / 2);
			
			drawer.setColor(Color.BLACK);
			
			drawer.drawOval(this.X, this.Y, this.WIDTH / 2, this.WIDTH / 2);
		}
		else {			
			drawer.setColor(Color.BLUE);
			
			drawer.fillRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
			
			drawer.setColor(Color.BLACK);
			
			drawer.drawRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
		}
	}
	
	public void setBonus(boolean bonus) {
		this.bonus = bonus;
	}
}