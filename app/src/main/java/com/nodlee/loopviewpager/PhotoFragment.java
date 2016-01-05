package com.nodlee.loopviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by nodlee on 16-1-2.
 */
public class PhotoFragment extends Fragment {
    private static final String EXTRA_PHOTO_RES_ID = "photo_res_id";
    private ImageView mPhotoIv;

    public static PhotoFragment newInstance(int photoResId) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_PHOTO_RES_ID, photoResId);

        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_photo, container, false);

        mPhotoIv = (ImageView) rootView.findViewById(R.id.img_photo);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int photoResId = getArguments().getInt(EXTRA_PHOTO_RES_ID);
        mPhotoIv.setImageResource(photoResId);
    }
}
