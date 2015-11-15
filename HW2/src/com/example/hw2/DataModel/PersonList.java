package com.example.hw2.DataModel;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class PersonList extends ArrayList<Person> {
	public PersonList (JSONArray json) throws JSONException {
		for(int i = 0; i < json.length(); i++) {
			this.add(new Person(json.getJSONObject(i)));
		}
	}
}
