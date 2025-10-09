package cs2110;

/**
 * A mage that can cast a fire spell and is controlled by the user through console input.
 */
public class FireMage extends Mage {

    /**
     * Constructs a new FireMage with the given `name` in the GameEngine 'engine' who knows the
     * spell 'fire spell'.
     */
    public FireMage(String name, GameEngine engine) {
        super(name, engine, "fire spell");
    }

    /**
     * Uses the console to query the user for which monster they would like to attack. If the target
     * is null, then the spell isn't cast. Otherwise, fireball damage and recoil damage are applied
     * to the enemy and the FireMage itself, respectively.
     */
    @Override
    protected void castSpell() {
        Actor target = engine.selectMonsterTarget();
        if (target == null) {
            return;
        }
        int normalRoll = engine.diceRoll(1, power());
        int fireballDamage = normalRoll * 2;
        target.defend(fireballDamage);
        int recoilDamage = fireballDamage / 4;
        this.takeDamage(recoilDamage);
    }
}