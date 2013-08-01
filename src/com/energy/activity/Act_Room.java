package com.energy.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.energy.application.MyApplication;

public class Act_Room  extends BaseActivity{
	
	private  List<String> list =  new  ArrayList<String>();  
	private Spinner room_sp_grade;
	private EditText room_et_keyword;
	private Button btn_search;
	private String[] grade_array = new String[] { "省", "市", "县" ,"机楼","机房","基站"};
	

	public void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_room);
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
				
				
				
			}
		});
	}
}
