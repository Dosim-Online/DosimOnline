package org.dosimonline;

import it.randomtower.engine.World;

import java.util.Random;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class WorldMenu extends World
{
	private Image logo;
	private Image hakotel;
	private Music music;
	private DisplayMode dm = Display.getDesktopDisplayMode();
	private short heartX = 0;
	// unused:
	// private int br = 224, bg = 224, bb = 224; // Bacground red, green and blue.
	private Random random = new Random();
	private int heartY = random.nextInt(dm.getHeight() - 20) + 10;

	private Button startButton, creditsButton, exitButton, settingsButton;
	private SettingsManager settings;

	private Image heart;

	public WorldMenu(int id, GameContainer gc)
	{
		super(id, gc);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException
	{
		settings = SettingsManager.getInstance();
		settings.apply(gc);
		
		logo = new Image("org/dosimonline/res/logo.png");
		hakotel = new Image("org/dosimonline/res/hakotel.png").getScaledCopy(
				dm.getWidth(), dm.getHeight());

		startButton = new Button(20, dm.getHeight() / 2 - 20, new Image(
				"org/dosimonline/res/buttons/start.png"), new Image(
				"org/dosimonline/res/buttons/startActive.png"));

		creditsButton = new Button(20, dm.getHeight() / 2 + 20, new Image(
				"org/dosimonline/res/buttons/credits.png"), new Image(
				"org/dosimonline/res/buttons/creditsActive.png"));

		settingsButton = new Button(20, dm.getHeight() / 2 + 60, new Image(
				"org/dosimonline/res/buttons/settings.png"), new Image(
				"org/dosimonline/res/buttons/settingsActive.png"));

		exitButton = new Button(20, dm.getHeight() / 2 + 100, new Image(
				"org/dosimonline/res/buttons/exit.png"), new Image(
				"org/dosimonline/res/buttons/exitActive.png"));

		heart = new Image("org/dosimonline/res/heart.png");
		music = new Music("org/dosimonline/res/audio/Makche-Alleviation.ogg");
		music.loop(1, 0.5f);
		gc.setShowFPS(false);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException
	{
		g.drawImage(hakotel, 0, 0);
		g.drawImage(heart, heartX, heartY);
		g.drawImage(logo, dm.getWidth() - logo.getWidth() - 10, dm.getHeight()
				/ 2 - logo.getHeight() / 2);

		startButton.render(g);
		creditsButton.render(g);
		settingsButton.render(g);
		exitButton.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException
	{
		// Traveling heart's stuff
		if (heartX < dm.getWidth())
		{
			heartX++;
		}
		else
		{
			heartY = random.nextInt(dm.getHeight() - 50) + 10;
			heartX = -50;
		}
		heart.rotate(0.1f);

		startButton.update(gc.getInput());
		creditsButton.update(gc.getInput());
		settingsButton.update(gc.getInput());
		exitButton.update(gc.getInput());

		if (startButton.activated())
			sbg.enterState(2);
		else if (creditsButton.activated())
			sbg.enterState(3);
		else if (settingsButton.activated())
			sbg.enterState(4);

		if (exitButton.activated() || gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
		{
			gc.exit();
		}
	}
}
