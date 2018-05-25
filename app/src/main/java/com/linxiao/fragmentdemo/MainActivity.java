package com.linxiao.fragmentdemo;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.linxiao.fragmentdemo.fragment.MineFragment;
import com.linxiao.fragmentdemo.fragment.ShoppingFragment;
import com.linxiao.fragmentdemo.fragment.SocialFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 目的：加深对fragment的理解
 * <p>
 * 细分：
 * 1.操作add、remove、replace、show、hide方法。
 * 2.操作fragment事务栈。
 * 3.操作getChildSupportManager
 * <p>
 * 实例:首页切换逻辑。
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private List<Fragment> mList;
    private SocialFragment mSocialFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.social).setOnClickListener(this);
        findViewById(R.id.shopping).setOnClickListener(this);
        findViewById(R.id.mine).setOnClickListener(this);

        FragmentManager supportFragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        mList = new ArrayList<>();
        //1
        mSocialFragment = SocialFragment.newInstance();
        fragmentTransaction.add(R.id.fragment_container, mSocialFragment, "socialFragment");
        mList.add(mSocialFragment);
        //2
        ShoppingFragment shoppingFragment = ShoppingFragment.newInstance();
        fragmentTransaction.add(R.id.fragment_container, shoppingFragment, "shoppingFragment");
        mList.add(shoppingFragment);
        //3
        MineFragment mineFragment = MineFragment.newInstance();
        fragmentTransaction.add(R.id.fragment_container, mineFragment, "mineFragment");
        mList.add(mineFragment);
        fragmentTransaction.commit();
        show(0);
    }


    /**
     * 显示和隐藏
     */
    public void show(int id) {
        FragmentManager supportFragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        for (int i = 0; i < mList.size(); i++) {
            if (i == id) {
                fragmentTransaction.show(mList.get(i));
            } else {
                fragmentTransaction.hide(mList.get(i));
            }
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.social: {
                show(0);
                break;
            }
            case R.id.shopping: {
                show(1);
                break;
            }
            case R.id.mine: {
                show(2);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        //support包中的回退栈需要手动操作
        //只有android.app.fragmentManager这个包中回退栈，在点击返回的时候，会自动回退
        if (mSocialFragment != null && !mSocialFragment.isHidden() && mSocialFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
            mSocialFragment.getChildFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }

    public void replaceTest(View view) {
        startActivity(new Intent(this,ReplaceTestActivity.class));
    }
}
