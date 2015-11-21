package com.example.nick.hw_5_6.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nick.hw_5_6.R;
import com.example.nick.hw_5_6.model.DataItem;

import java.util.List;

/**
 * Created by nick on 21.11.2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<DataItem> _data;
    private OnCardItemClick _lsnr;

    public MyAdapter (List<DataItem> data, OnCardItemClick lsnr) {
        _data = data;
        _lsnr = lsnr;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder vh = null;
        View v = null;
        v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_view_item, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (Integer)v.getTag();
                _lsnr.OnClick(_data.get(pos));
            }
        });

        vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder._subName.setText(_data.get(position).get_subname());
        holder._name.setText(_data.get(position).get_name());
        holder.v.setTag(Integer.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return _data == null ? 0 : _data.size();
    }

    public void UpdateData(List<DataItem> newData) {
        _data = newData;
        notifyDataSetChanged();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView _name;
        public TextView _subName;
        public View v;


        public MyViewHolder(View itemView) {
            super(itemView);

            _name = (TextView)itemView.findViewById(R.id.tvName);
            _subName = (TextView)itemView.findViewById(R.id.tvSubname);
            v = itemView;
        }
    }

    public interface OnCardItemClick {
        public void OnClick(DataItem item);
    }
}
