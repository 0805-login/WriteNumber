package util;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.writenumber.R;

public class ProgressDialog extends android.app.ProgressDialog {

    public ProgressDialog dialog;    	//定义自定义对话框对象
    private AnimationDrawable animation;  	//设置对话框动画资源
    private Context context;              	//设置对话框上下文
    private ImageView imageview;         	//设置对话框背景图片
    private String dialogtext;            	//设置对话框文字
    private TextView dialogtextTv;           	//显示对话框文字
    private int res_id;                    	//设置动画资源的id

    //自定义对话框构造方法头部
    public ProgressDialog(Context context, String content, int id) {
        super(context);
        this.context=context;   		//为上下文赋值
        this.dialogtext=content;   	//为对话框文字赋值
        this.res_id=id;   			//为动画资源id赋值
        //设置单击周边是否让dialog消失 设置为true 点击周边消失
        setCanceledOnTouchOutside(true);
    }   //自定义对话框构造方法尾部

    @Override
    protected void onCreate(Bundle savedInstanceState) { //创建的onCreate方法头
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);   //设置自定义对话框布局
        //获取布局文件中TextView组件
        dialogtextTv = (TextView) findViewById(R.id.loadingTv);
        //获取布局文件中ImageView组件
        imageview = (ImageView) findViewById(R.id.loadingIv);
        if (res_id == 0) {    	//当动画资源id为0时
            imageview.setBackgroundDrawable(null);   //设置背景为空
        } else {
            imageview.setBackgroundResource(res_id);   //否则设置指定动画资源id
        }
        // 通过ImageView对象拿到背景显示的动画资源文件
        animation = (AnimationDrawable) imageview.getBackground();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        imageview.post(new Runnable() {
            @Override
            public void run() {
                // 动画开始
                animation.start();
            }
        });
        // 设置显示文字
        dialogtextTv.setText(dialogtext);
    }   //创建的onCreate方法尾部


}   //自定义对话框类尾部

