package com.energy.bean;

import java.util.List;

import com.energy.bean.configinfo.Room;
import com.energy.bean.configinfo.Site;

public class ConfigInfo {
	private int status;
	private List<Room> rooms;
	private List<Site> sites;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<Room> getRooms() {
		return rooms;
	}
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	public List<Site> getSites() {
		return sites;
	}
	public void setSites(List<Site> sites) {
		this.sites = sites;
	}
}
