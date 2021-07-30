package com.example.writenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*************************************主界面***************************************************/
/***********************************************************************************************/
public class MainGame extends AppCompatActivity {


    static boolean isPlay = true;//设置音乐播放状态变量
    MediaPlayer mediaPlayer;//设置音乐播放器
    Button btn_music;//定义控制音乐播放器按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        //单击事件监听器
        /*Button btn_play = findViewById(R.id.btn_play);
        Button btn_music = findViewById(R.id.btn_music);
        Button btn_about = findViewById(R.id.btn_about);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        btn_music = findViewById(R.id.btn_music);
        playMusic();//调用播放音乐的方法

        Button btn_play = findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainGame.this,SelectActivity.class));
            }
        });
    }

    private void playMusic() {
        mediaPlayer = MediaPlayer.create(this,R.raw.main_music);
        mediaPlayer.setLooping(true);//设置背景音乐循环播放
        mediaPlayer.start();//启动播放音乐

    }

    public void onMusic(View v){
        if(isPlay == true){//如果音乐正在播放
            if(mediaPlayer != null){//音乐播放器不为空
                mediaPlayer.pause();//音乐处于停止状态
                btn_music.setBackgroundResource(R.drawable.music_close);//更换背景图片
                isPlay = false;//设置音乐处于停止状态
            }else {
                playMusic();
                btn_music.setBackgroundResource(R.drawable.music_open);
                isPlay = true;
            }
        }
        else if(isPlay == false){
            if(mediaPlayer != null){//音乐播放器不为空
                playMusic();
                btn_music.setBackgroundResource(R.drawable.music_open);//更换背景图片
                isPlay = true;//设置音乐处于停止状态
            }else {
                mediaPlayer.pause();
                btn_music.setBackgroundResource(R.drawable.music_close);
                isPlay = false;
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
    protected void onDestroy() {//退出游戏主界面时，音乐自动停止
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();//清空音乐资源
            mediaPlayer = null;
        }
    }

    @Override
    protected void onRestart() {//返回游戏主界面时，音乐自动播放
        super.onRestart();
        if(isPlay == true){
            playMusic();
        }
    }
}