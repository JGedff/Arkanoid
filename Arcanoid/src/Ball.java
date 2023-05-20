import java.awt.Graphics2D;
import java.awt.Rectangle;
import Sounds.Sound;

@SuppressWarnings("all")
public class Ball {
	private static final int DIAMETER = 30;
	private int X = 254;
	private int Y = 680;
	private int Xdirection = 1;
	private int Ydirection = 1;
	private Window game;
	
	public Ball(Window game) {
		this.game= game;
	}
	
	public void setX(int X) {
		this.X = X;
	}
	
	public int getX() {
		return this.X;
	}
	
	public void setY(int Y) {
		this.Y = Y;
	}
	
	public int getY() {
		return this.Y;
	}
	
	public void setYDirection(int Ydirection) {
		this.Ydirection = Ydirection;
	}
	
	public int getYdirection() {
		return this.Ydirection;
	}
	
	public void setXDirection(int Xdirection) {
		this.Xdirection = Xdirection;
	}
	
	public int getXdirection() {
		return this.Xdirection;
	}

	public void move() {
		
		if (this.X + this.Xdirection < 0) {
			this.Xdirection = 1;
			Sound.BALLBOUNCE.play();
		}
		
		if (this.X + this.Xdirection > game.getWidth() - DIAMETER) {
			this.Xdirection = -1;
			Sound.BALLBOUNCE.play();
		}
		
		if (this.Y + this.Ydirection < 0) {
			this.Ydirection = 1;
			Sound.BALLBOUNCE.play();
		}
		
		if (this.Y + this.Ydirection > game.getHeight() - DIAMETER) {
			game.user.setLives(game.user.getLives() - 1);
			
			Sound.LOSELIVE.play();
			
			if (game.user.getLives() <= 0) {
				if (game.getTutorial()) {
					game.user.setLives(game.user.getInitLives());
				}
				else {					
					game.gameOver();
				}
			}
			else {
				game.restart();
			}
		}
		
		if (collision()){
			this.Ydirection = -1;
			Sound.BALLBOUNCE.play();
		}
		
		collisionBrick();
		
		this.X = this.X + this.Xdirection;
		this.Y = this.Y + this.Ydirection;
	}

	public void collisionBrick() {
		
		int power = 0;
		
		for(int x = 0; x < game.arrayBricks.size(); x++) {
			
			if (this.getBounds().intersects(game.arrayBricks.get(x).getBounds())) {				
				
				if (game.arrayBricks.get(x) instanceof brickWhite || game.arrayBricks.get(x) instanceof brickOrange) {
					game.arrayBricks.get(x).effect();
					
					if (game.arrayBricks.get(x) instanceof brickOrange) {						
						this.Y = this.Y + 10;
						this.Ydirection = -this.Ydirection;
					}
				}
				
				if (game.arrayBricks.get(x).Lives > 0) {
					this.Ydirection = -this.Ydirection;
					
					x = damageBricks(x);
				}
			}
		}
	}
	
	public int damageBricks(int x) {
		int power = 0;
		int points = 0;
		
		if (!(game.arrayBricks.get(x) instanceof brickWhite && !(game.arrayBricks.get(x) instanceof brickBrown)
				&& !(game.arrayBricks.get(x) instanceof brickGray)) && !game.getTutorial()) {
			int coins = game.arrayBricks.get(x).generateCoin();
			
			if (coins > 0) {				
				game.setCoins(coins);
				game.user.setCoins(game.user.getCoins() + coins);
			}
		}
		
		while (power < game.user.getPower() && game.arrayBricks.get(x).getLives() > 0) {
			game.arrayBricks.get(x).destroy();
			power++;
			points = points + game.arrayBricks.get(x).points;
		}
		
		game.user.setPoints(game.user.getPoints() + points);
		
		if (game.arrayBricks.get(x).Lives <= 0) {
			
			if (game.arrayBricks.get(x) instanceof brickBlue) {
				brickBlue brick = (brickBlue) (game.arrayBricks.get(x));
				
				brick.setBonus(true);
				
				Sound.BREAKBRICK.play();
				Sound.BONUSFALL.play();
			}
			else if (game.arrayBricks.get(x) instanceof brickGreen) {
				game.arrayBricks.remove(x);
				
				x--;
				
				Sound.BREAKBRICK.play();
			}
			else if (game.arrayBricks.get(x) instanceof brickYellow) {
				game.arrayBricks.get(x).effect();
				
				game.arrayBricks.remove(x);
				
				x--;
				
				Sound.BREAKBRICK.play();
			}
			else if (game.arrayBricks.get(x) instanceof brickBrown) {
				Sound.ELIMINATEBRICKS.play();
				
				game.arrayBricks.get(x).effect();
				
				x--;
			}
			else if (game.arrayBricks.get(x) instanceof brickGray) {
				Sound.ELIMINATEBRICKS.play();
				
				game.arrayBricks.get(x).effect();
				
				x--;
			}
			else if (game.arrayBricks.get(x) instanceof brickRed) {
				Sound.BREAKBRICK.play();
				Sound.BRICKFALL.play();
			}
			else {
				Sound.GLITCH.play();
			}
			
			game.checkBlocks();
		}
		else {
			if (!(game.arrayBricks.get(x) instanceof brickOrange)) {
				Sound.BRICKHIT.play();
			}
		}
		
		return x;
	}
	
	public boolean collision() {
		return game.bar.getBounds().intersects(getBounds());
	}

	public void paint(Graphics2D drawer) {
		drawer.fillOval(this.X, this.Y, DIAMETER, DIAMETER);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(this.X, this.Y, DIAMETER, DIAMETER);
	}
}