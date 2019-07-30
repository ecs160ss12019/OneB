package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;

import com.example.breakout.BOGameController;
import com.example.breakout.BOLeaderboardItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GameLBState extends State {
    private static final int SIZE = 5;
    private BOLeaderboardItem rank1;
//    private List<BOLeaderboardItem> places = new ArrayList<>(SIZE);

    public GameLBState(BOGameController gc) {
        super(gc);
        gc.myRef = FirebaseDatabase.getInstance().getReference();
        // get top 5 profiles from DB and populate ArrayList
        readInData();
        rank1 = new BOLeaderboardItem((int)gc.getMeta().getDim().x, (int)gc.getMeta().getDim().y, gc.user, 1, gc, gc.menu.collider.top);
//        places.add(new BOLeaderboardItem((int)gc.getMeta().getDim().x, (int)gc.getMeta().getDim().y, places.get(0).text, gc, 5));
    }

    private void readInData() {
        // My top posts by number of stars
//        gc.myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Query myQuery = gc.myRef.child("users").child("").orderByChild("score");
//                System.out.println("HELLO" + myQuery);
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    Log.d("FUCK","TESTING LEADS");
////                    Log.d("KEY", postSnapshot.getChildren());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w("fuck", "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        });
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        gc.myLayout.draw(mCanvas, mPaint); // draw the background over the blocks.
        gc.leaderboard.draw(mCanvas, mPaint);


        rank1.draw(mCanvas, mPaint);
    }

    public void run(){

    }

    public void update() {

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: //placed finger on screen
                if (motionEvent.getX() < gc.menu.collider.left || motionEvent.getX() > gc.menu.collider.right || motionEvent.getY() > gc.menu.collider.bottom
                        || motionEvent.getY() < gc.menu.collider.top) {
                    gc.context = new GamePauseState(gc);

                }


        }
        return true;
    }
}
