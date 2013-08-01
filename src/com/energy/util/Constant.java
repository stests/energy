package com.energy.util;

import android.os.Environment;

import com.energy.bean.LoginResult;

public class Constant {
	public static LoginResult loginResult; 
	public static String dbpath = "/data"+ Environment.getDataDirectory().getAbsolutePath() + "/energy.s3db";
}
