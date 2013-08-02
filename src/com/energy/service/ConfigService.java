package com.energy.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.energy.bean.ConfigInfo;
import com.energy.bean.configinfo.Room;
import com.energy.bean.configinfo.Site;
import com.energy.util.Constant;
import com.energy.util.SQLiteUtil;

public class ConfigService {
	private String dbpath;
	private Context context;
	public ConfigService(Context context){
		this.context = context;
		dbpath = context.getFilesDir().getAbsolutePath()+Constant.dbpath;
	}
	
	SQLiteUtil sqLiteUtil = SQLiteUtil.getInstance();
	
	public boolean insertConfig(ConfigInfo configInfo){
		sqLiteUtil.execQuery(dbpath, "delete from gradeinfo");
		//province":"北京","city":"北京市","county":"海淀区","building":"机楼1","room":"机房1"}
		for(int i=0;i<configInfo.getRooms().size();i++){
			Room r = configInfo.getRooms().get(i);
			sqLiteUtil.execQuery(dbpath, "insert into gradeinfo (province,city,county,building,room,tpe) values" +
					" ('"+r.getProvince()+"','"+r.getProvince()+","+r.getCity()+"','"+r.getProvince()+","+r.getCity()+","+r.getCounty()+"','"+r.getProvince()+","+r.getCity()+","+r.getCounty()+","+r.getBuilding()+"','"+r.getProvince()+","+r.getCity()+","+r.getCounty()+","+r.getBuilding()+","+r.getRoom()+"',1)");
		}
		for(int i=0;i<configInfo.getSites().size();i++){
			Site s = configInfo.getSites().get(i);
			sqLiteUtil.execQuery(dbpath, "insert into gradeinfo (province,city,county,site,tpe) values" +
					" ('"+s.getProvince()+"','"+s.getProvince()+","+s.getCity()+"','"+s.getProvince()+","+s.getCity()+","+s.getCounty()+"','"+s.getProvince()+","+s.getCity()+","+s.getCounty()+","+s.getSite()+"',2)");
		}
		return true;
	}
	
	public List getList(String sql,String grade) {
		List list = new ArrayList();
		Cursor cursor = sqLiteUtil.openQuery(dbpath, sql);
		if(cursor.getCount()>0){
			while(cursor.moveToNext())
			{
				try {
					list.add(new String(cursor.getBlob(cursor.getColumnIndex(grade)), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
			}
		}
		return list;
	}
	
	
}
