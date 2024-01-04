import java.awt.Graphics;
import java.awt.Toolkit;

/** [Player.java]
  * Create a new Player objects that holds all the properties of a Character,
  * in addition to having the ability to collect orbs (and their benefits)
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class Player extends Character {
    Toolkit t = Toolkit.getDefaultToolkit();

    private boolean levelComplete;
    private int numOfBombs;

    /**
      * Player
      * This constructor creates a Player object, from x and y coordinates,
      * a health value, damage value, and max health value
      * @param coorX the x coordinate of the Player
      * @param coorY the y coordinate of the Player
      * @param health the current health of the Player
      * @param damage the damage value a Player may inflict upon another character
      * @param MAX_HEALTH the max health a Player may have
      */
    Player(int coorX, int coorY, int health, int damage, double MAX_HEALTH) {
        super(coorX, coorY, health, damage, MAX_HEALTH);

        this.levelComplete = false; //default
        this.idleSprite = t.getImage("src/images/player.png");
    }

    /**
      * setLevelComplete
      * This setter updates the Player's levelComplete state
      * @param levelComplete a boolean value that indicates if a Player has completed the level
      */
    public void setLevelComplete(boolean levelComplete) {
        this.levelComplete = levelComplete;
    }

    /**
      * getLevelComplete
      * This getter returns the Player's current levelComplete state
      * @return a boolean value of true if the Player's has completed the level
      */
    public boolean getLevelComplete() {
        return this.levelComplete;
    }

    /**
      * setNumOfBombs
      * This setter updates the Player's bomb count
      * @param numOfBombs the number of bombs collected by the player
      */
    public void setNumOfBombs(int numOfBombs) {
        this.numOfBombs = numOfBombs;
    }

    /**
      * getNumOfBombs
      * This getter returns the Player's bomb count
      * @return an int value of the lLayer's bomb count
      */
    public int getNumOfBombs() {
        return this.numOfBombs;
    }

    /**
     * resetDamage
     * This method reset's the Player's collected damage to '0'
     */
    public void resetDamage() {
        this.setDamage(0);
    }

    /**
      * attack
      * This method decreases the Pokémon's damage as a direct
      * proportion to the Player's damage points
      * @param character the Character that is being attacked
      */
    public void attack(Character character) {
        Pokemon pokemon = (Pokemon)character;

        if (this.getDamage() == pokemon.getHealth()) {
            pokemon.setHealth(pokemon.getHealth() - this.getDamage());
        } else {
            double damageLeft = this.getDamage() - pokemon.getHealth();
            pokemon.setHealth(pokemon.getHealth() - this.getDamage());

            this.setDamage((int)damageLeft);
        }
    }

    /**
      * applyBomb
      * This method decreases the Pokémon's damage as a direct
      * proportion to the number of bomb that a Player possesses
      * @param character the Character that the bomb's damage is being applied to
      */
    public void applyBomb(Character character) {
        for (int i = 0; i < this.numOfBombs; i++) {
            Pokemon pokemon = (Pokemon)character;
            pokemon.setHealth(pokemon.getHealth() - 50);
        }
    }

    /**
      * draw
      * This method draws an idle instance of the Player (not moving),
      * as well as it's healthbar to the screen
      * @param g the Java Graphics class
      */
    public void draw(Graphics g) {
        this.drawIdle(g);
        this.drawHealthBar(g);
    }
}