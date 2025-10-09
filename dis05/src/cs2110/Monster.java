package cs2110;

import java.util.Random;

/**
 * A monster in our game simulation that attacks a random living player on each of its turns.
 */
public class Monster implements Actor {

    /**
     * The starting health level of a monster.
     */
    public static final int STARTING_HEALTH = 20;

    /**
     * The name of this monster.
     */
    private final String name;

    /**
     * The current health level of this monster.
     */
    private int health;

    /**
     * The base power level of this monster.
     */
    private final int power;

    /**
     * The game engine that created this monster.
     */
    GameEngine engine;

    /**
     * Constructs a new monster with the given `name` and initializes its health and power levels.
     */
    public Monster(String name, GameEngine engine) {
        this.name = name;
        this.engine = engine;
        health = STARTING_HEALTH;
        power = engine.rng().nextInt(10,20);
    }

    /**
     * Launches an attack against a random living player.
     */
    @Override
    public void takeTurn() {
        System.out.println("------------------------------------------");
        System.out.println("Starting " + name + "'s Turn:\n");

        Player target = engine.randomLivingPlayer();
        System.out.println(name + " chooses to attack " + target.name());
        attack(target);
    }

    @Override
    public void attack(Actor target) {
        Random roll = new Random();
        int atk = roll.nextInt(power+1);
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
        int def = roll.nextInt(power+1);
        if (atk >= def) {
            health -= atk;
            if (health <= 0) {
                engine.processMonsterDeath(this);
            }
            return false;
        }
        return true;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int health() {
        return health;
    }

    @Override
    public int power() {
        return power;
    }

    @Override
    public String toString() {
        return name + ", power=" + power + ", health=" + health;
    }
}
