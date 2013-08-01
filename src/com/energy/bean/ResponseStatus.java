package com.energy.bean;

import java.io.Serializable;

public class ResponseStatus implements Serializable{
	private static final long serialVersionUID = -8909539420233080719L;
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
