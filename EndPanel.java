import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** [EndPanel.java]
  * Create a new EndPanel that is displayed at the end of the game
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class EndPanel extends JPanel implements ActionListener {
    Toolkit t = Toolkit.getDefaultToolkit();

    Image winBackground = t.getImage("src/images/win-page.png"); //win background image of home screen
    Image loseBackground = t.getImage("src/images/lose-page.png"); //lose background image of home screen

    Button quitButton;

    Icon quitIcon = new ImageIcon(t.getImage("src/images/icons/quit-icon.png").getScaledInstance(272, 120, Image.SCALE_DEFAULT)); //default play button image
    Icon quitIconRollover = new ImageIcon(t.getImage("src/images/icons/hover-quit-icon.png").getScaledInstance(272, 120, Image.SCALE_DEFAULT)); //hover play button image

    private boolean win;

    /**
      * EndPanel
      * This constructor creates a EndPanel object (with a quit button)
      */
    EndPanel() {
        this.setLayout(null); //allows for free movement of components

        this.quitButton = new Button(quitIcon, quitIconRollover, 370, 370, 272, 120, this);
        this.add(quitButton);

        win = true;
    }

    /**
      * setWin
      * This setter updates the win state
      * @param win a boolean value that indicates if the Player won or not
      */
    public void setWin(boolean win) {
        this.win = win;
    }

    /**
      * getWin
      * This getter returns the win state
      * @return true if the Player has won
      */
    public boolean getWin() {
        return this.win;
    }

    /**
      * actionPerformed
      * This method dictates the actions that should be
      * taken when a button is pressed
      * @param e the ActionEvent
      */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quitButton) { //if quit button is clicked, close the game
            System.exit(0);
        }
    }

    /**
      * paintComponent
      * This method draws all the necessary components on a EndPanel
      * @param g the Java Graphics class
      */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.getWin()) {
            g.drawImage(winBackground, 0, 0, null); //winner background
        } else {
            g.drawImage(loseBackground, 0, 0, null); //loser background
        }

        super.repaint();
    }
}