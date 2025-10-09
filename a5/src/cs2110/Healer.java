package cs2110;

/**
 * A mage that can cast a healing spell and is controlled by the user through console input.
 */
public class Healer extends Mage {

    /**
     * Constructs a new Healer with the given `name` in the GameEngine 'engine' who knows the spell
     * 'healing spell'.
     */
    public Healer(String name, GameEngine engine) {
        super(name, engine, "healing spell");
    }

    /**
     * Uses the console to query the user for which ally they would like to heal. If the ally is
     * null, then the spell isn't cast. Otherwise, the healing amount is applied to the ally.
     */
    @Override
    protected void castSpell() {
        Player ally = engine.selectPlayerTarget();
        if (ally == null) {
            return;
        }
        int healAmount = engine.diceRoll(0, power());
        ally.heal(healAmount);
    }
}