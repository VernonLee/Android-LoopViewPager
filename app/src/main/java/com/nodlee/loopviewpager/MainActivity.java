/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Vernon Lee
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
