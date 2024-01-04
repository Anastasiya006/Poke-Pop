import java.util.ArrayList;

/** [RestoreOrb.java]
  * Create a new RestoreOrb, with all the properties of any Orb,
  * with the addition of being able to restore all the Orbs on the screen
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class RestoreOrb extends Orb {
    /**
      * RestoreOrb
      * This constructor creates a RestoreOrb object, from x and y coordinates
      * @param COOR_X the x coordinate of the HealthOrb
      * @param COOR_Y the y coordinate of the HealthOrb
      */
    RestoreOrb(double COOR_X, double COOR_Y) {
        super(COOR_X, COOR_Y);

        this.orbImage = t.getImage("src/images/orbs/restore-orb.png");
    }

    /**
      * restoreOrbs
      * This method restores all the popped orbs back onto the screen
      */
    public void restoreOrbs() {
        PanelCardLayout.gameScreen.orbs = (ArrayList<Orb>) (PanelCardLayout.gameScreen.copyOfOrbs).clone();
    }

    /**
      * popped
      * This method follows the appropriate actions taken after a RestoreOrb is popped
      * @param orbs an ArrayList of all Orbs current on the screen
      * @param index the index of the popped HealthOrb
      * @param copyOfOrbs an ArrayList copy of all Orbs in the level (including popped ones)
      */
    public void popped(ArrayList<Orb> orbs, int index, ArrayList<Orb> copyOfOrbs) {
        copyOfOrbs.remove(this);
        this.restoreOrbs();
        copyOfOrbs.add(new DamageOrb(this.getCOOR_X(), this.getCOOR_Y(), 2, "normal")); //set a normal orb in its place (to appear if regenerated)
    }
}
