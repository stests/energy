package com.energy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

import com.energy.application.MyApplication;
import com.energy.util.Constant;

public class Act_View extends BaseActivity {
	
	LinearLayout ll_content;
	Spinner sp_zhibiao;
	Spinner sp_time;
	Context context;
	private String[] zhibiao = new String[] {"主能耗"};
	private String[] time = new String[] {"小时","周","天"};
	
	public void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_view);
		initPage();
	}
	
	public void initPage(){
		findView();
		viewWithGrade();
	}
	
	public void findView(){
		context = this;
		
		ll_content = (LinearLayout)findViewById(R.id.view_ll_content);
		sp_zhibiao = (Spinner)findViewById(R.id.view_sp_zhibiao);
		sp_time = (Spinner)findViewById(R.id.view_sp_time);
		
		sp_zhibiao = (Spinner) findViewById(R.id.view_sp_zhibiao);
		ArrayAdapter<String> grades = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, zhibiao);
		sp_zhibiao.setAdapter(grades);
		
		sp_time = (Spinner) findViewById(R.id.view_sp_time);
		ArrayAdapter<String> timeadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, time);
		sp_time.setAdapter(timeadapter);
		
		
	}
	
	public void viewWithGrade(){
		
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
		lp.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
		
		for(int i=0;i<Constant.search.size();i++){
			
			RelativeLayout rl = new RelativeLayout(context);
			rl.setLayoutParams(lp);
			rl.setMinimumHeight(34);
			if(i%2!=0){
				rl.setBackgroundResource(R.color.gray);
			}
			
			TextView tv = new TextView(context);
			tv.setLayoutParams(lp);
			tv.setGravity(Gravity.LEFT);
			tv.setText(Constant.search.get(i).toString());
			rl.addView(tv);
			
			ll_content.addView(rl);

			rl.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					RelativeLayout crl = (RelativeLayout)v;
					TextView t = (TextView)crl.getChildAt(0);
					
					showResult(t.getText().toString());
				}
			});
		}
	}
	
	public void showResult(String val){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(getResources().getString(R.string.url_getdata));
		stringBuffer.append("?uid="+Constant.loginResult.getUid());
		if(sp_zhibiao.getSelectedItem().toString().equals("主能耗")){
			Constant.zhibiao = 1;
			stringBuffer.append("&device_en=1");
		}
		
		String[] gradeArray = val.split(",");
		if(Constant.grade.equals("province")){
			stringBuffer.append("&province="+gradeArray[0]);
		}else if(Constant.grade.equals("city")){
			stringBuffer.append("&province="+gradeArray[0]);
			stringBuffer.append("&city="+gradeArray[1]);
		}else if(Constant.grade.equals("county")){
			stringBuffer.append("&province="+gradeArray[0]);
			stringBuffer.append("&city="+gradeArray[1]);
			stringBuffer.append("&county="+gradeArray[2]);
		}else if(Constant.grade.equals("building")){
			stringBuffer.append("&province="+gradeArray[0]);
			stringBuffer.append("&city="+gradeArray[1]);
			stringBuffer.append("&county="+gradeArray[2]);
			stringBuffer.append("&building="+gradeArray[3]);
		}else if(Constant.grade.equals("room")){
			stringBuffer.append("&province="+gradeArray[0]);
			stringBuffer.append("&city="+gradeArray[1]);
			stringBuffer.append("&county="+gradeArray[2]);
			stringBuffer.append("&building="+gradeArray[3]);
			stringBuffer.append("&room="+gradeArray[4]);
		}else if(Constant.grade.equals("site")){
			stringBuffer.append("&province="+gradeArray[0]);
			stringBuffer.append("&city="+gradeArray[1]);
			stringBuffer.append("&county="+gradeArray[2]);
			stringBuffer.append("&site="+gradeArray[3]);
		}
		
		String time = sp_time.getSelectedItem().toString();
		if(time.equals("周")){
			stringBuffer.append("&time=week");
		}else if(time.equals("天")){
			stringBuffer.append("&day=day");
		}
	
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("search", stringBuffer.toString());
		intent.putExtras(bundle);
		//图像显示
		if(Constant.viewType.equals("graph")){
			intent.setClass(Act_View.this, Act_Graph.class);
			startActivity(intent);
		}else{
			//列表显示
			intent.setClass(Act_View.this,Act_List.class);
			startActivity(intent);
		}
	}
	
}
