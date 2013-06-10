package org.dosimonline;

import it.randomtower.engine.entity.Entity;
import java.util.Random;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class EntityFlyingSpaghettiMonster extends Entity
{
	private Vector2f direction;
	private Random random = new Random();
	private int shallChangeDirection = 1;
	private int shallAttack = 100;
	private int life = 3;

	public EntityFlyingSpaghettiMonster(float x, float y) throws SlickException
	{
		super(x, y);
		Image image = new Image("org/dosimonline/res/sprites/fsm.png");
		setGraphic(image);
		setHitBox(0, 0, image.getWidth(), image.getHeight());
		addType("Pasta");
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException
	{
		super.update(container, delta);
		shallChangeDirection--;
		shallAttack--;
		float dosX = WorldPlains.dos.x;
		float dosY = WorldPlains.dos.y;

		if (shallAttack <= 0 && dosX > x - 500 && dosX < x + 500
				&& dosY > y - 500 && dosY < y + 500)
		{
			world.add(new EntityMeatball(x, y, dosX, dosY));
			shallAttack = 400;
		}

		if (shallChangeDirection <= 0)
		{
			newDirection();
			shallChangeDirection = 400;
		}

		x += direction.getX();
		y += direction.getX();

		if (collide("Dos", x, y) != null)
			WorldPlains.dos.life = 0;

		if (collide("Fireball", x, y) != null)
		{
			WorldPlains.dos.score += 10;
			this.destroy();
		}

		// Setting bounds.
		if (y <= -3500)
		{
			newDirection();
			y++;
		}
		if (y >= 100)
		{
			newDirection();
			y--;
		}
		if (x <= 600)
		{
			newDirection();
			x++;
		}
		if (x >= 7000)
		{
			newDirection();
			x--;
		}
	}

	private void newDirection()
	{
		int targetX = random.nextInt(6) - 3;
		int targetY = random.nextInt(6) - 3;

		direction = new Vector2f(targetX, targetY);
	}

	@Override
	public void destroy()
	{
		super.destroy();
	}
}
