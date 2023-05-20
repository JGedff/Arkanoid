import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Sounds.Sound;

@SuppressWarnings("all")
public class Window extends JPanel {
	
	Player user = new Player();
	Ball circle = new Ball(this); //Creates a circle in the actual window
	Raquet bar = new Raquet(this); //Creates a bar in the actual window
	ArrayList<Brick> arrayBricks = new ArrayList<Brick>();
	Shop shop = new Shop(this);
	
	private final int INITTIMERS = 1000;
	private final int SPEEDTIMER = 2500;

	private static boolean pause;
	
	private boolean Tutorial;

	private int minNumber;
	private int minScore;
	
	private boolean oneUp;
	private int oneUpTime = INITTIMERS;
	
	private static boolean Shopping = false;
	
	private int blocks = 1;
	
	private int Money = 0;
	private boolean coins = false;
	private int coinsTime = INITTIMERS;
	
	private static int powerTimes = 0;
	private static int livesTimes = 0;
	private static int resistanceTimes = 0;
	
	public int getInitTimer() {
		return this.INITTIMERS;
	}

	public boolean getTutorial() {
		return this.Tutorial;
	}

	public boolean isOneUp() {
		return this.oneUp;
	}
	
	public void setOneUp(boolean oneUp) {
		this.oneUp = oneUp;
	}

	public int getOneUpTime() {
		return this.oneUpTime;
	}
	
	public void setOneUpTime(int oneUpTime) {
		this.oneUpTime = oneUpTime;
	}

	public Window() {
		
		addKeyListener(new KeyListener() { //Creates a key listener with the configuration inside the {}
			
			@Override
			public void keyTyped(KeyEvent e) { //When a key is taped
			}

			@Override
			public void keyReleased(KeyEvent e) { //When a key is released
				bar.keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) { //When a key is pressed
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Window.pause = true;
				}
				else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					Window.pause = false;
				}
				else if (e.getKeyCode() == KeyEvent.VK_R && Shopping && Tutorial) {
					Window.resistanceTimes++;
					
					if (Window.resistanceTimes == 1) {
						JOptionPane.showMessageDialog(null, "With 0 resistance, the red bricks will kill you when they hit the player while falling");
						JOptionPane.showMessageDialog(null, "With 1 resistance, the red bricks will rest you 4 lives");
					}
					else if (Window.resistanceTimes == 2){
						JOptionPane.showMessageDialog(null, "With 2 resistance, the red bricks will rest you 3 lives");
					}
					else if (Window.resistanceTimes == 3) {
						JOptionPane.showMessageDialog(null, "With 3 resistance, the red bricks will rest you 2 lives");
					}
					else if (Window.resistanceTimes == 4) {
						JOptionPane.showMessageDialog(null, "With 4 resistance, the red bricks will rest you 1 lives");
					}
					
					shop.addResistance();
				}
				else if (e.getKeyCode() == KeyEvent.VK_L && Shopping && Tutorial) {
					if (Window.livesTimes == 0) {
						JOptionPane.showMessageDialog(null, "You bought one initial live.\nYou will have to end a game to see the new initial lives");
						Window.livesTimes++;
					}
					
					shop.addInitLives();
				}
				else if (e.getKeyCode() == KeyEvent.VK_P && Shopping && Tutorial) {
					if (Window.powerTimes == 0) {
						JOptionPane.showMessageDialog(null, "You bought power.\nEach time you hit a brick, this one will loose the same lives as your power\nThis afects the damage that causes the GRAY and BROWN bricks");
						Window.powerTimes++;
					}
					
					shop.addPower();
				}
				else if (e.getKeyCode() == KeyEvent.VK_R && Shopping) {
					shop.addResistance();
				}
				else if (e.getKeyCode() == KeyEvent.VK_L && Shopping) {
					shop.addInitLives();
				}
				else if (e.getKeyCode() == KeyEvent.VK_P && Shopping) {
					shop.addPower();
				}
				else if (e.getKeyCode() == KeyEvent.VK_Q && Tutorial) {
					Window.pause = true;
					JOptionPane.showMessageDialog(null, "Hope the tutorial helped you", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_Q && Shopping) {
					Window.Shopping = false;
				}
				else {					
					bar.keyPressed(e);
				}
			}
		});
		setFocusable(true); //Let the window keep the button pressed between different frames
		
		Sound.BACKGROUND.loop();
	}
	
	public void setCoins(int coin) {
		this.coins = true;
		this.coinsTime = this.INITTIMERS;
		this.Money = coin;
	}
	
	public void move() { //Makes the circle and the ball move
		circle.move();
		bar.move();
		
		for(int x = 0; x < arrayBricks.size(); x++) {
			if (arrayBricks.get(x) instanceof brickRed && arrayBricks.get(x).Lives <= 0) {
				arrayBricks.get(x).effect();

				if (arrayBricks.get(x).getBounds().intersects(bar.getBounds())) {

					brickRed brick = new brickRed();
					
					arrayBricks.remove(x);
					
					if (!this.Tutorial) {
						if (user.getResistance() < 1) {						
							gameOver();
						}
						else {
							user.setLives(user.getLives() - (brick.getDamage() - (user.getResistance() - 1)));
							
							if (user.getLives() <= 0) {
								gameOver();
							}
						}
					}
				}
				else if (arrayBricks.get(x).getY() >= 800) {
					arrayBricks.remove(x);
					
					checkBlocks();
				}
			}
			else if (arrayBricks.get(x) instanceof brickBlue && arrayBricks.get(x).Lives <= 0) {
				arrayBricks.get(x).effect();

				if (arrayBricks.get(x).getBounds().intersects(bar.getBounds())) {
					bar.setPowerup(true);
					bar.setPowerupTimer(bar.getPowerupTimer() + this.SPEEDTIMER);
					
					arrayBricks.remove(x);
					checkBlocks();
				}
				else if (arrayBricks.get(x).getY() >= 800) {
					arrayBricks.remove(x);
					
					checkBlocks();
				}
			}
		}
	}
	
	public void paintPause(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		
		g2d.setFont(new Font("DialogInput", Font.BOLD, 30));
		g2d.drawString("PAUSE", 250, 250);

		g2d.drawString("PRESS SPACE TO CONTINUE", 100, 475);
	}
	
	public void paintOneUp(Graphics2D g2d) {
		this.oneUpTime = this.oneUpTime - 1;
		
		g2d.setColor(Color.YELLOW);
		
		g2d.setFont(new Font("DialogInput", Font.BOLD, 30));
		g2d.drawString("+ 1 UP!", 250, 280);
		
		if (this.oneUpTime == 0) {
			this.oneUp = false;
		}
	}
	
	public void paintCoins(Graphics2D g2d) {
		this.coinsTime = this.coinsTime - 1;
		
		g2d.setColor(Color.YELLOW);
		
		g2d.setFont(new Font("DialogInput", Font.ITALIC, 30));
		g2d.drawString("+ " + this.Money + " coins!", 230, 440);
		
		if (this.coinsTime == 0) {
			this.coins = false;
		}
	}
	
	public void paintUserInformation(Graphics2D g2d) {
		g2d.setFont(new Font("DialogInput", Font.PLAIN, 17));
		g2d.drawString("Name: " + user.getUserName(), 30, 740);

		g2d.drawString("Lives: " + user.getLives(), 190, 740);

		g2d.drawString("Money: " + user.getCoins(), 320, 740);
		
		g2d.drawString("Points : " + user.getPoints(), 450, 740);
	}
	
	@Override
	public void paint(Graphics drawer) {
		super.paint(drawer); //Calls the class JPanel to paint the window all white
		
		Graphics2D g2d = (Graphics2D) drawer; //Creates a drawer with the name g2d

		if (this.Shopping) {
			shop.paintShop(g2d);
			paintUserInformation(g2d);
		}
		else {
			if (this.pause) {
				paintPause(g2d);
			}
			
			if (this.oneUp) {
				paintOneUp(g2d);
			}
			
			if (this.coins) {
				paintCoins(g2d);
			}
			
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Makes the objects more similar to the reality
			
			g2d.setColor(Color.GRAY);
			g2d.setFont(new Font("DialogInput", Font.BOLD, 30));
			
			if (this.Tutorial) {
				g2d.drawString("Tutorial: " + this.blocks, 200, 350);
				
				g2d.drawString("Press Q to leave the tutorial", 40, 550);
			}
			else {
				g2d.drawString("Level: " + this.blocks, 230, 350);
			}
			
			for(int x = 0; x < arrayBricks.size(); x++) {
				arrayBricks.get(x).paint(g2d);
			}
			
			g2d.setColor(Color.WHITE);
			circle.paint(g2d); //Paints the circle object
			bar.paint(g2d); //Paints the bar object
			
			paintUserInformation(g2d);
		}
	}
	
	public void gameOver() {
		int continueGame = 0;
		
		JOptionPane.showMessageDialog(null, "Your score: " + user.getPoints());
		
		Sound.CONGRATS.play();
		
		if (user.getPoints() > minScore) {
			JSONParser parser = new JSONParser();
			
			try {
				JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src\\score\\highscore.json"));
				
				JSONObject userData = new JSONObject();
				
				userData.put("Name", user.getUserName());
				userData.put("Points", user.getPoints());
				userData.put("Lives", user.getInitLives());
				userData.put("Resistance", user.getResistance());
				userData.put("Power", user.getPower());
				userData.put("Coins", user.getPoints());
				
				if (jsonArray.size() >= 10 && jsonArray.size() > 0) {
					jsonArray.remove(minNumber);
				}
				jsonArray.add(userData);
				
				getMinScore(jsonArray);
				
				String records = "NEW HIGHSCORE! \n";
				
				for(int x = 0; x < jsonArray.size(); x++) {
					JSONObject userInfo = (JSONObject) jsonArray.get(x);
					
					String info = userInfo.get("Name").toString();
					
					int score = Integer.parseInt(userInfo.get("Points").toString());
					
					records = records + " "+info+" "+score+" \n";
				}
				
				JOptionPane.showMessageDialog(null, records);
				
				FileWriter fileWritter = new FileWriter("src\\score\\highscore.json");
	            fileWritter.write(jsonArray.toJSONString());
	            
	            fileWritter.flush();
                fileWritter.close();
			} catch (IOException | ParseException e) {
				JOptionPane.showMessageDialog(null, "There was an unexpected error", "ERROR", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		
		Sound.BACKGROUND.stop();
		Sound.CONTINUEPLAY.loop();

		continueGame = JOptionPane.showConfirmDialog(null, "Do you want to restart?", "Game Over", JOptionPane.YES_NO_OPTION);
		
		if (continueGame == JOptionPane.YES_OPTION) {
			int goShopping = 0;
			
			Sound.CONTINUEPLAY.stop();
			Sound.BACKGROUND.loop();
			
			goShopping = JOptionPane.showConfirmDialog(null, "Enter the shop?", "Shop", JOptionPane.YES_NO_OPTION);
			
			if (goShopping == JOptionPane.YES_OPTION) {
				this.Shopping = true;

				this.pause = true;
			}
			
			restartGame();
		}
		else {
			Sound.CONTINUEPLAY.stop();
			Sound.GAMEOVER.play();
			JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION); //Creates a window that says Game Over
			System.exit(ABORT); //Stops the game
		}
	}

	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException, ParseException {

		Window exe = new Window();
		exe.start();
		
	}
	
	public void restartGame() {
		restart();
		
		this.blocks = 1;
		this.bar.setPowerup(false);
		this.bar.setPowerupTimer(0);
		
		arrayBricks.clear();
		
		user.setLives(user.getInitLives());
		user.setPoints(0);
		createBricks();
		
		try {
			gameMove();
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "There was an unexpected error", "ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(ABORT); //Stops the game
		}
	}
	
	public void getMinScore(JSONArray jsonArray) throws FileNotFoundException, IOException, ParseException {

		JSONObject jsonInit = (JSONObject) jsonArray.get(0);
		
		int Init = Integer.parseInt(jsonInit.get("Points").toString());
		
		minNumber = 0;
		minScore = Init;
		
		if (jsonArray.size() > 0) {
			for(int x = 1; x < jsonArray.size(); x++) {
				JSONObject score = (JSONObject) jsonArray.get(x);
				
				int userScore = Integer.parseInt(score.get("Points").toString());
				
				if (userScore < minScore) {
					minScore = userScore;
					minNumber = x;
				}
			}
		}
		else {
			minScore = 0;
		}
	}
	
	public void start() throws FileNotFoundException, IOException, ParseException {
		
		int tutorial = JOptionPane.showConfirmDialog(null, "Do you want to do the tutorial?", "Tutorial", JOptionPane.YES_NO_OPTION);
		
		this.setBackground(Color.BLACK);
		
		JFrame frame = new JFrame("Arcanoid"); //Creates a window with the name Arcanoid
		
		frame.setBackground(Color.BLACK);

		frame.add(this); //Ads the window game into the static window frame
		frame.setSize(610, 800); //Sets the size of the window as 610 of width and 800 of height
		frame.setResizable(false);
		frame.setVisible(true); //Makes the window visible
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Makes that when you click the close button, the window closes
		
		if (tutorial != JOptionPane.YES_OPTION) {
			playGame();
		}
		else {
			playTutorial();
		}
	}
	
	public void playTutorial() {
		user = new Player("Tutorial");
		
		JOptionPane.showMessageDialog(null, "If the ball touches the ground, you will lose one live");
		
		JOptionPane.showMessageDialog(null, "You can pause the game by pressing ESCAPE");
		
		int tutorialLevel = this.createBricksTutorial();
		
		this.Tutorial = true;
		
		try {
			gameMove();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playGame() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		
		JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src\\score\\highscore.json"));
		
		String userName = "";
		Player newUser;
		
		int reload = JOptionPane.NO_OPTION;
		
		if (jsonArray.size() > 0) {
			getMinScore(jsonArray);

			reload = JOptionPane.showConfirmDialog(null, "Do you want to reload another user information?", "Reload", JOptionPane.YES_NO_OPTION);
		}
		
		if (reload == JOptionPane.YES_OPTION) {
			
			String message = "";
			
			for(int x = 0; x < jsonArray.size(); x++) {
				JSONObject userData = (JSONObject) jsonArray.get(x);
				
				message += x + 1 + ". ";
				message += "Name: " + userData.get("Name").toString() + "\n";
				message += "Score: " + userData.get("Points").toString() + "\n\n";
			}
			
			boolean isValid = true;
			String userLoad;
			
			do {
				userLoad = JOptionPane.showInputDialog(message);
				
				if (userLoad != null) {					
					try {
						int value = Integer.parseInt(userLoad);
						
						if (value > 0 && value <= 10) {
							isValid = loadUserInt(value, isValid, jsonArray);
						}
						else {
							isValid = false;
						}
					}
					catch (Exception e) {
						isValid = loadUserString(userLoad, isValid, jsonArray);
					}
				}
				else {
					Sound.MAXED.play();
					
					JOptionPane.showMessageDialog(null, "ERROR\nUser name not valid\nYou MUST write a user name", "ERROR", JOptionPane.CANCEL_OPTION);
					userName = nameUser();
										
					user = new Player(userName.toUpperCase());
					isValid = true;
				}
			} while (!isValid);
		}
		else {
			userName = nameUser();

			user = new Player(userName);
		}
		
		this.createBricks();
		
		try {
			gameMove();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String nameUser() {
		boolean isString = false;
		String userName = "";

		do {
			userName = JOptionPane.showInputDialog("Write 3 Capital letters");
			
			if (userName != null) {
				try {
					int value = Integer.parseInt(userName);
					
					isString = false;	
				}
				catch (Exception e) {
					isString = true;
				}
			}
			else {
				Sound.MAXED.play();
				
				JOptionPane.showMessageDialog(null, "ERROR\nUser name not valid\nYou MUST write a user name", "ERROR", JOptionPane.CANCEL_OPTION);
			}
		} while (userName == null || userName.length() > 3 || userName.length() < 1 || !isString);

		return userName.toUpperCase();
	}

	public boolean loadUserString(String actualUser, boolean isValid, JSONArray jsonArray) {
		for (int x = 0; x < jsonArray.size(); x++) {
			JSONObject update = (JSONObject) (jsonArray.get(x));
			
			if (update.get("Name").equals(actualUser.toUpperCase())) {
				updateUser(update);
				
				return true;
			}
		}
		
		return false;
	}

	public boolean loadUserInt(int value, boolean isValid, JSONArray jsonArray) {
		
		try {
			JSONObject loadUser = (JSONObject) jsonArray.get(value - 1);
			updateUser(loadUser);

			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public void updateUser(JSONObject loadUser) {
		user.setUserName(loadUser.get("Name").toString());
		user.setPower(Integer.parseInt(loadUser.get("Power").toString()));
		user.setCoins(Integer.parseInt(loadUser.get("Coins").toString()));
		user.setResistance(Integer.parseInt(loadUser.get("Resistance").toString()));
		user.setInitLives(Integer.parseInt(loadUser.get("Lives").toString()));
	}

	public void gameMove() throws InterruptedException {
				
		while (true) { //Forever
			if (!this.pause) {
				this.move(); //Move the objects of the window
				
				if (this.bar.isPowerup()) {
					Thread.sleep(1);
					this.bar.setPowerupTimer(this.bar.getPowerupTimer() - 1);
					
					if (this.bar.getPowerupTimer() == 0) {
						this.bar.setPowerup(false);
					}
				}
				else {
					Thread.sleep(3);
				}
			}
			this.repaint(); //Repaint the window
		}
	}
	
	public void restart() {
		for (int x = 0; x < arrayBricks.size(); x++) {
			if (arrayBricks.get(x) instanceof brickRed && arrayBricks.get(x).Lives == 0) {
				arrayBricks.remove(x);
			}
		}
		
		circle.setX(280);
		circle.setY(680);
		circle.setXDirection(1);
		circle.setYDirection(-1);
		
		bar.setX(280);
	}
	
	public void checkBlocks() {
		int unbreakableBricks = 0;
		
		for(int x = 0; x < arrayBricks.size(); x++) {
			if (arrayBricks.get(x) instanceof brickWhite ||
					(arrayBricks.get(x) instanceof brickBrown && arrayBricks.get(x).getLives() <= 0) ||
					(arrayBricks.get(x) instanceof brickGray && arrayBricks.get(x).getLives() <= 0)) {
				unbreakableBricks++;
			}
		}
		
		if (arrayBricks.size() <= 0 || arrayBricks.size() - unbreakableBricks <= 0) {
			arrayBricks.clear();
			
			blocks++;
			
			if (this.Tutorial) {
				createBricksTutorial();
			}
			else {
				createBricks();
			}
			
			restart();
		}
	}
	
	public void shopTutorial() {
		this.Shopping = true;
		
		user.setCoins(10000);
		
		JOptionPane.showMessageDialog(null, "When you restart after a game, you will be able to shop");
		
		JOptionPane.showMessageDialog(null, "There's a chance to get mone when hitting a block\nStill, the GRAY, BROWN and WHITE bricks will not have this chance");
			
		JOptionPane.showMessageDialog(null, "Buy things to learn more!");
	}
	
	public void explainBlockType(int actualLevel) {
		
		switch(actualLevel) {
		case 1:
			JOptionPane.showMessageDialog(null, "Blue bricks drop an item that grows the game speed\nThey will break in one hit"
					+ "\nYou will get 40 points for each hit");

			break;
		case 2:
			JOptionPane.showMessageDialog(null, "Red bricks will fall when they break\nThey will break with two hits"
					+ "\nYou will get 25 points for each hit");
			
			break;
		case 3:
			JOptionPane.showMessageDialog(null, "Green bricks will break with three hits"
					+ "\nYou will get 10 points for each hit");
			
			break;
		case 4:
			JOptionPane.showMessageDialog(null, "White bricks will move when they get hit\nThey are unbreakable"
					+ "\nYou will not get any points for hitting it");
			
			break;
		case 5:
			JOptionPane.showMessageDialog(null, "Yellow bricks will give you one up\nThey will break in one hit"
					+ "\nYou will not get any points for hitting it");
			
			break;
		case 6:
			JOptionPane.showMessageDialog(null, "Brown bricks will hit all the line\nThey will break in one hit"
					+ "\nYou will get the points that this brick hits");
			
			break;
		case 7:
			JOptionPane.showMessageDialog(null, "Gray bricks will hit all the column\nThey will break in one hit"
					+ "\nYou will get the points that this brick hits");
			
			break;
		}
	}

	public void createBricks() {		
		int actualX = 0;
		int actualY = 0;
		int grayBricks = 0;
		int whiteBricks = 0;
		int brownBricks = 0;
		int yellowBricks = 0;
		int orangeBricks = 0;
		
		Sound.NEXTLEVEL.play();
		
		if (!this.Shopping) {
			JOptionPane.showMessageDialog(null, "Level " + this.blocks);
		}
		
		while (actualY < 30 * this.blocks) {
			
			while (actualX < 600) {
				int randomNumber;
				
				if (this.blocks < 8) {
					do {
						randomNumber = (int)(Math.random()*this.blocks+1);
					} while (whiteBricks >= 4 && randomNumber == 4 || yellowBricks >= 1 && randomNumber == 5 ||
							orangeBricks >= 1 && randomNumber == 6 || brownBricks >= 4 && randomNumber == 7 ||
							grayBricks >= 4 && randomNumber == 8);
				}
				else {
					do {
						randomNumber = (int)(Math.random()*8+1);
					} while (whiteBricks >= 4 && randomNumber == 4 || yellowBricks >= 1 && randomNumber == 5 ||
							orangeBricks >= 1 && randomNumber == 6 || brownBricks >= 4 && randomNumber == 7 ||
							grayBricks >= 4 && randomNumber == 8);
				}
				
				switch(randomNumber) {
				case 1:
					brickBlue brick1 = new brickBlue(actualX, actualY);

					arrayBricks.add(brick1);
					break;
				case 2:
					brickRed brick2 = new brickRed(actualX, actualY);

					arrayBricks.add(brick2);
					break;
				case 3:
					brickGreen brick3 = new brickGreen(actualX, actualY);

					arrayBricks.add(brick3);
					break;
				case 4:
					brickWhite brick4 = new brickWhite(actualX, actualY);

					whiteBricks++;
					
					arrayBricks.add(brick4);
					break;
				case 5:
					brickYellow brick5 = new brickYellow(actualX, actualY, this);

					yellowBricks++;
					
					arrayBricks.add(brick5);
					break;
				case 6:
					brickOrange brick6 = new brickOrange(actualX, actualY, this);
					
					orangeBricks++;
					
					arrayBricks.add(brick6);
					break;
				case 7:
					brickBrown brick7 = new brickBrown(actualX, actualY, this);
					
					brownBricks++;
					
					arrayBricks.add(brick7);
					break;
				case 8:
					brickGray brick8 = new brickGray(actualX, actualY, this);
					
					grayBricks++;
					
					arrayBricks.add(brick8);
				}
				
				actualX = actualX + 100;
			}
			
			actualY = actualY + 30;
			actualX = 0;
		}
	}
	
	public void recreateBricks() {
		int size = arrayBricks.size() - 1;
		int actualX = 0;
		int actualY = 0;
		int grayBricks = 0;
		int whiteBricks = 0;
		int brownBricks = 0;
		int yellowBricks = 0;
		
		Sound.GLITCH.play();
		
		arrayBricks.clear();
		
		for(int x = 0; x < size; x++) {
			while (actualY < 30 * this.blocks) {
				
				while (actualX < 600) {
					
					int randomNumber;
					
					do {
						randomNumber = (int)(Math.random()*7 + 1);
					} while (whiteBricks >= 4 && randomNumber == 4 || yellowBricks >= 1 && randomNumber == 5 ||
							brownBricks >= 4 && randomNumber == 6 || grayBricks >= 4 && randomNumber == 7);
					
					switch(randomNumber) {
					case 1:
						brickBlue brick1 = new brickBlue(actualX, actualY);
	
						arrayBricks.add(brick1);
						break;
					case 2:
						brickRed brick2 = new brickRed(actualX, actualY);
	
						arrayBricks.add(brick2);
						break;
					case 3:
						brickGreen brick3 = new brickGreen(actualX, actualY);
	
						arrayBricks.add(brick3);
						break;
					case 4:
						brickWhite brick4 = new brickWhite(actualX, actualY);
	
						whiteBricks++;
						
						arrayBricks.add(brick4);
						break;
					case 5:
						brickYellow brick5 = new brickYellow(actualX, actualY, this);
	
						yellowBricks++;
						
						arrayBricks.add(brick5);
						break;
					case 6:
						brickBrown brick6 = new brickBrown(actualX, actualY, this);
						
						brownBricks++;
						
						arrayBricks.add(brick6);
						break;
					case 7:
						brickGray brick7 = new brickGray(actualX, actualY, this);
						
						grayBricks++;
						
						arrayBricks.add(brick7);
					}
					
					actualX = actualX + 100;
				}
				
				actualY = actualY + 30;
				actualX = 0;
			}
		}
	}
	
	public int createBricksTutorial() {
		int actualX = 0;
		int actualY = 0;
		
		Sound.NEXTLEVEL.play();
				
		int actualLevel = 8;
		
		explainBlockType(actualLevel);
		
		while ((actualY < 30 && (actualLevel != 6 || actualLevel != 7)) || (actualY < 60 && (actualLevel == 6 || actualLevel == 7))) {
			
			while (actualX < 600) {
				
				switch(actualLevel) {
				case 1:
					brickBlue brick1 = new brickBlue(actualX, actualY);

					arrayBricks.add(brick1);
					break;
				case 2:
					brickRed brick2 = new brickRed(actualX, actualY);

					arrayBricks.add(brick2);
					break;
				case 3:
					brickGreen brick3 = new brickGreen(actualX, actualY);

					arrayBricks.add(brick3);
					break;
				case 4:
					brickWhite brick4 = new brickWhite(actualX, actualY);

					arrayBricks.add(brick4);
					
					actualX = actualX + 100;
					
					brickGreen brickN = new brickGreen(actualX, actualY);

					arrayBricks.add(brickN);
					break;
				case 5:					
					brickYellow brick5 = new brickYellow(actualX, actualY, this);

					arrayBricks.add(brick5);
					break;
				case 6:
					brickBrown brick6 = new brickBrown(actualX, actualY, this);
					
					arrayBricks.add(brick6);
					break;
				case 7:
					brickGray brick7 = new brickGray(actualX, actualY, this);
					
					arrayBricks.add(brick7);
					break;
				default:
					this.pause = true;
					shopTutorial();
					actualX = 600;
					actualY = 60;
				break;
				}
				
				actualX = actualX + 100;
			}
			
			actualY = actualY + 30;
			actualX = 0;
		}
		
		return actualLevel;
	}
}