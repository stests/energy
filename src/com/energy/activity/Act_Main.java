package com.energy.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.energy.application.MyApplication;

public class Act_Main extends BaseActivity implements OnClickListener {

	Context context = null;
	RelativeLayout rel_room, rel_search, rel_view,rel_config;

	public void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		initPage();
	}

	public void initPage() {
		findView();
	}

	public void findView() {
		rel_room = (RelativeLayout) findViewById(R.id.main_rl_room);
		rel_search = (RelativeLayout) findViewById(R.id.main_rl_search);
		rel_view = (RelativeLayout) findViewById(R.id.main_rl_view);
		rel_config = (RelativeLayout)findViewById(R.id.main_rl_config);
		context = this;
		rel_room.setOnClickListener(this);
		rel_search.setOnClickListener(this);
		rel_view.setOnClickListener(this);
		rel_config.setOnClickListener(this);
	}

	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.main_rl_room:
			intent.setClass(context, Act_Room.class);
			break;
		case R.id.main_rl_search:
			intent.setClass(context, Act_Search.class);
			break;
		case R.id.main_rl_view:
			intent.setClass(context, Act_View.class);
			break;
		case R.id.main_rl_config:
			intent.setClass(context, Act_Config.class);
			break;
		}
		startActivity(intent);
	}
	
	public void onBackPressed() {
	    new AlertDialog.Builder(context)   
	    .setTitle("提示")  
	    .setMessage("确定退出吗？")  
	    .setPositiveButton("是", new android.content.DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				MyApplication.getInstance().exit();
			}
	    })  
	    .setNegativeButton("否",null)  
	    .show();  
	}

}
