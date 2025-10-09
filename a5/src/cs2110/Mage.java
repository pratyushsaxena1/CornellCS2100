package cs2110;

/**
 * A player that can cast a spell and is controlled by the user through console input.
 */
public abstract class Mage extends Player {

    /**
     * The name of the spell that the Mage knows.
     */
    private final String spellName;

    /**
     * Constructs a new Mage with the given `name` who knows the given 'spellName' in the GameEngine
     * 'engine' and initializes the name of the spell they know.
     */
    public Mage(String name, GameEngine engine, String spellName) {
        super(name, engine);
        this.spellName = spellName;
    }

    /**
     * Uses the console to query the user for whether they would like to cast a spell. If they say
     * 'yes', then they cast their spell and skip the attack phase. If they say 'no', they
     * immediately go to the attack phase but don't cast a spell.
     */
    @Override
    public boolean chooseAction() {
        System.out.print("Would you like to cast a " + spellName + " (yes/no)? ");
        String response = engine.getInputLine();
        if (response.equals("yes")) {
            castSpell();
            return false;
        }
        return true;
    }

    /**
     * Uses the console to query the user for whether they would like to cast a spell and
     * potentially casts the spell. The available spell is determined by the Mage's subtype.
     */
    protected abstract void castSpell();
}