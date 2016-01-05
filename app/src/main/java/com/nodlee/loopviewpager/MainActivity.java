package com.nodlee.loopviewpager;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.viewpagerindicator.CirclePageIndicator;

public class MainActivity extends AppCompatActivity {
    /** 轮播时间间隔 */
    private static final int LOOP_INTERVAL = 3000;

    private ViewPager mGalleryViewPager;
    private GalleryAdapter mAdapter;
    private Handler mHandler;

    private static int[] sPhotos = {
            R.mipmap.pic_1, R.mipmap.pic_2, R.mipmap.pic_3,
            R.mipmap.pic_4, R.mipmap.pic_5

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();

        mAdapter = new GalleryAdapter(getSupportFragmentManager(), sPhotos);
        mGalleryViewPager = (ViewPager) findViewById(R.id.gallery);
        mGalleryViewPager.setAdapter(mAdapter);
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator_gallery);
        circlePageIndicator.setViewPager(mGalleryViewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startLoopTask();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLoopTask();
    }

    private void startLoopTask() {
        mHandler.postDelayed(loopTask, LOOP_INTERVAL);
    }

    /**
     * 跳转至下一个页面
     */
    private void next() {
        int currentPosition = mGalleryViewPager.getCurrentItem();
        if (currentPosition != mAdapter.getCount() - 1) {
            currentPosition += 1;
        } else {
            currentPosition = 0;
        }
        mGalleryViewPager.setCurrentItem(currentPosition, true);
    }

    private void stopLoopTask() {
        mHandler.removeCallbacks(loopTask);
    }

    private Runnable loopTask = new Runnable() {
        @Override
        public void run() {
            next();
            mHandler.postDelayed(this, LOOP_INTERVAL);
        }
    };

    private class GalleryAdapter extends FragmentPagerAdapter {
        private int[] photos;

        public GalleryAdapter(FragmentManager fm, int[] photos) {
            super(fm);
            this.photos = photos;
        }

        @Override
        public Fragment getItem(int position) {
            return PhotoFragment.newInstance(photos[position]);
        }

        @Override
        public int getCount() {
            return photos.length;
        }
    }
}
