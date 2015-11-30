package com.chebur.hw_9;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nick on 29.11.2015.
 */
public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>{

    List<Doctor> _data;

    public DoctorAdapter (List<Doctor> drs) {
        _data = drs;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_item, parent, false);
        DoctorViewHolder vh = new DoctorViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder holder, int position) {
        holder._name.setText(_data.get(position).get_name());
        holder._age.setText(String.valueOf(_data.get(position).get_age()));
        holder._id.setText(String.valueOf(_data.get(position).get_id()));
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public void ChangeDataSet(List<Doctor> data) {
        _data = data;
        notifyDataSetChanged();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView _id;
        TextView _name;
        TextView _age;

        public DoctorViewHolder(View itemView) {
            super(itemView);

            _id = (TextView)itemView.findViewById(R.id.tv_dr_id);
            _age = (TextView)itemView.findViewById(R.id.tv_dr_age);
            _name = (TextView)itemView.findViewById(R.id.tv_dr_name);
        }
    }
}
