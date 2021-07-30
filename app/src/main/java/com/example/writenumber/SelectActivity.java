package com.example.writenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
/*************************************选择数字界面***************************************************/
/***********************************************************************************************/
public class SelectActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;//定义音乐播放器对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        playMusic();

        ImageView num1 = findViewById(R.id.one);
        ImageView num2 = findViewById(R.id.two);
        ImageView num3 = findViewById(R.id.three);
        ImageView num4 = findViewById(R.id.four);
        ImageView num5 = findViewById(R.id.five);
        ImageView num6 = findViewById(R.id.six);
        ImageView num7 = findViewById(R.id.seven);
        ImageView num8 = findViewById(R.id.eight);
        ImageView num9 = findViewById(R.id.nine);
        ImageView num0 = findViewById(R.id.zero);
        ImageView click = findViewById(R.id.click);

        num1.setOnClickListener(new MyClick());
        num2.setOnClickListener(new MyClick());
        num3.setOnClickListener(new MyClick());
        num4.setOnClickListener(new MyClick());
        num5.setOnClickListener(new MyClick());
        num6.setOnClickListener(new MyClick());
        num7.setOnClickListener(new MyClick());
        num8.setOnClickListener(new MyClick());
        num9.setOnClickListener(new MyClick());
        num0.setOnClickListener(new MyClick());
        click.setOnClickListener(new MyClick());
    }

    private void playMusic() {
        mediaPlayer = MediaPlayer.create(this,R.raw.number_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private class MyClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.one:
                    startActivity(new Intent(SelectActivity.this,OneActivity.class));
                    break;
                case R.id.two:
                    startActivity(new Intent(SelectActivity.this,TwoActivity.class));
                    break;
                case R.id.three:
                    startActivity(new Intent(SelectActivity.this,ThreeActivity.class));
                    break;
                case R.id.four:
                    startActivity(new Intent(SelectActivity.this,FourActivity.class));
                    break;
                case R.id.five:
                    startActivity(new Intent(SelectActivity.this,FiveActivity.class));
                    break;
                case R.id.six:
                    startActivity(new Intent(SelectActivity.this,SixActivity.class));
                    break;
                case R.id.seven:
                    startActivity(new Intent(SelectActivity.this,SevenActivity.class));
                    break;
                case R.id.eight:
                    startActivity(new Intent(SelectActivity.this,EightActivity.class));
                    break;
                case R.id.nine:
                    startActivity(new Intent(SelectActivity.this,NineActivity.class));
                    break;
                case R.id.zero:
                    startActivity(new Intent(SelectActivity.this,ZeroActivity.class));
                    break;
                case R.id.click:
                    //播放视频
                    startActivity(new Intent(SelectActivity.this,VideoActivity.class));
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        playMusic();
    }


}