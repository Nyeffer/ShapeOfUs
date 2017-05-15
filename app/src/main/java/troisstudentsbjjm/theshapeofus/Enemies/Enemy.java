package troisstudentsbjjm.theshapeofus.Enemies;

import android.graphics.PointF;

/**
 * Created by Jeffherson on 2017-05-15.
 */

public abstract class Enemy {
    private PointF velocity;
    private float rotate;
    private int damage,
                health;
    private boolean isDead;

    // Update here

    // Setter and Getter
    public void setVelocity(PointF velocity) { this.velocity = velocity;   }
    public void setRotate(float rotate) { this.rotate = rotate; }
    public void setDamage(int damage) { this.damage = damage;   }
    public void setHealth(int health) { this.health = health;   }
    public void setIsDead(boolean isDead) { this.isDead = isDead;   }

    public PointF getVelocity() { return velocity;  }
    public float getRotate() {  return rotate;  }
    public int getDamage() { return damage; }
    public int getHealth() { return health; }
    public boolean getIsDead() { return isDead; }


}
