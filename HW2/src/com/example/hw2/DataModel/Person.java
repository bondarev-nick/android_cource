package com.example.hw2.DataModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Person {
	private int _id;
	private String _name;
	private String _surname;
	private int _age;
	private Boolean _isDegree;
	
	public Person () {};
	
	public Person(JSONObject json) throws JSONException {
		_id = json.getInt("id");
		_name = json.getString("name");
		_surname = json.getString("surname");
		_age = json.getInt("age");
		_isDegree = json.getBoolean("isDegree");
	}
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public String get_surname() {
		return _surname;
	}
	public void set_surname(String _surname) {
		this._surname = _surname;
	}
	public int get_age() {
		return _age;
	}
	public void set_age(int _age) {
		this._age = _age;
	}
	public Boolean get_isDegree() {
		return _isDegree;
	}
	public void set_isDegree(Boolean _isDegree) {
		this._isDegree = _isDegree;
	}
}
