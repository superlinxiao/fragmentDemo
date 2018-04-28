package com.linxiao.fragmentdemo.fragment.social;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linxiao.fragmentdemo.R;

/**
 * Description
 * Author lizheng
 * Create Data  2018\4\28 0028
 */
public class SocialDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.social_detail_fragment, null);
    }

    public static SocialDetailFragment newInstance() {
        Bundle args = new Bundle();
        SocialDetailFragment fragment = new SocialDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
