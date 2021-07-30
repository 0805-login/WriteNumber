package com.example.writenumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import util.ProgressDialog;

/*************************************数字0界面***************************************************/
/***********************************************************************************************/
public class ZeroActivity extends Activity {

    public ProgressDialog dialog;
    MediaPlayer mediaPlayer;//定义音乐播放器对象
    private ImageView iv_number;
    int i = 0;//第几张图片
    float x1;//按下时x的值
    float y1;//按下时y的值
    float x2;//离开时x的值
    float y2;//离开时y的值
    int ivx;//图片x坐标
    int ivy;//图片y坐标
    int type = 0;//书写标识，1开启0关闭
    int widthPixels;//屏幕宽度
    int heightPixels;//屏幕高度
    float scaleWidth;//宽度的缩放比例
    float scaleHeight;//高度的缩放比例
    Timer touchTimer = null;//点击在虚拟按钮上后用于连续动作的计时器
    Bitmap arrdown;//Bitmap图像处理
    boolean statusdialog = true;//dialog对话框状态
    private LinearLayout linearLayout = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);//设置数字3功能界面布局文件

        if (MainGame.isPlay == true) {
            playMusic();//播放背景音乐
        }

        initView();//创建并调用方法
    }

    public void Show(View v) {    // 创建演示按钮，单击事件方法
        if (dialog == null) {    // 如果自定义对话框为空
            // 实例化自定义对话框，设置显示文字和动画文件
            dialog = new ProgressDialog(this, "点击边缘取消", R.drawable.frame0);
        }
        dialog.show();        // 显示对话框
    }

    private void playMusic() {//播放背景音乐方法
        mediaPlayer = MediaPlayer.create(this, R.raw.zero);
        mediaPlayer.setLooping(true);//设置音乐循环播放
        mediaPlayer.start();//启动音乐播放
    }


    //获取布局文件中的相关控件、打开默认显示的书写图片等功能
    private void initView() {
        iv_number = findViewById(R.id.iv_num);//获取显示数字的ImageView组件
        linearLayout = findViewById(R.id.LinearLayout1);//获取写数字区域的布局
        LinearLayout write_layout = findViewById(R.id.LinearLayout_number);//获取书写界面布局
        write_layout.setBackgroundResource(R.drawable.back_num);//设置书写界面的布局背景
        widthPixels = this.getResources().getDisplayMetrics().widthPixels;//获取屏幕宽度
        heightPixels = this.getResources().getDisplayMetrics().heightPixels;//获取屏幕高度
        //以为图片等资源是按照1280*720来准备的，如果是其他分辨率，适应屏幕做准备
        scaleWidth = ((float) widthPixels / 720);
        scaleHeight = ((float) heightPixels / 1280);


        try {//通过输入流打开第一张图片
            InputStream is = getResources().getAssets().open("on0_1.png");
            //使用Bitmap解析第一张图片
            arrdown = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取布局的宽高信息
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_number.getLayoutParams();//获取布局的宽高信息
        //获取图片缩放后的宽度
        layoutParams.width = (int) (arrdown.getWidth() * scaleHeight);
        //获取图片缩放后的高度
        layoutParams.height = (int) (arrdown.getHeight() * scaleHeight);
        //根据图片缩放后的宽高，设置iv_number宽高
        iv_number.setLayoutParams(layoutParams);
        lodimagep(1);//调用lodimagep()方法，进入页面后加载第一个图片

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {//获取行动方式头部
                    case MotionEvent.ACTION_DOWN://手指按下事件
                        x1 = event.getX();
                        y1 = event.getY();
                        ivx = iv_number.getLeft();//获取手指按下图片时的x坐标
                        ivy = iv_number.getTop();//获取手指按下图片时的y坐标
                        if (x1 >= ivx && x1 <= ivx + (int) (arrdown.getWidth() * scaleWidth)
                                && y1 >= ivy & y1 <= ivy + (int) (arrdown.getHeight() * scaleWidth)) {
                            type = 1;//开启书写
                        } else {
                            type = 0;//否则关闭书写
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:        // 手势移动中判断
                        ivx = iv_number.getLeft();        // 获取图片的X坐标
                        ivy = iv_number.getTop();            // 获取图片的Y坐标
                        x2 = event.getX();                // 获取移动中手指在屏幕X坐标的位置
                        y2 = event.getY();                // 获取移动中手指在屏幕Y坐标的位置
                        // 下边 是根据比划 以及 手势 做图片的处理 滑动到不同位置 加载不同图片
                        if (type == 1) {                    // 如果书写开启
                            // 如果手指按下的X坐标大于等于图片的X坐标，或者小于等于缩放图片的X坐标时
                            if (x2 >= ivx && x2 <= ivx + (int) (arrdown.getWidth() * scaleWidth)) {
                                // 如果当前手指按下的Y坐标小于等于缩放图片的Y坐标，或者大于等于图片的Y坐标时
                                if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 && y2 >= ivy) {
                                    lodimagep(1);            // 调用lodimagep()方法，加载第一张显示图片
                                }
                                // 如果当前手指按下的Y坐标小于等于缩放图片的Y坐标
                                else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 2) {
                                    lodimagep(2);            // 调用lodimagep()方法，加载第二张显示图片
                                }
                                // 如果当前手指按下的Y坐标小于等于缩放图片的Y坐标
                                else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 3) {
                                    lodimagep(3);            // 调用lodimagep()方法，加载第三张显示图片
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 4) {
                                    lodimagep(4);            // 调用lodimagep()方法，加载第四张显示图片
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 5) {
                                    lodimagep(5);            // 调用lodimagep()方法，加载第五张显示图片
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 6) {
                                    lodimagep(6);            // 调用lodimagep()方法，加载第六张显示图片
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 7) {
                                    lodimagep(7);            // 调用lodimagep()方法，加载第七张显示图片
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 8) {
                                    lodimagep(8);            // 调用lodimagep()方法，加载第八张显示图片
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 9) {
                                    lodimagep(9);            // 调用lodimagep()方法，加载第九张显示图片
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 10) {
                                    lodimagep(10);
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 11) {
                                    lodimagep(11);
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 12) {
                                    lodimagep(12);
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 13) {
                                    lodimagep(13);
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 14) {
                                    lodimagep(14);
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 15) {
                                    lodimagep(15);
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 16) {
                                    lodimagep(16);
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 17) {
                                    lodimagep(17);
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 18) {
                                    lodimagep(18);
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 19) {
                                    lodimagep(19);
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 20) {
                                    lodimagep(20);
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 21) {
                                    lodimagep(21);
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 22) {
                                    lodimagep(22);
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 23) {
                                    lodimagep(23);
                                } else if (y2 <= ivy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 24) {
                                    lodimagep(24);   //加载最后一张图片时，将在lodimagep()方法中调用书写完成对话框
                                } else {
                                    type = 0;         // 手指离开 设置书写关闭
                                }

                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP://手势抬起判断
                        type = 0;//手势关闭
                        //当手指离开时
                        if (touchTimer != null) {//判断计时器是否为空
                            touchTimer.cancel();//中断计时器
                            touchTimer = null;//设置计时器为空
                        }
                        touchTimer = new Timer();//初始化计时器
                        touchTimer.schedule(new TimerTask() {//开启时间计时器
                            @Override
                            public void run() {
                                Thread thread = new Thread(new Runnable() {//创建子线程
                                    @Override
                                    public void run() {
                                        //创建用于发送信息
                                        Message message = new Message();
                                        message.what = 0;//message消息为2
                                        //发送消息给handler实现倒退显示图片
                                        mHandler.sendMessage(message);
                                    }
                                });
                                thread.start();//开启线程
                            }
                        }, 300, 200);

                }//获取行动方式头部
                return true;
            }
        });
    }

    //递减显示帧图片的handler消息头部
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0://当接收到手势抬起的子线程消息时
                    jlodimage();//调用资源图片倒退显示方法
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };//递减显示帧图片的handler消息尾部

    //当手势抬起时数字资源图片倒退显示jlodimage()方法头部
    private void jlodimage() {
        if (i == 25) {//如果当前图片位置等于25
        } else if (i < 25) {//当前图片位置小于25
            if (i > 1) {//当前图片位置大于1
                i--;
            } else if (i == 1) {
                i = 1;
                if (touchTimer != null) {//判断计时器是否为空
                    touchTimer.cancel();//中断计时器
                    touchTimer = null;//设置计时器为空
                }
            }
            String name = "on0_" + i;//图片的名称
            //获取图片资源
            int imgid = getResources().getIdentifier(name, "drawable", "com.example.writenumber");
            //给imageview设置图片
            iv_number.setBackgroundResource(imgid);
        }
    }//当手势抬起时数字资源图片倒退显示jlodimage()方法尾部

    //显示书写数字的第一张图片，也可以用于实现手势移动时加载不同的图片
    private synchronized void lodimagep(int j) {
        i = j;//当前图片的位置
        if (i < 25) {
            String name = "on0_" + i;//当前图片名称
            int imgid = getResources().getIdentifier(name, "drawable", "com.example.writenumber");
            iv_number.setBackgroundResource(imgid);
            i++;
        }
        if (j == 24) {
            if (statusdialog) {
                dialog();
            }
        }
    }

    //显示提示对话框
    protected void dialog() {//完成后提示对话框头部
        statusdialog = false;//修改对话框状态
        AlertDialog.Builder builder = new AlertDialog.Builder(com.example.writenumber.ZeroActivity.this);
        builder.setMessage("你真棒！送你一朵小红花！！");//设置对话框文本信息
        builder.setTitle("奖励");//设置对话框标题
        builder.setPositiveButton("完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//dialog消失
                statusdialog = true;//修改对话框状态
                finish();//关闭当前页面
            }
        });//对话框完成按钮单击事件尾部
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//对话框消失
                statusdialog = true;//修改对话框状态
                i = 1;
                lodimagep(i);//调用加载图片方法中的第一张图片
            }
        });
        builder.create().show();//创建并显示对话框
    }

    //数字1书写界面停止时，背景音乐停止
    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    //数字2书写界面清空所占内存资源时，背景音乐停止并清空音乐资源所占的内存
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
