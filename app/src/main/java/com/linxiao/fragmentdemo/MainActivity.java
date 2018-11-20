package com.linxiao.fragmentdemo;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import com.linxiao.fragmentdemo.fragment.MineFragment;
import com.linxiao.fragmentdemo.fragment.ShoppingFragment;
import com.linxiao.fragmentdemo.fragment.SocialFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.各个方法的执行顺序
 * onCreate > (onRestart) > onStart > (onRestoreInstanceState) > onResume > onPause > (onSaveInstanceState) >onStop > onDestroy
 * <p>
 * 2.
 * onStart之后， 用户可见，但是不可操作
 * onResume之后， 用户可见，可操作
 * onPause之后，用户可见，不可操作
 * onStop之后，用户不可见，不可操作
 * <p>
 * <p>
 * <p>
 * 3. commitAllowingStateLoss作用，不需要检查activity的state是否被保存，即不需要检查当前页面是不是进入后台。
 * 但是如果在commit的时候，onSaveInstanceState方法已经执行过了，那么fragmentManager的状态不会保存下来。
 * ui显示的效果如下
 * 1.如果activity在commitAllowingStateLoss之后没有销毁，不会影响正常的页面展示，只不过状态要等到下次执行OnSaveInstance的时候才会保存。
 * 2.如果activity在commitAllowingStateLoss之后被后台销毁了，那么fragmentManager在RestoreInstance的时候，没有最后的一次commit。
 * 3.如果在activity destroy之后提交，不管是commitAllowingStateLoss还是commit，直接会报activity is destroy exception。
 * <p>
 * 举例说明:
 * 1.在Activity里显示一个FragmentA;
 * 2.然后Activity被后台, onStop()和onSaveInstanceState()被调用;
 * 3.在某个事件触发下, 你用FragmentB replace FragmentA , 使用的是 commitAllowingStateLoss().
 * 这时候, 用户再返回应用, 可能会有两种情况发生:
 * 1.如果系统杀死了你的activity, 你的activity将会重建, 使用了上述步骤2保存的状态, 所以A会显示, B不会显示;
 * 2.如果系统没有杀死你的activity, 它会被提到前台, FragmentB就会显示出来, 到下次Activity stop的时候, 这个包含了B的状态就会被存下来.
 *
 * 4.activity的生命周期和fragment的生命周期中，一般是先执行fragment的方法，再执行activity。
 */
public class MainActivity extends Activity implements View.OnClickListener {

  public static final String MINE_FRAGMENT = "mineFragment";
  public static final String SOCIAL_FRAGMENT = "socialFragment";
  public static final String SHOPPING_FRAGMENT = "shoppingFragment";
  private List<Fragment> mList;
  private SocialFragment mSocialFragment;

  public static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.i(TAG, "onCreate start");
    super.onCreate(savedInstanceState);
    Log.i(TAG, "onCreate");
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
    boolean isRestore = true;
    //测试第二部：保证activity被回收后，可以复用之前的fragment
    //测试第三步：在开发者模式中设置不保留活动
    //1
    mSocialFragment = (SocialFragment) supportFragmentManager.findFragmentByTag(SOCIAL_FRAGMENT);
    if (mSocialFragment == null) {
      isRestore = false;
      mSocialFragment = SocialFragment.newInstance();
      fragmentTransaction.add(R.id.fragment_container, mSocialFragment, SOCIAL_FRAGMENT);
    }
    mList.add(mSocialFragment);
    //2
    ShoppingFragment shoppingFragment = (ShoppingFragment) supportFragmentManager.findFragmentByTag(SHOPPING_FRAGMENT);
    if (shoppingFragment == null) {
      isRestore = false;
      shoppingFragment = ShoppingFragment.newInstance();
      fragmentTransaction.add(R.id.fragment_container, shoppingFragment, SHOPPING_FRAGMENT);
    }
    mList.add(shoppingFragment);
    //3
    MineFragment mineFragment = (MineFragment) supportFragmentManager.findFragmentByTag(MINE_FRAGMENT);
    if (mineFragment == null) {
      isRestore = false;
      mineFragment = MineFragment.newInstance();
      fragmentTransaction.add(R.id.fragment_container, mineFragment, MINE_FRAGMENT);
    }
    mList.add(mineFragment);
    if (!isRestore) {
      fragmentTransaction.commit();
      show(0);
    }
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
//    commitAllowingStateLoss作用，不需要检查activity的state是否被保存，即不需要检查当前页面是不是进入后台
//    1.如果activity在commitAllowingStateLoss之后没有销毁，不会影响fragmentManager保存fragment的状态
//    2.如果activity在commitAllowingStateLoss之后被销毁了，那么fragmentManager不会保存onSaveInstance之后的提交
//    3.如果commit在destroy之后提交，不管是commitAllowingStateLoss还是commit，直接会报activity is destroy exception。
    fragmentTransaction.commitAllowingStateLoss();
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

  private void showTag(String tag) {
    for (int i = 0; i < mList.size(); i++) {
      Fragment fragment = mList.get(i);
      boolean hidden = fragment.isHidden();
      Log.i(TAG, tag + " i：" + i + "   " + hidden);
    }
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    showTag("onRestart");
  }

  @Override
  protected void onStart() {
    super.onStart();
    showTag("onstart");
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    Log.i(TAG, "onRestoreInstanceState");
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    showTag("onResume");
  }

  @Override
  protected void onPause() {
    Log.i(TAG, "onPause start");
    super.onPause();
    Log.i(TAG, "onPause");
    showTag("onPause");
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    Log.i(TAG, "onSaveInstanceState");
    super.onSaveInstanceState(outState);
    //测试第一步：在onSaveInstanceState之后执行commit操作
    Log.i(TAG, "run");
    show(2);
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.i(TAG, "onStop");
    showTag("onStop");
  }


  @Override
  protected void onDestroy() {
    Log.i(TAG, "onDestroy");
    super.onDestroy();
  }

  public void replaceTest(View view) {
    startActivity(new Intent(this, ReplaceTestActivity.class));
  }
}
