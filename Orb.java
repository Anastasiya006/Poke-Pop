import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Image;

/** [Orb.java]
  * An abstract class of an Orb object that holds the basic properties all
  * orbs must have (x and y coordinates, diameter, bounds)
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

abstract public class Orb {
    Toolkit t = Toolkit.getDefaultToolkit();

    private final double COOR_X, COOR_Y;

    private final int DIAMETER;
    private final Rectangle BOUNDS;

    Image orbImage;

    /**
      * Orb
      * This constructor creates an Orb object, from x and y coordinates
      * @param COOR_X the x coordinate of the Orb
      * @param COOR_Y the y coordinate of the Orb
      */
    Orb(double COOR_X, double COOR_Y) {
        this.COOR_X = COOR_X;
        this.COOR_Y = COOR_Y;
        this.DIAMETER = 15;
        this.BOUNDS = new Rectangle((int)COOR_X, (int)COOR_Y, this.DIAMETER, this.DIAMETER);
    }

    /**
      * getCOOR_X
      * This getter returns the Orb's x coordinate
      * @return an int value of the Orb's x coordinate
      */
    public double getCOOR_X() {
        return this.COOR_X;
    }

    /**
      * getCOOR_Y
      * This getter returns the Orb's y coordinate
      * @return an int value of the Orb's y coordinate
      */
    public double getCOOR_Y() {
        return this.COOR_Y;
    }

    /**
      * getDIAMETER
      * This getter returns the Orb's diameter
      * @return an int value of the Orb's diameter
      */
    public int getDIAMETER() {
        return this.DIAMETER;
    }

    /**
      * getBOUNDS
      * This getter returns the Orb's bounds (a Rectangle)
      * @return a Rectangle of the Orb's bounds
      */
    public Rectangle getBOUNDS() {
        return this.BOUNDS;
    }

    /**
      * draw
      * This method draws an Orb to the screen
      * @param g the Java Graphics class
      */
    public void draw(Graphics g) {
        g.drawImage(orbImage, (int)this.getCOOR_X(), (int)this.getCOOR_Y(), this.getDIAMETER(), this.getDIAMETER(), null);
    }

    /**
      * popped
      * This abstract method is present in all Orbs objects, and
      * indicates what actions take place affect the orb is popped
      * @param orbs an ArrayList of all Orbs current on the screen
      * @param index the index of the popped HealthOrb
      * @param copyOfOrbs an ArrayList copy of all Orbs in the level (including popped ones)
      */
    abstract void popped(ArrayList<Orb> orbs, int index, ArrayList<Orb> copyOfOrbs);
}