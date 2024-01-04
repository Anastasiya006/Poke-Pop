import java.awt.Robot;
import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import static javax.swing.SwingUtilities.convertPointToScreen;

/** [AimGuide.java]
  * Create a new AimGuide, that allows the player to see the Ball's anticipated path
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class AimGuide {
    Robot pixelColourFinder;

    Point mousePosition;

    private int mouseX, mouseY;

    private double stepX, stepY;
    private final int STEP_SIZE;

    private double length;
    private final double MAX_AIM_LENGTH;

    private boolean freeze;

    /**
      * AimGuide
      * This constructor creates an AimGuide object
      */
    AimGuide() {
        this.MAX_AIM_LENGTH = 180;
        this.STEP_SIZE = 10;
        this.freeze = false;

        try {
            this.pixelColourFinder = new Robot(); //determines the colour of pixels on the screen
        } catch (AWTException e) {
            System.out.println(e);
        }
    }

    /**
      * setMouseX
      * This setter updates the mouse's x coordinate
      * @param mouseX the mouse's x coordinate
      */
    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    /**
      * getMouseX
      * This getter returns the mouse's x coordinate
      * @return an int value of the mouse's x coordinate
      */
    public int getMouseX() {
        return this.mouseX;
    }

    /**
      * setMouseY
      * This setter updates the mouse's y coordinate
      * @param mouseY the mouse's y coordinate
      */
    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    /**
      * getMouseY
      * This getter returns the mouse's y coordinate
      * @return an int value of the mouse's y coordinate
      */
    public int getMouseY() {
        return this.mouseY;
    }

    /**
      * setStepX
      * This setter updates the x step of the Ball (based on the player's aim)
      * @param stepX the x step needed to reach the aim point
      */
    public void setStepX(double stepX) {
        this.stepX = stepX;
    }

    /**
      * getStepX
      * This getter returns the x step
      * @return an int value of the x step needed to be taken
      */
    public double getStepX() {
        return this.stepX;
    }

    /**
      * setStepY
      * This setter updates the y step of the Ball (based on the player's aim)
      * @param stepY the y step needed to reach the aim point
      */
    public void setStepY(double stepY) {
        this.stepY = stepY;
    }

    /**
      * getStepY
      * This getter returns the y step
      * @return an int value of the y step needed to be taken
      */
    public double getStepY() {
        return this.stepY;
    }

    /**
      * getSTEP_SIZE
      * This getter returns the step size between each dot drawn in the aim guide
      * @return an int value of the step size between each dot
      */
    public int getSTEP_SIZE() {
        return this.STEP_SIZE;
    }

    /**
      * setLength
      * This setter updates the aim guide's length
      * @param length the length of the aim guide
      */
    public void setLength(double length) {
        this.length = length;
    }

    /**
      * getLength
      * This getter returns aim guide's length
      * @return a double value of the aim guide's length
      */
    public double getLength() {
        return this.length;
    }

    /**
      * getMAX_AIM_LENGTH
      * This getter returns the max aim guide
      * @return a double value of the max aim guide length
      */
    public double getMAX_AIM_LENGTH() {
        return this.MAX_AIM_LENGTH;
    }

    /**
      * setFreeze
      * This setter updates freeze state of the aim guide
      * @param freeze a boolean value that indicates if the aim guide is frozen or not
      */
    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    /**
      * getFreeze
      * This getter returns the freeze state of the aim guide
      * @return true if the aim guide is frozen on the screen
      */
    public boolean getFreeze() {
        return this.freeze;
    }

    /**
      * calculateAimLength
      * This method calculates the length of the aim guide, defaulting it to the max if it is too large
      * @param ball the Ball that the user is aiming using the aim guide
      */
    public void calculateAimLength(Ball ball) {
        double deltaX, deltaY;

        if (this.mousePosition != null && !this.getFreeze()) { //read mouse position as long as aim isn't in freeze mode
            this.setMouseX((int) this.mousePosition.getX());
            this.setMouseY((int) this.mousePosition.getY());

            deltaX = (this.getMouseX() - (ball.getCoorX() - ball.getRADIUS())); //difference between mouse and ball (x coordinate)
            deltaY = (this.getMouseY() - (ball.getCoorY() - ball.getRADIUS())); //difference between mouse and ball (y coordinate)

            this.length = (int)(Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2))); //length of aim guide (using pythagorean theorem)

            this.setStepX(deltaX / (this.length / this.getSTEP_SIZE()));
            this.setStepY(deltaY / (this.length / this.getSTEP_SIZE()));

            if (this.getLength() > this.getMAX_AIM_LENGTH()) { //set aim length as max if exceeded
                this.setLength(this.getMAX_AIM_LENGTH());
            }
        }
    }

    /**
      * drawAimGuide
      * This method draws the aim guide onto the screen (stopping it when it collides with any element)
      * @param ball the Ball that the user is aiming using the aim guide
      * @param g the Java Graphics class
      */
    public void drawAimGuide(Graphics g, Ball ball) {
        g.setColor(Color.WHITE);
        Color backgroundColour = new Color(16,8,4);

        this.calculateAimLength(ball);

        for (int i = 1; i < (this.getLength() / this.getSTEP_SIZE()); i++) {
            int dotCoorX = ((int)(ball.getCoorX() + ball.getRADIUS()) + (int)(i * this.getStepX()));
            int dotCoorY = ((int)(ball.getCoorY() + ball.getRADIUS()) + (int)(i * this.getStepY()));

            Point dotPosition = new Point(dotCoorX, dotCoorY);
            convertPointToScreen(dotPosition, PanelCardLayout.gameScreen); //convert to pixel coordinates (of entire screen, since robot reads through pixels)

            Color currentColour = pixelColourFinder.getPixelColor(dotPosition.x, dotPosition.y); //get colour at intended dot position

            if (!currentColour.equals(backgroundColour)) { //draw circle/dashed aim line until it collides with an orb or the wall
                break;
            }

            g.fillOval(dotCoorX, dotCoorY, 3, 3);
        }
    }
}