package com.linxiao.fragmentdemo.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linxiao.fragmentdemo.MainActivity;
import com.linxiao.fragmentdemo.R;

/**
 * Description
 * Author lizheng
 * Create Data  2018\4\28 0028
 */
public class ShoppingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shopping_fragment, null);
    }

    public static ShoppingFragment newInstance() {
        Bundle args = new Bundle();
        ShoppingFragment fragment = new ShoppingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(MainActivity.TAG,"fragment oncreate");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(MainActivity.TAG,"fragment onPause");
    }
}
