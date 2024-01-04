import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.Graphics;

/** [GamePanel.java]
  * Create a new GamePanel that is displayed during the majority of the game
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class GamePanel extends JPanel implements ActionListener {
    Toolkit t = Toolkit.getDefaultToolkit();

    Mouse mouse;
    AimGuide aimGuide;

    Keyboard keyboard;

    Image levelTemplateImage = t.getImage("src/images/game-template.png"); //background image of game screen (same for each level)
    Image transitionImage = t.getImage("src/images/transition.png"); //transition image between levels

    BufferedImage bufferedImage;

    ArrayList<Icon> icons;

    Button homeButton, levelButton, fireButton;

    Ball ball;
    Timer ballTimer;

    ArrayList<Orb> orbs;
    ArrayList<Orb> copyOfOrbs;
    ArrayList<Character> characters;
    Character[] currentCharacters;
    Player player;

    private int numOfEnemies;

    int totalNumOfEnemies;

    private boolean update;

    private int animationFrames;

    Random random;

    /**
      * GamePanel
      * This constructor creates a GamePanel object (on which all game components will be drawn on)
      */
    GamePanel() {
        this.setLayout(null); //allows for free movement of components

        this.mouse = new Mouse();
        this.aimGuide = new AimGuide();
        this.keyboard = new Keyboard();

        this.ball = new Ball(492, 280);

        this.currentCharacters = new Character[14];

        this.icons = new ArrayList<Icon>();

        random = new Random();

        this.animationFrames = 0;
        this.update = false;

        //icons (default and hover) for home and level selection buttons
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                try {
                    bufferedImage = ImageIO.read(new File("src/images/icons/other-icons.png"));
                    icons.add(new ImageIcon((bufferedImage.getSubimage(i*256, j*240, 256, 240)).getScaledInstance(32, 30, Image.SCALE_DEFAULT)));
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }

        icons.add(new ImageIcon(t.getImage("src/images/icons/fire-icon.png").getScaledInstance(91, 40, Image.SCALE_DEFAULT))); //default icon for fire button
        icons.add(new ImageIcon(t.getImage("src/images/icons/hover-fire-icon.png").getScaledInstance(91, 40, Image.SCALE_DEFAULT))); //hover icon for fire button

        this.homeButton = new Button(icons.get(0), icons.get(2), 20, 20, 32, 30, this);
        this.levelButton = new Button(icons.get(1), icons.get(3), 20, 60, 32, 30, this);
        this.fireButton = new Button(icons.get(4), icons.get(5), 837, 610, 91, 40, this);

        this.add(homeButton);
        this.add(levelButton);
        this.add(fireButton);

        this.addKeyListener(keyboard);
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(mouse);
    }

    /**
      * setUpdate
      * This setter updates GamePanel's update state
      * @param update a boolean value that indicates if the Panel should be updated
      */
    public void setUpdate(boolean update) {
        this.update = update;
    }

    /**
      * getUpdate
      * This getter returns the GamePanel's update state
      * @return true if the panel should be updated
      */
    public boolean getUpdate() {
        return this.update;
    }

    /**
      * setNumOfEnemies
      * This setter updates GamePanel's number of enemies
      * @param numOfEnemies the number of enemies on screen
      */
    public void setNumOfEnemies(int numOfEnemies) {
        this.numOfEnemies = numOfEnemies;
    }

    /**
      * getNumOfEnemies
      * This getter returns the GamePanel's number of enemies
      * @return an int value of the panel's number of enemies
      */
    public int getNumOfEnemies() {
        return this.numOfEnemies;
    }

    /**
     * setAnimationFrames
     * This setter updates the GamePanel's number of animation frames for animatable elements of the panel
     * @param animationFrames an int value of the panel's animation frames
     */
    public void setAnimationFrames(int animationFrames) {
        this.animationFrames = animationFrames;
    }

    /**
      * getAnimationFrames
      * This getter returns the GamePanel's number of animation frames for animatable elements of the panel
      * @return an int value of the panel's animation frames
      */
    public int getAnimationFrames() {
        return this.animationFrames;
    }

    /**
      * actionPerformed
      * This method dictates the actions that should be
      * taken when a button is pressed
      * @param e the ActionEvent
      */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton) { //if home button is clicked, redirect user to home screen
            PanelCardLayout.cards.show(PanelCardLayout.cardContainer, "Home");

            this.reset();
        } else if (e.getSource() == levelButton) { //if level button is clicked, redirect user to level selection screen
            PanelCardLayout.cards.show(PanelCardLayout.cardContainer, "Level");

            this.reset();
        } else if (e.getSource() == fireButton) {
            fireButton.setEnabled(false);

            ball.setSpeedX(aimGuide.getStepX());
            ball.setSpeedY(aimGuide.getStepY());

            ball.setAim(false);

            ballTimer = new Timer(50, new BallTimerListener());

            ballTimer.start(); //start ball timer
        }
    }

    /**
      * paintComponent
      * This method draws all the necessary components on a GamePanel
      * @param g the Java Graphics class
      */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(levelTemplateImage, 0, 0, null);

        aimGuide.mousePosition = this.getMousePosition();

        for (int i = 0; i < orbs.size(); i++) {
            (orbs.get(i)).draw(g);
        }

        if (ball.getAim()) {
            aimGuide.drawAimGuide(g, ball);
        }

        ball.draw(g);

        for (int i = 0; i < currentCharacters.length; i++) {
            if (currentCharacters[i] != null) {
                currentCharacters[i].draw(g);
            }
        }

        if (this.getUpdate()) {
            this.animatePokemon(g);
        }

        if (this.getAnimationFrames() >= 10) {
            this.reset();
        }

        if (player.getLevelComplete()) {
            fireButton.setEnabled(false);
            g.drawImage(transitionImage, 0, 0, null);
        }

        g.setColor(Color.WHITE);
        g.drawString("# of enemies: " + this.getNumOfEnemies() + "/" + totalNumOfEnemies, 900, 30);

        super.repaint();
    }

    /**
      * animatePokemon
      * This method animates a Pokeémon's moving
      * @param g the Java Graphics class
      */
    public void animatePokemon(Graphics g) {
        this.setAnimationFrames(this.getAnimationFrames() + 1);

        for (int i = 0; i < currentCharacters.length; i++) {
            if ((currentCharacters[i] instanceof Pokemon) && (currentCharacters[i] != null)) {
                if ((currentCharacters[i-1] == null || (currentCharacters[i-1] instanceof Pokemon && ((Pokemon) currentCharacters[i-1]).getMove()))) {
                    Pokemon pokemon = (Pokemon)currentCharacters[i];

                    pokemon.setMove(true);

                    pokemon.moveForward();
                }
            }
        }

        try {
            Thread.sleep(40);
        } catch(InterruptedException e) {
            System.out.println(e);
        }
    }

    /**
      * reset
      * This method resets the screen for the next move
      */
    public void reset() {
        for (int i = 0; i < currentCharacters.length; i++) {
            if ((currentCharacters[i] instanceof Pokemon) && (((Pokemon) currentCharacters[i]).getMove())) {
                ((Pokemon) currentCharacters[i]).setMove(false); //can no longer move
                currentCharacters[i-1] = currentCharacters[i]; //move up in Array
                currentCharacters[i] = null;
            }
        }

        player.resetDamage();
        player.setNumOfBombs(0);

        ball.resetPosition();
        ball.setGravity(0);
        ball.setAim(true);

        if (ballTimer != null) {
            ballTimer.stop();
        }

        aimGuide.setFreeze(false);
        fireButton.setEnabled(true);

        this.setAnimationFrames(0);
        this.setUpdate(false);

        if (this.getNumOfEnemies() == 0) {
            player.setLevelComplete(true);
        }
    }

    /**
      * nextLevel
      * This method updates the screen to display the next level
      */
    public void nextLevel() {
        if (Main.currentLevel < PanelCardLayout.levelSelectionScreen.getLEVELS().length) { //CHANGE TO 6 LATER (ONLY HAVE 2 LEVELS DONE FOR NOW)
            Main.currentLevel++;

            Main.unlocked[Main.currentLevel-1] = true;

            PanelCardLayout.levelSelectionScreen.levelSetup(PanelCardLayout.levelSelectionScreen.getLEVELS()[Main.currentLevel-1], Main.currentLevel-1);

            PanelCardLayout.levelSelectionScreen.buttons[Main.currentLevel-1].setIcon(PanelCardLayout.levelSelectionScreen.defaultLevelIcons[Main.currentLevel-1]);
            PanelCardLayout.levelSelectionScreen.buttons[Main.currentLevel-1].setRolloverIcon(PanelCardLayout.levelSelectionScreen.hoverLevelIcons[Main.currentLevel-1]);
        } else {
            PanelCardLayout.cards.show(PanelCardLayout.cardContainer, "End");
            PanelCardLayout.endScreen.setWin(true);
        }
    }

    /** [BallTimerListener.java]
     * Create a new ActionListener that takes place after the ball timer ends
     * @author Anastasiya Volgina
     * @version 1.0 Build May 26 - Jun 12, 2023
     */
    public class BallTimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ball.shoot();
            ball.collision(orbs, copyOfOrbs); //ball collision with orbs (apply according damage/benefits per orb popped)

            if (ball.getCoorY() > (GameFrame.WIDTH + 50)) { //ball leaves the screen
                ballTimer.stop(); //stop ball timer
                for (int i = 1; i < currentCharacters.length; i++) { //loop through all characters current being displayed on screen
                    if (currentCharacters[i] instanceof Pokemon) {
                        if (player.getDamage() > 0) {
                            player.attack(currentCharacters[i]);
                        }

                        player.applyBomb(currentCharacters[i]); //apply bomb damage to all pokémon current on screen

                        if (currentCharacters[i].getHealth() <= 0) { //pokemon dies
                            currentCharacters[i] = null; //remove from current characters (no longer displayed on screen)

                            PanelCardLayout.gameScreen.setNumOfEnemies(PanelCardLayout.gameScreen.getNumOfEnemies() - 1); //reduce enemy count by 1
                        }
                    }

                    PanelCardLayout.gameScreen.setUpdate(true);
                }

                //if the last space on the screen is enemy and there are still enemies off-screen, generate possibility of new enemy being displayed
                boolean empty = true;
                for (int i = 1; i < currentCharacters.length; i++) {
                    if (currentCharacters[i] != null) {
                        empty = false;
                        break;
                    }
                }

                if (((currentCharacters[currentCharacters.length-1] == null) && (characters.size() > 0))) {
                    int randNum = random.nextInt(2); //generate possibility of adding a new enemy to the screen

                    if ((randNum != 0) || empty) { //2 of 3 chance in generating a new enemy OR screen is currently empty (new enemy must appear in this case)
                        currentCharacters[currentCharacters.length-1] = characters.get(0); //add new enemy to the screen
                        characters.remove(0);
                    }
                }

                if (currentCharacters[1] instanceof Pokemon) {
                    currentCharacters[1].attack(player);

                    if (player.getHealth() <= 0) {
                        PanelCardLayout.endScreen.setWin(false);
                        PanelCardLayout.cards.show(PanelCardLayout.cardContainer, "End");
                    }
                }
            }
        }
    }

    /** [Keyboard.java]
     * Create a KeyListener
     * @author Anastasiya Volgina
     * @version 1.0 Build May 26 - Jun 12, 2023
     */
    public class Keyboard implements KeyListener {
        public void keyPressed(KeyEvent e) {
            if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (player.getLevelComplete())) {
                PanelCardLayout.gameScreen.nextLevel();
                fireButton.setEnabled(true);
            }
        }

        public void keyTyped(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
        }
    }

    /** [Mouse.java]
     * Create a MouseListener
     * @author Anastasiya Volgina
     * @version 1.0 Build May 26 - Jun 12, 2023
     */
    public class Mouse implements MouseListener {
        public void mousePressed(MouseEvent e) {
            aimGuide.setFreeze(!aimGuide.getFreeze());
        }

        public void mouseClicked(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }
    }
}