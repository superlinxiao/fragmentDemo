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
public class SocialListFragment extends Fragment {

    private FragmentInterface mClickInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.social_list_fragment, container,false);
        inflate.findViewById(R.id.go_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickInterface!=null){
                    mClickInterface.onStartDetail();
                }
            }
        });
        return inflate;
    }
    public static SocialListFragment newInstance() {
        Bundle args = new Bundle();
        SocialListFragment fragment = new SocialListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setInterface(FragmentInterface fragmentInterface){
        this.mClickInterface = fragmentInterface;
    }

    public interface FragmentInterface{
        void onStartDetail();
    }


}
