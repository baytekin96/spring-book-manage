package com.example.demo.restResponse;

import java.util.HashMap;
import java.util.Map;

public class BaseResponse {
	private Boolean statu;
	private int code;
	private String message;
	private Map <Object,Object> data = new HashMap<Object,Object>();
	public Boolean getStatu() {
		return statu;
	}
	public void setStatu(Boolean statu) {
		this.statu = statu;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<Object, Object> getData() {
		return data;
	}
	public void setData(Map<Object, Object> data) {
		this.data = data;
	}
}