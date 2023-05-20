import java.awt.Color;
import java.awt.Graphics2D;

public class brickRed extends Brick {
		
	private int Velocity = 2;
	private int damage = 4;
	
	public brickRed() { super(); }
	
	public brickRed(int X, int Y) {
		super(X, Y);
		
		this.Lives = 2;
		this.points = 25;
	}
	
	public int getVelocity() {
		return this.Velocity;
	}
	
	public void setvelocity(int Velocity) {
		this.Velocity = Velocity;
	}

	public int getDamage() {
		return this.damage;
	}
	
	public void effect() {
		this.Y = this.Y + this.Velocity;
	}
	
	public void paint(Graphics2D drawer) {
		if (this.Lives == 2) {
			drawer.setColor(Color.RED);
			
			drawer.fillRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
		}
		else if (this.Lives == 1) {
			drawer.setColor(Color.decode("#FF3C3C"));
			
			drawer.fillRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
		}
		else {
			drawer.setColor(Color.decode("#FFCDCD"));
			
			drawer.fillRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
		}
		
		drawer.setColor(Color.BLACK);
		
		drawer.drawRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
	}
}