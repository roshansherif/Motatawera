package com.example.egyedu;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MyCoursesActivity extends AppCompatActivity {

    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController;
    private Button buttonURL;
    ImageView baaaackC;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_my_courses );

        this.videoView = (VideoView) findViewById ( R.id.videoView );
        this.baaaackC = (ImageView) findViewById ( R.id.baaaackC );
        this.buttonURL = (Button) findViewById ( R.id.button_url );
        baaaackC=findViewById ( R.id.baaaackC );
        baaaackC.setOnClickListener ( v -> {
            finish ();
        } );
        // Set the media controller buttons
        if (this.mediaController == null) {
            this.mediaController = new MediaController ( MyCoursesActivity.this );

            this.mediaController.setAnchorView ( videoView );

            // Set MediaController for VideoView
            this.videoView.setMediaController ( mediaController );
        }


        // When the video file ready for playback.
        this.videoView.setOnPreparedListener ( new OnPreparedListener () {

            public void onPrepared ( MediaPlayer mediaPlayer ) {

                videoView.seekTo ( position );
                if (position == 0) {
                    videoView.start ();
                }

                // When video Screen change size.
                mediaPlayer.setOnVideoSizeChangedListener ( new MediaPlayer.OnVideoSizeChangedListener () {
                    @Override
                    public void onVideoSizeChanged ( MediaPlayer mp , int width , int height ) {

                        // Re-Set the videoView that acts as the anchor for the MediaController
                        mediaController.setAnchorView ( videoView );
                    }
                } );
            }
        } );


        this.buttonURL.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                String videoURL = VideoViewUtils.URL_VIDEO_SAMPLE;
                VideoViewUtils.playURLVideo ( MyCoursesActivity.this , videoView , videoURL );
            }
        } );
    }

    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState ( Bundle savedInstanceState ) {
        super.onSaveInstanceState ( savedInstanceState );

        // Store current position.
        savedInstanceState.putInt ( "CurrentPosition" , videoView.getCurrentPosition () );
        videoView.pause ();
    }


    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState ( Bundle savedInstanceState ) {
        super.onRestoreInstanceState ( savedInstanceState );

        // Get saved position.
        position = savedInstanceState.getInt ( "CurrentPosition" );
        videoView.seekTo ( position );
    }

}