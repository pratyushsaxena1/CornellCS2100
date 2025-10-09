package cs2110;

/**
 * A player that has a weapon and is controlled by the user through console input.
 */
public class Fighter extends Player {

    /**
     * The weapon that is currently equipped by the Fighter.
     */
    private Weapon equippedWeapon;

    /**
     * Constructs a new Fighter with the given 'name' in the GameEngine 'engine' and initializes the
     * weapon as null.
     */
    public Fighter(String name, GameEngine engine) {
        super(name, engine);
        this.equippedWeapon = null;
    }

    /**
     * Returns the power level of the Fighter. Their equipped weapon can add to their power.
     */
    @Override
    public int power() {
        int base = super.power();
        if (equippedWeapon != null) {
            base += equippedWeapon.power();
        }
        return base;
    }

    /**
     * Returns the toughness level of the Fighter. Their equipped weapon can add to their
     * toughness.
     */
    @Override
    public int toughness() {
        int base = super.toughness();
        if (equippedWeapon != null) {
            base += equippedWeapon.toughness();
        }
        return base;
    }

    /**
     * Uses the console to query the user for whether they would like to change their current
     * equipment. If they say 'yes', the current weapon is unequipped and a new weapon is equipped
     * and then the user is directed to the attack phase. If they say 'no', they immediately go to
     * the attack phase.
     */
    @Override
    public boolean chooseAction() {
        System.out.print("Would you like to change your current equipment (yes/no)? ");
        String response = engine.getInputLine();
        if (response.equals("yes")) {
            if (equippedWeapon != null) {
                equippedWeapon.unequip();
                equippedWeapon = null;
            }
            Weapon newWeapon = engine.selectWeapon();
            if (newWeapon != null) {
                newWeapon.equip();
                equippedWeapon = newWeapon;
            }
        }
        return true;
    }

    /**
     * Updates the state of the game in response to this Fighter's death. Requires that this
     * Fighter's `health` is 0. If the Fighter dies and they had a weapon equipped, then they
     * unequip it.
     */
    @Override
    protected void processDeath() {
        if (equippedWeapon != null) {
            equippedWeapon.unequip();
            equippedWeapon = null;
        }
        super.processDeath();
    }
}