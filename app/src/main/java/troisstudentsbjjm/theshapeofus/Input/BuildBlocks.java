package troisstudentsbjjm.theshapeofus.Input;

import android.util.Log;

import java.util.ArrayList;
import troisstudentsbjjm.theshapeofus.Primatives.Square;

public class BuildBlocks {

    public ArrayList<Square> hitBlocks;

    public BuildBlocks(int WorldStart, int WorldEnd, int groundPos, int pixelsPerMeter){
        hitBlocks = new ArrayList<>();
        setHitBlocks(WorldStart,WorldEnd,groundPos,pixelsPerMeter);
    }


    private void setHitBlocks(int WorldStart, int WorldEnd, int groundPos, int pixelsPerMeter){
        for(int i = 0; i < ((WorldEnd - WorldStart)/pixelsPerMeter); i++){
            hitBlocks.add(i, new Square());
            hitBlocks.get(i).hitBox.set(pixelsPerMeter*i,groundPos - 2*pixelsPerMeter,pixelsPerMeter*i + pixelsPerMeter,groundPos);
            hitBlocks.get(i).isActive = true;
            hitBlocks.get(i).location.set(hitBlocks.get(i).hitBox.left, groundPos - pixelsPerMeter);
        }
    }
}
