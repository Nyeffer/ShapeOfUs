package troisstudentsbjjm.theshapeofus.Towers;

/**
 * Created by mrber on 2017-05-15.
 */

public class Bullets {

    private float x;
    private float y;
    private float xVelocity;
    private int direction;

    Bullets(float x, float y, int speed, int direction) {
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.xVelocity = speed * direction;
    }

    public int getDirection() {
        return  direction;
    }

    public void hideBullet() {
        this.x = -100;
        this.xVelocity = 0;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
