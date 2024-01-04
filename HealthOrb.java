import java.util.ArrayList;

/** [HealthOrb.java]
  * Create a new HealthOrb, with all the properties of any Orb,
  * with the addition of a health value (that the Player may collect)
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class HealthOrb extends Orb {
    private final int HEALTH;

    /**
      * HealthOrb
      * This constructor creates a HealthOrb object, from x and y coordinates
      * @param COOR_X the x coordinate of the HealthOrb
      * @param COOR_Y the y coordinate of the HealthOrb
      */
    HealthOrb(double COOR_X, double COOR_Y) {
        super(COOR_X, COOR_Y);

        this.HEALTH = 10;
        this.orbImage = t.getImage("src/images/orbs/health-orb.png");
    }

    /**
      * getDAMAGE
      * This getter returns the HealthOrb's health value
      * @return an int value of the HealthOrb's health
      */
    public int getHEALTH() {
        return this.HEALTH;
    }

    /**
      * gainDamage
      * This method regains the appropriate health value in a Player's health
      */
    public void restoreHealth() {
        Player player = PanelCardLayout.gameScreen.player; //todo have player as parameter instead?

        if (player.getHealth() <= (player.getMAX_HEALTH() - this.getHEALTH())) {
            player.setHealth(player.getHealth()+this.getHEALTH());
        } else {
            player.setHealth(player.getMAX_HEALTH());
        }
    }

    /**
      * popped
      * This method follows the appropriate actions taken after a HealthOrb is popped
      * @param orbs an ArrayList of all Orbs current on the screen
      * @param index the index of the popped HealthOrb
      * @param copyOfOrbs an ArrayList copy of all Orbs in the level (including popped ones)
      */
    public void popped(ArrayList<Orb> orbs, int index, ArrayList<Orb> copyOfOrbs) {
        copyOfOrbs.remove(this);
        orbs.remove(this);
        this.restoreHealth();
        //special Orbs are removed and replaced with normal orbs (should they be regenerated onto the screen)
        copyOfOrbs.add(new DamageOrb(this.getCOOR_X(), this.getCOOR_Y(), 2, "normal")); //set a normal orb in its place (to appear if regenerated)
    }
}
