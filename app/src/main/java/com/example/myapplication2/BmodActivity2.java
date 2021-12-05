package com.example.myapplication2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;

public class BmodActivity2 extends AppCompatActivity {

    Robot robot= Robot.getInstance();
    private Button btn_b2h;
    private Button music_p;
    private Button music_s;
    private Button video_p;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmod2);

        btn_b2h =findViewById(R.id.btn_b2h);
        music_p =findViewById(R.id.music_p);
        music_s =findViewById(R.id.music_s);
        video_p =findViewById(R.id.video_p);

        initDatabase();

        video_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BmodActivity2.this, VideoActivity.class);
                startActivity(intent);
            }
        });

        btn_b2h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BmodActivity2.this, MainActivity.class);
                startActivity(intent); //홈으로 이동
                finish();

            }
        });

        music_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(BmodActivity2.this,R.raw.music);
                mediaPlayer.start();
            }
        });

        music_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            }
        });

        databaseReference.child("music2").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                mediaPlayer = MediaPlayer.create(BmodActivity2.this,R.raw.music);
                mediaPlayer.start();
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        databaseReference.child("video2").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                Intent intent = new Intent(BmodActivity2.this, VideoActivity.class);
                startActivity(intent);
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        databaseReference.child("stop_music2").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        databaseReference.child("stop_video2").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                Intent intent = new Intent(BmodActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void initDatabase() {

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("log");
        mReference.child("log").setValue("check");

        mChild = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mReference.addChildEventListener(mChild);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReference.removeEventListener(mChild);
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

