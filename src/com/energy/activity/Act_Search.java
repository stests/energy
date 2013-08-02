package com.energy.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.energy.application.MyApplication;
import com.energy.service.ConfigService;
import com.energy.util.Constant;

public class Act_Search  extends BaseActivity{
	
	private List<String> list =  new  ArrayList<String>();  
	private Spinner room_sp_grade;
	private EditText room_et_keyword;
	private Button btn_search;
	private String[] grade_array = new String[] { "省", "市", "县" ,"机楼","机房","基站"};
	

	public void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_search);
		initPage();
	}
	
	public void initPage(){
		findView();
		bindEvent();
	}
	
	public void findView(){
		room_sp_grade = (Spinner) findViewById(R.id.room_sp_grade);
		ArrayAdapter<String> grades = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, grade_array);
		room_sp_grade.setAdapter(grades);
		
		room_et_keyword = (EditText)findViewById(R.id.room_et_key);
		btn_search = (Button)findViewById(R.id.room_btn_search);
	}
	
	public void bindEvent(){
		btn_search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String grade = room_sp_grade.getSelectedItem().toString();
				String keyword = room_et_keyword.getText().toString();
				if(!keyword.isEmpty()){
					showResult(grade,keyword);
				}
			}
		});
	}
	
	public void showResult(String grade,String keyword){
		ConfigService configService = new ConfigService(Act_Search.this);
		List result = null;
		StringBuffer stringBuffer = new StringBuffer();
		if(grade.equals("省")){
			Constant.grade = "province";
			stringBuffer.append("select DISTINCT(province)  from gradeinfo where province like '%"+keyword+"%'");
			result =  configService.getList(stringBuffer.toString(),"province");
		}else if(grade.equals("市")){
			Constant.grade = "city";
			stringBuffer.append("select DISTINCT(city)  from gradeinfo where city like '%"+keyword+"%'");
			result =  configService.getList(stringBuffer.toString(),"city");
		}else if(grade.equals("县")){
			Constant.grade = "county";
			stringBuffer.append("select DISTINCT(county)  from gradeinfo where county like '%"+keyword+"%'");
			result =  configService.getList(stringBuffer.toString(),"county");
		}else if(grade.equals("机楼")){
			Constant.grade = "building";
			stringBuffer.append("select DISTINCT(building) from gradeinfo where building like '%"+keyword+"%' and tpe = 1");
			result =  configService.getList(stringBuffer.toString(),"building");
		}else if(grade.equals("机房")){
			Constant.grade = "room";
			stringBuffer.append("select DISTINCT(room) from gradeinfo where room like '%"+keyword+"%' and tpe = 1");
			result =  configService.getList(stringBuffer.toString(),"room");
		}else if(grade.equals("基站")){
			Constant.grade = "site";
			stringBuffer.append("select DISTINCT(site) from gradeinfo where site like '%"+keyword+"%' and tpe = 2");
			result =  configService.getList(stringBuffer.toString(),"site");
		}
		
		if(result.size()>0){
			Constant.search = result;
			Intent intent = new Intent();
			intent.setClass(Act_Search.this, Act_View.class);
			// 转向登陆后的页面
			startActivity(intent);
		}else{
			Toast.makeText(Act_Search.this, "未查询到结果！",
					Toast.LENGTH_SHORT).show();
		}
		
	}
	
}
