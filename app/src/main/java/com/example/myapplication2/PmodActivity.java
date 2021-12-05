package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication2.GCActivity;
import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;

public class PmodActivity extends AppCompatActivity {

    private Button btn_dg;
    private Button btn_gc;
    private Button btn_p2h;
    Robot robot= Robot.getInstance();

    public String value;
    public String value2 = "2";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmod);

        btn_dg =findViewById(R.id.btn_dg);
        btn_gc =findViewById(R.id.btn_gc);
        btn_p2h =findViewById(R.id.btn_p2h);

        initDatabase();

        mReference = mDatabase.getReference("cry_flag"); // 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                if(value.equals(value2)){
                    Intent intent = new Intent(PmodActivity.this, GCActivity.class);
                    startActivity(intent); //GC로 이동
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //주간모드
        btn_dg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TtsRequest ttsRequest = TtsRequest.create("사용자를 따라갑니다", true);
                robot.speak(ttsRequest); //말하기 기능
                robot.beWithMe();   //따라가기 기능
                //울음소리 신호창
            }
        });


        //야간모드
        btn_gc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TtsRequest ttsRequest = TtsRequest.create("보호자방으로 이동합니다", true);
                robot.speak(ttsRequest); //말하기 기능
                robot.goTo("보호자방"); //부모방이동
                //울음소리 신호창
            }
        });

        btn_p2h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PmodActivity.this, MainActivity.class);
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