package cs2110;

import java.util.Random;

/**
 * A player in our game simulation that accepts console input on each turn to determine which
 * monster to attack.
 */
public class Player implements Actor {

    /**
     * The starting health level of a monster.
     */
    public static final int STARTING_HEALTH = 20;

    /**
     * The name of this player.
     */
    private final String name;

    /**
     * The species of this player.
     */
    private final String species;

    /**
     * The current health level of this player.
     */
    private int health;

    /**
     * The base power level of this player.
     */
    private final int power;

    /**
     * The game engine that created this player.
     */
    GameEngine engine;

    /**
     * Constructs a new player with the given `name` and `species` and initializes its health and
     * power levels.
     */
    public Player(String name, String species, GameEngine engine) {
        this.name = name;
        this.engine = engine;
        this.species = species.toLowerCase();
        health = STARTING_HEALTH;
        power = engine.rng().nextInt(10, 20);
    }

    /**
     * Queries the user for which living monster they'd like to attack and then launches an attack
     * against that monster.
     */
    @Override
    public void takeTurn() {
        System.out.println("------------------------------------------");
        System.out.println("Starting " + name + "'s Turn:\n");
        System.out.println("Select the number of the monster you'd like to attack: ");

        Monster[] livingMonsters = engine.livingMonsters();
        for (int i = 0; i < livingMonsters.length; i++) {
            System.out.println("[" + i + "] " + livingMonsters[i]);
        }

        Monster target = null;
        while (target == null) {
            try {
                System.out.print("Selection: ");
                target = livingMonsters[Integer.parseInt(engine.getInputLine())];
            } catch (Exception e) { // either input was not a number or was an invalid index
                System.out.println("Your input couldn't be parsed successfully. Try again.");
            }
        }
        attack(target);
    }

    @Override
    public void attack(Actor target) {
        Random roll = new Random();
        int atk = roll.nextInt(power + 1);
        boolean def = target.defend(atk);
        if (def) {
            System.out.println(target.name() + " successfully defended, no damage was "
                    + "taken.");
        } else {
            System.out.print(name + "'s attack did " + atk + " damage! ");
            if (target.health() > 0) {
                System.out.println(target.name() + " is now at " + target.health() + " health.");
            } else {
                System.out.println(target.name() + " has been defeated!");
            }
        }
    }

    @Override
    public boolean defend(int atk) {
        Random roll = new Random();
        int def = roll.nextInt(power + 1);
        if (atk >= def) {
            health -= atk;
            if (health <= 0) {
                engine.processPlayerDeath(this);
            }
            return false;
        }
        return true;
    }

    @Override
    public String name() {
        return name;
    }

    /**
     * Returns the species of this player.
     */
    public String species() {
        return species;
    }

    @Override
    public int health() {
        return health;
    }

    @Override
    public int power() {
        return power;
    }

}
