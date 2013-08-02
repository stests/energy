package com.energy.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.energy.application.MyApplication;
import com.energy.bean.ConfigInfo;
import com.energy.bean.ResponseStatus;
import com.energy.service.ConfigService;
import com.energy.util.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Act_Config  extends BaseActivity{
	
	Button btn_getconfig;
	
	private ProgressDialog proDialog;
	
	public void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_config);
		initPage();
	}
	
	private void initPage(){
		findView();
		bindEvent();
	}
	
	private void findView(){
		btn_getconfig = (Button)findViewById(R.id.config_btn_getconfig);
	}
	
	private void bindEvent(){
		btn_getconfig.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				proDialog = ProgressDialog.show(Act_Config.this, "连接中..",
						"正在获取数据...", true, true);
				
				Thread loginThread = new Thread(new ConfigHandler());
				loginThread.start();
				
			}
		});
	}
	
	class ConfigHandler implements Runnable {
		public void run() {
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(getResources().getString(R.string.url_config)+"?companyId="+Constant.loginResult.getCompanyId(), new AsyncHttpResponseHandler() {
			    public void onSuccess(String response) {
			    	ResponseStatus status = JSON.toJavaObject(JSON.parseObject(response), ResponseStatus.class);
			    	if(status.getStatus()==400){
			    		Toast.makeText(Act_Config.this, "数据未获取成功！",
								Toast.LENGTH_SHORT).show();
			    	}else{
			    		ConfigInfo configinfo = JSON.toJavaObject(JSON.parseObject(response), ConfigInfo.class);
			    		//数据导入操作
			    		ConfigService configService = new ConfigService(Act_Config.this);
			    		configService.insertConfig(configinfo);
			    	}
			    	proDialog.dismiss();
			    }
			    public void onFailure(Throwable e, String response) {
			    	Toast.makeText(Act_Config.this, "登陆失败:\n1.请检查您网络连接.\n2.请联系我们.!",
							Toast.LENGTH_SHORT).show();
			    }
			    public void onFinish() {
			    	proDialog.dismiss();
			    }
			});
		}
	}
	
}
