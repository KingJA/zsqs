package com.kingja.zsqs.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.kingja.zsqs.R;
import com.kingja.zsqs.base.IStackActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2020/5/11 0011 下午 3:39
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FragmentSir implements IStackActivity {
    private FragmentActivity fragmentActivity;
    private FragmentManager supportFragmentManager;
    private OnBackStackCountChangedListener onBackStackCountChangedListener;
    private List<Fragment> fragments = new ArrayList<>();
    private String TAG="FragmentSir";

    public FragmentSir(FragmentActivity fragmentActivity,OnBackStackCountChangedListener onBackStackCountChangedListener) {
        this.fragmentActivity = fragmentActivity;
        this.supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        this.onBackStackCountChangedListener = onBackStackCountChangedListener;
    }

    @Override
    public void addStack(Fragment stackFragment) {
        addFragment(stackFragment);
        notifyBackStackCount();
    }

    @Override
    public void addStackAndOutLast(Fragment addFragment, Fragment outFragment) {
        supportFragmentManager.popBackStack(outFragment.getClass().getSimpleName(), 1);
        fragments.remove(outFragment);
        addFragment(addFragment);
        notifyBackStackCount();
    }

    @Override
    public void outStack(Fragment stackFragment) {
        supportFragmentManager.popBackStack(stackFragment.getClass().getSimpleName(), 1);
        fragments.remove(stackFragment);
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.show(fragments.get(fragments.size() - 1)).commitAllowingStateLoss();
        notifyBackStackCount();
    }

    private void addFragment(Fragment stackFragment) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        if (fragments.size() > 0) {
            if (fragments.get(fragments.size() - 1).getClass() == stackFragment.getClass()) {
                Log.e(TAG, "已存在 ");
                return;
            }
            fragmentTransaction.hide(fragments.get(fragments.size() - 1)).add(R.id.rl_content, stackFragment).show(stackFragment);
            fragmentTransaction.addToBackStack(stackFragment.getClass().getSimpleName());
        } else {
            fragmentTransaction.add(R.id.rl_content, stackFragment);
        }
        fragmentTransaction.commit();
        fragments.add(stackFragment);
    }

    private void notifyBackStackCount() {
        supportFragmentManager.executePendingTransactions();
        int backStackEntryCount = supportFragmentManager.getBackStackEntryCount();
        if (onBackStackCountChangedListener != null) {
            onBackStackCountChangedListener.OnBackStackCountChanged(backStackEntryCount);
        }
    }

    public void clearStack() {
        supportFragmentManager.executePendingTransactions();
        for (int i = 0; i < supportFragmentManager.getBackStackEntryCount(); i++) {
            supportFragmentManager.popBackStack();
        }
        for (int i = fragments.size() - 1; i > 0; i--) {
            fragments.remove(i);
        }
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.show(fragments.get(fragments.size() - 1)).commit();
        notifyBackStackCount();
    }

    public interface OnBackStackCountChangedListener {
        void OnBackStackCountChanged(int count);
    }
}
