package com.example.breakout;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;


//public class Audio{
//
//    MediaPlayer mp;
//    Context context;
//
//    public Audio(Context ct){
//        this.context = ct;
//    }
//    public void playClick(){
//        mp = MediaPlayer.create(context, R.raw.click);
//        mp.prepare();
//        mp.start();
//    }

public class BOMediaPlayer {
    /*
    Handles Media playing!
     */

    //TODO: Michel, pls handle this. Put media stuff here and call.

    /*
        will do!
            - Michel
     */

    public MediaPlayer media;
    public MediaPlayer media_won;
    public MediaPlayer media_lost;
    Context context;

    public BOMediaPlayer(Context newContext){
        this.context = newContext;
        //initialize all the different sounds and load into objects
//        media =  MediaPlayer.create(newContext, R.raw.game_soundtrack);
//
//        media_won = MediaPlayer.create(newContext, R.raw.you_won);
//
//        media_lost =  MediaPlayer.create(newContext, R.raw.game_over);
    }

    public void playSoundtrack(){
        if(media == null) {
            media = MediaPlayer.create(context, R.raw.game_soundtrack);
        }
        //try catch in case the asset fails to load or something
//        try {
//            media.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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

    public void playYouWon(){
        media_won = MediaPlayer.create(context, R.raw.you_won);
        //try catch in case the asset fails to load or something
//        try {
//            media.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        media_won.start();
    }

    public void playGameOver(){
        media_lost = MediaPlayer.create(context, R.raw.game_over);
        //try catch in case the asset fails to load or something
//        try {
//            media.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        media_lost.start();
    }


}
