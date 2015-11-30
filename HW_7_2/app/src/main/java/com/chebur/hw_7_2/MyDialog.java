package com.chebur.hw_7_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by nick on 25.11.2015.
 */
public class MyDialog extends DialogFragment {

    private EditText _et;
    private View _dlgContent;
    private DlgEventListener _lsnr;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            _lsnr = (DlgEventListener)activity;
        }
        catch (Exception ex) {
            Toast.makeText(getActivity(), "implementation of 'DlgEventListener' is missed",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Title");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                _lsnr.onYes(_et.getText().toString());
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                _lsnr.onCancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                _lsnr.onNo();
            }
        });

        LayoutInflater infl = getActivity().getLayoutInflater();
        _dlgContent = infl.inflate(R.layout.dlg_layout, null);
        builder.setView(_dlgContent);

        _et = (EditText)_dlgContent.findViewById(R.id.et);
        Bundle arg = null;
        String txt = null;
        if ((arg = getArguments()) != null &&
                (txt = arg.getString(MainActivity.MY_KEY)) != null) {
            _et.setText(txt);
        }


        return builder.create();
    }

    interface DlgEventListener {
        void onYes(String str);
        void onNo();
        void onCancel();
    }
}
