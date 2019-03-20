

import util.Vector2D;

public class Bullet extends GameObject {

    private int baseDamage = 1;
    private float velocity;

    Bullet(Vector2D pos, float direction, int damage) {
        this.position = pos;
        this.baseDamage += damage;
    }

    @Override
    public void update() {

    }

}
