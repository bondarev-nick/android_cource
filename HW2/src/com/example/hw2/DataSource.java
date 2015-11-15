package com.example.hw2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class DataSource {
	private String _fName;
	private Context _ctx;
	
	public DataSource(Context ctx, String fName) {
		_fName = fName;
		_ctx = ctx;
	}
	
	public String getData() throws IOException {
		StringBuilder strb = new StringBuilder();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(_ctx.getAssets().open(_fName)));
		String line;
		while((line = reader.readLine()) != null) {
			strb.append(line);
			Log.d("***", line);
		}
		
		return strb.toString();
	}
}
