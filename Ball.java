import java.awt.Toolkit;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/** [Ball.java]
  * Create a new Ball object that can interacts with the orbs on screen
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class Ball {
    Toolkit t = Toolkit.getDefaultToolkit();

    private final double START_COOR_X, START_COOR_Y;
    private double coorX, coorY;

    private double speedX, speedY;
    private double gravity;

    private final int DIAMETER;
    private final double RADIUS;
    private Rectangle bounds;

    private boolean aim;
    Image pokeballImage;

    /**
      * Ball
      * This constructor creates a Ball object, from its starting x and y coordinates
      * @param START_COOR_X the starting x coordinate of the Ball
      * @param START_COOR_Y the starting y coordinate of the Ball
      */
    Ball(int START_COOR_X, int START_COOR_Y) {
        this.START_COOR_X = START_COOR_X;
        this.START_COOR_Y = START_COOR_Y;
        this.coorX = START_COOR_X;
        this.coorY = START_COOR_Y;

        this.gravity = 0;

        this.DIAMETER = 15;
        this.RADIUS = this.DIAMETER / 2;
        this.bounds = new Rectangle(START_COOR_X, START_COOR_Y, this.DIAMETER, this.DIAMETER);

        this.aim = true;
        pokeballImage = t.getImage("src/images/pokeball.png");
    }

    /**
      * getSTART_COOR_X
      * This getter returns the Ball's starting x coordinate
      * @return a double value of the Ball's starting x coordinate
      */
    public double getSTART_COOR_X() {
        return this.START_COOR_X;
    }

    /**
      * getSTART_COOR_Y
      * This getter returns the Ball's starting y coordinate
      * @return a double value of the Ball's starting y coordinate
      */
    public double getSTART_COOR_Y() {
        return this.START_COOR_Y;
    }

    /**
      * setCoorX
      * This setter updates the Ball's current x coordinate
      * @param coorX the Ball's x coordinate
      */
    public void setCoorX(double coorX) {
        this.coorX = coorX;
    }

    /**
      * getCoorX
      * This getter returns the Ball's current x coordinate
      * @return a double value of the Ball's x coordinate
      */
    public double getCoorX() {
        return this.coorX;
    }

    /**
      * setCoorY
      * This setter updates the Ball's current y coordinate
      * @param coorY the Ball's y coordinate
      */
    public void setCoorY(double coorY) {
        this.coorY = coorY;
    }

    /**
      * getCoorY
      * This getter returns the Ball's current y coordinate
      * @return a double value of the Ball's y coordinate
      */
    public double getCoorY() {
        return this.coorY;
    }

    /**
      * setSpeedX
      * This setter updates the Ball's current x speed
      * @param speedX the Ball's x speed
      */
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    /**
      * getSpeedX
      * This getter returns the Ball's current x speed
      * @return a double value of the Ball's x speed
      */
    public double getSpeedX() {
        return this.speedX;
    }

    /**
      * setSpeedY
      * This setter updates the Ball's current y speed
      * @param speedY the Ball's y speed
      */
    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    /**
      * getSpeedY
      * This getter returns the Ball's current y speed
      * @return a double value of the Ball's y speed
      */
    public double getSpeedY() {
        return this.speedY;
    }

    /**
      * setGravity
      * This setter updates the Ball's current gravity
      * @param gravity the Ball's gravity
      */
    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    /**
      * getGravity
      * This getter returns the Ball's current gravity
      * @return a double value of the Ball's gravity
      */
    public double getGravity() {
        return this.gravity;
    }

    /**
      * getDIAMETER
      * This getter returns the Ball's diameter
      * @return an int value of the Ball's diameter
      */
    public int getDIAMETER() {
        return this.DIAMETER;
    }

    /**
      * getRADIUS
      * This getter returns the Ball's radius
      * @return a double value of the Ball's radius
      */
    public double getRADIUS() {
        return this.RADIUS;
    }

    /**
      * setAim
      * This setter updates the Player's aim state
      * @param aim a boolean value that indicates if the Player can aim the ball or not
      */
    public void setAim(boolean aim) {
        this.aim = aim;
    }

    /**
      * getAim
      * This getter returns the Player's current aim state
      * @return a boolean value of true if the Player can aim
      */
    public boolean getAim() {
        return this.aim;
    }

    /**
      * getBOUNDS
      * This getter returns the Ball's bounds (a Rectangle)
      * @return a Rectangle of the Ball's bounds
      */
    public Rectangle getBounds() {
        return this.bounds;
    }

    /**
     * shoot
     * This method updates the Ball's coordinates and speed, as it is launched
     */
    public void shoot() {
        this.setSpeedY(this.getSpeedY() + this.getGravity()); //gradually falling due to gravity

        if (((this.getCoorX() + this.getSpeedX()) >= (760 - this.getDIAMETER())) || ((this.getCoorX() + this.getSpeedX()) <= 245)) { //hits the sides of the orb bounded area (black rectangle)
            this.setSpeedX(-this.getSpeedX()); //reverse horizontal speed (goes in the other direction)
        }

        if ((this.getCoorY() + this.getSpeedY()) <= 270) { //hits the top of the top of the orb bounded area (black rectangle)
            this.setSpeedY(-this.getSpeedY()); //reverse vertical speed (goes in the other direction)
        }

        this.setCoorX(this.getCoorX() + this.getSpeedX()); //move x coordinate, in accordance to the horizontal speed
        this.setCoorY(this.getCoorY() + this.getSpeedY()); //move y coordinate, in accordance to the vertical speed

        this.updateBounds(); //update the bounds that encloses the ball (for collision)
    }

    /**
      * collision
      * This method pops all the orbs the Ball collides with,
      * sending the Ball bouncing in the other direction
      * @param orbs an ArrayList of all Orbs current on the screen
      * @param copyOfOrbs an ArrayList copy of all Orbs in the level (including popped ones)
      */
    public void collision(ArrayList<Orb> orbs, ArrayList<Orb> copyOfOrbs) {
        for (int i = 0; i < orbs.size(); i++) {
            if (this.getBounds().intersects((orbs.get(i)).getBOUNDS())) { //ball collides with an orb
                this.bounce();

                orbs.get(i).popped(orbs, i, copyOfOrbs); //remove orb from the screen, and take according action based on popped orb
            }
        }
    }

    /**
      * bounce
      * This method updates the direction of the Ball, when it bounces off of a surface
      */
    public void bounce() {
        this.setSpeedX(-this.getSpeedX()); //reverse horizontal speed (goes in the other direction)
        this.setSpeedY(-this.getSpeedY()); //reverse vertical speed (goes in the other direction)
        this.setGravity(0.75); //stronger gravity
        this.setSpeedY(this.getSpeedY() * this.getGravity()); //change vertical speed in accordance to gravity
    }

    /**
      * resetPosition
      * This method resets the Ball's x and y coordinates back to the starting ones
      */
    public void resetPosition() {
        this.setCoorX(this.getSTART_COOR_X()); //reset x coordinate
        this.setCoorY(this.getSTART_COOR_Y()); //reset y coordinate
    }

    /**
      * updateBounds
      * This method updates the bound (Rectangle) that encircles the Ball, in accordance with the Ball current location
      */
    public void updateBounds() {
        if (this.getBounds() != null) {
            (this.getBounds()).setLocation((int)this.coorX, (int)this.coorY);
        }
    }

    /**
      * draw
      * This method draws a Ball to the screen
      * @param g the Java Graphics class
      */
    public void draw(Graphics g) {
        g.drawImage(pokeballImage, (int)(this.getCoorX()), (int)(this.getCoorY()), this.getDIAMETER(), this.getDIAMETER(), null);
    }
}