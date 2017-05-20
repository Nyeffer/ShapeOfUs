package troisstudentsbjjm.theshapeofus.LevelMaterials;

import troisstudentsbjjm.theshapeofus.Primatives.GameObject;

/**
 * Created by Braedon Jolie on 2017-05-19.
 */

public class WhiteTile extends GameObject {

    public WhiteTile(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        setTraversable();

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        // choose a bitmap
        setBitmapName("tile");

        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();
    }

    @Override
    public void update(long fps, float gravity) {
        // Nothing because this is a floor tile
    }
}
