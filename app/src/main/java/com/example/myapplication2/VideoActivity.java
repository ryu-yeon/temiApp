package com.example.myapplication2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    private Button btn_v2b;
    private VideoView videoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);


        btn_v2b = findViewById(R.id.btn_v2b);
        videoView = findViewById(R.id.videoView);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);

        videoView.setVideoURI(uri);
        videoView.start();

        btn_v2b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, BmodActivity2.class);
                startActivity(intent);
                finish();
            }
        });


    }
}



