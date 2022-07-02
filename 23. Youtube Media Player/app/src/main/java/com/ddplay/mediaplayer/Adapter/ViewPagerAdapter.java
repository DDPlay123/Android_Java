package com.ddplay.mediaplayer.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ddplay.mediaplayer.Fragment.FragmentChat;
import com.ddplay.mediaplayer.Fragment.FragmentCommunity;
import com.ddplay.mediaplayer.Fragment.FragmentHome;
import com.ddplay.mediaplayer.Fragment.FragmentInfo;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new FragmentHome();
                break;
            case 1:
                fragment = new FragmentCommunity();
                break;
            case 2:
                fragment = new FragmentChat();
                break;
            default:
                fragment = new FragmentInfo();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
