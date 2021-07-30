package com.example.writenumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
/*************************************登录界面***************************************************/
/***********************************************************************************************/
//<!--设置无标题栏，全屏显示 android:theme="@style/Theme.AppCompat.NoActionBar"-->
public class LoginActivity extends AppCompatActivity {

    SharedPreferences mySharedPreferences;//声明信息保存类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText et_username = findViewById(R.id.userName);
        EditText et_password = findViewById(R.id.password);

        //登录
//        final String user = "hjl11111";//用户名
//        final String pass = "123456";//密码


//        Map<String, String> userInformation = SaveInformation.getSaveInformation(this);
//        if (userInformation != null) {
//            et_username.setText(userInformation.get("username"));
//            et_password.setText(userInformation.get("password"));
//        }

        final Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();


//                if (username.equals(user) & password.equals(pass)) {
//                    Intent intent = new Intent(LoginActivity.this,StartActivity.class);
//                    startActivity(intent);
//                }
//                else {
//                    new AlertDialog.Builder(LoginActivity.this)
//                            .setTitle("错误提示")
//                            .setMessage("用户名或密码错误")
//                            .setNegativeButton("确定",null)
//                            .show();
//                }
                //获取保存文件中的用户名和密码
                String savedUsername = mySharedPreferences.getString("username","");
                String savedPassword = mySharedPreferences.getString("password","");
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"用户名或密码为空",Toast.LENGTH_SHORT).show();
                }else if((savedUsername.trim().equals(username) && savedPassword.trim().equals(password)) == true){
                        Toast.makeText(LoginActivity.this, "用户名和密码都正确！", Toast.LENGTH_LONG).show();

                        //成功登陆，进入LoginokActivity界面
                        startActivity(new Intent(LoginActivity.this,StartActivity.class));
                        finish();
                }else if((savedUsername.trim().equals(username) && savedPassword.trim().equals(password)) == false){
                        //错误的话
                        Toast.makeText(LoginActivity.this, "用户名或者密码错误，请确认信息或者去注册", Toast.LENGTH_LONG).show();
                        return;
                }else{
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,StartActivity.class));
                }
            }
        });

        //注册
        final Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = et_username.getText().toString().trim();
                String pass = et_password.getText().toString().trim();
                //注册开始，判断注册条件
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(LoginActivity.this, "注册的用户名或密码不可为空", Toast.LENGTH_SHORT).show();
                } else {
                    //实例化SharedPreferences对象
                    mySharedPreferences= getSharedPreferences("myuserinfo",
                            LoginActivity.MODE_PRIVATE);
                    //实例化SharedPreferences.Editor对象（第二步）
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    //用putString的方法保存数据
                    editor.putString("username", name);
                    editor.putString("password", pass);
                    //提交当前数据
                    editor.commit();
                    Toast.makeText(LoginActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();

                    Dialog dialog=new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("注册")
                        .setMessage("你确定注册信息吗？")
                        .setPositiveButton("确定", new  DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        }).create();//创建一个dialog
                    dialog.show();//显示对话框，否者不成功
                }


                }
            });
    }
}

