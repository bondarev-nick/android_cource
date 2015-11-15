package com.example.hw1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tv1;
	private TextView tv2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv1 = (TextView)findViewById(R.id.tv1);
		tv2 = (TextView)findViewById(R.id.tv2);
		
		//set anonimus listener
		tv1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity.this.ProcessClick();
				
			}
		});
		
		//set declared anonimus listener
		tv2.setOnClickListener(lisnr);
	}
	
	//listener defined in activity_main.xml
	public void onBtnClick(View v) {
		ProcessClick();
	}
	
	OnClickListener lisnr = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			MainActivity.this.ProcessClick();
		}
	};

	protected void ProcessClick() {
		String tmp_str = tv1.getText().toString();
		tv1.setText(tv2.getText());
		tv2.setText(tmp_str);
	}
}
