package com.energy.activity;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.energy.application.MyApplication;
import com.energy.bean.ResponseStatus;
import com.energy.bean.ResultDataResult;
import com.energy.bean.RoomDataResult;
import com.energy.bean.SiteDataResult;
import com.energy.util.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Act_List extends BaseActivity {
	
	private String search;
	private Context context;
	private LinearLayout ll_room;
	private LinearLayout ll_site;
	
	public void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_list);
		initPage();
	}
	
	public void initPage(){
		context = this;
		findView();
		Bundle bundle = this.getIntent().getExtras();
		search = bundle.getString("search");
		showResult();
	}
	
	public void showResult(){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(search, new AsyncHttpResponseHandler() {
		    public void onSuccess(String response) {
		    	ResponseStatus status = JSON.toJavaObject(JSON.parseObject(response), ResponseStatus.class);
		    	if(status.getStatus()==200){
		    		if(Constant.grade.equals("site")){
		    			//只存在SITE的形式
		    			
		    			SiteDataResult sitedata = JSON.toJavaObject(JSON.parseObject(response), SiteDataResult.class);
		    			ll_site.setVisibility(0);
		    			
		    		}else if(Constant.grade.equals("room")||Constant.grade.equals("building")){
		    			//room的形式
		    			RoomDataResult roomdata = JSON.toJavaObject(JSON.parseObject(response), RoomDataResult.class);
		    			
		    			if(roomdata.getRoom_data().getData().size()>0){
		    				showRoomList(roomdata.getRoom_data().getData());
		    			}else{
		    				Toast.makeText(Act_List.this, "没有获取到数据！",
									Toast.LENGTH_SHORT).show();
		    			}
		    			
		    			ll_room.setVisibility(0);
		    			
		    		}else{
		    			//两者都存在的情况
		    			ll_room.setVisibility(0);
		    			ll_site.setVisibility(0);
		    			ResultDataResult resultdata =  JSON.toJavaObject(JSON.parseObject(response), ResultDataResult.class);
		    		}
		    		
		    	}else{
		    		Toast.makeText(Act_List.this, "数据获取错误",
							Toast.LENGTH_SHORT).show();
		    	}
		    }
		    public void onFailure(Throwable e, String response) {
		    	Toast.makeText(Act_List.this, "请检查网络！",
						Toast.LENGTH_SHORT).show();
		    }
		    public void onFinish() {
		    }
		});
	}
	
	public void findView(){
		ll_room = (LinearLayout)findViewById(R.id.list_ll_room);
		ll_site = (LinearLayout)findViewById(R.id.list_ll_site);
	}
	
	
	public void showRoomList(List data){
		for(int i=0;i<data.size();i++){
			JSONObject jsobj = (JSONObject)data.get(i);
			RelativeLayout rel = showView(jsobj);
			if(i%2!=0){
				rel.setBackgroundResource(R.color.gray);
			}
			ll_room.addView(rel);
		}
	}
	
	public RelativeLayout showView(JSONObject jsobj){
		
		RelativeLayout rel = new RelativeLayout(context);
		
		if(Constant.zhibiao == 1){
			//主能耗
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);

			TextView tv_SMPS = new TextView(context);
			tv_SMPS.setMinHeight(38);
			tv_SMPS.setPadding(3, 11, 0, 0);
			tv_SMPS.setTextSize(14);
			tv_SMPS.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,0.5f));
			tv_SMPS.setText("开关电源:"+jsobj.get("SMPS"));
			
			TextView tv_COOL = new TextView(context);
			tv_COOL.setMinHeight(38);
			tv_COOL.setPadding(3, 11, 0, 0);
			tv_COOL.setTextSize(14);
			tv_COOL.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,0.5f));
			tv_COOL.setText("空调:"+jsobj.get("cooling"));
			
			LinearLayout ll = new LinearLayout(context);
			ll.setLayoutParams(lp);
			ll.setOrientation(LinearLayout.HORIZONTAL);
			ll.addView(tv_SMPS);
			ll.addView(tv_COOL);
			
			TextView tv_light = new TextView(context);
			tv_light.setMinHeight(38);
			tv_light.setPadding(3, 11, 0, 0);
			tv_light.setTextSize(14);
			tv_light.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,0.5f));
			tv_light.setText("光照:"+jsobj.get("lighting"));
			
			TextView tv_other = new TextView(context);
			tv_other.setMinHeight(38);
			tv_other.setPadding(3, 11, 0, 0);
			tv_other.setTextSize(14);
			tv_other.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,0.5f));
			tv_other.setText("其他能耗:"+jsobj.get("other"));
			
			LinearLayout ll_2 = new LinearLayout(context);
			ll_2.setLayoutParams(lp);
			ll_2.setOrientation(LinearLayout.HORIZONTAL);
			ll_2.addView(tv_light);
			ll_2.addView(tv_other);
			
			TextView tv_pr = new TextView(context);
			tv_pr.setMinHeight(38);
			tv_pr.setPadding(3, 11, 0, 0);
			tv_pr.setTextSize(14);
			tv_pr.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,0.5f));
			tv_pr.setText("主设备能耗:"+jsobj.get("primaryDevice"));
			
			TextView tv_sum = new TextView(context);
			tv_sum.setMinHeight(38);
			tv_sum.setPadding(3, 11, 0, 0);
			tv_sum.setTextSize(14);
			tv_sum.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,0.5f));
			tv_sum.setText("总能耗:"+jsobj.get("sum"));
			
			LinearLayout ll_3 = new LinearLayout(context);
			ll_3.setLayoutParams(lp);
			ll_3.setOrientation(LinearLayout.HORIZONTAL);
			ll_3.addView(tv_pr);
			ll_3.addView(tv_sum);
			
			TextView tv_t = new TextView(context);
			tv_t.setMinHeight(38);
			tv_t.setPadding(3, 11, 0, 0);
			tv_t.setTextSize(14);
			tv_t.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			tv_t.setText("时间:"+jsobj.get("date"));
			
			LinearLayout ll_4 = new LinearLayout(context);
			ll_4.setLayoutParams(lp);
			ll_4.setOrientation(LinearLayout.HORIZONTAL);
			ll_4.addView(tv_t);
			
			LinearLayout ll_all = new LinearLayout(context);
			ll_all.setLayoutParams(lp);
			ll_all.setOrientation(LinearLayout.VERTICAL);
			ll_all.addView(ll);
			ll_all.addView(ll_2);
			ll_all.addView(ll_3);
			ll_all.addView(ll_4);
			
			rel.setLayoutParams(lp);
			rel.addView(ll_all);
			
		}
		return rel;
	}

}
