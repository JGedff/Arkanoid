public class Player {
	
	private int Lives = 0;
	private int initLives = 3;
	private int resistance = 0;
	private int Points = 0;
	private int coins = 0;
	private int power = 1;
	private String userName;

	public Player() { }
	
	public Player(String userName) {
		this.userName = userName;
		
		if (userName.equals("Tutorial")) {
			this.coins = 10000;
		}

		this.Lives = this.initLives;
		this.power = 1;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPoints() {
		return this.Points;
	}

	public void setPoints(int points) {
		this.Points = points;
	}

	public int getLives() {
		return this.Lives;
	}

	public void setLives(int lives) {
		this.Lives = lives;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}
	
	public int getPower() {
		return this.power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}

	public int getInitLives() {
		return this.initLives;
	}
	
	public void setInitLives(int initLives) {
		this.initLives = initLives;
		this.Lives = this.initLives;
	}
	
	public int getResistance() {
		return this.resistance;
	}
	
	public void setResistance(int resistance) {
		this.resistance = resistance;
	}
	
}