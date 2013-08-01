package com.energy.service;

import com.energy.bean.ConfigInfo;
import com.energy.util.Constant;
import com.energy.util.SQLiteUtil;

public class ConfigService {
	
	SQLiteUtil sqLiteUtil = SQLiteUtil.getInstance();
	
	public boolean insertConfig(ConfigInfo configInfo){
		sqLiteUtil.execQuery(Constant.dbpath, "delete from gradeinfo");
		
		
		return true;
	}
	
}
