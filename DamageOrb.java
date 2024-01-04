import java.util.ArrayList;

/** [DamageOrb.java]
  * Create a new DamageOrb, with all the properties of any Orb,
  * with the addition of a damage value (that the Player may collect)
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class DamageOrb extends Orb {
    private final int DAMAGE;
    private final String TYPE;

    /**
      * DamageOrb
      * This constructor creates a DamageOrb object, from x and y coordinates,
      * a damage value, and a damage orb type (normal or bomb)
      * @param COOR_X the x coordinate of the DamageOrb
      * @param COOR_Y the y coordinate of the DamageOrb
      * @param DAMAGE the damage value of the DamageOrb
      * @param TYPE the type of DamageOrb (either normal or bomb)
      */
    DamageOrb(double COOR_X, double COOR_Y, int DAMAGE, String TYPE) {
        super(COOR_X, COOR_Y);

        this.DAMAGE = DAMAGE;
        this.TYPE = TYPE;

        if (this.TYPE.equals("normal")) {
            this.orbImage = t.getImage("src/images/orbs/normal-orb.png");
        } else if (this.TYPE.equals("bomb")) {
            this.orbImage = t.getImage("src/images/orbs/bomb-orb.png");
        }
    }

    /**
      * getDAMAGE
      * This getter returns the DamageOrb's damage value
      * @return an int value of the DamageOrb's damage
      */
    public int getDAMAGE() {
        return this.DAMAGE;
    }

    /**
      * getTYPE
      * This getter returns the DamageOrb's type
      * @return a String value of the DamageOrb's type (normal or bomb)
      */
    public String getTYPE() {
        return this.TYPE;
    }

    /**
      * gainDamage
      * This method adds the appropriate damage value to a Player,
      * depending on each DamageOrb has been popped
      * @param orbs an ArrayList of all Orbs current on the screen
      * @param index the index of the popped DamageOrb
      */
    public void gainDamage(ArrayList<Orb> orbs, int index) {
        Player player = PanelCardLayout.gameScreen.player; //Player receiving the damage points

        if (this.getTYPE().equals("normal")) {
            player.setDamage(player.getDamage() + ((DamageOrb) orbs.get(index)).getDAMAGE()); //adjust Player's damage points in accordance to normal orb damage
        } else {
            player.setNumOfBombs(player.getNumOfBombs() + 1); //increase the Player's collected bomb count
        }
    }

    /**
      * popped
      * This method follows the appropriate actions taken after a DamageOrb is popped
      * @param orbs an ArrayList of all Orbs current on the screen
      * @param index the index of the popped DamageOrb
      * @param copyOfOrbs an ArrayList copy of all Orbs in the level (including popped ones)
      */
    public void popped(ArrayList<Orb> orbs, int index, ArrayList<Orb> copyOfOrbs) {
        this.gainDamage(orbs, index); //gain damage points from the popped Orb

        if (this.getTYPE().equals("bomb")) { //special Orbs are removed and replaced with normal orbs (should they be regenerated onto the screen)
            copyOfOrbs.remove(this); //remove bomb from copy ArrayList of orbs
            orbs.remove(this); //remove bomb from screen displayed ArrayList of orbs
            copyOfOrbs.add(new DamageOrb(this.getCOOR_X(), this.getCOOR_Y(), 2, "normal")); //set a normal orb in its place (to appear if regenerated)
        } else {
            orbs.remove(this); //pop the normal orb, and remove from the screen
        }
    }
}