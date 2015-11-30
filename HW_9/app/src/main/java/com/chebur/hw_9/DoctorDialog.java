package com.chebur.hw_9;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by nick on 30.11.2015.
 */
public class DoctorDialog extends DialogFragment {

    private DoctorDialogLestener _lsnr;
    private EditText _drName;
    private EditText _drAge;
    private Button _addDr;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _lsnr = (DoctorDialogLestener) activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder bldr = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.doctor_dialog, null);
        _drName = (EditText)v.findViewById(R.id.etDrName);
        _drAge = (EditText)v.findViewById(R.id.etDrAge);
        _addDr = (Button)v.findViewById(R.id.btnAddDr);
        _addDr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(_drName.getText()) && !TextUtils.isEmpty(_drAge.getText())) {
                    Doctor dr = new Doctor(_drName.getText().toString(), Integer.parseInt(_drAge.getText().toString()));
                    _lsnr.OnDialogOK(dr);
                    dismiss();
                }
            }
        });
        bldr.setView(v);

        return bldr.create();
    }

    interface DoctorDialogLestener {
        public void OnDialogOK(Doctor dr);
    }
}
