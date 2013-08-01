package com.energy.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.energy.application.MyApplication;
import com.energy.bean.LoginResult;
import com.energy.bean.ResponseStatus;
import com.energy.util.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Act_Login extends Activity {

	private EditText login_et_name;
	private EditText login_et_pass;
	private Button login_btn_login;
	private ProgressDialog proDialog;
	String name;
	String pass;

	public void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login);
		initPage();
	}

	private void initPage() {
		findView();
		bindEvent();
	}

	private void findView() {
		login_et_name = (EditText) findViewById(R.id.login_et_name);
		login_et_pass = (EditText) findViewById(R.id.login_et_pass);
		login_btn_login = (Button) findViewById(R.id.login_btn_login);
	}

	private void bindEvent() {
		login_btn_login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				name = login_et_name.getText().toString();
				pass = login_et_pass.getText().toString();
				if (!name.isEmpty() && !pass.isEmpty()) {
					
					proDialog = ProgressDialog.show(Act_Login.this, "连接中..",
							"连接中..请稍后....", true, true);
					
					Thread loginThread = new Thread(new LoginHandler());
					loginThread.start();
				} else {
					Toast.makeText(Act_Login.this,
							"请输入用户名、密码！", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}
	
	class LoginHandler implements Runnable {
		public void run() {
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(getResources().getString(R.string.url_login)+"?name="+name+"&pw="+pass, new AsyncHttpResponseHandler() {
			    public void onSuccess(String response) {
			    	ResponseStatus status = JSON.toJavaObject(JSON.parseObject(response), ResponseStatus.class);
			    	if(status.getStatus()==400){
			    		Toast.makeText(Act_Login.this, "用户名或密码错误！",
								Toast.LENGTH_SHORT).show();
			    	}else{
			    		Constant.loginResult =  JSON.toJavaObject(JSON.parseObject(response), LoginResult.class);
			    		Intent intent = new Intent();
			    		intent.setClass(Act_Login.this, Act_Main.class);
			    		startActivity(intent);
			    	}
			    }
			    public void onFailure(Throwable e, String response) {
			    	Toast.makeText(Act_Login.this, "登陆失败:\n1.请检查您网络连接.\n2.请联系我们.!",
							Toast.LENGTH_SHORT).show();
			    }
			    public void onFinish() {
			    	proDialog.dismiss();
			    }
			});
		}
	}

}
