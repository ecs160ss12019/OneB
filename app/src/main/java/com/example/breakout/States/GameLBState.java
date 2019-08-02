package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import com.example.breakout.BOGameController;
import com.example.breakout.BOLeaderboardItem;
import com.example.breakout.BORecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLBState extends State {

    private int SIZE;
    private List<BORecord> top5 = new ArrayList<>();
    private List<BOLeaderboardItem> top5_LB = new ArrayList<>();

    public GameLBState(BOGameController gc) {
        super(gc);
        gc.myRef = FirebaseDatabase.getInstance().getReference("users");

        // get top 5 profiles from DB and populate ArrayList
        populateTopFive();
    }

    private void populateTopFive() {
        gc.myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SIZE = (int)Math.min(5, dataSnapshot.getChildrenCount());

                top5.clear();
                top5_LB.clear();

                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    top5.add(new BORecord(0, user.getKey(), user.child("score").getValue(int.class)));
                }

                // Sort the list and set top5 ranks
                sortList(top5);
                setRanks(top5);
                top5 = top5.subList(0, SIZE);

                top5_LB.add(new BOLeaderboardItem((int)gc.getMeta().getDim().x, (int)gc.getMeta().getDim().y, gc, top5.get(0), gc.leaderboard.collider.top + gc.getMeta().getDim().y/(float)10));

                for (int i = 1; i < SIZE; i++) {
                    System.out.println(top5.get(i).name);
                    top5_LB.add(new BOLeaderboardItem((int)gc.getMeta().getDim().x, (int)gc.getMeta().getDim().y, gc, top5.get(i), top5_LB.get(i-1).collider.bottom));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DB Error: ", databaseError.getMessage());
            }
        });
    }

    private void sortList(List<BORecord> records) {
        Collections.sort(records);
        Collections.reverse(records);
    }

    private void setRanks(List<BORecord> records) {
        for (int i = 0; i < SIZE; i++) {
            records.get(i).setRank(i+1);
        }
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        gc.myLayout.draw(mCanvas, mPaint); // draw the background over the blocks.
        gc.leaderboard.draw(mCanvas, mPaint);

        for (BOLeaderboardItem l : top5_LB) {
            l.draw(mCanvas, mPaint);
        }
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
