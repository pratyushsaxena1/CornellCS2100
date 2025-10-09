package cs2110;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Manages the state of our game simulation by creating and keeping track of players and monsters
 * and facilitating the turn order.
 */
public class GameEngine {

    /**
     * The array of players in this game simulation. Within this array, the first `numLivingPlayers`
     * entries reference `Player` objects with `health() > 0` and the remaining entries reference
     * `Player` objects with `health == 0`.
     */
    private Player[] players;

    /**
     * The number of players who are currently alive in the game. Must have `0 <= numLivingPlayers <
     * players.length`.
     */
    private int numLivingPlayers;

    /**
     * The array of monsters in this game simulation. Within this array, the first
     * `numLivingMonsters` entries reference `Monster` objects with `health() > 0` and the remaining
     * entries reference `Monster` objects with `health == 0`.
     */
    private Monster[] monsters;

    /**
     * The number of monsters who are currently alive in the game. Must have `0 <= numLivingMonsters
     * < monsters.length`.
     */
    private int numLivingMonsters;

    /**
     * The random number generator that is used to model random events in this game.
     */
    private final Random rng;

    /**
     * The Scanner used to accept player console input
     */
    private final Scanner sc;

    /**
     * Constructs a new game engine with a seeded random number generator and a scanner to process
     * console input.
     */
    public GameEngine() {
        rng = new Random(123456L);
        sc = new Scanner(System.in);
    }

    /**
     * Carries out the simulation of our dungeon battle game by initializing the players and
     * monsters before entering the main game loop.
     */
    private void simulateGame() {
        System.out.println("*** Welcome to the Dungeons of Dragon Day! ***\n");

        initializePlayers();
        initializeMonsters();
        mainGameLoop();
    }

    /**
     * Queries for console input to set up the players of this game simulation.
     */
    private void initializePlayers() {
        System.out.print("How many players will you have? ");
        numLivingPlayers = Integer.parseInt(sc.nextLine());
        players = new Player[numLivingPlayers];

        System.out.println("Enter the player names, one at a time.");
        for (int i = 0; i < numLivingPlayers; i++) {
            System.out.print((i + 1) + ": ");
            String name = sc.nextLine();
            System.out.print("   Species: ");
            players[i] = new Player(name, sc.nextLine(), this);
        }
    }

    /**
     * Returns a randomly selected monster name.
     */
    private String getRandomMonsterName() {
        try {
            int line = rng.nextInt(100);
            return Files.readAllLines(Paths.get("monsters.txt")).get(line + 1);
        } catch (IOException e) {
            return "Monster";
        }
    }

    /**
     * Creates the monsters for this game simulation and prints their information to the console.
     */
    private void initializeMonsters() {
        numLivingMonsters = (players.length / 2) + 1;

        System.out.println("\nYou'll battle against " + numLivingMonsters + " monsters.");

        monsters = new Monster[numLivingMonsters];
        for (int i = 0; i < numLivingMonsters; i++) {
            monsters[i] = new Monster(getRandomMonsterName(), this);
            System.out.println("[" + i + "] " + monsters[i]);
        }
    }

    /**
     * Runs the main game loop. Terminates when the Players have won (all Monsters are dead), or
     * when the Players have lost (all Players are dead). While there are still living Players and
     * Monsters, generates a turn order for all active Actors at the start of each turn, and
     * executes each Actor's turn in the generated order. At the end of the game, if the Players
     * have won, the message "Congratulations! You defeated the monsters!" is printed. Otherwise, if
     * the Players have lost, the message "The monsters defeated you. Better luck next time!" is
     * printed.
     */
    private void mainGameLoop() {
        int round = 1;

        // TODO 3: Fill in an appropriate boolean expression to guard the game loop
        while(numLivingMonsters > 0 && numLivingPlayers > 0) {
            System.out.println("==========================================");
            System.out.println("Starting Round " + round + "\n");

            Actor[] actors; // contains living Actors in their turn order for this round
            // TODO 4: Assign `actors` a reference to an array consisting of all Actors who are
            //  *alive* at the start of this round. This array should have no "empty" or `null`
            //  entries. Call the 'shuffle()' helper method to randomize the order of the `actors`.
            actors = new Actor[numLivingPlayers + numLivingMonsters];
            for (int i = 0; i < numLivingPlayers; i++) {
                actors[i] = players[i];
            }
            for (int j = numLivingPlayers; j < actors.length; j++) {
                actors[j] = monsters[j - numLivingPlayers];
            }
            shuffle(actors);

            System.out.println("The turn order will be: ");
            for (int i = 0; i < actors.length; i++) {
                System.out.println((i + 1) + ": " + actors[i].name());
            }

            // TODO 5: Have the living Actors take turns according to the generated order.
            for (int i = 0; i < actors.length; i++) {
                if (actors[i].health() > 0 && numLivingPlayers > 0 && numLivingMonsters >0) {
                    actors[i].takeTurn();
                }
            }
            round++;
        }

        // TODO 6: Print the correct end game message (from the specifications).
        if (numLivingMonsters == 0) {
            System.out.println("Congratulations! You defeated the monsters!");
        }
        else {
            System.out.println("The monsters defeated you. Better luck next time!");
        }
    }

    /**
     * Performs a Fisher-Yates shuffle on this array of actors
     */
    private void shuffle(Actor[] actors) {
        for (int i = 0; i < actors.length; i += 1) {
            int j = rng.nextInt(i, actors.length);
            swap(actors, i, j);

        }
    }

    /**
     * Returns a reference to a random player that is currently alive in this simulation.
     */
    public Player randomLivingPlayer() {
        int randomIndex = rng.nextInt(numLivingPlayers);
        return players[randomIndex];
    }

    /**
     * Returns a reference to an array copy containing references to all living monsters. Client
     * code is free to make modifications to this array.
     */
    public Monster[] livingMonsters() {
        return Arrays.copyOf(monsters, numLivingMonsters);
    }

    /**
     * Returns the random number generator associated to this game engine.
     */
    public Random rng() {
        return rng;
    }

    /**
     * Reports that the given `monster` has been defeated.
     */
    public void processMonsterDeath(Monster monster) {
        // TODO 1: Implement this method according to its specifications.
        //  Hint: your implementation must maintain the class invariants corresponding to 'monsters'
        //  and 'numLivingMonsters'
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i] == monster) {
                swap(monsters, i, numLivingMonsters - 1);
                break;
            }
        }
        numLivingMonsters--;
    }

    /**
     * Reports that the given `player` has been defeated.
     */
    public void processPlayerDeath(Player player) {
        // TODO 2: Implement this method according to its specifications.
        //  Hint: your implementation must maintain the class invariants corresponding to 'players'
        //  and 'numLivingPlayers'
        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) {
                swap(players, i, numLivingPlayers - 1);
                break;
            }
        }
        numLivingPlayers--;
    }

    /**
     * Swaps the entries of the `actors` array in the given indices `x` and `y`.
     */
    private static void swap(Actor[] actors, int x, int y) {
        Actor temp = actors[x];
        actors[x] = actors[y];
        actors[y] = temp;
    }

    /**
     * Returns the next line of console input from the user.
     */
    public String getInputLine() {
        return sc.nextLine();
    }

    /**
     * Runs a game simulation.
     */
    public static void main(String[] args) {
        GameEngine engine = new GameEngine();
        engine.simulateGame();
    }
}
