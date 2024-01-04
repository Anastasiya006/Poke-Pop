import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;

/** [Character.java]
  * An abstract class of a Character object that holds the basic properties all
  * characters must have (x and y coordinates, health value, damage value, and a max health)
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

abstract public class Character {
    private int coorX, coorY;

    private final double MAX_HEALTH;
    private double health;

    private int damage;

    Image idleSprite;

    /**
      * Character
      * This constructor creates a Character object, from x and y coordinates,
      * a health value, damage value, and max health value
      * @param coorX the x coordinate of the Character
      * @param coorY the y coordinate of the Character
      * @param health the current health of the Character
      * @param damage the damage value a Character may inflict upon another character
      * @param MAX_HEALTH the max health a Character may have
      */
    Character(int coorX, int coorY, int health, int damage, double MAX_HEALTH) {
        this.coorX = coorX;
        this.coorY = coorY;
        this.health = health;
        this.damage = damage;
        this.MAX_HEALTH = MAX_HEALTH;
    }

    /**
      * setCoorX
      * This setter updates the Character's current x coordinate
      * @param coorX the Character's x coordinate
      */
    public void setCoorX(int coorX) {
        this.coorX = coorX;
    }

    /**
      * getCoorX
      * This getter returns the Character's current x coordinate
      * @return an int value of the Character's x coordinate
      */
    public int getCoorX() {
        return this.coorX;
    }

    /**
     * setCoorY
     * This setter updates the Character's current y coordinate
     * @param coorY the Character's y coordinate
     */
    public void setCoorY(int coorY) {
        this.coorY = coorY;
    }

    /**
      * getCoorY
      * This getter returns the Character's current y coordinate
      * @return an int value of the Character's y coordinate
      */
    public int getCoorY() {
        return this.coorY;
    }

    /**
      * getMAX_HEALTH
      * This getter returns the Character's max health
      * @return a double value of the Character's max health
      */
    public double getMAX_HEALTH() {
        return this.MAX_HEALTH;
    }

    /**
      * setHealth
      * This setter updates the Character's current health
      * @param health the Character's current health
      */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
      * getHealth
      * This getter returns the Character's health
      * @return a double value of the Character's health
      */
    public double getHealth() {
        return this.health;
    }

    /**
      * setDamage
      * This setter updates the Character's current damage
      * @param damage the Character's current damage
      */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
      * getDamage
      * This getter returns the Character's damage
      * @return an int value of the Character's damage
      */
    public int getDamage() {
        return this.damage;
    }

    /**
      * attack
      * This abstract method is present in all Character objects, and indicates the
      * course of action that each object must take when it attacks another Character
      * @param character the Character that is being attacked
      */
    public abstract void attack (Character character);

    /**
      * draw
      * This abstract method is present in all Character objects, and
      * indicates the how to draw each type of Character object
      * @param g the Java Graphics class
      */
    public abstract void draw(Graphics g);

    /**
      * drawIdle
      * This method draws an idle instance of the Character (not moving)
      * @param g the Java Graphics class
      */
    public void drawIdle(Graphics g) {
        g.drawImage(idleSprite, this.getCoorX(), this.getCoorY(), null);
    }

    /**
      * drawHealthBar
      * This method draws the healthbar above the Character
      * @param g the Java Graphics class
      */
    public void drawHealthBar(Graphics g) {
        double ratio = (this.getHealth() / this.getMAX_HEALTH()); //ratio between the Character's health and the healthbar's length

        //draw background of healthbar
        g.setColor(Color.BLACK);
        g.drawRect((this.getCoorX() + 5), (this.getCoorY() - 20), 40, 6);
        g.fillRect((this.getCoorX() + 5), (this.getCoorY() - 20), 40, 6);

        //draw the Character's current healthbar
        g.setColor(new Color(220, 76, 76));
        g.fillRect((this.getCoorX() + 5), (this.getCoorY() - 20), (int)(ratio * 40), 6);

        //display the Character's current health, in accordance with its max health
        g.setFont(new Font("Calibri", Font.PLAIN, 9));
        g.setColor(Color.WHITE);
        String text = ((int) this.getHealth() + "/" + (int) this.getMAX_HEALTH());
        int width = g.getFontMetrics().stringWidth(text);

        g.drawString(text, this.getCoorX() + (50 - width)/2, (this.getCoorY() - 25)); //center text directly over the character
    }
}