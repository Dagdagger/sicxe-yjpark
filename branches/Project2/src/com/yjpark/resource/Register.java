package com.yjpark.resource;

public class Register {
	private int regNumber;
	private String data = "0";

	public Register(int number) {
		this.regNumber = number;
	}
	
	public int getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(int regNumber) {
		this.regNumber = regNumber;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getDataInt() {
		return Integer.parseInt(this.data);
	}
	public int getDataInt(int mode) {
		return Integer.parseInt(this.data, mode);
	}
}
