package com.example.mbenben.studydemo.layout.viewpager.cardviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by MBENBEN on 2017/8/5.
 *
 * 原项目GitHub：https://github.com/crazysunj/CardSlideView
 */

public class CardPagerAdapter extends FragmentStatePagerAdapter {

    private List<CardItem> mCardItems;
    private boolean mIsLoop;

    CardPagerAdapter(FragmentManager fm, List<CardItem> cardItems, boolean isLoop) {
        super(fm);
        mCardItems = cardItems;
        mIsLoop = isLoop;
    }

    @Override
    public Fragment getItem(int position) {
        return mCardItems.get(position);
    }

    @Override
    public int getCount() {
        return mIsLoop ? Integer.MAX_VALUE : getRealCount();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, mIsLoop ? position % getRealCount() : position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mIsLoop) {
            CardViewPager viewPager = (CardViewPager) container;
            int pos = viewPager.getCurrentItem();
            int i = pos % getRealCount();
            int j = position % getRealCount();
            if (j >= i - 2 && j <= i + 2) {
                return;
            }
            super.destroyItem(container, j, object);
            return;
        }
        super.destroyItem(container, position, object);
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
        if (mIsLoop) {
            CardViewPager viewPager = (CardViewPager) container;
            int position = viewPager.getCurrentItem();
            if (position == 0) {
                position = getFristItem();
            } else if (position == getCount() - 1) {
                position = getLastItem();
            }
            viewPager.setCurrentItem(position, false);
        }
    }

    private int getRealCount() {
        return mCardItems == null ? 0 : mCardItems.size();
    }

    private int getFristItem() {
        int realCount = getRealCount();
        return Integer.MAX_VALUE / realCount / 2 * realCount;
    }

    private int getLastItem() {
        int realCount = getRealCount();
        return Integer.MAX_VALUE / realCount / 2 * realCount - 1;
    }
}
