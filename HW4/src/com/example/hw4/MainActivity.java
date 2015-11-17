package com.example.hw4;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	private List<String> _data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		_data = new ArrayList<String>();

		for (int i = 0; i < 50; i++) {
			_data.add(String.format("Name %d, Surname %d", i, i));
		}
		
		ListView lv = (ListView)findViewById(R.id.listView);
		lv.setAdapter(new MyAdapter(this, _data));
	}

}
