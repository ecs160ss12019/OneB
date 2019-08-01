package com.example.breakout;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Random;

public class BODestroyBlocks extends BOPowerUp {

    public BOGameController gc;
    ArrayList<Integer> activeBlocks = new ArrayList<>();

    public BODestroyBlocks(BOGameController gc) {
        this.gc = gc;
        for (int i = 0; i < gc.blocks.size(); i++){
            if(gc.blocks.get(i).getDeadStatus() == false)
                activeBlocks.add(i);
        }


        int blocksToDestroy = activeBlocks.size() / 2;

        Random random = new Random();
        while(blocksToDestroy > 0) {

            int position = random.nextInt(activeBlocks.size());
            int blockToDestroy = activeBlocks.get(position);
            //it can select same block twice.
            gc.blocks.get(blockToDestroy).setDeadStatus();
            gc.blocks.get(blockToDestroy).collider = new RectF(-1,-1,-1,-1);
            blocksToDestroy--;
        }


    }

    public void draw(Canvas mCanvas, Paint mPaint)
    {
        Point dim = gc.getMeta().getDim();
        // gc.powerUp = new BONoPowerup();
        mCanvas.drawText(this.type(), dim.x / 55,dim.y / 4, mPaint);
    }

    // Code the logic of our power-up.
    public void apply(BOGameController gc) {

    }

    public String type() {
        return "Destroy Blocks";
    }

    public BOObject getMember() {
        return null;
    }

    @Override
    public void time() {
        gc.powerUp = new BONoPowerUp(gc);

    }
}
