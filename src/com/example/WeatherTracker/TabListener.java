package com.example.WeatherTracker;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by anoopc on 12/2/14.
 */
public class TabListener <T extends Fragment> implements ActionBar.TabListener {
    private Fragment fragment;
    private final Activity activity;
    private final String mTag;
    private final Class<T> mClass;

    public TabListener(Activity activity, String mTag, Class<T> mClass) {
        this.activity = activity;
        this.mTag = mTag;
        this.mClass = mClass;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (this.fragment == null) {
            this.fragment = Fragment.instantiate(this.activity, this.mClass.getName());
            ft.add(R.id.mainContainer, this.fragment, this.mTag);
        } else {
            ft.attach(this.fragment);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.detach(this.fragment);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
