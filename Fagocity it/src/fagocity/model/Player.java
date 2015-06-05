package fagocity.model;

import java.awt.Color;

import fagocity.controller.PlayerController;

public class Player extends Actor {
	public static final int defaultRadius = 30;
	public static final double defaultSpeed = 8;
	public static int lives = 3;
		
	public Player(int x, int y, double velX, double velY, int radius, Color color) {
		super(x, y, velX, velY, radius, color);
		actorController = new PlayerController(this);
	}
}
