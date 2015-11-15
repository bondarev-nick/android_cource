package com.example.hw2;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.hw2.DataModel.DataModel;
import com.example.hw2.DataModel.PersonList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ProgressDialog _progress;
	private int _index = 0;
	
	private TextView _tvId;
	private TextView _tvFname;
	private TextView _tvLname;
	private TextView _tvAge;
	private CheckBox _cbDegree;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			_tvId = (TextView)findViewById(R.id.tvId);
			_tvFname = (TextView)findViewById(R.id.tvFname);
			_tvLname = (TextView)findViewById(R.id.tvLname);
			_tvAge = (TextView)findViewById(R.id.tvAge);
			_cbDegree = (CheckBox)findViewById(R.id.cbDegree);
			
			DataModel dm = DataModel.getInstance();
			PersonList pl = dm.getPersonList();
			if (pl == null) {
				_progress = ProgressDialog.show(this, "Loading...", "");
				new LoadTask().execute();
			}
			else {
				DisplayData();
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
		}
	}

	private PersonList loadPersonList(String fName) throws JSONException, IOException, InterruptedException {
		DataSource ds = new DataSource(this, fName);
		String data = ds.getData();
		JSONObject jsonBase = new JSONObject(data);
		JSONArray arr = jsonBase.getJSONArray("people");
		PersonList pl = new PersonList(arr);
		//emulate very hard data loading...
		Thread.sleep(5000l);
		return pl;
	}
	
	private void DisplayData() {
		PersonList pl = DataModel.getInstance().getPersonList();
		_tvId.setText(String.format("%d", pl.get(_index).get_id()));
		_tvFname.setText(pl.get(_index).get_name());
		_tvLname.setText(pl.get(_index).get_surname());
		_tvAge.setText(String.format("%d", pl.get(_index).get_age()));
		_cbDegree.setChecked(pl.get(_index).get_isDegree());

		_index++;
		if (_index >= DataModel.getInstance().getPersonList().size()) {
			_index = 0;
		}
	}
	
	public void onBtnClick(View v) {
		DisplayData();
	}
	
	class LoadTask extends AsyncTask<Void, Void, PersonList> {

		@Override
		protected PersonList doInBackground(Void... params) {
			PersonList pl = null;
			try {
				pl = loadPersonList(getResources().getString(R.string.fname));
			} catch (NotFoundException | JSONException | IOException | InterruptedException e) {
				e.printStackTrace();
			}
			return pl;
		}
		
		@Override
		protected void onPostExecute(PersonList result) {
			MainActivity.this._progress.dismiss();
			if (result == null) {
				Toast.makeText(MainActivity.this,
						"Something went wrong :(", Toast.LENGTH_SHORT).show();
			}
			else {
				DataModel.getInstance().setPersonList(result);
				DisplayData();
			}
		}
		
	}
}
