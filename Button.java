import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.event.ActionListener;

/** [Button.java]
  * Creates a new Button with all the default settings that all buttons share
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class Button extends JButton {
    /**
      * Button
      * This constructor creates a Button object, from indicated icons (for both states),
      * x and y coordinates, a width, height, and ActionListener
      * @param defaultIcon the default icon that is shown when a button is on the screen
      * @param hoverIcon the icon that is shown when a button is hovered over with a mouse
      * @param coorX the x coordinate of the button
      * @param coorY the y coordinate of the button
      * @param width the width of the button
      * @param height the height of the button
      * @param a the ActionListener associated with the button
      */
    Button(Icon defaultIcon, Icon hoverIcon, int coorX, int coorY, int width, int height, ActionListener a) {
        this.setIcon(defaultIcon); //sets image over button
        this.setRolloverIcon(hoverIcon); //sets lighter version of image over button (to depict that it is being hovered over)
        this.setRolloverEnabled(true);
        this.setLocation(coorX, coorY);
        this.setSize(width, height);
        this.setBorderPainted(false); //makes button invisible (only image will be visible)
        this.setContentAreaFilled(false); //makes button invisible (only image will be visible)
        this.setFocusPainted(false); //makes button invisible (only image will be visible)
        this.setOpaque(false); //makes button invisible (only image will be visible)
        this.addActionListener(a);
    }
}