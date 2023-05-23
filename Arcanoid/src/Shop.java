import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Sounds.Sound;

import javax.swing.JOptionPane;

@SuppressWarnings("all")
public class Shop{

	private int INITTIMERS = 1000;
	
	private int powerPrice = 200;
	private int initLivesPrice = 1000;
	private int resistancePrice = 500;
	
	private boolean resistanceMoney = true;
	private boolean livesMoney = true;
	private boolean powerMoney = true;
	private int resistanceTime = INITTIMERS;
	private int livesTime = INITTIMERS;
	private int powerTime = INITTIMERS;

	private Window game;
	
	public Shop() { }
	
	public Shop(Window game) {
		this.game = game;
	}
	
	public void paintShop(Graphics2D drawer) {
		
		lessTime();
		
		paintResistance(drawer);
		paintPower(drawer);
		paintInitLives(drawer);
		paintLeave(drawer);
	}
	
	public void lessTime() {
		if (!this.resistanceMoney) {
			this.resistanceTime--;
			
			if (this.resistanceTime == 0) {
				this.resistanceMoney = true;
			}
		}
		
		if (!this.powerMoney) {
			this.powerTime--;
			
			if (this.powerTime == 0) {
				this.powerMoney = true;
			}
		}
		
		if (!this.livesMoney) {
			this.livesTime--;
			
			if (this.livesTime == 0) {
				this.livesMoney = true;
			}
		}
	}
	
	public void paintResistance(Graphics2D g2d) {		
		if (this.resistanceMoney) {
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("DialogInput", Font.PLAIN, 30));
		}
		else {
			g2d.setColor(Color.RED);
			g2d.setFont(new Font("DialogInput", Font.BOLD, 30));
		}
		
		if (game.user.getResistance() == 4) {
			g2d.setColor(Color.YELLOW);
			g2d.setFont(new Font("DialogInput", Font.BOLD, 30));
			
			g2d.drawString("RESISTANCE MAXED: " + game.user.getResistance(), 10, 30);
		}
		else {
			g2d.drawString("PRESS R TO BUY RESISTANCE", 10, 30);
			g2d.drawString("PRICE: " + this.resistancePrice, 10, 60);
			g2d.drawString("ACTUAL RESISTANCE: " + game.user.getResistance(), 10, 90);
		}
	}
	
	public void paintPower(Graphics2D g2d) {
		if (this.powerMoney) {
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("DialogInput", Font.PLAIN, 30));
		}
		else {
			g2d.setColor(Color.RED);
			g2d.setFont(new Font("DialogInput", Font.BOLD, 30));
		}
		
		if (game.user.getPower() == 3) {
			g2d.setColor(Color.YELLOW);
			g2d.setFont(new Font("DialogInput", Font.BOLD, 30));
			
			g2d.drawString("POWER MAXED: " + game.user.getPower(), 10, 150);
		}
		else{			
			g2d.drawString("PRESS P TO BUY POWER", 10, 150);
			g2d.drawString("PRICE: " + this.powerPrice, 10, 180);
			g2d.drawString("ACTUAL POWER: " + game.user.getPower(), 10, 210);			
		}
	}

	public void paintInitLives(Graphics2D g2d) {
		if (this.livesMoney) {
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("DialogInput", Font.PLAIN, 30));
		}
		else {
			g2d.setColor(Color.RED);
			g2d.setFont(new Font("DialogInput", Font.BOLD, 30));
		}
		
		if (game.user.getInitLives() == 10) {
			g2d.setColor(Color.YELLOW);
			g2d.setFont(new Font("DialogInput", Font.BOLD, 30));
			
			g2d.drawString("INITIAL LIVES MAXED: " + game.user.getInitLives(), 10, 270);
		}
		else {
			g2d.drawString("PRESS L TO BUY INITIAL LIVES", 10, 270);
			g2d.drawString("PRICE: " + this.initLivesPrice, 10, 300);
			g2d.drawString("ACTUAL INITIAL LIVES: " + game.user.getInitLives(), 10, 330);
		}
		
	}

	public void paintLeave(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("DialogInput", Font.PLAIN, 30));
		
		g2d.drawString("PRESS Q TO LEAVE", 10, 390);
	}

	public void addPower() {
		if (game.user.getCoins() >= powerPrice && game.user.getPower() < 3) {
			Sound.BUY.play();

			JOptionPane.showMessageDialog(null, "You bought 1 Power!");
			game.user.setCoins(game.user.getCoins() - powerPrice);
			game.user.setPower(game.user.getPower() + 1);
		}
		else if (game.user.getPower() == 3) {
			Sound.MAXED.play();
			
			JOptionPane.showMessageDialog(null, "You maxed the power!\nYou can't buy it any more.", "Shop", JOptionPane.CANCEL_OPTION);
		}
		else {
			Sound.NOTENOUGHMONEY.play();
			
			poor("POWER");
		}
	}

	public void addResistance() {
		if (game.user.getCoins() >= resistancePrice && game.user.getResistance() < 4) {
			Sound.BUY.play();
			
			JOptionPane.showMessageDialog(null, "You bought 1 Resistance!");
			game.user.setCoins(game.user.getCoins() - resistancePrice);
			game.user.setResistance(game.user.getResistance() + 1);
		}
		else if (game.user.getResistance() == 4) {
			Sound.MAXED.play();
			
			JOptionPane.showMessageDialog(null, "You maxed the resistance!\nYou can't buy it any more.", "Shop", JOptionPane.CANCEL_OPTION);
		}
		else {
			Sound.NOTENOUGHMONEY.play();
			
			poor("RESISTANCE");
		}
	}
	
	public void addInitLives() {
		if (game.user.getCoins() >= initLivesPrice && game.user.getInitLives() < 10) {
			Sound.BUY.play();
			
			JOptionPane.showMessageDialog(null, "You bought 1 Live!");
			game.user.setCoins(game.user.getCoins() - initLivesPrice);
			game.user.setInitLives(game.user.getInitLives() + 1);
		}
		else if (game.user.getInitLives() == 10) {
			Sound.MAXED.play();
			
			JOptionPane.showMessageDialog(null, "You maxed the lives!\nYou can't buy it any more.", "Shop", JOptionPane.CANCEL_OPTION);
		}
		else {
			Sound.NOTENOUGHMONEY.play();
			
			poor("INITLIVES");
		}
	}
	
	public int notMoneyResistance() {
		this.resistanceMoney = false;
		this.resistanceTime = this.INITTIMERS;
		
		return this.resistancePrice - game.user.getCoins();
	}
	
	public int notMoneyPower() {
		this.powerMoney = false;
		this.powerTime = this.INITTIMERS;
		
		return this.powerPrice - game.user.getCoins();
	}

	public int notMoneyLives() {
		this.livesMoney = false;
		this.livesTime = this.INITTIMERS;
		
		return this.initLivesPrice - game.user.getCoins();
	}
	
	public void poor(String buy) {
		if (buy.equals("RESISTANCE")) {
			JOptionPane.showMessageDialog(null, "You don't have enough money! \n You need " + notMoneyResistance() + " coins!");
		}
		else if (buy.equals("POWER")) {
			JOptionPane.showMessageDialog(null, "You don't have enough money! \n You need " + notMoneyPower() + " coins!");
		}
		else {
			JOptionPane.showMessageDialog(null, "You don't have enough money! \n You need " + notMoneyLives() + " coins!");
		}
	}
}