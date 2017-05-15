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

    // Setter and Getter
    public void setVelocity(PointF velocity) { this.velocity = velocity;   }
    public void setRotate(float rotate) { this.rotate = rotate; }
    

    public PointF getVelocity() { return velocity;  }
    public float getRotate() {  return rotate;  }
    public int getDamage() { return damage; }


}
