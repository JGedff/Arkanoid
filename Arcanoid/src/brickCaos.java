import java.awt.Color;
import java.awt.Graphics2D;

public class brickCaos extends Brick {
	private Window game;
	private int color = 0;
	
	public brickCaos () { super(); }
	
	public brickCaos(int X, int Y, Window game) {
		super(X, Y);
		
		this.Lives = 0;
		this.game = game;
		this.points = 0;
	}
	
	public void effect() {
		game.recreateBricks();
	}
	
	public void paint(Graphics2D drawer) {
		
		switch(this.color) {
		case 0: drawer.setColor(Color.BLUE); break;
		case 1: drawer.setColor(Color.RED); break;
		case 2: drawer.setColor(Color.GREEN); break;
		case 3: drawer.setColor(Color.WHITE); break;
		case 4: drawer.setColor(Color.YELLOW); break;
		case 5: drawer.setColor(Color.ORANGE); break;
		case 6: drawer.setColor(Color.decode("#964b00")); break;
		}
		
		this.color = (int) (Math.random()*6+1);
		
		drawer.fillRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
		
		drawer.setColor(Color.BLACK);
		
		drawer.drawRect(this.X, this.Y, this.WIDTH, this.HEIGHT);
	}
	
	public void destroy() { }
}