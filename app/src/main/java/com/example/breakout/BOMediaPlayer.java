package com.example.breakout;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;


public class BOMediaPlayer {
    /*
    Handles Media playing!
     */

    private MediaPlayer media;
    private MediaPlayer media_won;
    private MediaPlayer media_lost;

    private Context context;

    public BOMediaPlayer(Context newContext){
        this.context = newContext;
        //initialize all the different sounds and load into objects
        //media =  MediaPlayer.create(newContext, R.raw.game_soundtrack);
//
        //media_won = MediaPlayer.create(newContext, R.raw.you_won);
//
        //media_lost =  MediaPlayer.create(newContext, R.raw.game_over);
    }

    public void playSoundtrack(){
        if(media == null) {
            media = MediaPlayer.create(context, R.raw.game_soundtrack);
        }

        media.start();
        media.setLooping(true);
    }

    //stop the soundtrack from playing and clear from memory, like when closing the app
    public void stopSoundtrack(){
        media.stop();
        media.release();
    }

    //temporarily pause soundtrack but keep in memory for a quick resume
    public void pauseSoundtrack(){
        media.pause();
    }

    public void restartSoundtrack(){
        if(media != null) {
            media.seekTo(0); // this will make it so the song plays from the beginning.
            media.start(); // restart the music
        }
    }

    //sound used when the player wins a level
    //TODO: make sure that the winning and losing sounds work correctly
    public void playYouWon(){
        if(media_won == null) {
            media_won = MediaPlayer.create(context, R.raw.you_won);
        }
        media_won.start();
    }

    public void pauseYouWon(){
        media_won.pause();
    }


    public void playGameOver(){
        if(media_lost == null) {
            media_lost = MediaPlayer.create(context, R.raw.game_over);
        }
        media_lost.start();
    }

    public void pauseGameOver(){
        media_lost.pause();
    }


}
