package troisstudentsbjjm.theshapeofus.Towers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.opengl.Matrix;
import android.util.Log;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Enemies.DeathAnimation;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Circle;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Square;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Triangle;
import troisstudentsbjjm.theshapeofus.Primatives.Square;
import troisstudentsbjjm.theshapeofus.R;

/**
 * Created by Jeffherson on 2017-05-22.
 */

public class OmniGon extends Square{

    private Bitmap[] bitmaps;

    public float health = 100;

    int bitmapIndex = 0;
    int pixelsPerMeter;

    final long TIME_BETWEEN_DRAWS = 100;
    long drawTime;

    public PointF omniGonCenter;

    public OmniGon(Context context, float x, float y, int pixelsPerMeter) {
        this.pixelsPerMeter = pixelsPerMeter;

        location.set(x,y- 5*pixelsPerMeter);
        hitBox.set((float) (x+0.7*pixelsPerMeter),location.y,x + 2*pixelsPerMeter,y + 5*pixelsPerMeter);
        omniGonCenter = new PointF((float) (hitBox.left+(hitBox.right-hitBox.left)*0.5), (float) (hitBox.top + (hitBox.bottom- hitBox.top)*0.5)-pixelsPerMeter);
        size = hitBox.right - hitBox.left;
        isActive = true;

        setBitmaps(context);
    }


    private void setBitmaps(Context context){
        bitmaps = new Bitmap[10];
        bitmaps[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi1);
        bitmaps[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi2);
        bitmaps[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi3);
        bitmaps[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi4);
        bitmaps[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi5);
        bitmaps[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi6);
        bitmaps[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi7);
        bitmaps[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi8);
        bitmaps[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi9);
        bitmaps[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi10);
    }


    public void draw(Canvas canvas, Paint paint) {
        if (isActive){
            canvas.drawBitmap(bitmaps[bitmapIndex], location.x, location.y, paint);
            paint.setColor(Color.argb(255,255,255,255));
            canvas.drawText("OmniGon Health: " + (int)health,hitBox.left - 2*pixelsPerMeter,hitBox.top-pixelsPerMeter,paint);
        }
    }


    private void updateBitmap(){
        if(System.currentTimeMillis() >= drawTime + TIME_BETWEEN_DRAWS) {
            drawTime = System.currentTimeMillis();
            if(bitmapIndex < 9) {
                bitmapIndex++;
            } else {
                bitmapIndex = 0;
            }
        }
    }


    public void update(ArrayList<Enemy_Circle> C_Enemies, ArrayList<Enemy_Square> S_Enemies, ArrayList<Enemy_Triangle> T_Enemies, long fps) {
        updateBitmap();
        update_C(C_Enemies, fps);
        update_S(S_Enemies, fps);
        update_T(T_Enemies, fps);
    }


    private void update_C(ArrayList<Enemy_Circle> C_Enemies, long fps) {
        for (Enemy_Circle Enemy : C_Enemies) {
            checkTowerHealth(Enemy);
            if (isActive) {
                    if (!Enemy.isBlocked) {
                        if (((Enemy.center.x + Enemy.radius * pixelsPerMeter) + (Enemy.velocityX / fps)) >= hitBox.left) {
                            Enemy.location.x += (hitBox.left - (Enemy.center.x + (0.5 * Enemy.size * pixelsPerMeter)));
                            Enemy.isBlocked = true;
                        }
                    } else if (Enemy.isDead && !Enemy.readyToExplode && Enemy.center.x + Enemy.size*pixelsPerMeter > hitBox.left) {
                        health -= Enemy.damage * 0.5;
                        Enemy.isBlocked = false;
                    } else if (!Enemy.isDead && Enemy.readyToExplode && Enemy.center.x + Enemy.size*pixelsPerMeter > hitBox.left) {
                        health -= Enemy.damage;
                        Enemy.destroy();
                    } else {
                        if (Enemy.center.x + Enemy.radius * pixelsPerMeter > hitBox.left && Enemy.center.x + Enemy.radius * pixelsPerMeter < hitBox.right) {
                            if (Enemy.location.x - (hitBox.left - Enemy.center.x + Enemy.radius * pixelsPerMeter) / fps < hitBox.left) {
                                Enemy.location.x = (hitBox.left - (Enemy.size * pixelsPerMeter));
                            } else {
                                Enemy.location.x -= (hitBox.left - Enemy.center.x + Enemy.radius * pixelsPerMeter) / fps;
                            }
                        }

                }
            }
        }
    }


    private void checkTowerHealth(Enemy_Circle Enemy){
        if (health <= 0){
            destroyTower();
            if (Enemy.isBlocked && !isActive){
                Enemy.isBlocked = false;
            }
        }
    }


    private void update_S(ArrayList<Enemy_Square> S_Enemies, long fps) {
        for (Enemy_Square Enemy : S_Enemies) {
            checkTowerHealth(Enemy);
            if (isActive) {
                if (Enemy.facingRight) {
                    if (!Enemy.rolling) {
                        if (Enemy.hitBox.right >= hitBox.left && !Enemy.isDead) {
                            health -= Enemy.damage;
                            Enemy.destroy();
                        }
                    } else {
                        if ((Enemy.hitBox.right + Enemy.size * pixelsPerMeter) >= hitBox.left) {
                            Enemy.rolling = false;
                        }
                    }
                }
            }
        }
    }


    private void checkTowerHealth(Enemy_Square Enemy){
        if (health <= 0){
            destroyTower();
        }
    }


    private void update_T(ArrayList<Enemy_Triangle> T_Enemies, long fps){
        for (Enemy_Triangle Enemy : T_Enemies) {
            checkTowerHealth(Enemy);
            if (isActive) {
                if (Enemy.facingRight) {
                    if ((Enemy.A.x + pixelsPerMeter) >= hitBox.left && !Enemy.isBlocked && (Enemy.A.x + pixelsPerMeter) < hitBox.right) {
                        Enemy.location.x = (hitBox.left - pixelsPerMeter);
                        Enemy.velocity.x = 0;
                        if (Enemy.location.y + Enemy.velocity.y / fps >= Enemy.spawnPoint.y) {
                            Enemy.location.y = Enemy.spawnPoint.y;
                            Enemy.isBlocked = true;
                            Enemy.velocity.y = 0;
                        }
                    } else if (hitBox.contains(Enemy.center.x, Enemy.center.y) && !Enemy.isDead) {
                        health -= Enemy.damage;
                        Enemy.destroy();
                    }
                }
            }
        }
    }


    private void checkTowerHealth(Enemy_Triangle Enemy){
        if (health <= 0){
            destroyTower();
            if (Enemy.isBlocked && !isActive){
                if ((Enemy.A.x + 1.5*pixelsPerMeter) >= hitBox.left && (Enemy.A.x + pixelsPerMeter) < hitBox.right)
                    Enemy.isBlocked = false;
            }
        }
    }


    private void destroyTower(){
        isActive = false;
    }
}
