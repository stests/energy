package com.energy.bean;

import com.energy.bean.resultdata.Roomdata;

public class RoomDataResult {
	Roomdata room_data;
	private int status;
	public Roomdata getRoom_data() {
		return room_data;
	}
	public void setRoom_data(Roomdata room_data) {
		this.room_data = room_data;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
