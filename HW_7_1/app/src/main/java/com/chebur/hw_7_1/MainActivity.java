package com.chebur.hw_7_1;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        MyFragmentDialog.DialogEventListener{

    public static String VIEW_ID = "parent_view_id";
    private TextView _tv1, _tv2, _tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _tv1 = (TextView)findViewById(R.id.tv1);
        _tv2 = (TextView)findViewById(R.id.tv2);
        _tv3 = (TextView)findViewById(R.id.tv3);

        _tv1.setOnClickListener(this);
        _tv2.setOnClickListener(this);
        _tv3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        showDialog(v);
    }

    @Override
    public void onDialogResult(List<String> strs, int parent) {
        if (parent == _tv1.getId()) {
            _tv1.setText(strs.toString());
        }
        else if (parent == _tv2.getId()) {
            _tv2.setText(strs.toString());
        }
        else if (parent == _tv3.getId()) {
            _tv3.setText(strs.toString());
        }
    }

    private void showDialog(View parent) {
        MyFragmentDialog dlg = new MyFragmentDialog();
        Bundle bnd = new Bundle();
        bnd.putInt(VIEW_ID, parent.getId());
        dlg.setArguments(bnd);
        dlg.show(getSupportFragmentManager(), "tag1");
    }
}
