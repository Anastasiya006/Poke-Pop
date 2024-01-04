import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** [HomePanel.java]
  * Create a new EndPanel that is displayed at the beginning of the game
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class HomePanel extends JPanel implements ActionListener {
    Toolkit t = Toolkit.getDefaultToolkit();

    Image background = t.getImage("src/images/home-page.png"); //background image of home screen

    Button playButton;

    Icon playIcon = new ImageIcon(t.getImage("src/images/icons/play-icon.png").getScaledInstance(272, 120, Image.SCALE_DEFAULT)); //default play button image
    Icon playIconRollover = new ImageIcon(t.getImage("src/images/icons/hover-play-icon.png").getScaledInstance(272, 120, Image.SCALE_DEFAULT)); //hover play button image

    /**
      * HomePanel
      * This constructor creates a HomePanel object (with a play button)
      */
    HomePanel() {
        this.setLayout(null); //allows for free movement of components

        this.playButton = new Button(playIcon, playIconRollover, 370, 380, 272, 120, this);
        this.add(playButton);
    }

    /**
      * actionPerformed
      * This method dictates the actions that should be
      * taken when a button is pressed
      * @param e the ActionEvent
      */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) { //if game button is clicked, redirect user to level selection screen
            PanelCardLayout.cards.show(PanelCardLayout.cardContainer, "Level");
        }
    }

    /**
      * paintComponent
      * This method draws all the necessary components on a HomePanel
      * @param g the Java Graphics class
      */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, null);

        super.repaint();
    }
}