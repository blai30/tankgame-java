package GameObjects;

/**
 * The base class for various types of walls.
 */
public abstract class Wall extends GameObject implements SolidObject {

    /**
     * Determines if the wall is allowed to be destroyed by other game elements.
     * @return True for breakable and false for unbreakable
     */
    public abstract boolean isBreakable();

    public abstract void takeDamage(int damageDealt);

}
