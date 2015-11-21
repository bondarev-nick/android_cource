package com.example.nick.hw_5_6.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.nick.hw_5_6.R;
import com.example.nick.hw_5_6.adapter.MyAdapter;
import com.example.nick.hw_5_6.model.DataItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnCardItemClick{

    private RecyclerView _rv;
    private MyAdapter _adp;
    private List<DataItem> _data = new ArrayList<DataItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _rv = (RecyclerView)findViewById(R.id.rcView);
        _adp = new MyAdapter(_data, this);
        _rv.setAdapter(_adp);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        _rv.setLayoutManager(llm);

        showData();
    }

    private void showData() {
        _data = initData(200);
        _adp.UpdateData(_data);
    }

    private List<DataItem> initData(int num) {
        ArrayList<DataItem> data = new ArrayList<DataItem>();

        for (int i = 0; i < num; i++) {
            DataItem di = new DataItem();
            di.set_name(String.format("Some looooong name %d", i));
            di.set_subname(String.format("just a short subname %d", i));
            data.add(di);
        }

        return  data;
    }

    @Override
    public void OnClick(DataItem item) {
        Toast.makeText(this, item.get_name(), Toast.LENGTH_SHORT).show();
        Intent gpsIntent = new Intent(this, GpsActivity.class);
        startActivity(gpsIntent);
    }
}
