package com.example.egyedu;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoViewUtils {


    public static final String URL_VIDEO_SAMPLE  = "https://firebasestorage.googleapis.com/v0/b/egy-edu-26c94.appspot.com/o/1-Intro.mp4?alt=media&token=f052a753-1fb9-4714-8a84-00f9ead8c31c";

    public static final String LOG_TAG= "AndroidVideoView";



    public static void playRawVideo(Context context, VideoView videoView, String resName)  {
        try {
            // ID of video file.
            int id = VideoViewUtils.getRawResIdByName ( context, resName );

            Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + id);
            Log.i(LOG_TAG, "Video URI: "+ uri);

            videoView.setVideoURI(uri);
            videoView.requestFocus();

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error Play Raw Video: "+e.getMessage());
            Toast.makeText(context,"Error Play Raw Video: "+ e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static void playLocalVideo(Context context, VideoView videoView, String localPath)  {
        try {

        } catch(Exception e) {
            Log.e(LOG_TAG, "Error Play Local Video: "+ e.getMessage());
            Toast.makeText(context,"Error Play Local Video: "+ e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static void playURLVideo(Context context, VideoView videoView, String videoURL)  {
        try {
            Log.i(LOG_TAG, "Video URL: "+ videoURL);

            Uri uri= Uri.parse( videoURL );

            videoView.setVideoURI(uri);
            videoView.requestFocus();

        } catch(Exception e) {
            Log.e(LOG_TAG, "Error Play URL Video: "+ e.getMessage());
            Toast.makeText(context,"Error Play URL Video: "+ e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //     Find ID corresponding to the name of the resource (in the directory RAW).
    public static int getRawResIdByName(Context context, String resName) {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName, "raw", pkgName);

        Log.i(LOG_TAG, "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }
}