package com.chebur.hw_9;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by nick on 29.11.2015.
 */
public class BtnFragment extends Fragment {

    ImageButton _btn;
    FrgListener _lsn;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        _lsn = (FrgListener)activity;
        Log.d("qwe", "sdfsdsdggdg");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_fragment, null);
        _btn = (ImageButton) v.findViewById(R.id.btn_add);
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("qwe", "sdfsdgfasd");
                _lsn.OnAdd();
            }
        });

        return v;
    }

    interface FrgListener {
        public void OnAdd();
    }
}
