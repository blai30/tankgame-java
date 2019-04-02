package GameObjects;

public abstract class Weapon extends GameObject {

    protected int totalDamage = 1;  // Default base damage
    protected float velocity;

    public int dealDamage() {
        return this.totalDamage;
    }

    protected abstract void init();

}
