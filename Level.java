import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/** [Level.java]
  * Creates a new Level based on the information indicated in
  * a text file (separate text file for each leve)
  * @author Anastasiya Volgina
  * @version 1.0 Build May 26 - Jun 12, 2023
  */

public class Level {
    File level;
    Scanner input;
    private final String FILENAME;

    private final ArrayList<Orb> ORBS;
    private final ArrayList<Character> CHARACTERS;
    private Player player;

    /**
      * Level
      * This constructor creates a Character object, from x and y coordinates,
      * a health value, damage value, and max health value
      * @param FILENAME the filename of the file that holds the level's information
      */
    Level(String FILENAME) {
        this.FILENAME = FILENAME;
        this.ORBS = new ArrayList<Orb>();
        this.CHARACTERS = new ArrayList<Character>();
    }

    /**
      * getFILENAME
      * This getter returns the filename that corresponds with the Level
      * @return a String value of the Level's filename
      */
    public String getFILENAME() {
        return this.FILENAME;
    }

    /**
      * getORBS
      * This getter returns the Level's ArrayList of Orbs
      * @return an ArrayList of Level's Orbs
      */
    public ArrayList<Orb> getORBS() {
        return this.ORBS;
    }

    /**
      * getCHARACTERS
      * This getter returns the Level's ArrayList of Characters
      * @return an ArrayList of Level's Characters (Player and Pokémon)
      */
    public ArrayList<Character> getCHARACTERS() {
        return this.CHARACTERS;
    }

    /**
      * setPlayer
      * This setter updates the Level's Player
      * @param player the Level's Player object
      */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
      * getPlayer
      * This getter returns the Level's Player object
      * @return a Player object
      */
    public Player getPlayer() {
        return player;
    }

    /**
      * generateLevel
      * This method generates all the necessary components (orbs, pokémon, player)
      * of a level using a text file for reference
      */
    public void generateLevel() {
        String header;
        int amount;
        double coorX, coorY;

        int normalDamage = 2;
        int bombDamage = 50;

        try {
            level = new File(this.getFILENAME());
            input = new Scanner(level);

            while (input.hasNext()) {
                do {
                    header = input.nextLine(); //read header of section until it's length is greater than '0' (not empty)
                } while (header.length() == 0);

                amount = Integer.parseInt(header.substring(header.lastIndexOf(" ") + 1)); //get the amount of each game object needed

                if (header.contains("Orbs: ")) { //create orbs
                    String typeOfOrb = header.substring(0, header.indexOf(" ")).toLowerCase(); //get the type of orb (normal, bomb, restore, or health)

                    for (int i = 0; i < amount; i++) {
                        coorX = Double.parseDouble(input.next());
                        coorY = Double.parseDouble(input.next());

                        if (typeOfOrb.equals("normal")) { //add new normal orb
                            this.getORBS().add(new DamageOrb(coorX, coorY, normalDamage, typeOfOrb));
                        } else if (typeOfOrb.equals("bomb")) { //add new bomb orb
                            this.getORBS().add(new DamageOrb(coorX, coorY, bombDamage, typeOfOrb));
                        } else if (typeOfOrb.equals("restore")) { //add new restore orb
                            this.getORBS().add(new RestoreOrb(coorX, coorY));
                        } else if (typeOfOrb.equals("health")) { //add new health orb
                            this.getORBS().add(new HealthOrb(coorX, coorY));
                        }
                    }
                } else if (header.contains("Player:")) { //create player
                    coorX = Double.parseDouble(input.next());
                    coorY = Double.parseDouble(input.next());

                    this.setPlayer(new Player((int)coorX, (int)coorY, 50, 0, 50));
                    this.getCHARACTERS().add(this.getPlayer());
                } else if (header.contains("Pokemon:")) { //create pokémon
                    for (int i = 0; i < amount; i++) {
                        String typeOfPokemon = input.next(); //get the type of pokémon (fennekin, froakie, pancham)
                        int stage = Integer.parseInt(input.next());
                        int damage = Integer.parseInt(input.next());
                        int maxHealth = Integer.parseInt(input.next());
                        coorX = Double.parseDouble(input.next());
                        coorY = Double.parseDouble(input.next());

                        if (typeOfPokemon.equals("Fennekin")) { //add new Fennekin pokémon
                            this.getCHARACTERS().add(new Pokemon((int)coorX, (int)coorY, maxHealth, damage, "Fennekin", stage, maxHealth));
                        } else if (typeOfPokemon.equals("Froakie")) { //add new Froakie pokémon
                            this.getCHARACTERS().add(new Pokemon((int)coorX, (int)coorY, maxHealth, damage, "Froakie", stage, maxHealth));
                        } else if (typeOfPokemon.equals("Pancham")) { //add new Pancham pokémon
                            this.getCHARACTERS().add(new Pokemon((int)coorX, (int)coorY, maxHealth, damage, "Pancham", stage, maxHealth));
                        }
                    }
                }
            }

            input.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
}