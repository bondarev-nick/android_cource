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
	
	
	public MyAdapter(Context ctx, List<String> data) {
		_ctx = ctx;
		_data = data;
		_inflater = (LayoutInflater)_ctx.
				getSystemService(_ctx.LAYOUT_INFLATER_SERVICE);
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
			v.setTag(new MyViewHolder(v));
		}
		
		MyViewHolder vh = (MyViewHolder)v.getTag();
		TextView tv = vh._tv;
		if (tv != null) {
			tv.setText(_data.get(position).toString());
		}
		
		return v;
	}
	
	private class MyViewHolder {
		
		public View _v;
		public TextView _tv;
		
		public MyViewHolder(View v) {
			_v = v;
			_tv = (TextView)_v.findViewById(R.id.tv);
		}
	}

}
