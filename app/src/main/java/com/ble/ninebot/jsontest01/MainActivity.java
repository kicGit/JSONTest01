package com.ble.ninebot.jsontest01;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static MediaPlayer mMediaPlayer = null;

    TextView parseJsonText;
    TextView IDString;
    TextView nameTest;
    TextView jsonText;
    Button playButton;

    private static final String path = "/sdcard/emotion.json";

    private static final String JSONString = "{\"address\":\"上海\",\"id\":3,\"name\":\"daming\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMediaPlayer = new MediaPlayer();

        parseJsonText = (TextView) findViewById(R.id.parseJSON);
        IDString = (TextView) findViewById(R.id.IDString);
        nameTest = (TextView) findViewById(R.id.nameTest);
        jsonText = (TextView) findViewById(R.id.textView);

        playButton = (Button)findViewById(R.id.play_music);


    }
    public void onParse(View v){
        Toast.makeText(this, "Parse", Toast.LENGTH_LONG).show();
        try {
            JSONTokener jsonTokener = new JSONTokener(JSONString);
            JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
            parseJsonText.setText(jsonObject.getString("address"));
            IDString.setText(jsonObject.getString("id"));
            nameTest.setText(jsonObject.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onPlayMusic(View v){

        if(mMediaPlayer.isPlaying()){
            Log.d(TAG, "music is playing!");
            mMediaPlayer.reset();
            Log.d(TAG, "music !");
            playButton.setText("Start Playing Music");
        } else {
            playButton.setText("Stop Playing Music");
            mMediaPlayer.reset();
            try {
                Log.d(TAG, "entry try-catch!");
                mMediaPlayer.setDataSource("/sdcard/a.mp3");
                Log.d(TAG, "get a music file!");
                mMediaPlayer.prepare();
                mMediaPlayer.start();
                Log.d(TAG, "music is playing!");
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(MainActivity.this, "music is finished!", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileContent(String path){
        String JSONString2 = "";
        try {
            Log.d(TAG, ">>>>>>>>>>entry try-catch");
            FileInputStream inputStream = new FileInputStream(new File(path));
            Log.d(TAG, ">>>>>>>>>>get a file");
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while(inputStream.read(bytes) != -1){
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            Log.d(TAG, ">>>>close the stream");
            inputStream.close();
            arrayOutputStream.close();
            JSONString2 = new String(arrayOutputStream.toByteArray(),"GBK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONString2;
    }

    public void onGetText(View v){
        try {
            JSONTokener jsonTokener = new JSONTokener(getFileContent(path));
            JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
            parseJsonText.setText("faceFlv: " + jsonObject.getString("faceFlv"));
            IDString.setText("voiceFile: " + jsonObject.getString("voiceFile"));
            nameTest.setText("duration: " + jsonObject.getString("duration"));
            jsonText.setText("repeat: " + jsonObject.getString("repeat"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
