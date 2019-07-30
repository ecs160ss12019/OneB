package com.example.breakout;

public class BODestroyBlocks extends BOPowerUp {

    public BOGameController gc;

    public BODestroyBlocks(BOGameController gc){
        this.gc = gc;
        int blocksAlive = 0;
        for (int i = 0; i < gc.blocks.size(); i++){
            if (!gc.blocks.get(i).getDeadStatus()) {
                blocksAlive++;
            }
        }
        for (int j = 0; j < blocksAlive % 2; j++) {
            if (!gc.blocks.get(j).getDeadStatus()) {
                gc.blocks.get(j).setDeadStatus();
            }
        }
    }

    // Code the logic of our power-up.
    public void apply(BOGameController gc) {
        ;

    }


    public String type() {
        return "Destroy Blocks";
    }

    public BOObject getMember() {
        return null;
    }

    @Override
    public void time() {
        gc.powerUp = new BONoPowerUp();

    }
}
