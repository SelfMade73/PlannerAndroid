package com.example.myapplication.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter  extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private List<String>    pageTitles = new ArrayList<>();

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.pageTitles.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    public void addFragment(Fragment fragment,String title){
        this.fragments.add(fragment);
        this.pageTitles.add(title);
    }
}
