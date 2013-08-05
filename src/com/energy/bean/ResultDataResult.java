package com.energy.bean;

import com.energy.bean.resultdata.Roomdata;
import com.energy.bean.resultdata.Sitedata;

public class ResultDataResult {
	private Roomdata room_data;
	private Sitedata site_data;
	private int status;
	public Roomdata getRoom_data() {
		return room_data;
	}
	public void setRoom_data(Roomdata room_data) {
		this.room_data = room_data;
	}
	public Sitedata getSite_data() {
		return site_data;
	}
	public void setSite_data(Sitedata site_data) {
		this.site_data = site_data;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
