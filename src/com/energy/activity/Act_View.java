package com.energy.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.energy.application.MyApplication;
import com.energy.bean.configinfo.Room;
import com.energy.util.Constant;

public class Act_View extends BaseActivity {
	
	LinearLayout ll_content;
	Spinner sp_zhibiao;
	Context context;

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
	}
	
	public void viewWithGrade(){
		
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		
		for(int i=0;i<Constant.search.size();i++){
			if(Constant.grade.equals("province")){
				
			}else if(Constant.grade.equals("city")){
				
			}else if(Constant.grade.equals("county")){
				
			}else if(Constant.grade.equals("building")){
				
				Room r = (Room)Constant.search.get(i);
				
				RelativeLayout rl = new RelativeLayout(context);
    			rl.setLayoutParams(lp);
    			rl.setMinimumHeight(34);
    			rl.setPadding(3, 0, 3, 0);
    			if(i%2!=0){
    				rl.setBackgroundResource(R.color.gray);
    			}
    			
    			TextView tv = new TextView(context);
    			tv.setLayoutParams(lp);
    			tv.setGravity(Gravity.LEFT);
    			tv.setText(r.getBuilding());
    			rl.addView(tv);
    			
    			ll_content.addView(rl);
    			
			}else if(Constant.grade.equals("room")){
				
			}else if(Constant.grade.equals("site")){
				
			}
		}
	}
	
}
