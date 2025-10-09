package cs2110;

/**
 * An individual who takes turns during the game simulation.
 */
public interface Actor {

    /**
     * Simulates the actions that take place during one turn for this Actor.
     */
    void takeTurn();

    /**
     * Launches an attack against the given `target`.
     */
    void attack(Actor target);

    /**
     * Responds to an attack with the given `atk` roll.
     * Returns `true` if the defense succeeded, false otherwise.
     */
    boolean defend(int atk);

    /**
     * Returns the name of this Actor.
     */
    String name();

    /**
     * Returns the current health level for this Actor.
     */
    int health();

    /**
     * Returns the base power level for this Actor.
     */
    int power();
}
