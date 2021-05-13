/**********************************************
Assignment 2
Course: btp400 - winter 2021
Author: Sammy, Mostafa, Shakha
ID: 161334180
Section: NAA
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
Date: 2020-04-21
**********************************************/
package application;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import Audio.Music;
import entity.Character;
import entity.Player;
import entity.Sprite;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

/**
 * a class for the overworld, where players walk around
 * @author Sammy, Mostafa, Shakha
 *
 */
public class Map
{
	private final int size = 64;
	private final int width = 800;
	private final int height = 400;

	int enemyCount = 3;
	private BorderPane pane;
	private long lastNanoTime;
	private Player p;
	private ArrayList<String> input;
	private int level;
	Character c[];

	/**
	 * creates a map
	 * @param p the player that explores the map
	 * @param level the level to render, supports 1-3, determines what enemies to spawn
	 * @throws FileNotFoundException if it can't find the needed assets
	 */
	public Map(Player p, int level) throws FileNotFoundException
	{
		Music m = new Music("assets/audio/BGM" + level + ".mp3");
		m.play();
		m.loop();
		if (level == 3)
		{
			enemyCount = 1;
		}
		this.level = level;
		this.p = p;
		p.setDown();
		p.getMapIcon().setSize(size, size);
		input = new ArrayList<String>();
		pane = new BorderPane();
		Canvas canvas = new Canvas(width, height);
		pane.setCenter(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Sprite bg = new Sprite("assets/img/bg" + level + ".png");
		bg.setSize(height, width);
		createEnemies();
		Sprite stairCase = new Sprite("assets/img/stairs.png");
		if (level < 3)
		{
			stairCase.setSize(size, size);
			stairCase.setPosition(width - size, height - size);
		}
		Main.getScene().setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			public void handle(KeyEvent e)
			{
				String code = e.getCode().toString();
				if (!input.contains(code))
				{
					input.add(code);
				}
			}
		});

		Main.getScene().setOnKeyReleased(new EventHandler<KeyEvent>()
		{
			public void handle(KeyEvent e)
			{
				String code = e.getCode().toString();
				input.remove(code);
			}
		});

		lastNanoTime = System.nanoTime();
		AnimationTimer timer = new AnimationTimer()
		{
			public void handle(long currentNanoTime)
			{
				// get time
				double elapsedTime = (currentNanoTime - lastNanoTime) / 10000000.0;
				lastNanoTime = currentNanoTime;
				// input
				input();
				// update sprites
				p.getMapIcon().update(elapsedTime);
				checkBounds();
				bg.update(elapsedTime);
				if (level < 3)
				{
					stairCase.update(elapsedTime);
				}
				// render sprites
				gc.clearRect(0, 0, width, height);
				bg.render(gc);
				p.getMapIcon().render(gc, size, size);
				for (int i = 0; i < enemyCount; i++)
				{
					c[i].getMapIcon().render(gc, (int) c[i].getMapIcon().getWidth(),
							(int) c[i].getMapIcon().getHeight());
				}
				if (level < 3)
				{
					stairCase.render(gc, size, size);
				}
				// calculate collisions
				for (int i = 0; i < enemyCount; i++)
				{
					if (p.getMapIcon().intersects(c[i].getMapIcon()))
					{
						this.stop();
						m.stop();
						p.getMapIcon().setVelocity(0, 0);
						p.getMapIcon().update(elapsedTime);
						p.getMapIcon().render(gc, size, size);
						resetInput();
						Battle b = new Battle(p, c[i], level);
						Main.getPane().setCenter(b.getPane());
					}
				}
				if (level < 3 && stairCase.intersects(p.getMapIcon()))
				{
					Music swap = new Music("assets/audio/next.wav");
					swap.play();
					this.stop();
					m.stop();
					p.getMapIcon().setVelocity(0, 0);
					p.getMapIcon().update(elapsedTime);
					p.getMapIcon().render(gc);
					resetInput();
					try
					{
						p.getMapIcon().setPosition(0, 0);
						Map m;
						m = new Map(p, level + 1);
						Main.getPane().setCenter(m.getPane());
					} catch (FileNotFoundException e)
					{
						e.printStackTrace();
					}
				}
			}
		};
		timer.start();
	}

	/**
	 * gets the pane
	 * @return the pane
	 */
	public Node getPane()
	{
		return pane;
	}

	/**
	 * changes players direction based on input
	 */
	private void input()
	{
		boolean upDown = false;
		boolean leftRight = false;
		if (input.contains("W") || input.contains("UP"))
		{
			p.getMapIcon().setVelY(-1);
			upDown = true;
			p.setUp();
		}
		if (input.contains("S") || input.contains("DOWN"))
		{
			p.getMapIcon().setVelY(1);
			upDown = true;
			p.setDown();
		}
		if (!upDown)
		{
			p.getMapIcon().setVelY(0);
		}
		if (input.contains("A") || input.contains("LEFT"))
		{
			p.getMapIcon().setVelX(-1);
			leftRight = true;
			p.setLeft();
		}
		if (input.contains("D") || input.contains("RIGHT"))
		{
			p.getMapIcon().setVelX(1);
			leftRight = true;
			p.setRight();
		}
		if (!leftRight)
		{
			p.getMapIcon().setVelX(0);
		}
		if (input.isEmpty())
		{
			p.getMapIcon().pause();
		} else
		{
			p.getMapIcon().play();
		}
	}

	/**
	 * spawns in enemies
	 */
	private void createEnemies()
	{
		Random rand = new Random();
		c = new Character[enemyCount];
		for (int i = 0; i < enemyCount; i++)
		{
			if (level == 1)
			{
				c[i] = new Character("SkeleBoi", 100, 10, 1, 20, "assets/img/skele.png", 1,
						"assets/img/skeleBattle.png", 8, "assets/img/skeleDeath.png", 6, "assets/audio/atk.mp3",
						"assets/audio/skeleDeath.mp3");
				c[i].getMapIcon().setSize(size, size);
			} else if (level == 2)
			{
				c[i] = new Character("Orc", 100, 10, 1, 50, "assets/img/orc.png", 1, "assets/img/orcAttack.png", 13,
						"assets/img/orcDeath.png", 6, "assets/audio/orkAtk.wav", "assets/audio/orkDeath.wav");
				c[i].addLvl(5);
				c[i].setHp(Integer.MAX_VALUE);
				c[i].getMapIcon().setSize(size, size);
			} else
			{
				c[i] = new Character("Dragon", 200, 15, 1, 100, "assets/img/dragon.png", 1,
						"assets/img/dragonAttack.png", 8, "assets/img/dragonDeath.png", 8, "assets/audio/dragonAtk.wav",
						"assets/audio/dragonDeath.wav");
				c[i].setBoss(true);
				c[i].addLvl(10);
				c[i].setHp(Integer.MAX_VALUE);
				c[i].getMapIcon().setSize(size * 3, size * 3);
			}

			boolean intersects = false;
			do
			{
				c[i].getMapIcon().setPosition(rand.nextInt((int) (width - c[i].getMapIcon().getWidth())),
						rand.nextInt((int) (height - c[i].getMapIcon().getHeight())));

				intersects = false;
				for (int j = 0; j < i; j++)
				{
					if (c[i].getMapIcon().intersects(c[j].getMapIcon()))
					{
						intersects = true;
					}
				}
			} while (c[i].getMapIcon().intersects(p.getMapIcon()) || intersects);
		}
	}

	/**
	 * clears the input
	 */
	private void resetInput()
	{
		input.remove("S");
		input.remove("DOWN");
		input.remove("W");
		input.remove("UP");
		input.remove("D");
		input.remove("RIGHT");
		input.remove("A");
		input.remove("LEFT");
	}

	/**
	 * makes sure player does not go out of bounds
	 */
	private void checkBounds()
	{
		if (p.getMapIcon().getPosX() < 0)
		{
			p.getMapIcon().setPosX(0);
		}
		if (p.getMapIcon().getPosX() > (width - size))
		{
			p.getMapIcon().setPosX(width - size);
		}
		if (p.getMapIcon().getPosY() < 0)
		{
			p.getMapIcon().setPosY(0);
		}
		if (p.getMapIcon().getPosY() > (height - size))
		{
			p.getMapIcon().setPosY(height - size);
		}
	}

}
