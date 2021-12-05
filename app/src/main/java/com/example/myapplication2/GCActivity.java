package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;

public class GCActivity extends AppCompatActivity {

    private Button music_p;
    private Button music_s;
    private Button video_p;
    private Button video_s;
    private Button btn_g2p;
    Robot robot= Robot.getInstance();

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gc);

        music_p =findViewById(R.id.music_p);
        music_s =findViewById(R.id.music_s);
        video_p =findViewById(R.id.video_p);
        video_s =findViewById(R.id.video_s);
        btn_g2p =findViewById(R.id.btn_g2p);

        initDatabase();

        TtsRequest ttsRequest = TtsRequest.create("아이가 울고있습니다", true);
        robot.speak(ttsRequest);

        music_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //음악 재생 버튼 눌렀을때
                databaseReference.child("music").push().setValue(true);
            }
        });

        music_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //정지 버튼 눌렀을때
                databaseReference.child("stop_music").push().setValue(true);
            }
        });

        video_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //영상 재생 버튼 눌렀을때
                databaseReference.child("video").push().setValue(true);
            }
        });

        video_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //영상 재생 버튼 눌렀을때
                databaseReference.child("stop_video").push().setValue(true);
            }
        });

        btn_g2p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GCActivity.this, PmodActivity.class);
                startActivity(intent);
                finish();
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
    }
}