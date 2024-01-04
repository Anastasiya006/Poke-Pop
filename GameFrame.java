import javax.swing.JFrame;

/** [GameFrame.java]
  * Create a new GameFrame, on which each panel may de displayed
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class GameFrame extends JFrame {
    static final int WIDTH = 1000;
    static final int HEIGHT = 700;

    /**
      * GameFrame
      * This constructor creates a GameFrame object (that is placed in the middle of the user's screen)
      */
    GameFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null); //sets frame in the middle of user's screen
        this.setUndecorated(true); //hides the top bar
        this.setResizable(false); //user cannot change the size of the screen, it is fixed
    }
}
