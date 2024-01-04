import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/** [LevelSelectionPanel.java]
  * Create a new LevelSelectionPanel that is displayed
  * at when the user is choosing a level to play
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class LevelSelectionPanel extends JPanel implements ActionListener {
    Toolkit t = Toolkit.getDefaultToolkit();

    Image background = t.getImage("src/images/level-page.png"); //background image of level selection screen

    Icon[] defaultLevelIcons = new Icon[3];
    Icon[] hoverLevelIcons = new Icon[3];
    Icon lockedLevelIcon = new ImageIcon(t.getImage("src/images/icons/level-locked-icon.png").getScaledInstance(192, 180, Image.SCALE_DEFAULT)); //icon for locked levels

    Button level1Button, level2Button, level3Button;
    Button[] buttons;

    BufferedImage bufferedImage;

    private final Level[] LEVELS = {new Level("src/levels/level-1.txt"), new Level("src/levels/level-2.txt"), new Level("src/levels/level-3.txt")};

    /**
      * LevelSelectionPanel
      * This constructor creates a LevelSelectionPanel object (with 6 level buttons)
      */
    LevelSelectionPanel () {
        this.setLayout(null); //allows for free movement of components

        //default icons for each level button
        for (int i = 0; i < defaultLevelIcons.length; i++) {
            try {
                bufferedImage = ImageIO.read(new File("src/images/icons/level-icons.png"));
                defaultLevelIcons[i] = new ImageIcon((bufferedImage.getSubimage((i * 96), 0, 96, 90)).getScaledInstance(192, 180, Image.SCALE_DEFAULT));
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        //hover icons for each level button
        for (int i = 0; i < defaultLevelIcons.length; i++) {
            try {
                bufferedImage = ImageIO.read(new File("src/images/icons/hover-level-icons.png"));
                hoverLevelIcons[i] = new ImageIcon((bufferedImage.getSubimage((i * 96), 0, 96, 90)).getScaledInstance(192, 180, Image.SCALE_DEFAULT));
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        //buttons that redirect user to their corresponding level
        this.level1Button = new Button(defaultLevelIcons[0], hoverLevelIcons[0], 120, 300, 192, 180, this);
        this.level2Button = new Button(lockedLevelIcon, lockedLevelIcon, 410, 300, 192, 180, this);
        this.level3Button = new Button(lockedLevelIcon, lockedLevelIcon, 700, 300, 192, 180, this);

        buttons = new Button[]{level1Button, level2Button, level3Button};

        for (int i = 0; i < buttons.length; i++) {
            this.add(buttons[i]);
        }
    }

    /**
      * getLEVELS
      * This getter returns the LevelSelectionPanel's Array of Levels
      * @return an Array of Level objects
      */
    public Level[] getLEVELS() {
        return this.LEVELS;
    }

    /**
      * actionPerformed
      * This method dictates the actions that should be
      * taken when a button is pressed
      * @param e the ActionEvent
      */
    public void actionPerformed(ActionEvent e) { //only redirects to level if unlocked
        if ((e.getSource() == level1Button) && (Main.unlocked[0])) { //if level 1 button is clicked, redirect user to level 1
            levelSetup(this.getLEVELS()[0], 0);

            PanelCardLayout.cards.show(PanelCardLayout.cardContainer, "Game");
        } else if ((e.getSource() == level2Button) && (Main.unlocked[1])) {  //if level 2 button is clicked, redirect user to level 2
            levelSetup(this.getLEVELS()[1], 1);

            PanelCardLayout.cards.show(PanelCardLayout.cardContainer, "Game");
        } else if ((e.getSource() == level3Button) && (Main.unlocked[2])) { //if level 3 button is clicked, redirect user to level 3
            levelSetup(this.getLEVELS()[2], 2);

            PanelCardLayout.cards.show(PanelCardLayout.cardContainer, "Game");
        }
    }

    /**
      * levelSetup
      * This method sets up all the variables of a gameScreen,
      * in accordance to the level chosen by the user
      * @param level the current Level of the game
      * @param levelIndex the index of the Level in the overall Levels Array
      */
    public void levelSetup(Level level, int levelIndex) {
        level.getCHARACTERS().clear(); //reset characters (assures there are no duplicates)
        level.getORBS().clear(); //resets orbs (assures there are no duplicates)

        level.generateLevel(); //generates level using pre-determined settings in .txt file

        PanelCardLayout.gameScreen.orbs = this.getLEVELS()[levelIndex].getORBS(); //set orbs to current level's layout

        PanelCardLayout.gameScreen.copyOfOrbs = (ArrayList<Orb>) (PanelCardLayout.gameScreen.orbs).clone();
        PanelCardLayout.gameScreen.characters = this.getLEVELS()[levelIndex].getCHARACTERS(); //set characters to current level's layout
        PanelCardLayout.gameScreen.player = this.getLEVELS()[levelIndex].getPlayer(); //reset player for new level

        PanelCardLayout.gameScreen.setNumOfEnemies(((PanelCardLayout.gameScreen.characters).size()-1)); //number of enemies that must be defeated for the level
        PanelCardLayout.gameScreen.totalNumOfEnemies = PanelCardLayout.gameScreen.getNumOfEnemies();

        PanelCardLayout.gameScreen.currentCharacters = updateCurrentBoard(); //current characters displayed on screen (more may be off-screen)
    }

    /**
      * updateCurrentBoard
      * This method sets up all the characters that
      * can be currently displayed on the screen
      */
    public Character[] updateCurrentBoard() {
        Character[] current = new Character[15];

        for (int i = 0; i < PanelCardLayout.gameScreen.characters.size(); i++) {
            int coorX = ((PanelCardLayout.gameScreen.characters).get(i)).getCoorX();

            if (coorX < 1000) { //if characters x coordinate fits into the screen, add to current characters (to be displayed on screen)
                int index = ((coorX - 250) / 50); //determine the ratio between the character's x coordinate and their index in the Array
                current[index] = (PanelCardLayout.gameScreen.characters).get(i);

                PanelCardLayout.gameScreen.characters.remove(i); //remove from list (avoids the issue of being added twice)
                i--; //moves index back (ensures no characters are missed)
            }
        }

        return current; //current characters that appear on the screen at the start of the new level
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