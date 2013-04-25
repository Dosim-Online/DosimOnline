package org.dosimonline;
import it.randomtower.engine.entity.Entity;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityDos extends Entity
{
    private Animation dosWalkLeft;
    private Animation dosWalkRight;
    private Image dosStanding;
    private boolean jumpAllowed = true;
    private float jump;
    public static int direction = 1; //1 is left, 2 is right.
    private float gravity = WorldPlains.gravity;
    private float moveSpeed = 10;
    
    public EntityDos (float x, float y) throws SlickException
    {
        super(x, y);
        SpriteSheet dosSheet = new SpriteSheet("org/dosimonline/res/sprites/dos.png", 20, 36);
        dosWalkLeft = new Animation();
        dosWalkLeft.setAutoUpdate(true);
        dosWalkLeft.addFrame(dosSheet.getSprite(0, 0).getFlippedCopy(true, false), 150);
        dosWalkLeft.addFrame(dosSheet.getSprite(1, 0).getFlippedCopy(true, false), 150);
        
        dosWalkRight = new Animation();
        dosWalkRight.setAutoUpdate(true);
        dosWalkRight.addFrame(dosSheet.getSprite(0, 0), 150);
        dosWalkRight.addFrame(dosSheet.getSprite(1, 0), 150);
        dosStanding = dosSheet.getSprite(0, 0);
        define("right", Input.KEY_D);
        define("left", Input.KEY_A);
        define("up", Input.KEY_W);
        define("q", Input.KEY_Q);
        setHitBox(0, 0, dosStanding.getWidth(), dosStanding.getHeight());
        addType("Dos");
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        super.render(gc, g);
        
        if (check("right") && direction == 2)
        {
            g.drawAnimation(dosWalkRight, x, y);
            if (collide("Solid", x + moveSpeed, y) == null) {x += moveSpeed;}
        }
        else if (check("left") && direction == 2)
        {
            g.drawAnimation(dosWalkRight, x, y);
            if (collide("Solid", x - moveSpeed, y) == null) {x -= moveSpeed;}
        }
        else if (check("right") && direction == 1)
        {
            g.drawAnimation(dosWalkLeft, x, y);
            if (collide("Solid", x + moveSpeed, y) == null) {x += moveSpeed;}
        }
        else if (check("left") && direction == 1)
        {
            g.drawAnimation(dosWalkLeft, x, y);
            if (collide("Solid", x - moveSpeed, y) == null) {x -= moveSpeed;}
        }
        else if (direction == 2) {g.drawImage(dosStanding, x, y);}
        else if (direction == 1) {g.drawImage(dosStanding.getFlippedCopy(true, false), x, y);}
    }
    
    @Override
    public void update(GameContainer gc, int delta) throws SlickException
    {
        super.update(gc, delta);
        DisplayMode dm = Display.getDesktopDisplayMode();
        int mouseX = gc.getInput().getMouseX();
        
        if (mouseX > dm.getWidth() / 2) {direction = 2;}
        else {direction = 1;}
        
        if (jumpAllowed && check("up"))
        {
            jumpAllowed = false;
            if (collide("Solid", x, y) == null) {jump = 50;}
        }
        
        //Gravity
        if (collide("Solid", x, y + gravity) == null)
        {y += gravity; jumpAllowed = false;} 
        else {jumpAllowed = true;}
        
        if (jump > 0 && collide("Solid", x, y) == null)
        {
            y -= gravity * 2;
            jump --;
        }
        
        if (this.x <= 600) {x += moveSpeed;}
        if (this.x >= 7000) {x -= moveSpeed;}
        if (WorldPlains.life == 0) {this.destroy();}
        
        if (collide("Ladder", x, y) != null && jump > 0) {jump = 0;}
        if (collide("Ladder", x, y) != null) {if (check("up")) {y -= gravity * 2 + 1;}}
        if (collide("Solid", x, y - (gravity * 2)) != null) {y += gravity * 2 + 1;}
    }
}