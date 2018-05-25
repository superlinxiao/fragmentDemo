package com.linxiao.fragmentdemo;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.linxiao.fragmentdemo.fragment.MineFragment;

/**
 * 测试replace的fragment，在activity重构的时候，之前旧的fragment会不会重新加载
 *
 * 结果：
 * 就的fragment仍然会被attach到activity中，但是接下来会马上被新的fragment替换。
 *
 * 其他：
 *
 * 如果是通过add方式添加到的fragmentManager中，那么再重构的时候，会继续留在fragmentManager中.
 *
 * 另外参考回忆录：
 * 使用loadrootfragment不复用，导致null的原因探究
 */
public class ReplaceTestActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repalce);
        initView();
    }

    private void initView() {
        FragmentManager supportFragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        MineFragment mSocialFragment = MineFragment.newInstance();
        fragmentTransaction.replace(R.id.fragment_container, mSocialFragment, "socialFragment");
        fragmentTransaction.commitAllowingStateLoss();
    }
}
