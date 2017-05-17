package troisstudentsbjjm.theshapeofus.Towers;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jeffherson on 2017-05-16.
 */

public class Tower_Circle extends Tower {
    // Set the worldLocation
    private int rateOfFire;
    private int lastShotTime;
    private int nextBullet;

    // private CopyOnWriteArrayList<Bullet> bullet;

    Tower_Circle(int x, int y) {
        super(x, y);
        setDamage(3);
        lastShotTime = -1;
        nextBullet = -1;
    }

    public void shoot() {
        if(System.currentTimeMillis() - lastShotTime > 1000/rateOfFire) {
            nextBullet++;

        }
    }

}
