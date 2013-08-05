package com.energy.bean;

import com.energy.bean.resultdata.Sitedata;

public class SiteDataResult {
	private Sitedata site_data;
	private int status;
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
