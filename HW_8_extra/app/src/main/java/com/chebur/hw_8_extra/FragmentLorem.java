package com.chebur.hw_8_extra;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nick on 25.11.2015.
 */
public class FragmentLorem extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewRoot = (ViewGroup)inflater.inflate(R.layout.frag_content, container, false);

        return viewRoot;
    }
}
