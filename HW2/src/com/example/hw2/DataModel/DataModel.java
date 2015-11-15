package com.example.hw2.DataModel;

public class DataModel {
	private static DataModel _inst = null;
	private PersonList _list = null;
	
	public static DataModel getInstance() {
		if (_inst == null) {
			_inst = new DataModel();
		}
		
		return _inst;
	}
	
	public void setPersonList(PersonList lst) {
		_list = lst;
	}
	
	public PersonList getPersonList() {
		return _list;
	}
}
