package com.example.project3app3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ImagesFragment extends Fragment {

    private static final String TAG = "ImagesFragment";

    private ImageView mImagesView;
    private int mCurrIdx = 0;
    private int creationFlag = 0;

    int getShownIndex() {
        return mCurrIdx;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout defined in image_fragment.xml
        // The last parameter is false because the returned view does not need to be attached to the container ViewGroup
        return inflater.inflate(R.layout.image_fragment, container, false);
    }


    // Set up some information about the mImagesView ImageView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImagesView = getView().findViewById(R.id.tvimageView);
        mImagesView.setImageResource(MainActivity.context.getResources().getIdentifier(MainActivity.mImagesArray[mCurrIdx], "drawable", "com.example.project3app3"));
        creationFlag = 1;
    }

    // Show the Image at position newIndex
    void showImageAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= MainActivity.mImagesArray.length)
            return;
        mCurrIdx = newIndex;
        //Flag to indicate mImagesView has been initialized and an image resource can be set
        if(creationFlag > 0)
            mImagesView.setImageResource(MainActivity.context.getResources().getIdentifier(MainActivity.mImagesArray[mCurrIdx], "drawable", "com.example.project3app3"));
    }

}
