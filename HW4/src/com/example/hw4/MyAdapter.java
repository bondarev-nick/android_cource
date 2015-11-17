package com.example.hw4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private Context _ctx;
	List<String> _data;
	LayoutInflater _inflater;
	ViewCach _vc;
	
	public MyAdapter(Context ctx, List<String> data) {
		_ctx = ctx;
		_data = data;
		_inflater = (LayoutInflater)_ctx.
				getSystemService(_ctx.LAYOUT_INFLATER_SERVICE);
		_vc = new ViewCach(); 
	}
	
	@Override
	public int getCount() {
		return _data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = _inflater.inflate(R.layout.lv_item, parent, false);
		}
		
		TextView tv = _vc.getTextView(v);
		if (tv != null) {
			tv.setText(_data.get(position).toString());
		}
		
		return v;
	}
	
	private class ViewCach {
		private Map<View, View> _cach;
		
		public ViewCach () {
			_cach = new HashMap<View, View>();
		}
		
		public TextView getTextView(View v) {
			Log.d("aaa", String.format("Cach size: %d", _cach.size()));
			TextView tv = (TextView)_cach.get(v);
			if (tv == null) {
				tv = (TextView)v.findViewById(R.id.tv);
				_cach.put(v, tv);
			}

			return tv;
		}
	}

}
