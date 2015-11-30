package com.chebur.hw_7_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nick on 24.11.2015.
 */
public class MyFragmentDialog extends DialogFragment {

    private DialogEventListener _lsnr;
    private List<String> result = new ArrayList<String>();
    private int _parent;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        _parent = getArguments().getInt(MainActivity.VIEW_ID);

        AlertDialog.Builder dlgBUilder = new AlertDialog.Builder(getActivity()).
                setTitle("Choose smth").
                setMultiChoiceItems(R.array.names, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        String[] str_arr = getResources().getStringArray(R.array.names);
                        if (isChecked) {
                            result.add(str_arr[which]);
                        }
                        else {
                            result.remove(str_arr[which]);
                        }
                    }
                }).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _lsnr.onDialogResult(result, _parent);
                    }
                });

        return dlgBUilder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _lsnr = (DialogEventListener) activity;
        }
        catch (ClassCastException ex) {
            Toast.makeText(activity, "activity must implement DialogEventListener",
                    Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }

    public interface DialogEventListener {
        void onDialogResult(List<String> strs, int parent);
    }
}
