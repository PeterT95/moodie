package com.example.moodie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.felipecsl.gifimageview.library.GifImageView;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class LaunchActivity extends AppCompatActivity {

    private GifImageView gifImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

//      gifImageView = findViewById(R.id.gifimage);

        InputStream inputSteam = null;
        byte[] bytes;

//        try {
//
//            inputSteam = getAssets().open("theone.gif");
//            bytes = IOUtils.toByteArray(inputSteam);
//            gifImageView.setBytes(bytes);
//            gifImageView.startAnimation();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (inputSteam != null) {
//                try {
//                    inputSteam.close();
//                    Log.i("LaunchGif", "Input Stream closed successfully");
//                } catch (IOException e) {
//                    Log.i("LaunchGif", "Failed to close Input Stream: " + e);
//                }
//
//            }
//        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LaunchActivity.this.startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                LaunchActivity.this.finish();
            }
        }, 2000);

    }

}

