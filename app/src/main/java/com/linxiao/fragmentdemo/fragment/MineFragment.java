package com.linxiao.fragmentdemo.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linxiao.fragmentdemo.R;

import java.util.logging.Logger;

/**
 * Description
 * Author lizheng
 * Create Data  2018\4\28 0028
 */
public class MineFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mine_fragment, container, false);
    }

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("test_test", "onAttach " + toString());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("test_test", "onViewCreated " + toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test_test", "onDestroy  " + toString());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("test_test", "onDetach  " + toString());

    }
}
