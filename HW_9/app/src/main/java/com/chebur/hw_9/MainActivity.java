package com.chebur.hw_9;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BtnFragment.FrgListener, DoctorDialog.DoctorDialogLestener {

    private android.support.v7.widget.RecyclerView _rv;
    private DoctorAdapter _da;
    private List<Doctor> _doctors = new ArrayList<Doctor>();
    MyDBHelper _db;
    FragmentTransaction _fTr;
    BtnFragment _frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        _rv.setLayoutManager(llm);
        _rv.setAdapter(_da = new DoctorAdapter(_doctors));

        _frag = new BtnFragment();
        _fTr = getFragmentManager().beginTransaction();
        _fTr.replace(R.id.fragment_place, _frag);
        _fTr.commit();

        _db = new MyDBHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShowData();
    }

    private void ShowData() {
        _doctors = _db.GetDoctors();
        _da.ChangeDataSet(_doctors);
    }

    @Override
    public void OnAdd() {
        DoctorDialog drdlg = new DoctorDialog();
        drdlg.show(getSupportFragmentManager(), "doctor_dlg");
    }

    @Override
    public void OnDialogOK(Doctor dr) {
        _db.UpdateDoctor(dr);
        ShowData();
    }
}
