package com.linxiao.fragmentdemo.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linxiao.fragmentdemo.R;
import com.linxiao.fragmentdemo.fragment.social.SocialDetailFragment;
import com.linxiao.fragmentdemo.fragment.social.SocialListFragment;

/**
 * Description
 * Author lizheng
 * Create Data  2018\4\28 0028
 */
public class SocialFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.social_fragment, container, false);

        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction mFragmentTransaction = childFragmentManager.beginTransaction();
        final SocialListFragment socialListFragment = SocialListFragment.newInstance();
        socialListFragment.setInterface(new SocialListFragment.FragmentInterface() {
            @Override
            public void onStartDetail() {
                SocialDetailFragment socialDetailFragment = SocialDetailFragment.newInstance();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.add(R.id.social_fragment_container, socialDetailFragment, null);
                ft.hide(socialListFragment);
                ft.addToBackStack(null);
                ft.commitAllowingStateLoss();
            }
        });
        mFragmentTransaction.add(R.id.social_fragment_container, socialListFragment, null);
        mFragmentTransaction.commitAllowingStateLoss();
        return inflate;
    }

    public static SocialFragment newInstance() {
        Bundle args = new Bundle();
        SocialFragment fragment = new SocialFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
