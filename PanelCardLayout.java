import javax.swing.JPanel;
import java.awt.CardLayout;

/** [PanelCardLayout.java]
  * Create a new PanelCardLayout, that holds all the Panel of the game (HomePanel,
  * LevelSelectionPanel, GamePanel, EndPanel), as well as displays the appropriate one
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class PanelCardLayout {
    static GameFrame frame;
    static HomePanel homeScreen;
    static LevelSelectionPanel levelSelectionScreen;
    static GamePanel gameScreen;
    static EndPanel endScreen;
    static JPanel cardContainer;
    static CardLayout cards;

    PanelCardLayout() {
        frame = new GameFrame();
        homeScreen = new HomePanel(); //home screen
        levelSelectionScreen = new LevelSelectionPanel(); //level selection screen (choose which level to play)
        gameScreen = new GamePanel(); //game screen (where the game is played)
        endScreen = new EndPanel(); //end screen
        cardContainer = new JPanel();

        cards = new CardLayout();
        cardContainer.setLayout(cards);

        //add all panels
        cardContainer.add(homeScreen, "Home");
        cardContainer.add(levelSelectionScreen, "Level");
        cardContainer.add(gameScreen, "Game");
        cardContainer.add(endScreen, "End");

        frame.add(cardContainer);
        frame.setVisible(true);
    }
}