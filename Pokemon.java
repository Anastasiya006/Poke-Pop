import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/** [Pokemon.java]
  * Create a new Pokemon objects that holds all the properties of a Character,
  * in addition to having move forward
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class Pokemon extends Character {
    Toolkit t = Toolkit.getDefaultToolkit();

    private int spriteCount;
    private boolean move;
    private final String TYPE;

    Image[][] fennekinSprites = {{t.getImage("src/images/pokemon images/fennekin evolution/fennekin-1.png"), t.getImage("src/images/pokemon images/fennekin evolution/fennekin-2.png")},
            {t.getImage("src/images/pokemon images/fennekin evolution/braixen-1.png"), t.getImage("src/images/pokemon images/fennekin evolution/braixen-2.png")},
            {t.getImage("src/images/pokemon images/fennekin evolution/delphox-1.png"), t.getImage("src/images/pokemon images/fennekin evolution/delphox-2.png")}};

    Image[][] froakieSprites = {{t.getImage("src/images/pokemon images/froakie evolution/froakie-1.png"), t.getImage("src/images/pokemon images/froakie evolution/froakie-2.png")},
            {t.getImage("src/images/pokemon images/froakie evolution/frogadier-1.png"), t.getImage("src/images/pokemon images/froakie evolution/frogadier-2.png")},
            {t.getImage("src/images/pokemon images/froakie evolution/greninja-1.png"), t.getImage("src/images/pokemon images/froakie evolution/greninja-2.png")}};

    Image[][] panchamSprites = {{t.getImage("src/images/pokemon images/pancham evolution/pancham-1.png"), t.getImage("src/images/pokemon images/pancham evolution/pancham-2.png")},
            {t.getImage("src/images/pokemon images/pancham evolution/pangoroo-1.png"), t.getImage("src/images/pokemon images/pancham evolution/pangoroo-2.png")}};

    Image[] sprites;

    /**
      * Pokemon
      * This constructor creates a Pokemon object, from x and y coordinates,
      * a health value, damage value, a Pokemon type, an evolution stage, and max health value
      * @param coorX the x coordinate of the Pokemon
      * @param coorY the y coordinate of the Pokemon
      * @param health the current health of the Pokemon
      * @param damage the damage value a Pokemon may inflict upon another character
      * @param TYPE the type of Pokemon
      * @param stage the evolution stage of the Pokemon
      * @param MAX_HEALTH the max health a Pokemon may have
      */
    Pokemon(int coorX, int coorY, int health, int damage, String TYPE, int stage, double MAX_HEALTH) {
        super(coorX, coorY, health, damage, MAX_HEALTH);

        this.spriteCount = 0;
        this.move = false;
        this.TYPE = TYPE;

        //get correct sprites in accordance to type of pokémon, and it's current stage (0-2)
        if (this.TYPE.equals("Fennekin")) {
            this.sprites = fennekinSprites[stage];
        } else if (this.TYPE.equals("Froakie")) {
            this.sprites = froakieSprites[stage];
        } else if (this.TYPE.equals("Pancham")) {
            this.sprites = panchamSprites[stage];
        }

        this.idleSprite = sprites[0];
    }

    /**
      * setMove
      * This setter updates the Pokemon's boolean move state
      * @param move the Pokemon's current move state
      */
    public void setMove(boolean move) {
        this.move = move;
    }

    /**
      * getMove
      * This getter returns the Pokemon's current move state
      * @return a boolean value of true if the Pokemon's is moving
      */
    public boolean getMove() {
        return this.move;
    }

    /**
      * setSpriteCount
      * This setter updates the Pokemon's sprite count
      * @param spriteCount the Pokemon's sprite count
      */
    public void setSpriteCount(int spriteCount) {
        this.spriteCount = spriteCount;
    }

    /**
      * getSpriteCount
      * This getter returns the Pokemon's sprite count (which even and odd numbers indicating different images)
      * @return an int value of the Pokemon's sprite count
      */
    public int getSpriteCount() {
        return this.spriteCount;
    }

    /**
      * attack
      * This method decreases the Player's damage as a direct
      * proportion to the Pokemon's damage value
      * @param character the Character that is being attacked
      */
    public void attack(Character character) {
        Player player = (Player)character;
        player.setHealth(player.getHealth() - this.getDamage());
    }

    /**
      * moveForward
      * This method updates the Pokemon's x coordinate, to move slightly forward
      */
    public void moveForward() {
        this.setCoorX(this.getCoorX() - 5);
    }

    /**
      * animate
      * This method animates the Pokemon's walking
      * @param g the Java Graphics class
      */
    public void animate(Graphics g) {
        this.setSpriteCount(this.getSpriteCount() + 1);

        if (this.getSpriteCount() % 2 == 0) {
            g.drawImage(sprites[1], this.getCoorX(), this.getCoorY(), null);
        } else {
            g.drawImage(sprites[0], this.getCoorX(), this.getCoorY(), null);
        }
    }

    /**
      * draw
      * This method draws either an idle instance or a moving instance of
      * the Pokemon, as well as, its healthbar above it
      * @param g the Java Graphics class
      */
    public void draw(Graphics g) {
        if (this.getMove()) {
            this.animate(g);
        } else {
            this.drawIdle(g);
        }

        this.drawHealthBar(g);
    }
}